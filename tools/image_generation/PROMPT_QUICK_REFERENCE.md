# FLUX PRO PROMPTS - QUICK REFERENCE

## Generation Priority Order

### Phase 1: Critical First Impressions (15 prompts)
**Generate these first** - highest user visibility
- 1.1 Primary App Icon
- 1.3 Splash Screen Background
- 2.1 Home Screen Background
- 3.1-3.8 Default Avatars (8 prompts)
- 9.1 Primary Action Button
- 9.4 Card Background
- 11.1 Welcome Screen

### Phase 2: Core Content (34 prompts)
**Essential for main features**
- 4.1-4.12 All Zodiac Signs (12 prompts)
- 5.1-5.7 All Chakras (7 prompts)
- 6.1-6.8 All Moon Phases (8 prompts)
- 7.1-7.5 All Elements (5 prompts)
- 2.2 Profile Background
- 2.3 Compatibility Background

### Phase 3: Enhanced Experience (25 prompts)
**Enriches the app experience**
- 8.1-8.8 Sacred Geometry (8 prompts)
- 10.1-10.8 Spiritual Symbols (8 prompts)
- 11.2-11.6 Onboarding Illustrations (5 prompts)
- 2.4-2.10 Additional Backgrounds (7 prompts)

### Phase 4: Polish & Complete (25 prompts)
**Completes the visual system**
- 1.2, 1.4-1.8 Additional Branding (6 prompts)
- 3.9-3.10 Avatar Frames (2 prompts)
- 9.2-9.3, 9.5-9.12 UI Elements (10 prompts)
- 2.11-2.15 Specialized Backgrounds (5 prompts)

---

## Color Palette Quick Reference

### Primary Colors
```
Mystic Purple:    #7C3AED  (Primary brand)
Cosmic Violet:    #6B21A8  (Deep spiritual)
Sacred Gold:      #D97706  (Accent/highlight)
Night Sky:        #1E1B4B  (Dark background)
```

### Secondary Colors
```
Stardust Blue:    #3B82F6  (Air/throat)
Aurora Pink:      #EC4899  (Romantic/tantric)
Moonlight Silver: #E2E8F0  (Light accent)
```

### Chakra Colors (in order)
```
Root:        #EF4444  (Red)
Sacral:      #F97316  (Orange)
Solar:       #FDE047  (Yellow)
Heart:       #22C55E  (Green)
Throat:      #3B82F6  (Blue)
Third Eye:   #6366F1  (Indigo)
Crown:       #8B5CF6  (Violet)
```

---

## Standard Dimensions Reference

### Icons
- App Icon: 1024x1024
- Notification: 192x192
- UI Icons: 128x128 or 256x256
- Avatars: 512x512
- Avatar Frames: 600x600

### Backgrounds
- Mobile Screen: 1080x1920 (portrait)
- Square Hero: 1080x1080
- Feature Banner: 1920x1080 (landscape)

### Content Images
- Zodiac/Chakra/Moon: 512x512
- Sacred Geometry: 1024x1024
- UI Elements: Variable (see prompts)

---

## Key Style Keywords for Consistency

### Quality Terms
- "8K ultra-detailed"
- "professional"
- "crisp rendering"
- "photorealistic" (for moon phases)
- "vector-quality" (for symbols)

### Spiritual Aesthetic
- "mystical"
- "cosmic"
- "spiritual"
- "sacred"
- "ethereal"

### Lighting
- "soft glow"
- "subtle aura"
- "radiant"
- "luminous"
- "studio lighting"

### Composition
- "centered composition"
- "symmetrical"
- "balanced"
- "dark background"
- "suitable for [use case]"

---

## Common Prompt Structure

```
[Subject description], [style attributes],
[color specifications with hex], [composition notes],
[quality keywords], [size/format notes]
```

**Example:**
"Professional app icon design featuring celestial globe,
deep cosmic purple gradient (#6B21A8 to #7C3AED),
golden ring orbital path (#D97706), centered composition,
8K ultra-detailed, clean edges, suitable for app stores"

---

## Batch Generation Tips

### Grouping for Efficiency
1. **Generate similar types together**: All zodiac at once, all chakras together
2. **Same dimensions**: Batch 512x512 prompts, then 1024x1024, etc.
3. **Similar styles**: Icons together, backgrounds together

### Quality Control Checklist
- ✓ Colors match SpiritAtlas palette
- ✓ Readable at target size
- ✓ Consistent style across category
- ✓ Dark background works with light text
- ✓ Transparent where needed (icons/avatars)

### Post-Processing Pipeline
1. **Remove backgrounds**: Icons, avatars, symbols
2. **Optimize formats**:
   - PNG for transparency
   - WebP for backgrounds (smaller file)
3. **Create density versions**:
   - @1x (mdpi)
   - @2x (xhdpi)
   - @3x (xxhdpi)
4. **Compress**: Use TinyPNG or similar
5. **Organize**: By category folders

---

## Critical Success Factors

### Must-Have Qualities
1. **Brand Consistency**: Purple-gold-indigo palette throughout
2. **Professional Polish**: 8K detail, clean execution
3. **Spiritual Authenticity**: Respectful sacred symbol rendering
4. **Mobile Optimized**: Clear at small sizes, fast loading
5. **Accessibility**: Sufficient contrast for readability

### Avoid
- ❌ Overly complex backgrounds that compete with text
- ❌ Colors outside SpiritAtlas palette
- ❌ Low resolution or blurry details
- ❌ Inconsistent style between related images
- ❌ Cultural appropriation or disrespect of sacred symbols

---

## Testing Checklist

Before finalizing generated images:

### Visual Quality
- [ ] Image is crisp at target display size
- [ ] Colors match SpiritAtlas palette exactly
- [ ] No artifacts or generation errors
- [ ] Proper composition (centered, balanced)
- [ ] Appropriate level of detail

### Technical
- [ ] Correct dimensions
- [ ] Proper file format (PNG/WebP)
- [ ] File size optimized for mobile
- [ ] Transparency where needed
- [ ] Multiple density versions created

### Integration
- [ ] Works on dark background
- [ ] Works on light background (if applicable)
- [ ] Text remains readable over image
- [ ] Consistent with other category images
- [ ] Approved by design review

---

## Quick Command Examples

### For highest priority (Phase 1):
```bash
# Generate first 15 critical images
flux-generate --prompts 1.1,1.3,2.1,3.1-3.8,9.1,9.4,11.1
```

### For complete zodiac set:
```bash
# All 12 zodiac signs
flux-generate --prompts 4.1-4.12 --dimensions 512x512
```

### For all chakras:
```bash
# Complete 7-chakra system
flux-generate --prompts 5.1-5.7 --dimensions 512x512
```

---

**Last Updated**: December 9, 2025
**Total Prompts**: 99
**Categories**: 11
**Ready for Production**: Yes
