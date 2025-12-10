# Optimization Final Plan - SpiritAtlas
**Date:** 2025-12-10  
**Owner:** Performance/Packaging

---

## Quick Wins (Do Next)
1) **Fix ART profile wildcard crash** (`expandReleaseArtProfileWildcards` index error) so release can build; then re-run R8.  
2) **Vectorize simple icons** (zodiac glyphs, dropdown/info/error where feasible) to save ~200-300 KB across densities; keep photorealistic assets as WebP.  
3) **Enable full shrinker for release** (R8 + resource shrink + minify); ensure `isMinifyEnabled=true` and `shrinkResources=true` in `release` block, and supply consumer rules where referenced.  
4) **Remove density splits** (already deprecated) and rely on App Bundle + Play delivery; strip PNG fallbacks (now migrated to WebP).  
5) **Room schema warning**: set `room.schemaLocation` (via `id("androidx.room")` plugin) or `exportSchema=false` to cut annotation overhead noise.

## Size Targets
- Release APK <20 MB (current debug 117 MB; shrinker + proguard + vectorization should reduce drastically).
- Image budget ~25 MB (already WebP); avoid embedding raw assets in app module.

## Follow-ups
- Add **baseline profile** refresh after image moves to optimize startup.  
- Consider **ABI splits** (ARM64-only) to trim native libs if needed.  
- Re-run `./gradlew :app:assembleRelease` after fixes and record size + mapping files.
