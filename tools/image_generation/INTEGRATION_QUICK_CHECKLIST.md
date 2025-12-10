# SpiritAtlas Image Integration - Quick Checklist

**Use this checklist to track progress during implementation**

---

## Pre-Integration Setup

- [ ] Review `DETAILED_INTEGRATION_PLAN.md`
- [ ] Confirm FLUX 1.1 Pro access or alternative image generation method
- [ ] Set up image generation environment
- [ ] Test `optimize_for_android.py` script with sample image
- [ ] Create git branch: `feature/image-integration`

---

## Phase 1: Critical Assets (Week 1)

### Image Generation
- [ ] Generate App Branding images (8)
- [ ] Generate Background images (15)
- [ ] Generate Primary UI buttons (3)
- [ ] **Total Phase 1: 26 images**

### Optimization
- [ ] Run optimization script on Phase 1 images
- [ ] Verify WebP conversion successful
- [ ] Check all density folders created
- [ ] Generate launcher icon mipmaps
- [ ] Verify file sizes acceptable

### Code Setup
- [ ] Create `/core/ui/src/main/java/com/spiritatlas/core/ui/resources/SpiritualResources.kt`
- [ ] Create `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ImageBackgrounds.kt`
- [ ] Update `AndroidManifest.xml` with new launcher icon

### Screen Updates
- [ ] Update `SplashScreen.kt` with image background
- [ ] Update `HomeScreen.kt` with image background
- [ ] Test app launch on emulator
- [ ] Test app launch on physical device

### Testing
- [ ] Verify launcher icon appears correctly
- [ ] Verify splash screen loads without delay
- [ ] Verify no crashes on image load
- [ ] Check memory usage in Android Profiler
- [ ] Test on multiple densities (xhdpi, xxhdpi, xxxhdpi)

---

## Phase 2: Core Features (Week 2)

### Image Generation
- [ ] Generate Zodiac images (12)
- [ ] Generate Chakra images (7)
- [ ] Generate Moon phase images (8)
- [ ] Generate Avatar images (10)
- [ ] **Total Phase 2: 37 images**

### Optimization
- [ ] Run optimization script on Phase 2 images
- [ ] Verify all zodiac images optimized
- [ ] Verify all chakra images optimized
- [ ] Check moon phase transparency
- [ ] Check avatar image quality

### Code Creation
- [ ] Create `AvatarComponents.kt`
- [ ] Create `ChakraVisualization.kt`
- [ ] Create `ZodiacImageComponents.kt`
- [ ] Update `SpiritualResources.kt` with Phase 2 mappings
- [ ] Add unit tests for resource mappings

### Screen Updates
- [ ] Update `HomeScreen.kt` - add moon phase image
- [ ] Update `HomeScreen.kt` - add zodiac icon
- [ ] Update `ProfileScreen.kt` - add avatar selector
- [ ] Update `ProfileLibraryScreen.kt` - display avatars on cards
- [ ] Update `ProfileDetailScreen.kt` - add zodiac display
- [ ] Update `ProfileDetailScreen.kt` - add chakra section
- [ ] Update `CompatibilityDetailScreen.kt` - add chakra comparison
- [ ] Update `CompatibilityDetailScreen.kt` - add zodiac synastry

### Testing
- [ ] Test avatar selection in profile creation
- [ ] Test avatar display in profile cards
- [ ] Test zodiac images load for all 12 signs
- [ ] Test chakra visualization displays all 7 chakras
- [ ] Test moon phase changes correctly
- [ ] Verify no memory leaks with multiple profiles

---

## Phase 3: Visual Polish (Week 3)

### Image Generation
- [ ] Generate Sacred Geometry images (8)
- [ ] Generate Element images (5)
- [ ] Generate Spiritual Symbol images (8)
- [ ] Generate remaining UI elements (9)
- [ ] Generate Onboarding illustrations (6)
- [ ] **Total Phase 3: 36 images**

### Optimization
- [ ] Run optimization script on Phase 3 images
- [ ] Verify sacred geometry transparency
- [ ] Check element image quality
- [ ] Verify symbol images optimized
- [ ] Check onboarding illustration sizes

### Code Updates
- [ ] Update all screen backgrounds to use images
- [ ] Add sacred geometry overlay components
- [ ] Add element icon displays
- [ ] Add spiritual symbol section dividers
- [ ] Update `SpiritualResources.kt` with Phase 3 mappings

### Screen Updates
- [ ] Update all 15 screen backgrounds
- [ ] Add sacred geometry overlays to key screens
- [ ] Add element icons to Ayurveda sections (ProfileDetail)
- [ ] Add element icons to Compatibility sections
- [ ] Add spiritual symbols as section dividers
- [ ] Create Onboarding module (if needed)
- [ ] Build onboarding flow with illustrations

### Testing
- [ ] Test all backgrounds display correctly
- [ ] Test sacred geometry overlays don't interfere with content
- [ ] Test element icons load correctly
- [ ] Test onboarding flow navigation
- [ ] Verify app performance still acceptable

---

## Phase 4: UX Enhancement (Week 4)

### Final Images
- [ ] Review any missing images from 99 total
- [ ] Generate any remaining images
- [ ] Generate variants if needed

### Loading States
- [ ] Implement circular loading indicator image
- [ ] Implement linear progress bar image
- [ ] Replace shimmer effects with image-based loading

### Success/Error States
- [ ] Implement success checkmark image
- [ ] Implement error warning image
- [ ] Update success screens to use celebration images

### Empty States
- [ ] Implement "No profiles yet" illustration
- [ ] Implement "No results" illustration
- [ ] Update empty state components

### Final Testing
- [ ] Test on Pixel 3a (low-end, xxhdpi)
- [ ] Test on Pixel 5 (mid-range, xxhdpi)
- [ ] Test on Pixel 7 Pro (high-end, xxxhdpi)
- [ ] Test on Pixel Tablet (tablet, xhdpi)
- [ ] Run memory profiler - check for leaks
- [ ] Run performance profiler - verify 60fps
- [ ] Test with airplane mode (cached images)
- [ ] Test with cleared cache (first load)

### APK Size Check
- [ ] Build release APK
- [ ] Check APK size < 60 MB
- [ ] If too large, remove xxxhdpi for non-critical images
- [ ] If still too large, reduce WebP quality to 85

### Documentation
- [ ] Update project documentation with new components
- [ ] Document any breaking changes
- [ ] Update README with image attribution
- [ ] Create developer guide for adding new images

---

## Final Verification

### All Images Integrated
- [ ] 8 App Branding images ✓
- [ ] 15 Background images ✓
- [ ] 10 Avatar images ✓
- [ ] 12 Zodiac images ✓
- [ ] 7 Chakra images ✓
- [ ] 8 Moon Phase images ✓
- [ ] 5 Element images ✓
- [ ] 8 Sacred Geometry images ✓
- [ ] 12 UI Element images ✓
- [ ] 8 Spiritual Symbol images ✓
- [ ] 6 Onboarding images ✓
- [ ] **Total: 99 images ✓**

### All Screens Updated
- [ ] SplashScreen.kt ✓
- [ ] HomeScreen.kt ✓
- [ ] Onboarding screens ✓
- [ ] ProfileLibraryScreen.kt ✓
- [ ] ProfileScreen.kt (creation) ✓
- [ ] ProfileDetailScreen.kt ✓
- [ ] ProfileComparisonScreen.kt ✓
- [ ] CompatibilityScreen.kt ✓
- [ ] CompatibilityDetailScreen.kt ✓
- [ ] EnrichmentResultScreen.kt ✓
- [ ] SettingsScreen.kt ✓
- [ ] ConsentScreen.kt ✓
- [ ] Tantric screens ✓
- [ ] Astrology screens ✓
- [ ] Numerology screens ✓
- [ ] Human Design screens ✓
- [ ] Moon calendar screens ✓

### All Components Created
- [ ] SpiritualResources.kt ✓
- [ ] ImageBackgrounds.kt ✓
- [ ] AvatarComponents.kt ✓
- [ ] ChakraVisualization.kt ✓
- [ ] ZodiacImageComponents.kt ✓
- [ ] CosmicBackgrounds.kt updated ✓

### Quality Assurance
- [ ] No crashes on image load
- [ ] No memory leaks detected
- [ ] App launches within 2 seconds
- [ ] All screens load within 1 second
- [ ] Smooth scrolling in ProfileLibrary
- [ ] Images sharp on all densities
- [ ] Dark mode works correctly
- [ ] Light mode works correctly

### Performance Metrics
- [ ] APK size < 60 MB
- [ ] Memory usage < 200 MB
- [ ] Startup time < 2 seconds
- [ ] Screen load time < 1 second
- [ ] 60 FPS maintained during scroll
- [ ] No ANR (Application Not Responding) errors

### User Experience
- [ ] Visual appeal improved vs Canvas
- [ ] No visual regressions
- [ ] Accessibility labels present
- [ ] Content descriptions accurate
- [ ] TalkBack compatible
- [ ] Large text compatible

---

## Post-Integration

### Git
- [ ] Commit all changes
- [ ] Push feature branch
- [ ] Create pull request
- [ ] Request code review
- [ ] Address review comments
- [ ] Merge to main

### Release
- [ ] Build release APK/Bundle
- [ ] Test release build on devices
- [ ] Update version code/name
- [ ] Generate release notes
- [ ] Upload to Play Store (internal test track)
- [ ] Test internal release
- [ ] Promote to beta
- [ ] Monitor crash reports

### Monitoring
- [ ] Monitor Crashlytics for image-related crashes
- [ ] Monitor Performance Monitoring
- [ ] Check user feedback on visual changes
- [ ] Monitor app size complaints
- [ ] Track loading time metrics

---

## Quick Commands Reference

### Image Generation
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
python generate_flux_pro_quick.py --category <category> --start 1 --end 12
```

### Image Optimization
```bash
python optimize_for_android.py --input generated_images/flux_pro_v1.1 --output ../../app/src/main/res --quality 90
```

### Build
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
./gradlew :app:assembleDebug
```

### Install
```bash
./gradlew :app:installDebug
```

### Test
```bash
./gradlew :core:ui:test
./gradlew connectedAndroidTest
```

### APK Size
```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

### Git
```bash
git checkout -b feature/image-integration
git add .
git commit -m "feat(ui): integrate 99 FLUX Pro images"
git push origin feature/image-integration
```

---

## Troubleshooting Quick Reference

| Issue | Solution |
|-------|----------|
| Images not loading | Check resource IDs match filenames; clean rebuild |
| Pixelated images | Verify xxxhdpi exists; check baseline resolution |
| OutOfMemoryError | Reduce Coil cache size; lower image quality |
| Slow loading | Enable disk cache; preload critical images |
| APK too large | Remove xxxhdpi; reduce quality to 85; use App Bundle |
| Wrong density | Check folder naming; verify device density |

---

## Progress Tracking

**Start Date:** _____________

**Phase 1 Complete:** _____________
**Phase 2 Complete:** _____________
**Phase 3 Complete:** _____________
**Phase 4 Complete:** _____________

**Final Completion:** _____________

**Total Images Integrated:** _____ / 99
**Total Screens Updated:** _____ / 17
**Total Components Created:** _____ / 6

---

**Document Version:** 1.0
**Last Updated:** December 9, 2025
