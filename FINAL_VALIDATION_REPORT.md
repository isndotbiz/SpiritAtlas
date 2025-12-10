# Final Validation Report - SpiritAtlas (Phase 2)
**Date:** 2025-12-10  
**Owner:** Release Validation  
**Status:** ⚠️ Blocked on release artifact (debug build OK)

---

## Build & Packaging
- `./gradlew assembleDebug` ✅ (warnings only)
- `./gradlew :app:assembleRelease` ❌ `expandReleaseArtProfileWildcards` -> `Index 61 out of bounds for length 61`
  - Likely malformed ART profile wildcard; rerun with `--stacktrace --info` to locate offending rule.
  - Also saw missing `feature/compatibility/consumer-rules.pro` reference during release merge.

## Artifact Size
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk` = **117 MB** (includes debug symbols, no R8 shrink).
- Release APK: **not produced** (blocked above). Target should be <20 MB once shrinker/signing fixed.

## Performance Signals (from existing perf report)
- Image payload: ~25 MB across 265 WebP assets (good baseline).
- Startup target: <2.5s (not measured this run; prior report flagged blocked by build).
- Scroll/FPS: assumed 60 FPS; no regressions observed in code audit.

## Warnings Observed
- Room: schema export warning (`room.schemaLocation` not set) during release compile.
- Numerous unused-parameter warnings in navigation/UI modules (non-blocking).
- Unable to strip `libandroidx.graphics.path.so` (packaged as-is).

## Immediate Actions to Unblock Release
1) Inspect ART profile rules: `./gradlew :app:assembleRelease --stacktrace --info` to find the wildcard causing `Index 61` error; trim/replace offending pattern in `baseline-prof.txt` or related profile file.  
2) Provide/adjust `feature/compatibility/consumer-rules.pro` or remove the reference if unneeded.  
3) Set Room schema export (apply `id("androidx.room")` and set `schemaDirectory` or `exportSchema=false`).  
4) Re-run `./gradlew :app:assembleRelease` and capture APK size; enable R8 shrinking/obfuscation for release if not already active.  

## Validation Exit Criteria (pending)
- ✅ Release build completes
- ✅ APK size <20 MB (with shrinker/optimizations)
- ✅ Startup time <2.5s (instrumentation or baseline profile)
- ✅ 60 FPS scroll on main surfaces (Recycler/LazyColumn traces)
