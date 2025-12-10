# SpiritAtlas - Visual Asset Specifications
**Design Team Production Guide**
*Date: December 2025*

---

## Document Purpose

This document provides exact specifications for creating all marketing visual assets for SpiritAtlas. Each asset includes dimensions, color codes, typography, layout guidelines, and export requirements.

---

## Global Design System

### Brand Colors (Exact Hex Codes)

**Primary Palette:**
```
Cosmic Violet:    #7C3AED (RGB: 124, 58, 237)
Mystic Purple:    #9D7FF5 (RGB: 157, 127, 245)
Aurora Pink:      #FF6BB5 (RGB: 255, 107, 181)
Sacred Gold:      #FFC857 (RGB: 255, 200, 87)
Temple Bronze:    #CD7F32 (RGB: 205, 127, 50)
```

**Dark Mode Backgrounds:**
```
Background:       #1A1625 (RGB: 26, 22, 37) - Warm dark purple-black
Surface:          #211D2A (RGB: 33, 29, 42) - Warm dark surface
Surface Variant:  #2D2937 (RGB: 45, 41, 55) - Warmer variant
```

**Light Mode Backgrounds:**
```
Background:       #FEF7FF (RGB: 254, 247, 255) - Very light lavender
Surface:          #FFFBFE (RGB: 255, 251, 254) - Off-white
```

**Accent Colors:**
```
Chakra Red:       #FF6B6B (RGB: 255, 107, 107)
Heart Green:      #10B981 (RGB: 16, 185, 129)
Throat Blue:      #3B82F6 (RGB: 59, 130, 246)
Crown White:      #F3F4F6 (RGB: 243, 244, 246)
```

**Semantic Colors:**
```
Success:          #10B981 (RGB: 16, 185, 129)
Warning:          #F59E0B (RGB: 245, 158, 11)
Error:            #EF4444 (RGB: 239, 68, 68)
Info:             #3B82F6 (RGB: 59, 130, 246)
```

### Typography

**Font Stack:**
- **Primary:** Roboto, "Helvetica Neue", Arial, sans-serif
- **Headers:** Roboto Bold (700)
- **Body:** Roboto Regular (400)
- **Emphasis:** Roboto Medium (500)
- **Light:** Roboto Light (300)

**Font Sizes:**
```
Hero:             48px / 3rem / Bold
H1:               40px / 2.5rem / Bold
H2:               32px / 2rem / Bold
H3:               24px / 1.5rem / Semi-Bold (600)
H4:               20px / 1.25rem / Semi-Bold
Body Large:       18px / 1.125rem / Regular
Body:             16px / 1rem / Regular
Body Small:       14px / 0.875rem / Regular
Caption:          12px / 0.75rem / Regular
```

**Line Heights:**
```
Headers:          1.2x (tight)
Body:             1.5x (comfortable reading)
Captions:         1.4x
```

### Gradients

**Primary Gradient (Cosmic Purple):**
```css
linear-gradient(135deg, #7C3AED 0%, #9D7FF5 100%)
```

**Secondary Gradient (Aurora):**
```css
linear-gradient(135deg, #FF6BB5 0%, #9D7FF5 100%)
```

**Gold Gradient:**
```css
linear-gradient(135deg, #FFC857 0%, #CD7F32 100%)
```

**Dark Background Gradient:**
```css
linear-gradient(180deg, #1A1625 0%, #2D2937 100%)
```

**Hero Gradient (Multi-Stop):**
```css
linear-gradient(135deg,
  #7C3AED 0%,
  #9D7FF5 33%,
  #FF6BB5 66%,
  #FFC857 100%
)
```

### Effects

**Drop Shadows:**
```css
Light Shadow:     0 2px 4px rgba(0, 0, 0, 0.1)
Medium Shadow:    0 4px 8px rgba(0, 0, 0, 0.15)
Heavy Shadow:     0 8px 16px rgba(0, 0, 0, 0.2)
Card Shadow:      0 4px 12px rgba(124, 58, 237, 0.15)
```

**Glows:**
```css
Purple Glow:      0 0 24px rgba(157, 127, 245, 0.5)
Pink Glow:        0 0 24px rgba(255, 107, 181, 0.5)
Gold Glow:        0 0 24px rgba(255, 200, 87, 0.5)
```

**Border Radius:**
```
Extra Small:      4px
Small:            8px
Medium:           12px
Large:            16px
Extra Large:      24px
Circular:         50% / 999px
```

---

## Asset 1: App Icon

### Specifications
**Format:** PNG with alpha channel
**Sizes Required:**
- 512x512px (Play Store)
- 192x192px (xxxhdpi)
- 144x144px (xxhdpi)
- 96x96px (xhdpi)
- 72x72px (hdpi)
- 48x48px (mdpi)

**Safe Area:**
- Keep all critical elements within 464x464px center area (512px with 24px margin)
- Account for circular mask on some devices

### Design Composition

**Background:**
- Circular gradient: Cosmic Violet (#7C3AED) center to Mystic Purple (#9D7FF5) edges
- Add subtle radial glow from center (opacity 30%)

**Main Element (Choose One):**

**Option A: Sri Yantra**
- White/gold Sri Yantra geometry centered
- Size: ~70% of icon (358x358px on 512px canvas)
- Line weight: 3-4px at 512px size
- Color: Sacred Gold (#FFC857) with white (#FFFFFF) highlight
- Add subtle glow effect (Purple Glow)

**Option B: Flower of Life**
- White/gold Flower of Life pattern centered
- Size: ~75% of icon (384x384px on 512px canvas)
- Line weight: 2-3px at 512px size
- Color: Sacred Gold (#FFC857) with 50% opacity overlay
- Petals should be clearly visible at 48px

**Option C: Abstract Mandala**
- Custom mandala combining circle, triangle, lotus elements
- Size: ~70% of icon
- Color: Gold gradient with white accents
- Must remain recognizable when scaled down

**Layer Structure:**
1. Background gradient layer
2. Glow effect layer (Multiply blend, 30% opacity)
3. Sacred geometry main element
4. Highlight accents (white, 40% opacity)
5. Outer subtle border (white, 10% opacity, 2px)

**Export Settings:**
- Format: PNG-24 with transparency
- Color Space: sRGB
- Compression: Maximum quality
- Test rendering at 48x48px to ensure clarity

---

## Asset 2: Feature Graphic (Play Store Banner)

### Specifications
**Dimensions:** 1024x500px (Exact, no margins)
**Format:** PNG or JPG (PNG preferred for transparency)
**File Size:** Under 1MB
**Safe Text Area:** 1024x340px (centered vertically, 80px margin top/bottom)

### Design Composition

**Background Layer:**
- Full-bleed dark gradient: #1A1625 (top) to #2D2937 (bottom)
- Add starfield effect: Small white dots (2-4px) at 20% opacity, scattered
- Add 3-5 larger "stars" with subtle glow (8-12px with 30% glow)
- Optional: Very subtle sacred geometry pattern as texture (5% opacity)

**Left Section (0-400px):**

**SpiritAtlas Wordmark Logo:**
- Position: X: 50px, Y: centered vertically
- Size: ~300px wide × proportional height
- Style: Clean sans-serif or custom spiritual font
- Color: White (#FFFFFF) or gold gradient
- Add subtle glow effect (Purple Glow at 40% opacity)

**Center Section (400-700px):**

**Sacred Geometry Showcase:**
- Float 3-4 sacred geometry elements:
  - Flower of Life (120px diameter, top-center)
  - Sri Yantra (100px, bottom-left)
  - Merkaba (80px, middle-right)
  - Small stars/accents scattered
- Each element with subtle glow
- Slightly rotated (5-15 degrees) for dynamic feel
- Semi-transparent (60-80% opacity)
- Colors: Gold, white, light purple

**Right Section (700-1024px):**

**5 System Icons Arranged:**
- Layout: Pentagon formation or circular arrangement
- System icons (each ~50-60px):
  1. Numbers icon (Numerology) - top
  2. Zodiac wheel (Astrology) - top-right
  3. Lotus (Ayurveda) - bottom-right
  4. Energy channels (Human Design) - bottom-left
  5. Yin-yang or sacred union (Tantric) - top-left
- Central connection lines (1px, 20% opacity, connecting all icons)
- Each icon color-coded slightly (maintain harmony)

**Text Overlay (Center, Below Elements):**

**Primary Headline:**
```
Text: "5 SPIRITUAL SYSTEMS • ONE POWERFUL APP"
Font: Roboto Bold
Size: 32px
Color: White (#FFFFFF)
Position: Centered, Y: 250px
Tracking: 50 (loose letter spacing)
Text Shadow: 0 2px 8px rgba(0,0,0,0.5)
```

**Secondary Headline:**
```
Text: "Numerology • Astrology • Ayurveda • Human Design • Tantric"
Font: Roboto Medium
Size: 18px
Color: Aurora Pink (#FF6BB5) or Sacred Gold (#FFC857)
Position: Centered, Y: 295px
Tracking: 25
Text Shadow: 0 2px 4px rgba(0,0,0,0.4)
```

**Tertiary Text:**
```
Text: "119 Custom Images • Privacy-First • Instant Compatibility"
Font: Roboto Regular
Size: 14px
Color: White (#FFFFFF) at 70% opacity
Position: Centered, Y: 330px
```

**Quality Checks:**
- [ ] All text readable at thumbnail size (256x125px)
- [ ] No critical elements in bottom 80px (can be cut off on some devices)
- [ ] Export at 2x resolution (2048x1000px) then downscale for quality
- [ ] Test on both light and dark backgrounds

---

## Asset 3-10: App Screenshots (8 Screenshots)

### Specifications
**Dimensions:** 1080x2340px (Standard Android 9:19.5 ratio)
**Format:** PNG-24 with transparency
**Safe Area:**
- Status bar: Top 96px
- Navigation bar: Bottom 120px
- Keep critical content in: 1080x2124px (96px top + 120px bottom margins)

**Text Overlay Safe Area:**
- Recommended: 1008x2124px (36px side margins + top/bottom as above)

### Screenshot Design System

**Background Treatment:**
- Use actual app screenshots as base layer
- Add semi-transparent gradient overlay (20-40% opacity) for text readability
- Overlay gradient: Top-to-bottom, dark (#1A1625 at 60%) to transparent

**Text Overlay Style:**

**Headline:**
```
Font: Roboto Bold
Size: 72-84px
Color: White (#FFFFFF)
Position: Top third (Y: 400-600px range)
Alignment: Left or Center
Max Width: 900px
Line Height: 1.2
Text Shadow: 0 4px 12px rgba(0,0,0,0.6)
```

**Body Copy:**
```
Font: Roboto Regular
Size: 38-44px
Color: White (#FFFFFF) at 90% opacity
Position: Below headline (gap: 40px)
Max Width: 900px
Line Height: 1.4
Text Shadow: 0 2px 8px rgba(0,0,0,0.5)
```

**Decorative Elements:**
- Corner accent: Small sacred geometry in top-right (100x100px, 30% opacity)
- Bottom badge: Optional feature badge (e.g., "Privacy-First" or "119 Images")

---

### Screenshot 1: Hero - 5-in-1 System Showcase

**Base Screenshot:** Home screen or onboarding screen with cosmic background

**Layout:**

**Upper Third (Y: 300-800px):**
```
Headline:
"5 SPIRITUAL SYSTEMS
ONE POWERFUL APP"

Font: Roboto Bold
Size: 84px
Color: White
Alignment: Center
Line Height: 1.1
```

**Middle Third (Y: 850-1500px):**

**System Grid Display:**
- 5 circular badges arranged in pattern:
  - Top row: Numerology, Astrology (Y: 900px)
  - Middle row: Ayurveda, Human Design, Tantric (Y: 1150px)
- Each badge: 180x180px circle
- Badge background: Glassmorphic effect (white 15% opacity, 16px blur)
- Badge border: 2px white at 30% opacity
- Icon size: 80x80px inside each badge
- Label below each badge (24px, white, bold)

**System Labels:**
```
"Numerology" • "Astrology" • "Ayurveda"
"Human Design" • "Tantric"
```

**Lower Third (Y: 1600-2200px):**
```
Body Copy:
"Get your complete spiritual blueprint with
Numerology, Astrology, Ayurveda, Human Design,
and Tantric wisdom integrated in one app."

Font: Roboto Regular
Size: 40px
Color: White 90% opacity
Alignment: Center
Max Width: 900px
```

**Corner Accent:**
- Top-right: Flower of Life pattern (120x120px, white 20% opacity)

---

### Screenshot 2: Onboarding - Beautiful First Impression

**Base Screenshot:** Onboarding screen with cosmic gradient

**Upper Third:**
```
Headline:
"CREATE YOUR
SPIRITUAL PROFILE"

Font: Roboto Bold
Size: 80px
Color: White
Alignment: Left
Position: X: 80px, Y: 400px
```

**Middle Section:**
- Show onboarding form fields (name, birthdate, location)
- Highlight sacred geometry background element
- Show progress indicator (e.g., "Step 1 of 3")

**Lower Third:**
```
Body Copy:
"Start with just 3 fields or go deep
with 36 comprehensive data points
for master-level insights"

Font: Roboto Regular
Size: 42px
Color: Aurora Pink (#FF6BB5)
Position: X: 80px, Y: 1800px
Max Width: 920px
```

**Badge (Bottom Right):**
```
"3 Fields Minimum
36 Fields Maximum"

Background: Glassmorphic card
Position: Bottom-right corner (margin: 60px)
Size: 320x140px
```

---

### Screenshot 3: Profile Screen - Comprehensive Insights

**Base Screenshot:** User profile screen showing multiple spiritual systems

**Upper Third:**
```
Headline:
"YOUR COMPLETE
COSMIC BLUEPRINT"

Font: Roboto Bold
Size: 78px
Color: White
Alignment: Center
Position: Y: 350px
```

**Middle Section:**
- Show actual profile screen with:
  - Birth chart wheel visible
  - Numerology numbers displayed
  - Dosha indicators
  - Human Design type
  - Card-based layout

**Lower Third:**
```
Body Copy:
"All 5 systems integrated
Personalized insights
AI-powered analysis"

Font: Roboto Medium
Size: 44px
Color: Sacred Gold (#FFC857)
Alignment: Center
Position: Y: 2000px
```

**System Pills (Bottom):**
- Horizontal row of 5 small pills/tags
- Each: 160px × 48px rounded rectangle
- Background: Semi-transparent white (20% opacity)
- Text: System names, 20px, bold, white
- Position: Y: 2120px, centered horizontally

---

### Screenshot 4: Compatibility Selection - Choose Profiles

**Base Screenshot:** Profile selection screen for compatibility

**Upper Third:**
```
Headline:
"INSTANT COMPATIBILITY
INSIGHTS"

Font: Roboto Bold
Size: 76px
Color: White
Alignment: Center
Position: Y: 300px
Line Height: 1.1
```

**Middle Section:**
- Show two profile cards side by side or stacked
- Each profile card:
  - Avatar/icon (120x120px circle)
  - Name
  - Quick stats (zodiac sign, life path number, etc.)
- Connection visual between profiles (heart icon or energy line)
- "Analyze Compatibility" button prominently displayed

**Feature Pills (Bottom Third):**
```
6 Dimensions Analyzed:
✓ Numerology Alignment
✓ Astrology Synastry
✓ Tantric Harmony
✓ Energetic Resonance
✓ Communication Styles
✓ Emotional Depth

Font: Roboto Regular
Size: 36px
Color: White
Position: Y: 1600px, left-aligned (X: 80px)
Line spacing: 60px between items
Checkmark color: Heart Green (#10B981)
```

---

### Screenshot 5: Compatibility Results - Detailed Report

**Base Screenshot:** Compatibility results screen with score

**Upper Third:**

**Score Display (Center):**
- Large circular progress indicator
- Score: "87%" in center (120px font size, white, bold)
- Circle size: 280x280px
- Circle color: Aurora Pink to Sacred Gold gradient
- Position: Centered, Y: 400px

**Profile Avatars:**
- Two small profile avatars (100x100px) positioned flanking the score circle
- Position: Left avatar (X: 240px), Right avatar (X: 740px), Y: 480px

**Middle Section:**
```
Headline:
"EXCELLENT MATCH"

Font: Roboto Bold
Size: 64px
Color: Sacred Gold (#FFC857)
Alignment: Center
Position: Y: 800px
```

**Score Breakdown (6 Categories):**
- Horizontal bars showing scores for each dimension
- Each bar: 900px wide × 60px tall
- Bar fill: Gradient (Cosmic Violet to Aurora Pink)
- Labels: 32px, white, left-aligned
- Scores: 32px, white, right-aligned
- Position: Y: 950-1450px range (100px spacing between bars)

**Example Bar:**
```
[Label] Numerology Alignment     [Bar: 85% filled]     85%
```

**Lower Third:**
```
Body Copy:
"Strengths • Challenges • Growth Tips
Tap to see detailed recommendations"

Font: Roboto Regular
Size: 38px
Color: White 80% opacity
Alignment: Center
Position: Y: 2000px
```

---

### Screenshot 6: Privacy Features - Trust & Security

**Base Screenshot:** Settings or privacy screen

**Upper Third:**
```
Headline:
"PRIVACY-FIRST
DESIGN"

Font: Roboto Bold
Size: 84px
Color: White
Alignment: Center
Position: Y: 350px
```

**Middle Section:**

**Feature Cards (Stacked):**

**Card 1: Offline Calculation**
- Background: Glassmorphic card (white 15%, blur)
- Size: 900px × 200px
- Icon: Lock/Shield (left side, 80x80px)
- Text: "Calculate Offline" (48px bold)
- Subtext: "No internet required for core features" (32px regular)
- Position: Y: 700px

**Card 2: Local AI**
- Same style as Card 1
- Icon: Device/Computer (80x80px)
- Text: "Local AI Option" (48px bold)
- Subtext: "100% private insights on your device" (32px regular)
- Position: Y: 960px

**Card 3: No Data Selling**
- Same style as Card 1
- Icon: Shield with checkmark (80x80px)
- Text: "No Data Sold, Ever" (48px bold)
- Subtext: "Your spiritual data stays yours" (32px regular)
- Position: Y: 1220px

**Card 4: Encrypted Storage**
- Same style as Card 1
- Icon: Lock with key (80x80px)
- Text: "AES-256 Encryption" (48px bold)
- Subtext: "Bank-grade security for your data" (32px regular)
- Position: Y: 1480px

**Badge (Bottom):**
```
"Privacy by Design
Not an Afterthought"

Background: Sacred Gold gradient
Size: 600px × 120px
Position: Bottom center (Y: 2050px)
Font: 42px bold, Dark text color (#1A1625)
```

---

### Screenshot 7: Visual Gallery - Sacred Geometry Showcase

**Base Screenshot:** Gallery or image showcase screen

**Upper Third:**
```
Headline:
"119 STUNNING
CUSTOM IMAGES"

Font: Roboto Bold
Size: 80px
Color: White
Alignment: Center
Position: Y: 300px
```

**Middle Section:**

**Image Grid (3x3 or 4x3):**
- Show 9-12 thumbnail previews of best spiritual images
- Each thumbnail: 300x300px (with 30px gap)
- Images to feature:
  - Flower of Life
  - Sri Yantra
  - Merkaba
  - Zodiac constellation (e.g., Leo, Scorpio)
  - Chakra visualization (e.g., Heart chakra)
  - Moon phase (Full moon or New moon)
  - Yin Yang symbol
  - Om symbol
  - Tree of Life
- Add subtle border to each: 2px white at 20% opacity
- Position: Centered grid, Y: 650-1700px

**Category Labels (Below Grid):**
```
"Sacred Geometry • Zodiac Art • Chakras
Moon Phases • Spiritual Symbols"

Font: Roboto Medium
Size: 36px
Color: Aurora Pink (#FF6BB5)
Alignment: Center
Position: Y: 1850px
```

**Lower Third:**
```
Body Copy:
"Market-leading visual design with
FLUX 1.1 Pro AI-generated imagery
Exceptional quality, optimized for Android"

Font: Roboto Regular
Size: 38px
Color: White 90% opacity
Alignment: Center
Position: Y: 2000px
Max Width: 900px
```

**Quality Badge (Top Right):**
```
"Tier 1
9.2/10 Avg"

Background: Glassmorphic card
Size: 160px × 80px
Position: Top-right corner (margin: 40px)
```

---

### Screenshot 8: Features Overview - Everything Included

**Base Screenshot:** Features list or home screen overview

**Upper Third:**
```
Headline:
"EVERYTHING YOU NEED"

Font: Roboto Bold
Size: 84px
Color: White
Alignment: Center
Position: Y: 300px
```

**Middle Section:**

**Feature List (Two Columns or Stacked):**

**Left Column (X: 80px):**
```
✓ Unlimited Profiles
✓ Daily Insights
✓ AI Personalization
✓ Birth Chart Analysis
✓ Numerology Calculator
✓ Dosha Assessment

Font: Roboto Medium
Size: 40px
Color: White
Line Spacing: 90px between items
Checkmark: Heart Green (#10B981), 48px
Position: Y: 650px start
```

**Right Column (X: 580px):**
```
✓ Offline-First Design
✓ 6-Dimension Compatibility
✓ Sacred Geometry Images
✓ Human Design Insights
✓ No Ads
✓ Privacy Guaranteed

Font: Roboto Medium
Size: 40px
Color: White
Line Spacing: 90px between items
Checkmark: Heart Green (#10B981), 48px
Position: Y: 650px start
```

**Lower Third:**

**CTA Card:**
```
Background: Aurora Pink to Cosmic Violet gradient
Size: 900px × 200px
Border Radius: 24px
Position: Centered, Y: 2000px

Text: "Download Free Today"
Font: Roboto Bold
Size: 52px
Color: White
Alignment: Center

Subtext: "Core features included. No credit card required."
Font: Roboto Regular
Size: 32px
Color: White 90% opacity
```

---

## Asset 11: Promo Video Storyboard

### Specifications
**Duration:** 15-30 seconds
**Resolution:** 1920x1080px (16:9) or 1080x1920px (9:16 for mobile)
**Format:** MP4 (H.264)
**Frame Rate:** 30fps minimum, 60fps preferred
**Audio:** Optional background music (mystical, calming)

### Scene Breakdown

**Scene 1: App Icon Reveal (0-3s)**
- **Visual:** Black screen → App icon appears with glow effect
- **Animation:** Scale from 50% to 100%, rotation spin (360°), glow pulse
- **Duration:** 2 seconds
- **Transition:** Icon transforms into expanding mandala pattern

**Text Overlay (1.5-3s):**
```
"SpiritAtlas"
Font: 72px bold, white
Position: Below icon
Fade in: 0.5s, Hold: 1s, Fade out: 0.5s
```

**Scene 2: 5 Systems Showcase (3-8s)**
- **Visual:** Five system icons appear in sequence
- **Animation:** Each icon flies in from different direction, lands in position
- **Timing:**
  - Numerology icon: 3.0s
  - Astrology icon: 3.5s
  - Ayurveda icon: 4.0s
  - Human Design icon: 4.5s
  - Tantric icon: 5.0s
- **Connection lines:** Draw between icons (5.5-6.5s)
- **Background:** Cosmic purple gradient with particle effects

**Text Overlay (6-8s):**
```
"5 Spiritual Systems
One Powerful App"

Font: 64px bold, white
Position: Center screen
Animation: Fade in over 0.5s
```

**Scene 3: App Screens Showcase (8-15s)**
- **Visual:** Quick transitions between key app screens
- **Screen 1 (8-10s):** Onboarding screen
- **Screen 2 (10-12s):** Profile screen with birth chart
- **Screen 3 (12-14s):** Compatibility selection
- **Screen 4 (14-15s):** Results with score
- **Transition:** Smooth slide or fade between screens

**Text Overlays (Sequential):**
```
8-10s:  "Create Your Profile"
10-12s: "Discover Your Blueprint"
12-14s: "Analyze Compatibility"
14-15s: "Get Instant Insights"

Font: 52px bold, white
Position: Top third
Animation: Slide up + fade
```

**Scene 4: Image Gallery Flash (15-18s)**
- **Visual:** Rapid montage of spiritual images (0.3s each)
- **Images:** Show 8-10 best images in quick succession
- **Effect:** Zoom in slightly on each image, crossfade transition
- **Background:** Keep dark to make images pop

**Text Overlay (16-18s):**
```
"119 Custom Spiritual Images"

Font: 56px bold, Aurora Pink
Position: Center screen
Animation: Scale pulse
```

**Scene 5: Privacy Focus (18-21s)**
- **Visual:** Lock icon with shield, offline badge
- **Animation:** Lock clicks closed, shield glows
- **Background:** Darker background with subtle grid pattern

**Text Overlay (18-21s):**
```
"Privacy-First Design
Calculate Offline"

Font: 52px bold, white
Position: Center screen
Animation: Fade in
```

**Scene 6: Compatibility Highlight (21-26s)**
- **Visual:** Two profile avatars, compatibility score animates from 0% to 87%
- **Animation:**
  - Avatars slide in from sides (21-22s)
  - Heart icon appears between them (22s)
  - Score counter animates (22-24s)
  - 6 dimension bars fill (24-26s)
- **Background:** Romantic pink/purple gradient

**Text Overlay (23-26s):**
```
"Instant Compatibility Insights
6 Dimensions Analyzed"

Font: 48px bold, white
Position: Bottom third
Animation: Slide up
```

**Scene 7: Final CTA (26-30s)**
- **Visual:** App icon returns with download button
- **Animation:**
  - Icon scales in (26-27s)
  - Download button slides up (27-28s)
  - Platform badges appear (28-29s)
- **Background:** Return to cosmic purple gradient

**Text Overlays:**
```
26-28s: "Your Complete Spiritual OS"
Font: 64px bold, white
Position: Above icon

28-30s: "Download Free Today"
Font: 56px bold, Sacred Gold
Position: Bottom third
Animation: Pulse/glow
```

**Platform Badge (29-30s):**
- Google Play Store badge
- Position: Bottom center
- Size: 400px wide
- Animation: Fade in + slight scale

---

## Asset 12: Social Media Assets

### Instagram Square Post (1080x1080px)

**Layout:**

**Background:**
- Cosmic purple gradient (top to bottom)
- Subtle starfield overlay (20% opacity)

**Upper Third (0-360px):**
```
Logo: SpiritAtlas wordmark
Size: 800px wide (proportional height)
Color: White
Position: Centered, Y: 120px
```

**Middle Third (360-720px):**

**5 System Icons (Circular Arrangement):**
- Center: Connection symbol (80x80px)
- Surrounding: 5 system icons (each 100x100px) in circle
- Radius: 200px from center
- Connecting lines between all icons (1px, white 30% opacity)

**Lower Third (720-1080px):**
```
Headline:
"5 Systems • 1 App"

Font: Roboto Bold
Size: 72px
Color: White
Position: Centered, Y: 800px

Subtext:
"Numerology • Astrology • Ayurveda
Human Design • Tantric"

Font: Roboto Regular
Size: 36px
Color: Aurora Pink
Position: Centered, Y: 900px

CTA:
"Download Free → Link in Bio"

Font: Roboto Medium
Size: 40px
Color: Sacred Gold
Position: Centered, Y: 1000px
```

---

### Instagram Story (1080x1920px)

**Swipeable 5-Story Series:**

**Story 1: Introduction**
- Background: Full-bleed cosmic gradient
- App icon: Center, 400x400px
- Text: "Introducing SpiritAtlas" (96px bold)
- Subtext: "Swipe up to discover →" (48px)
- Sticker: "NEW" badge (top-right)

**Story 2: 5 Systems Highlight**
- Background: Dark with sacred geometry pattern
- Show 5 system icons vertically stacked
- Each with name and brief description (32px)
- Animated checkmarks appearing sequentially

**Story 3: Visual Excellence**
- Background: Gallery of 9 image thumbnails (3x3 grid)
- Text overlay: "119 Custom Images" (80px bold)
- Subtext: "Sacred Geometry • Zodiac • Chakras" (36px)
- Tap-through indicator

**Story 4: Privacy Focus**
- Background: Lock/shield visual
- Large headline: "Privacy-First" (120px bold)
- 3 feature pills:
  - "Calculate Offline"
  - "Local AI Option"
  - "No Data Sold"
- Glassmorphic card style

**Story 5: Download CTA**
- Background: App screenshots montage (blurred)
- Large CTA button: "Download Free" (600x120px)
- QR code option (bottom)
- "Available on Google Play" badge

---

### Twitter/X Header (1500x500px)

**Background:**
- Cosmic purple gradient (left to right)
- Subtle sacred geometry pattern (10% opacity)
- Starfield effect

**Left Section (0-500px):**
- SpiritAtlas logo (300px wide)
- Position: X: 100px, Y: centered

**Center Section (500-1000px):**
- Floating sacred geometry elements (3-4 small symbols)
- Sizes: 60-100px
- Opacity: 40-60%
- Slight rotation for visual interest

**Right Section (1000-1500px):**
```
Text: "5 Spiritual Systems
One Powerful App"

Font: Roboto Bold
Size: 48px
Color: White
Position: X: 1100px, Y: centered
Alignment: Left
```

---

### Facebook Cover (820x312px)

**Similar to Twitter header but adapted for dimensions:**

**Background:**
- Cosmic purple gradient
- Starfield overlay

**Left Section:**
- App icon (200x200px)
- Position: X: 60px, Y: 56px (centered vertically)

**Center Section:**
- Sacred geometry elements (smaller, 40-60px)
- Scattered for balance

**Right Section:**
```
Headline: "SpiritAtlas"
Font: 56px bold, white

Subtext: "Complete Spiritual Insights
Numerology • Astrology • Ayurveda
Human Design • Tantric"

Font: 24px regular, white 90% opacity

Position: X: 400px, Y: centered
Alignment: Left
```

---

## Export Guidelines

### File Naming Convention
```
Format: asset-type_variant_size.extension

Examples:
- app-icon_sri-yantra_512x512.png
- feature-graphic_hero_1024x500.png
- screenshot-01_hero_1080x2340.png
- social_instagram-square_1080x1080.png
```

### Quality Control Checklist

Before delivering assets, verify:
- [ ] All colors match brand hex codes exactly
- [ ] All text is legible at smallest display size
- [ ] All images are properly compressed (under file size limits)
- [ ] All assets exported in correct color space (sRGB)
- [ ] All PNG assets have transparency where intended
- [ ] All JPG assets have no visible compression artifacts
- [ ] All dimensions are exact (no 1px off errors)
- [ ] All text elements use correct fonts and weights
- [ ] All gradients are smooth (no banding)
- [ ] All shadows and glows are subtle and appropriate

### File Size Targets
- App Icon: <200KB per size
- Feature Graphic: <800KB
- Screenshots: <500KB each
- Social Media Images: <300KB
- Video: <50MB

### Compression Settings
- PNG: TinyPNG or ImageOptim (lossy compression acceptable)
- JPG: Quality 85-90 (Photoshop "High" or "Very High")
- Video: H.264, CRF 23, preset: medium

---

## Delivery Format

### Folder Structure
```
SpiritAtlas_Marketing_Assets/
├── 01_App_Icons/
│   ├── app-icon_512x512.png
│   ├── app-icon_192x192.png
│   ├── app-icon_144x144.png
│   ├── app-icon_96x96.png
│   ├── app-icon_72x72.png
│   └── app-icon_48x48.png
├── 02_Feature_Graphics/
│   ├── feature-graphic_hero_1024x500.png
│   └── feature-graphic_hero_2048x1000.png (2x quality)
├── 03_Screenshots/
│   ├── screenshot-01_hero_1080x2340.png
│   ├── screenshot-02_onboarding_1080x2340.png
│   ├── screenshot-03_profile_1080x2340.png
│   ├── screenshot-04_compatibility_1080x2340.png
│   ├── screenshot-05_results_1080x2340.png
│   ├── screenshot-06_privacy_1080x2340.png
│   ├── screenshot-07_gallery_1080x2340.png
│   └── screenshot-08_features_1080x2340.png
├── 04_Social_Media/
│   ├── instagram/
│   │   ├── post_square_1080x1080.png
│   │   ├── story-01_intro_1080x1920.png
│   │   ├── story-02_systems_1080x1920.png
│   │   ├── story-03_images_1080x1920.png
│   │   ├── story-04_privacy_1080x1920.png
│   │   └── story-05_cta_1080x1920.png
│   ├── twitter/
│   │   ├── header_1500x500.png
│   │   └── post_image_1200x675.png
│   └── facebook/
│       ├── cover_820x312.png
│       └── post_image_1200x630.png
├── 05_Promo_Video/
│   ├── promo-video_15s_1920x1080.mp4
│   ├── promo-video_30s_1920x1080.mp4
│   ├── promo-video_15s_1080x1920.mp4 (vertical)
│   └── promo-video_30s_1080x1920.mp4 (vertical)
├── 06_Source_Files/
│   ├── (Figma/Sketch/Adobe files)
│   └── (Asset library: logos, icons, fonts)
└── README.md (This document)
```

---

## Review & Approval Process

### Stage 1: Design Mockups
- Deliver low-res mockups for all assets
- Review brand consistency, layout, messaging
- Iterate based on feedback

### Stage 2: High-Res Draft
- Deliver full-resolution assets (with watermark)
- Review final quality, colors, text legibility
- Test assets in actual app store listings (test account)

### Stage 3: Final Delivery
- Deliver production-ready assets (no watermark)
- Include source files for future edits
- Provide usage documentation

---

## Timeline Estimate

**Phase 1: App Icon (2-3 days)**
- Design 3 concepts
- Refine selected concept
- Export all sizes

**Phase 2: Feature Graphic (1-2 days)**
- Design composition
- Refine based on feedback
- Export production files

**Phase 3: Screenshots (4-5 days)**
- Design layout system (1 day)
- Create all 8 screenshots (2-3 days)
- Refine and export (1 day)

**Phase 4: Social Media Assets (2-3 days)**
- Adapt layouts for each platform
- Create story series
- Export all variants

**Phase 5: Promo Video (5-7 days)**
- Storyboard (1 day)
- Animation (3-4 days)
- Sound design (1 day)
- Final render (1 day)

**Total Estimated Timeline: 14-20 days**

---

## Additional Resources

### Stock Assets to Use
- Starfield textures: [Unsplash space category]
- Cosmic backgrounds: [NASA public domain images]
- Sacred geometry vectors: [Custom or license-free sources]
- Icon libraries: Material Icons, Feather Icons

### Font Resources
- Roboto: Google Fonts (free, open-source)
- Alternative spiritual fonts: (if custom font desired, provide license)

### Design Tool Recommendations
- Figma: For collaborative design
- Adobe Photoshop: For raster image work
- Adobe Illustrator: For vector icons
- After Effects: For video animation
- Blender: For 3D sacred geometry renders (if needed)

---

## Contact & Questions

For questions about specifications or clarifications:
- Review MARKETING_ASSETS.md for messaging guidance
- Consult brand guidelines for color/typography standards
- Request additional app screenshots if needed for base layers
- Ask for specific sacred geometry references if unclear

---

*Document Version: 1.0*
*Last Updated: December 2025*
*Owner: SpiritAtlas Design Team*
