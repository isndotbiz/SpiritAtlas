#!/usr/bin/env python3
"""
Generate HTML coverage dashboard from coverage summary JSON.
"""

import json
import sys
from pathlib import Path
from datetime import datetime


HTML_TEMPLATE = '''<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SpiritAtlas Test Coverage Dashboard</title>
    <style>
        :root {{{{
            --primary: #6366f1;
            --success: #22c55e;
            --warning: #f59e0b;
            --danger: #ef4444;
            --bg-dark: #0f172a;
            --bg-card: #1e293b;
            --text: #e2e8f0;
            --text-muted: #94a3b8;
        }}}}

        * {{
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }}

        body {{
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background: var(--bg-dark);
            color: var(--text);
            line-height: 1.6;
            padding: 2rem;
        }}

        .container {{
            max-width: 1200px;
            margin: 0 auto;
        }}

        header {{
            text-align: center;
            margin-bottom: 3rem;
            padding-bottom: 2rem;
            border-bottom: 2px solid var(--bg-card);
        }}

        h1 {{
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
            background: linear-gradient(135deg, var(--primary), #8b5cf6);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }}

        .timestamp {{
            color: var(--text-muted);
            font-size: 0.9rem;
        }}

        .stats-grid {{
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-bottom: 3rem;
        }}

        .stat-card {{
            background: var(--bg-card);
            border-radius: 1rem;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }}

        .stat-label {{
            color: var(--text-muted);
            font-size: 0.875rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            margin-bottom: 0.5rem;
        }}

        .stat-value {{
            font-size: 2.5rem;
            font-weight: bold;
            margin-bottom: 0.5rem;
        }}

        .stat-value.success {{
            color: var(--success);
        }}

        .stat-value.warning {{
            color: var(--warning);
        }}

        .stat-value.danger {{
            color: var(--danger);
        }}

        .progress-bar {{
            height: 8px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 4px;
            overflow: hidden;
            margin-top: 1rem;
        }}

        .progress-fill {{
            height: 100%;
            background: linear-gradient(90deg, var(--primary), #8b5cf6);
            transition: width 0.3s ease;
        }}

        .modules-table {{
            background: var(--bg-card);
            border-radius: 1rem;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            overflow-x: auto;
        }}

        table {{
            width: 100%;
            border-collapse: collapse;
        }}

        th {{
            text-align: left;
            padding: 1rem;
            color: var(--text-muted);
            font-weight: 600;
            border-bottom: 2px solid rgba(255, 255, 255, 0.1);
        }}

        td {{
            padding: 1rem;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }}

        tr:hover {{
            background: rgba(255, 255, 255, 0.02);
        }}

        .module-name {{
            font-weight: 600;
            font-family: 'Monaco', 'Menlo', monospace;
        }}

        .coverage-badge {{
            display: inline-flex;
            align-items: center;
            padding: 0.25rem 0.75rem;
            border-radius: 9999px;
            font-size: 0.875rem;
            font-weight: 600;
        }}

        .coverage-badge.success {{
            background: rgba(34, 197, 94, 0.1);
            color: var(--success);
        }}

        .coverage-badge.warning {{
            background: rgba(245, 158, 11, 0.1);
            color: var(--warning);
        }}

        .coverage-badge.danger {{
            background: rgba(239, 68, 68, 0.1);
            color: var(--danger);
        }}

        .status-icon {{
            margin-left: 0.5rem;
        }}

        .chart-container {{
            background: var(--bg-card);
            border-radius: 1rem;
            padding: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-bottom: 2rem;
        }}

        .chart-title {{
            font-size: 1.25rem;
            margin-bottom: 1.5rem;
            font-weight: 600;
        }}

        .bar-chart {{
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }}

        .bar-row {{
            display: flex;
            align-items: center;
            gap: 1rem;
        }}

        .bar-label {{
            min-width: 150px;
            font-size: 0.875rem;
            font-family: 'Monaco', 'Menlo', monospace;
        }}

        .bar {{
            flex: 1;
            height: 32px;
            background: rgba(255, 255, 255, 0.05);
            border-radius: 4px;
            overflow: hidden;
            position: relative;
        }}

        .bar-fill {{
            height: 100%;
            background: linear-gradient(90deg, var(--primary), #8b5cf6);
            display: flex;
            align-items: center;
            justify-content: flex-end;
            padding-right: 0.5rem;
            font-size: 0.875rem;
            font-weight: 600;
            transition: width 0.5s ease;
        }}

        .threshold-line {{
            position: absolute;
            left: 80%;
            top: 0;
            bottom: 0;
            width: 2px;
            background: var(--warning);
            opacity: 0.5;
        }}

        footer {{
            text-align: center;
            margin-top: 3rem;
            padding-top: 2rem;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
            color: var(--text-muted);
            font-size: 0.875rem;
        }}
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>🌟 SpiritAtlas Test Coverage Dashboard</h1>
            <p class="timestamp">Generated: {timestamp}</p>
        </header>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Overall Line Coverage</div>
                <div class="stat-value {overall_status}">{overall_coverage:.1f}%</div>
                <div class="progress-bar">
                    <div class="progress-fill" style="width: {overall_coverage}%"></div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-label">Overall Branch Coverage</div>
                <div class="stat-value {branch_status}">{branch_coverage:.1f}%</div>
                <div class="progress-bar">
                    <div class="progress-fill" style="width: {branch_coverage}%"></div>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-label">Modules Passing (≥80%)</div>
                <div class="stat-value {modules_status}">{modules_passing}/{modules_total}</div>
                <p class="stat-label" style="margin-top: 0.5rem;">
                    {modules_percentage:.0f}% modules passing
                </p>
            </div>
        </div>

        <div class="chart-container">
            <h2 class="chart-title">Module Coverage</h2>
            <div class="bar-chart">
                {module_bars}
            </div>
        </div>

        <div class="modules-table">
            <h2 class="chart-title">Module Details</h2>
            <table>
                <thead>
                    <tr>
                        <th>Module</th>
                        <th>Line Coverage</th>
                        <th>Branch Coverage</th>
                        <th>Files < 80%</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    {module_rows}
                </tbody>
            </table>
        </div>

        <footer>
            <p>Generated by SpiritAtlas Coverage Monitor</p>
            <p>Threshold: 80% line coverage per module</p>
        </footer>
    </div>
</body>
</html>
'''


def get_status_class(percentage: float) -> str:
    """Get CSS class based on coverage percentage"""
    if percentage >= 80:
        return 'success'
    elif percentage >= 60:
        return 'warning'
    else:
        return 'danger'


def generate_module_bars(modules: list) -> str:
    """Generate HTML for module coverage bars"""
    bars = []
    for module in sorted(modules, key=lambda m: m['line_coverage'], reverse=True):
        coverage = module['line_coverage']
        status = get_status_class(coverage)

        bar_html = f'''
                <div class="bar-row">
                    <div class="bar-label">{module['name']}</div>
                    <div class="bar">
                        <div class="threshold-line"></div>
                        <div class="bar-fill" style="width: {coverage}%">{coverage:.1f}%</div>
                    </div>
                </div>'''
        bars.append(bar_html)

    return ''.join(bars)


def generate_module_rows(modules: list) -> str:
    """Generate HTML table rows for modules"""
    rows = []
    for module in sorted(modules, key=lambda m: m['line_coverage'], reverse=True):
        line_coverage = module['line_coverage']
        branch_coverage = module['branch_coverage']
        meets_threshold = module['meets_threshold']
        low_files = module['low_coverage_files_count']

        line_status = get_status_class(line_coverage)
        branch_status = get_status_class(branch_coverage)
        status_badge = 'success' if meets_threshold else 'danger'
        status_icon = '✓' if meets_threshold else '✗'

        row_html = f'''
                    <tr>
                        <td class="module-name">{module['name']}</td>
                        <td><span class="coverage-badge {line_status}">{line_coverage:.1f}%</span></td>
                        <td><span class="coverage-badge {branch_status}">{branch_coverage:.1f}%</span></td>
                        <td>{low_files}</td>
                        <td><span class="coverage-badge {status_badge}">{status_icon} {'PASS' if meets_threshold else 'FAIL'}</span></td>
                    </tr>'''
        rows.append(row_html)

    return ''.join(rows)


def main():
    """Main entry point"""
    project_root = Path(__file__).parent.parent.parent
    json_path = project_root / 'build' / 'reports' / 'coverage_summary.json'

    if not json_path.exists():
        print(f"Coverage summary not found: {json_path}", file=sys.stderr)
        print("Run: python3 scripts/coverage/parse_coverage.py", file=sys.stderr)
        sys.exit(1)

    # Load coverage data
    with open(json_path) as f:
        data = json.load(f)

    # Generate HTML
    overall_coverage = data['total_line_coverage']
    branch_coverage = data['total_branch_coverage']
    modules_passing = data['modules_passing_80']
    modules_total = data['modules_total']
    modules_percentage = (modules_passing / modules_total * 100) if modules_total > 0 else 0

    html = HTML_TEMPLATE.format(
        timestamp=datetime.fromisoformat(data['timestamp']).strftime('%Y-%m-%d %H:%M:%S'),
        overall_coverage=overall_coverage,
        overall_status=get_status_class(overall_coverage),
        branch_coverage=branch_coverage,
        branch_status=get_status_class(branch_coverage),
        modules_passing=modules_passing,
        modules_total=modules_total,
        modules_percentage=modules_percentage,
        modules_status='success' if modules_passing == modules_total else 'warning',
        module_bars=generate_module_bars(data['modules']),
        module_rows=generate_module_rows(data['modules'])
    )

    # Save HTML
    html_path = project_root / 'build' / 'reports' / 'coverage_dashboard.html'
    with open(html_path, 'w') as f:
        f.write(html)

    print(f"Coverage dashboard generated: {html_path}")
    print(f"Open in browser: file://{html_path}")


if __name__ == '__main__':
    main()
