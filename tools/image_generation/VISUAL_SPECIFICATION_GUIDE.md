# SpiritAtlas Visual Specification Guide
*Design Standards for FLUX 1.1 Pro Image Generation*

## Brand Visual Identity

### Core Brand Attributes
- **Premium Spiritual**: High-quality, professional spiritual app (think Co-Star, Sanctuary)
- **Cosmic & Mystical**: Deep space, celestial bodies, sacred geometry
- **Modern Minimalist**: Clean lines, not overly ornate or busy
- **Accessible Mysticism**: Spiritual without being overwhelming or exclusive

### Visual Style Direction
```
NOT like: New age bookstore posters, clip art, overly literal symbols
YES like: Premium meditation apps, astronomical photography, modern tarot decks
```

---

## Color System Deep Dive

### Primary Gradient (Mystic Purple)
Used for: Primary actions, main branding, spiritual energy
```
Light:  #A855F7  ─┐
Main:   #7C3AED   ├─ Smooth gradient transitions
Deep:   #6B21A8  ─┘

Usage Rules:
- Backgrounds: Deep to Main
- UI Elements: Main to Light
- Never below 40% opacity on dark backgrounds
```

### Accent Gradient (Sacred Gold)
Used for: Highlights, success states, illumination, special features
```
Light:  #FBBF24  ─┐
Main:   #D97706   ├─ Warmth and divine light
Deep:   #B45309  ─┘

Usage Rules:
- Sparingly - maximum 20% of composition
- Always paired with purple (never alone)
- Full moons, chakra centers, completion states
```

### Foundation (Night Sky)
Used for: Dark backgrounds, deep space, grounding
```
Base:    #1E1B4B  (Deep indigo-black)
Variant: #1A1A3E  (Slightly purple tint)

Usage Rules:
- Primary dark background
- Never pure black (#000000)
- Subtle purple undertone maintains brand
```

### Supporting Colors

**Cosmic Blue** (#3B82F6)
- Air element, throat chakra, intellectual energy
- Use for: Air signs, communication features

**Aurora Pink** (#EC4899)
- Romantic energy, tantric features, feminine divine
- Use for: Compatibility screens, relationship features

**Moonlight Silver** (#E2E8F0)
- Moon phases, subtle highlights, secondary text
- Use for: Moon visualizations, light accents

---

## Typography & Text Overlay Guidelines

### Ensuring Readability

**On Purple Backgrounds:**
```
✓ White text (#FFFFFF)
✓ Moonlight Silver (#E2E8F0)
✓ Sacred Gold (#D97706) for highlights
✗ Pure black
✗ Mid-tone purples
```

**On Dark Backgrounds:**
```
✓ White (#FFFFFF)
✓ Light purple (#A855F7)
✓ Moonlight Silver (#E2E8F0)
✗ Dark purples
✗ Mid-tone grays
```

**Contrast Ratios:**
- Minimum 4.5:1 for body text
- Minimum 3:1 for large text (18pt+)
- Always test with actual text overlay

---

## Sacred Geometry Precision

### Mathematical Accuracy Requirements

**Perfect Circles:**
- All circles must be mathematically perfect
- Use compass-style precision terminology
- Specify "perfect geometric circle" in prompts

**Symmetry:**
- All sacred geometry must be perfectly symmetrical
- Specify "mathematically precise symmetry"
- Use "mirror symmetry" or "rotational symmetry" as needed

**Proportions:**
- Golden ratio (φ = 1.618) for Fibonacci spirals
- Exact degrees for geometric divisions:
  - Zodiac: 30° per sign (360°/12)
  - Elements: 72° per element (360°/5)
  - Chakras: Vertical alignment only

### Quality Checks
```
Sacred Geometry Checklist:
□ All lines meet at precise angles
□ All circles are perfect (not elliptical)
□ Pattern is perfectly symmetrical
□ Proportions follow mathematical rules
□ No distortion or warping
```

---

## Astronomical Accuracy

### Moon Phase Rendering

**Critical Requirements:**
1. **Illumination Percentages:**
   - New Moon: 0%
   - Waxing/Waning Crescent: 25%
   - First/Last Quarter: 50%
   - Waxing/Waning Gibbous: 75%
   - Full Moon: 100%

2. **Terminator Line (Shadow Boundary):**
   - Must be curved, not straight
   - Smooth gradient transition
   - Consistent with illumination percentage

3. **Surface Features:**
   - Maria (dark spots) visible
   - Craters with relief on terminator
   - Earthshine on dark side for crescents

4. **Orientation:**
   - Northern hemisphere view (illuminated from right during waxing)
   - Consistent orientation across all phases

**Avoid:**
- Cartoon moon faces
- Flat, 2D crescents
- Incorrect terminator angles
- Missing surface detail

### Star Fields

**Realistic Star Rendering:**
- Varied star sizes (not all same size)
- Varied brightness levels
- Occasional color hints (blue, orange, white stars)
- Random distribution, not grid pattern
- Bokeh effect for distant stars

**Density:**
- Sparse: 20-30 stars per 1080x1920 background
- Medium: 50-80 stars
- Dense: 100+ stars (Milky Way effect)

---

## UI Element Specifications

### Glassmorphic Effects

**Modern Glass Card:**
```
Properties:
- Background: Base color at 8-15% opacity
- Blur: 20-40px gaussian blur
- Border: 1px solid white at 10-20% opacity
- Shadow: Soft, colored (purple-tinted)
- Corner Radius: 12-16px

Prompt Keywords:
"frosted glass effect"
"semi-transparent"
"subtle blur"
"glassmorphic design"
```

### Glow Effects

**Spiritual Glow (not neon):**
```
Characteristics:
- Soft edges (not harsh)
- Fades gradually outward
- Colored according to element
- Maximum 50% opacity at center
- Suggests energy, not electricity

Avoid:
- Neon-style bright edges
- Harsh, sharp glows
- Overpowering intensity
- Pure white glows (use colored)
```

### Button States

**Visual Hierarchy:**
1. **Primary Actions**: Full purple gradient background
2. **Secondary Actions**: Purple outline, transparent fill
3. **Tertiary Actions**: Text-only, subtle hover

**State Indicators:**
- Normal: Standard gradient
- Hover: Slightly lighter (mobile: not needed)
- Pressed: Slightly darker, subtle inset shadow
- Disabled: 50% opacity, no glow

---

## Icon Design Standards

### Style Consistency

**All icons must maintain:**
- Line weight: 2-3px for 256x256, 4-6px for 512x512
- Corner style: Rounded (not sharp or square)
- Style: Geometric/minimalist spiritual
- Complexity: Simple enough to read at 48x48px

**Icon Categories:**

**Navigation Icons** (128x128)
- Single color (purple)
- Minimal detail
- Clear at small sizes

**Feature Icons** (256x256)
- Can use gradient
- Moderate detail
- Recognizable silhouette

**Decorative Icons** (512x512)
- Full gradient and effects
- High detail acceptable
- Hero/featured placement

---

## Zodiac Sign Specifications

### Constellation Style

**Not Generic Zodiac Symbols:**
```
✗ Simple line drawings of animals
✗ Astrology glyph symbols (♈♉♊)
✗ Cartoon animal characters
```

**Yes - Constellation Art:**
```
✓ Connected star patterns forming sign shape
✓ Astronomical constellation lines
✓ Realistic star rendering
✓ Subtle mythological form suggested
```

### Element-Based Color Coding

**Fire Signs** (Aries, Leo, Sagittarius):
- Primary: Orange-Red (#F97316 to #DC2626)
- Accent: Gold (#D97706)
- Energy: Dynamic, radiating outward

**Earth Signs** (Taurus, Virgo, Capricorn):
- Primary: Green-Brown (#22C55E to #78350F)
- Accent: Bronze (#B45309)
- Energy: Stable, grounded

**Air Signs** (Gemini, Libra, Aquarius):
- Primary: Cyan-Violet (#06B6D4 to #8B5CF6)
- Accent: Sky Blue (#3B82F6)
- Energy: Flowing, ethereal

**Water Signs** (Cancer, Scorpio, Pisces):
- Primary: Teal-Indigo (#14B8A6 to #6366F1)
- Accent: Deep Purple (#6B21A8)
- Energy: Fluid, emotional depth

---

## Chakra Visualization Standards

### Authentic Representation

**Each chakra includes:**
1. **Lotus Petals**: Correct number per chakra
   - Root: 4 petals
   - Sacral: 6 petals
   - Solar: 10 petals
   - Heart: 12 petals
   - Throat: 16 petals
   - Third Eye: 2 petals
   - Crown: 1000 petals (stylized as many)

2. **Bija Mantra** (Sanskrit seed sound):
   - Root: LAM (लं)
   - Sacral: VAM (वं)
   - Solar: RAM (रं)
   - Heart: YAM (यं)
   - Throat: HAM (हं)
   - Third Eye: OM (ॐ)
   - Crown: Silent (or pure light)

3. **Element Symbol**:
   - Root: Square (earth)
   - Sacral: Crescent (water)
   - Solar: Triangle pointing down (fire)
   - Heart: Hexagram/Six-pointed star (air)
   - Throat: Circle (ether)
   - Third Eye: None (beyond elements)
   - Crown: None (pure consciousness)

### Color Accuracy

**Use exact chakra colors from palette:**
```
Root (Muladhara):     #EF4444  Deep Red
Sacral (Svadhisthana): #F97316  Orange
Solar (Manipura):      #FDE047  Yellow
Heart (Anahata):       #22C55E  Green
Throat (Vishuddha):    #3B82F6  Blue
Third Eye (Ajna):      #6366F1  Indigo
Crown (Sahasrara):     #8B5CF6  Violet
```

**Glow Intensity by Chakra:**
- Root: Grounded, minimal glow
- Sacral: Flowing, gentle glow
- Solar: Powerful, strong glow
- Heart: Radiant, balanced glow
- Throat: Clear, crisp glow
- Third Eye: Mystical, intense glow
- Crown: Transcendent, brilliant glow

---

## Background Design Rules

### Layering for Depth

**Three-Layer Approach:**
```
Layer 1 (Back): Base gradient or solid color
Layer 2 (Mid): Nebula clouds, energy wisps
Layer 3 (Front): Particles, stars, subtle patterns

Opacity Balance:
- Layer 1: 100%
- Layer 2: 30-60%
- Layer 3: 10-30%
```

### Content Readability

**Background Intensity Rules:**
```
Screen Type          Max Visual Intensity
─────────────────────────────────────────
Text-heavy           20% (very subtle)
Cards over bg        40% (moderate)
Media/Hero           80% (can be bold)
Splash/Onboarding    100% (full visual)
```

**Safe Zones:**
- Top 25%: Can be busier (behind status bar)
- Middle 50%: Must be clear for content
- Bottom 25%: Can have subtle patterns

### Motion Consideration

**For animated backgrounds:**
- Slow movement only (20+ second loops)
- Subtle particle drift
- Gentle pulsing (not strobing)
- No rapid changes (accessibility)

---

## File Delivery Specifications

### Naming Convention

```
Format: [category]_[name]_[size].ext

Examples:
icon_app_primary_1024x1024.png
bg_home_screen_1080x1920.webp
zodiac_aries_constellation_512x512.png
chakra_heart_anahata_512x512.png
ui_button_primary_normal_800x200.png
```

### Format by Use Case

**Icons & Transparent Assets:**
- Format: PNG-24 with alpha
- Color: sRGB
- DPI: 72 (screen optimized)

**Backgrounds:**
- Format: WebP (or JPEG if no transparency)
- Quality: 85-90%
- Color: sRGB
- DPI: 72

**High-Quality Decorative:**
- Format: PNG-24
- Quality: Maximum
- Color: sRGB
- DPI: 72

### Android Density Buckets

**Generate multiple sizes:**
```
mdpi:   @1x  (baseline)
hdpi:   @1.5x
xhdpi:  @2x
xxhdpi: @3x
xxxhdpi: @4x (optional, for hero images)

Example for 512px icon:
- mdpi:   512x512   (85 KB)
- xhdpi:  1024x1024 (295 KB)
- xxhdpi: 1536x1536 (620 KB)
```

---

## Quality Assurance Checklist

### Pre-Generation Review
- [ ] Prompt includes exact hex colors
- [ ] Dimensions specified correctly
- [ ] Style keywords for quality included
- [ ] Composition specified (centered, etc.)
- [ ] Background color/transparency specified

### Post-Generation Review
- [ ] Colors match SpiritAtlas palette (use eyedropper)
- [ ] No generation artifacts or errors
- [ ] Proper resolution and clarity
- [ ] Composition is balanced
- [ ] Works on intended background color

### Technical Validation
- [ ] File size appropriate (<500KB for backgrounds)
- [ ] Correct file format
- [ ] Proper transparency (if applicable)
- [ ] sRGB color space
- [ ] No color banding in gradients

### Brand Consistency
- [ ] Matches other images in category
- [ ] Aligns with SpiritAtlas aesthetic
- [ ] Professional quality
- [ ] Culturally respectful (sacred symbols)
- [ ] Approved by design lead

---

## Common Issues & Solutions

### Issue: Colors Don't Match
**Problem:** Generated colors slightly off from hex codes
**Solution:**
- Re-emphasize hex codes in prompt: "exact color #7C3AED"
- Post-process with color adjustment layer
- Use "professional color grading" in prompt

### Issue: Too Busy/Overwhelming
**Problem:** Background competes with UI content
**Solution:**
- Reduce opacity of decorative elements to 20-30%
- Add "subtle" and "minimal" to prompt
- Specify "suitable for text overlay"

### Issue: Sacred Geometry Imperfect
**Problem:** Circles not perfect, symmetry off
**Solution:**
- Emphasize "mathematically precise"
- Use "perfect geometric circles"
- Specify "vector-quality precision"
- May require post-processing in vector editor

### Issue: Moon Phases Look Flat
**Problem:** Missing depth and realism
**Solution:**
- Add "crater relief" and "terminator shadow detail"
- Specify "photorealistic lunar photography"
- Include "NASA quality" or "professional astronomy"

### Issue: Glows Too Harsh/Neon
**Problem:** Looks electric rather than spiritual
**Solution:**
- Specify "soft ethereal glow, not neon"
- Use "gentle radiance" instead of "bright glow"
- Add "spiritual energy, not electric"

---

## Cultural Sensitivity Guidelines

### Sacred Symbols - Handle with Respect

**Do:**
- Research authentic representation
- Use traditional color associations
- Include proper Sanskrit/Hebrew/etc. text
- Maintain symbolic integrity
- Respect religious significance

**Don't:**
- Distort or stylize beyond recognition
- Mix unrelated sacred traditions inappropriately
- Use sacred symbols as mere decoration
- Misrepresent cultural/religious meanings
- Use symbols from closed practices without permission

### Specific Considerations

**Hindu/Buddhist Symbols** (Om, Lotus, Chakras):
- Use authentic Devanagari script
- Maintain traditional proportions
- Respect color symbolism

**Kabbalistic Symbols** (Tree of Life):
- Correct 10 Sephirot placement
- Accurate pathways (22 connecting lines)
- Hebrew letter accuracy if included

**Native/Indigenous Symbols**:
- Avoid appropriation of closed practices
- Consult with cultural advisors if needed
- Use universal spiritual symbols instead

**Islamic Symbols**:
- Respect aniconism (no depiction of holy figures)
- Geometric patterns are appropriate
- Use with cultural consultation

---

## Appendix: Prompt Engineering Tips

### Effective Modifiers

**Quality Boosters:**
- "8K ultra-detailed" (not just "high quality")
- "professional [field] photography" (be specific)
- "cinematic" (for dramatic compositions)
- "studio lighting" (for clean, even lighting)

**Style Refinement:**
- "modern minimalist" (reduces clutter)
- "premium app design" (maintains professionalism)
- "sacred geometry precision" (for mathematical accuracy)
- "photorealistic" (for naturalistic subjects)

**Composition Control:**
- "centered composition" (for icons, balanced layouts)
- "symmetrical" (for mandalas, sacred geometry)
- "rule of thirds" (for dynamic compositions)
- "negative space" (prevents overcrowding)

### Negative Prompts (What to Avoid)

While FLUX 1.1 Pro doesn't use explicit negative prompts, you can guide away from unwanted results:
```
Instead of: "no text"
Use: "clean visual design suitable for text overlay"

Instead of: "no people"
Use: "abstract cosmic visualization"

Instead of: "not cluttered"
Use: "minimal, clean composition with negative space"
```

### Testing Variations

**A/B Test Approach:**
```
Version A: "cosmic purple gradient (#7C3AED to #6B21A8)"
Version B: "cosmic purple gradient from #7C3AED at top to #6B21A8 at bottom"

Version A: "soft mystical glow"
Version B: "ethereal radiance, gentle spiritual energy, not harsh"
```

Generate multiple versions, select best, iterate on winning prompt.

---

**Document Version**: 1.0
**Last Updated**: December 9, 2025
**For**: SpiritAtlas Android App
**Image Generation**: FLUX 1.1 Pro
**Status**: Ready for Production Use
