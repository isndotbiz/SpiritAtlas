#!/usr/bin/env python3
"""
Parse JaCoCo XML coverage reports and generate comprehensive analysis.

This script:
1. Parses all JaCoCo XML reports in the project
2. Calculates coverage percentages for each module
3. Identifies files with low coverage
4. Generates JSON data for dashboard
5. Creates coverage summary
"""

import xml.etree.ElementTree as ET
import json
import sys
from pathlib import Path
from dataclasses import dataclass, asdict
from typing import List, Dict
from datetime import datetime


@dataclass
class CoverageCounter:
    """Coverage counter for a specific metric"""
    type: str
    missed: int
    covered: int

    @property
    def total(self) -> int:
        return self.missed + self.covered

    @property
    def percentage(self) -> float:
        if self.total == 0:
            return 0.0
        return (self.covered / self.total) * 100


@dataclass
class SourceFileCoverage:
    """Coverage data for a single source file"""
    name: str
    line_coverage: CoverageCounter
    branch_coverage: CoverageCounter
    instruction_coverage: CoverageCounter

    @property
    def line_percentage(self) -> float:
        return self.line_coverage.percentage

    @property
    def meets_threshold(self) -> bool:
        return self.line_percentage >= 80.0


@dataclass
class ModuleCoverage:
    """Coverage data for a module"""
    name: str
    line_coverage: CoverageCounter
    branch_coverage: CoverageCounter
    instruction_coverage: CoverageCounter
    files: List[SourceFileCoverage]

    @property
    def line_percentage(self) -> float:
        return self.line_coverage.percentage

    @property
    def branch_percentage(self) -> float:
        return self.branch_coverage.percentage

    @property
    def meets_threshold(self) -> bool:
        return self.line_percentage >= 80.0

    @property
    def low_coverage_files(self) -> List[SourceFileCoverage]:
        """Files with < 80% line coverage"""
        return [f for f in self.files if not f.meets_threshold]


def parse_counter(element) -> CoverageCounter:
    """Parse a counter element from XML"""
    counter_type = element.get('type')
    missed = int(element.get('missed', 0))
    covered = int(element.get('covered', 0))
    return CoverageCounter(counter_type, missed, covered)


def parse_sourcefile(sourcefile_elem) -> SourceFileCoverage:
    """Parse a sourcefile element from XML"""
    name = sourcefile_elem.get('name')
    counters = {
        counter.get('type'): parse_counter(counter)
        for counter in sourcefile_elem.findall('counter')
    }

    return SourceFileCoverage(
        name=name,
        line_coverage=counters.get('LINE', CoverageCounter('LINE', 0, 0)),
        branch_coverage=counters.get('BRANCH', CoverageCounter('BRANCH', 0, 0)),
        instruction_coverage=counters.get('INSTRUCTION', CoverageCounter('INSTRUCTION', 0, 0))
    )


def parse_module_report(xml_path: Path, module_name: str) -> ModuleCoverage:
    """Parse a JaCoCo XML report for a module"""
    try:
        tree = ET.parse(xml_path)
        root = tree.getroot()

        # Aggregate counters from all packages
        line_missed, line_covered = 0, 0
        branch_missed, branch_covered = 0, 0
        instruction_missed, instruction_covered = 0, 0
        files = []

        for package in root.findall('.//package'):
            # Parse source files
            for sourcefile in package.findall('sourcefile'):
                files.append(parse_sourcefile(sourcefile))

            # Aggregate package counters
            for counter in package.findall('counter'):
                counter_type = counter.get('type')
                missed = int(counter.get('missed', 0))
                covered = int(counter.get('covered', 0))

                if counter_type == 'LINE':
                    line_missed += missed
                    line_covered += covered
                elif counter_type == 'BRANCH':
                    branch_missed += missed
                    branch_covered += covered
                elif counter_type == 'INSTRUCTION':
                    instruction_missed += missed
                    instruction_covered += covered

        return ModuleCoverage(
            name=module_name,
            line_coverage=CoverageCounter('LINE', line_missed, line_covered),
            branch_coverage=CoverageCounter('BRANCH', branch_missed, branch_covered),
            instruction_coverage=CoverageCounter('INSTRUCTION', instruction_missed, instruction_covered),
            files=files
        )

    except Exception as e:
        print(f"Error parsing {xml_path}: {e}", file=sys.stderr)
        return None


def find_coverage_reports(project_root: Path) -> List[tuple]:
    """Find all JaCoCo XML reports in the project"""
    reports = []

    # Core modules (JVM)
    core_modules = ['numerology', 'astro', 'ayurveda', 'humandesign']
    for module in core_modules:
        xml_path = project_root / 'core' / module / 'build' / 'reports' / 'jacoco' / 'test' / 'jacocoTestReport.xml'
        if xml_path.exists():
            reports.append((f'core:{module}', xml_path))

    # Domain module (JVM)
    domain_xml = project_root / 'domain' / 'build' / 'reports' / 'jacoco' / 'test' / 'jacocoTestReport.xml'
    if domain_xml.exists():
        reports.append(('domain', domain_xml))

    # Android modules
    android_modules = [
        ('data', 'data'),
        ('feature:compatibility', 'feature/compatibility'),
        ('feature:home', 'feature/home'),
        ('feature:profile', 'feature/profile'),
        ('feature:settings', 'feature/settings'),
    ]

    for module_name, module_path in android_modules:
        xml_path = project_root / module_path / 'build' / 'reports' / 'jacoco' / 'jacocoTestReport' / 'jacocoTestReport.xml'
        if xml_path.exists():
            reports.append((module_name, xml_path))

    return reports


def generate_summary(modules: List[ModuleCoverage]) -> Dict:
    """Generate coverage summary"""
    total_line_missed = sum(m.line_coverage.missed for m in modules)
    total_line_covered = sum(m.line_coverage.covered for m in modules)
    total_branch_missed = sum(m.branch_coverage.missed for m in modules)
    total_branch_covered = sum(m.branch_coverage.covered for m in modules)

    modules_passing = sum(1 for m in modules if m.meets_threshold)
    modules_total = len(modules)

    return {
        'timestamp': datetime.now().isoformat(),
        'total_line_coverage': (total_line_covered / (total_line_covered + total_line_missed) * 100) if (total_line_covered + total_line_missed) > 0 else 0,
        'total_branch_coverage': (total_branch_covered / (total_branch_covered + total_branch_missed) * 100) if (total_branch_covered + total_branch_missed) > 0 else 0,
        'modules_passing_80': modules_passing,
        'modules_total': modules_total,
        'modules': [
            {
                'name': m.name,
                'line_coverage': round(m.line_percentage, 2),
                'branch_coverage': round(m.branch_percentage, 2),
                'meets_threshold': m.meets_threshold,
                'low_coverage_files_count': len(m.low_coverage_files)
            }
            for m in modules
        ]
    }


def print_text_report(modules: List[ModuleCoverage]):
    """Print human-readable coverage report"""
    print("\n" + "=" * 70)
    print("  SpiritAtlas Test Coverage Report")
    print("=" * 70 + "\n")

    # Overall summary
    total_line_missed = sum(m.line_coverage.missed for m in modules)
    total_line_covered = sum(m.line_coverage.covered for m in modules)
    total_line_coverage = (total_line_covered / (total_line_covered + total_line_missed) * 100) if (total_line_covered + total_line_missed) > 0 else 0

    print(f"Overall Line Coverage: {total_line_coverage:.1f}%")
    print(f"Modules Meeting 80% Threshold: {sum(1 for m in modules if m.meets_threshold)}/{len(modules)}")
    print()

    # Module details
    print("Module Coverage:")
    print("-" * 70)
    print(f"{'Module':<30} {'Line %':>10} {'Branch %':>10} {'Status':>12}")
    print("-" * 70)

    for module in sorted(modules, key=lambda m: m.line_percentage, reverse=True):
        status = "✓ PASS" if module.meets_threshold else "✗ FAIL"
        status_color = "\033[92m" if module.meets_threshold else "\033[91m"
        reset_color = "\033[0m"

        print(f"{module.name:<30} {module.line_percentage:>9.1f}% {module.branch_percentage:>9.1f}% {status_color}{status:>12}{reset_color}")

    print("-" * 70)
    print()

    # Low coverage files
    low_coverage_modules = [m for m in modules if m.low_coverage_files]
    if low_coverage_modules:
        print("Files with < 80% Coverage:")
        print("-" * 70)

        for module in low_coverage_modules:
            print(f"\n{module.name}:")
            for file in sorted(module.low_coverage_files, key=lambda f: f.line_percentage):
                print(f"  {file.name:<50} {file.line_percentage:>6.1f}%")

        print()

    print("=" * 70 + "\n")


def main():
    """Main entry point"""
    project_root = Path(__file__).parent.parent.parent
    reports = find_coverage_reports(project_root)

    if not reports:
        print("No coverage reports found. Run tests first:", file=sys.stderr)
        print("  ./gradlew test", file=sys.stderr)
        sys.exit(1)

    print(f"Found {len(reports)} coverage reports")

    # Parse all reports
    modules = []
    for module_name, xml_path in reports:
        print(f"Parsing {module_name}...")
        coverage = parse_module_report(xml_path, module_name)
        if coverage:
            modules.append(coverage)

    if not modules:
        print("Failed to parse any coverage reports", file=sys.stderr)
        sys.exit(1)

    # Generate summary
    summary = generate_summary(modules)

    # Save JSON data
    json_path = project_root / 'build' / 'reports' / 'coverage_summary.json'
    json_path.parent.mkdir(parents=True, exist_ok=True)
    with open(json_path, 'w') as f:
        json.dump(summary, f, indent=2)

    print(f"\nCoverage data saved to: {json_path}")

    # Print text report
    print_text_report(modules)

    # Exit with error if coverage < 80%
    if summary['total_line_coverage'] < 80.0:
        print(f"\033[91m✗ Overall coverage ({summary['total_line_coverage']:.1f}%) is below 80% threshold\033[0m")
        sys.exit(1)
    else:
        print(f"\033[92m✓ Overall coverage ({summary['total_line_coverage']:.1f}%) meets 80% threshold\033[0m")


if __name__ == '__main__':
    main()
