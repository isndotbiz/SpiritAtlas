# Archived Code

This directory contains code that has been removed from the active codebase but preserved for reference, documentation, or potential future use.

## Purpose

Archived code serves several purposes:
- **Reference** - Example implementations that demonstrate API usage
- **Documentation** - Code samples that explain complex features
- **Recovery** - Functional code that may be needed in the future
- **History** - Preservation of development artifacts

## Directory Structure

```
archived_code/
├── example_files/          # Demo and showcase code
├── stub_implementations/   # Incomplete or placeholder code
└── unused_providers/       # Alternative implementations (AI providers)
```

## Guidelines

### When to Archive vs. Delete

**Archive if:**
- Code demonstrates useful patterns or APIs
- Implementation is functional but not currently needed
- May be needed for future features
- Provides valuable documentation

**Delete if:**
- Code is broken or outdated
- Relies on deprecated dependencies
- Serves no documentation or reference purpose
- Contains security vulnerabilities

### Using Archived Code

Archived code is NOT imported or compiled. To use archived code:

1. **Review** - Check if the code is still relevant and compatible
2. **Copy** - Copy the file to the appropriate active directory
3. **Update** - Modify imports, dependencies, API calls as needed
4. **Test** - Ensure the code works with current codebase
5. **Document** - Update documentation to reflect the restoration

## Archive Inventory

### Example Files (8 files)

Demo and showcase code for various UI components and features.

| File | Lines | Description | Last Known Status |
|------|-------|-------------|-------------------|
| CouplesAnalysisUsageExample.kt | 208 | EnhancedCouplesAnalysisEngine API demo | Working |
| GradientTextExamples.kt | 504 | All gradient text effects showcase | Working |
| NavTransitionsExamples.kt | 383 | Navigation transition patterns | Working |
| TransitionPreview.kt | ~150 | Preview transition animations | Working |
| ErrorComponentsExample.kt | ~150 | Error component demos | Working |
| SpiritualBottomSheetsExample.kt | ~200 | Bottom sheet examples | Working |
| SpiritualIconsExample.kt | ~180 | Icon showcase | Working |
| SpiritualIconsExtendedExample.kt | ~250 | Extended icon examples | Working |

### Stub Implementations (2 files)

Incomplete or placeholder implementations.

| File | Lines | Description | Status |
|------|-------|-------------|--------|
| SpiritualPullRefreshIntegration.kt | 41 | Pull-to-refresh integration | Stub/Incomplete |
| TooltipsAndCoachMarks.kt | 25 | Onboarding tooltips | Stub/Incomplete |

**Note:** Working pull-to-refresh exists in `SpiritualPullRefresh.kt` in active codebase.

### Unused Providers (3 files)

Alternative AI provider implementations that are functional but not actively used.

| File | Lines | Model | Status | Notes |
|------|-------|-------|--------|-------|
| GeminiProvider.kt | 140 | Google Gemini 2.0 Flash | Functional | Excellent reasoning, 1M context |
| GroqProvider.kt | 166 | Llama 3.3 70B | Functional | Ultra-fast (800+ tok/s) |
| OpenAIProvider.kt | 146 | GPT-4o / GPT-4o-mini | Functional | Industry standard, user API key required |

**Note:** These providers ARE still in the active codebase but copied here for reference. They are injected into `CombinedAiProvider` but not actively used unless explicitly configured.

## Restoration Checklist

If you need to restore archived code:

- [ ] Identify the archived file to restore
- [ ] Check dependencies and imports
- [ ] Copy to appropriate active directory
- [ ] Update package declarations if needed
- [ ] Update imports and dependencies
- [ ] Run tests to verify functionality
- [ ] Update documentation
- [ ] Remove from archive (optional)

## Maintenance

This archive should be reviewed periodically:

- **Quarterly** - Review for outdated or unnecessary files
- **Major Version** - Clean up archives incompatible with new versions
- **Dependency Updates** - Flag archives that rely on deprecated libraries

## Related Documentation

- [DEAD_CODE_REPORT.md](../DEAD_CODE_REPORT.md) - Complete analysis of dead code elimination
- [CLAUDE.md](../CLAUDE.md) - Project coding conventions and guidelines
- [docs/](../docs/) - Additional technical documentation

## Archive Date

**Initial Archive:** 2025-12-10
**Last Updated:** 2025-12-10
**Archived By:** OPTIMIZATION AGENT 2: Dead Code Eliminator

## Questions?

If you're unsure whether to use archived code, consult:
1. The DEAD_CODE_REPORT.md for context on why it was archived
2. The code itself for compatibility with current APIs
3. Team leads or senior developers for architectural guidance

---

**Remember:** Archived code is frozen in time. Always review and update before integrating into active development.
