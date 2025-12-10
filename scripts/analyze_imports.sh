#!/bin/bash

# SpiritAtlas Import Health Check Script
# Analyzes Kotlin imports for common issues
# Usage: ./scripts/analyze_imports.sh

set -e

PROJECT_ROOT="/Users/jonathanmallinger/Workspace/SpiritAtlas"
cd "$PROJECT_ROOT"

echo "=========================================="
echo "  SpiritAtlas Import Health Check"
echo "=========================================="
echo ""

# 1. Wildcard imports
echo "📦 WILDCARD IMPORTS"
echo "-------------------"
WILDCARD_COUNT=$(grep -r "import.*\.\*" . --include="*.kt" 2>/dev/null | wc -l | tr -d ' ')
WILDCARD_FILES=$(grep -r "import.*\.\*" . --include="*.kt" -l 2>/dev/null | wc -l | tr -d ' ')
echo "Total wildcard imports: $WILDCARD_COUNT"
echo "Files with wildcards: $WILDCARD_FILES"
echo ""

echo "Top wildcard patterns:"
grep -r "import.*\.\*" . --include="*.kt" 2>/dev/null | sed 's/.*import //' | sort | uniq -c | sort -rn | head -10
echo ""

# 2. Material conflicts
echo "🎨 MATERIAL THEME CONFLICTS"
echo "---------------------------"
MATERIAL2_COUNT=$(grep -r "import androidx.compose.material\.MaterialTheme" . --include="*.kt" 2>/dev/null | wc -l | tr -d ' ')
MATERIAL3_COUNT=$(grep -r "import androidx.compose.material3\.MaterialTheme" . --include="*.kt" 2>/dev/null | wc -l | tr -d ' ')
echo "Material2 (old): $MATERIAL2_COUNT files"
echo "Material3 (new): $MATERIAL3_COUNT files"

if [ "$MATERIAL2_COUNT" -gt 0 ]; then
    echo "⚠️  WARNING: Material2 imports found! Should use Material3."
    grep -r "import androidx.compose.material\.MaterialTheme" . --include="*.kt" -l 2>/dev/null
else
    echo "✅ No Material2 conflicts"
fi
echo ""

# 3. Duplicate imports
echo "🔄 DUPLICATE IMPORTS (within files)"
echo "-----------------------------------"
DUPLICATES=$(find . -name "*.kt" -type f -exec awk '
/^import / {
    if (seen[$0]++) {
        print FILENAME ":" NR ":" $0
    }
}
/^package / || /^$/ || /^class / || /^fun / || /^@/ || /^interface / || /^object / || /^data / || /^sealed / || /^enum / {
    delete seen
}
' {} + 2>/dev/null)

if [ -z "$DUPLICATES" ]; then
    echo "✅ No duplicate imports found"
else
    echo "⚠️  Duplicates found:"
    echo "$DUPLICATES"
fi
echo ""

# 4. R class references
echo "🆁 R CLASS REFERENCES"
echo "--------------------"
echo "R class imports found:"
grep -r "import.*\.R$" . --include="*.kt" 2>/dev/null || echo "None found"
echo ""

# 5. Logging imports
echo "📝 LOGGING CONSISTENCY"
echo "---------------------"
ANDROID_LOG=$(grep -r "import android.util.Log" . --include="*.kt" -l 2>/dev/null | wc -l | tr -d ' ')
TIMBER_LOG=$(grep -r "import timber.log.Timber" . --include="*.kt" -l 2>/dev/null | wc -l | tr -d ' ')
echo "android.util.Log: $ANDROID_LOG files"
echo "timber.log.Timber: $TIMBER_LOG files"

if [ "$ANDROID_LOG" -gt 0 ] && [ "$TIMBER_LOG" -gt 0 ]; then
    echo "ℹ️  Mixed logging - consider standardizing on Timber"
fi
echo ""

# 6. Module breakdown
echo "📊 WILDCARD IMPORTS BY MODULE"
echo "-----------------------------"
for module in "core/ui" "feature" "data" "domain" "app"; do
    if [ -d "$module" ]; then
        count=$(grep -r "import.*\.\*" "$module" --include="*.kt" 2>/dev/null | wc -l | tr -d ' ')
        printf "%-15s %s\n" "$module:" "$count wildcards"
    fi
done
echo ""

# 7. Domain model wildcards
echo "🎯 DOMAIN MODEL WILDCARDS"
echo "-------------------------"
DOMAIN_WILDCARDS=$(grep -r "^import com.spiritatlas.domain.model.\*" . --include="*.kt" -l 2>/dev/null | wc -l | tr -d ' ')
echo "Files using domain.model.*: $DOMAIN_WILDCARDS"
echo ""

# 8. Unused imports check (requires compilation)
echo "🧹 COMPILE CHECK"
echo "---------------"
echo "Running quick compile to check for import issues..."
if ./gradlew :app:compileDebugKotlin --console=plain 2>&1 | grep -q "BUILD SUCCESSFUL"; then
    echo "✅ Compilation successful - no unresolved imports"
else
    echo "⚠️  Compilation issues detected - check gradle output"
fi
echo ""

# Summary
echo "=========================================="
echo "  SUMMARY"
echo "=========================================="
echo ""

ISSUES=0

if [ "$MATERIAL2_COUNT" -gt 0 ]; then
    echo "❌ Material2/Material3 conflict detected"
    ISSUES=$((ISSUES + 1))
fi

if [ -n "$DUPLICATES" ]; then
    echo "❌ Duplicate imports detected"
    ISSUES=$((ISSUES + 1))
fi

if [ "$WILDCARD_COUNT" -gt 300 ]; then
    echo "⚠️  High wildcard import count ($WILDCARD_COUNT) - consider explicit imports"
fi

if [ "$ISSUES" -eq 0 ]; then
    echo "✅ ALL CHECKS PASSED"
    echo ""
    echo "Import health: GOOD"
    echo "Recommended action: None (optional: reduce wildcard imports)"
else
    echo "⚠️  $ISSUES ISSUE(S) FOUND"
    echo ""
    echo "Import health: NEEDS ATTENTION"
    echo "Recommended action: Review issues above"
fi

echo ""
echo "=========================================="
echo "Report generated: $(date)"
echo "=========================================="
