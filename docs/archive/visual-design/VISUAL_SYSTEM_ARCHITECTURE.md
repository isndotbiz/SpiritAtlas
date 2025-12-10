# SpiritAtlas Visual System Architecture

**Document Version:** 1.0
**Last Updated:** 2025-12-09
**Status:** Production-Ready Design Specification

## Executive Summary

This document defines the comprehensive visual system architecture for SpiritAtlas, leveraging 99 professionally-generated spiritual images across 11 categories to create a cohesive, beautiful, and deeply spiritual user experience. The system combines AI-generated imagery with custom vector icons, sacred geometry, and dynamic backgrounds to create an immersive spiritual journey.

---

## Table of Contents

1. [Image Asset Inventory](#1-image-asset-inventory)
2. [Screen-by-Screen Visual Design](#2-screen-by-screen-visual-design)
3. [Background System Architecture](#3-background-system-architecture)
4. [Icon System & Mapping](#4-icon-system--mapping)
5. [Seasonal & Contextual Imagery](#5-seasonal--contextual-imagery)
6. [Chakra Progression Visualization](#6-chakra-progression-visualization)
7. [Sacred Geometry in Analysis](#7-sacred-geometry-in-analysis)
8. [Avatar Customization System](#8-avatar-customization-system)
9. [Loading States & Animations](#9-loading-states--animations)
10. [Implementation Components](#10-implementation-components)
11. [Design Principles](#11-design-principles)

---

## 1. Image Asset Inventory

### 1.1 Category Breakdown

**Total Images:** 99 (63 generated, 36 vector icons)

#### Generated Assets (63 images)
```
Category 1: App Icons & Branding (10 images)
├── Primary App Icons (2 variants: Lotus, Merkaba)
├── Splash Screens (2 variants: Deep Space, Aurora)
├── Logos (2: Seed of Life, Flower of Life)
├── Wordmark (1)
├── Icon Badges (3)

Category 2: Buttons & Interactive (12 images)
├── Primary Buttons (3 variants)
├── Secondary Buttons (2 variants)
├── Floating Action Buttons (2)
├── Toggle Components (5)

Category 3: Backgrounds (15 images)
├── Cosmic Gradients (3)
├── Sacred Geometry Patterns (4)
├── Energy Fields (3)
├── Meditation/Profile (5)

Category 4: Avatars (6 images)
├── Default Avatars (3: Masculine, Feminine, Non-binary)
├── Avatar Frames (2)
├── Avatar Overlays (1)

Category 5: Spiritual Symbols (20 images)
├── Zodiac Constellations (12)
├── Chakra Visualizations (7)
├── Sacred Geometry (3: Flower of Life, Metatron's Cube, Sri Yantra)
├── Element Symbols (4: Fire, Water, Earth, Air)
├── Moon Phases (8)
├── Yin Yang (1)
```

#### Vector Icons (36 built-in)
```
Profile Types: 3 (Masculine, Feminine, Non-binary)
Zodiac Signs: 12 (All signs)
Chakras: 7 (Root to Crown)
Elements: 5 (Fire, Water, Earth, Air, Spirit)
Moon Phases: 4 (New, Waxing, Full, Waning)
Sacred Geometry: 3 (Flower of Life, Metatron's Cube, Vesica Piscis)
Planets: 6 (Sun, Venus, Mars, Mercury, Jupiter, Saturn)
```

### 1.2 Size Specifications

```kotlin
// Image size categories
sealed class ImageSize(val width: Int, val height: Int) {
    object AppIcon : ImageSize(1024, 1024)
    object SplashScreen : ImageSize(1080, 1920)
    object Logo : ImageSize(512, 512)
    object Button : ImageSize(360, 120)
    object Avatar : ImageSize(512, 512)
    object Background : ImageSize(1536, 1024)
    object Icon : ImageSize(96, 96)
    object ZodiacArt : ImageSize(400, 400)
    object ChakraVisualization : ImageSize(512, 512)
    object MoonPhase : ImageSize(200, 200)
}
```

---

## 2. Screen-by-Screen Visual Design

### 2.1 Splash Screen (App Launch)

**Visual Strategy:** Deep cosmic immersion

```
Layers (bottom to top):
1. Background: splash_screen_deep_space.png (1080×1920)
   - OR: splash_screen_aurora.png (alternate)
   - Smooth fade-in animation (800ms)

2. Center Logo: logo_flower_of_life.png (512×512)
   - Scale-up animation from 0.8 to 1.0 (600ms)
   - Simultaneous fade-in
   - Subtle rotation pulse (0° to 5° and back)

3. Bottom Wordmark: logo_wordmark_spiritatlas.png (1200×300)
   - Delayed fade-in (400ms delay)
   - Slight upward slide (20dp)

4. Loading Indicator: Custom chakra-colored dots
   - Positioned below wordmark
   - Sequential chakra color animation
```

**Implementation:**
- Background rotates daily between Deep Space and Aurora
- Logo variant rotates weekly (Seed of Life, Flower of Life, Merkaba)
- Loading animation uses 7 chakra colors in sequence

### 2.2 Home Screen

**Visual Strategy:** Starfield with dynamic cosmic elements

```
Layers:
1. Background: StarfieldBackground composable (procedural)
   - Animated twinkling stars
   - Subtle nebula clouds
   - Purple/violet cosmic gradient

2. Top AppBar:
   - Semi-transparent glassmorphic surface
   - Avatar: Current user avatar with energy_aura_ring.png overlay
   - Logo: Small logo_seed_of_life.png (48dp)

3. Content Cards:
   - Profile overview card: Sacred geometry border pattern
   - Quick actions: Floating buttons with gradient backgrounds
   - Recent compatibility: Energy field backgrounds

4. Floating Action Button:
   - fab_glowing_circle.png
   - Add new profile action
   - Pulsing glow animation
```

**Dynamic Elements:**
- Background adjusts based on time of day
- Moon phase indicator updates in real-time
- Seasonal zodiac constellation highlights

### 2.3 Profile Library Screen

**Visual Strategy:** Sacred geometry with organized profiles

```
Layers:
1. Background: SacredGeometryBackground composable
   - Rotating Flower of Life pattern
   - Subtle pulse animation
   - Light purple gradient overlay

2. Profile Grid:
   Each profile card:
   - Avatar: User avatar with avatar_frame_sacred_circle.png
   - Energy Aura: Contextual color based on dominant chakra
   - Element Indicator: Small element icon (fire/water/earth/air)
   - Zodiac Badge: Constellation art in corner

3. Filter Pills:
   - Glassmorphic buttons
   - Active state with cosmic gradient
   - Element/zodiac filtering
```

**Special Features:**
- Grid layout with staggered fade-in animation
- Long-press reveals detailed energy aura visualization
- Swipe actions with element-colored backgrounds

### 2.4 Profile Detail Screen

**Visual Strategy:** Immersive spiritual profile with chakra visualization

```
Layers:
1. Header Background: Zodiac-specific constellation art
   - Example: aries_constellation.png (400×400)
   - Blurred background effect
   - Gradient overlay for readability

2. Hero Section:
   - Large avatar (256dp) with sacred_circle_frame.png
   - Energy aura ring (animated pulsing)
   - Dominant chakra glow effect

3. Content Sections:
   a) Numerology:
      - Life Path Number with sacred geometry
      - Golden ratio circular indicators

   b) Astrology:
      - Sun/Moon/Rising cards with constellation backgrounds
      - Planet icons with planetary colors

   c) Chakra System:
      - Vertical chakra column visualization
      - Individual chakra icons (chakra_visualization_*.png)
      - Energy flow animation between chakras

   d) Ayurveda:
      - Dosha balance circular diagram
      - Element symbols with gradients

   e) Human Design:
      - Bodygraph overlay on sacred geometry
      - Energy center highlights

4. Floating Sections:
   - Each section on glassmorphic card
   - Subtle shadow and border glow
```

**Interactive Elements:**
- Tap chakra for detailed chakra screen
- Swipe between profile sections
- Pull-to-refresh with spiritual pull refresh animation

### 2.5 Compatibility Analysis Screen

**Visual Strategy:** Dual-energy visualization with merging cosmic forces

```
Layers:
1. Background: CosmicConnectionBackground composable
   - Dual-tone energy fields (purple + pink/teal)
   - Flowing particle effects
   - Energy convergence in center

2. Header:
   - Two avatars positioned left/right
   - Sacred geometry connection lines between them
   - Compatibility heart overlay in center

3. Score Rings:
   - Central compatibility score with sacred geometry
   - Outer rings showing sub-scores:
     * Numerology compatibility (golden ring)
     * Astrological harmony (cosmic violet ring)
     * Chakra resonance (rainbow gradient ring)
     * Elemental balance (element-colored ring)

4. Analysis Sections:
   a) Strengths:
      - Cards with tantric_passion gradient
      - Sacred geometry borders

   b) Challenges:
      - Cards with earth_connection gradient
      - Growth-oriented visualization

   c) Sacred Union Insights:
      - Golden sri_yantra.png background
      - Tantric wisdom cards

5. Energy Exchange Visualization:
   - Animated particles flowing between avatars
   - Chakra-to-chakra energy lines
   - Pulsing sacred geometry
```

**Dynamic Behavior:**
- Particle flow speed based on compatibility score
- Color palette adjusts based on dominant elements
- Sacred geometry rotates slowly

### 2.6 Compatibility Detail Screen

**Visual Strategy:** Deep analysis with sacred geometry overlays

```
Layers:
1. Background: metatrons_cube.png at 5% opacity
   - Static sacred geometry foundation
   - Gradient overlay

2. Detailed Metrics:
   - Each metric as glassmorphic card
   - Element-specific background tints
   - Icon badges for categories

3. Visual Charts:
   - Chakra compatibility: Side-by-side chakra visualizations
   - Element balance: Flower of Life with colored sections
   - Planetary aspects: Orbit diagram with planet icons
```

### 2.7 Settings Screen

**Visual Strategy:** Clean spiritual interface with subtle cosmic touches

```
Layers:
1. Background: EnhancedSpiritualBackground (minimal overlay)
   - Subtle cosmic gradient
   - Very light sacred geometry watermark

2. Settings Groups:
   - Glassmorphic section headers
   - Toggle switches with cosmic gradient tracks
   - Icons from spiritual icon set

3. Theme Preview:
   - Live preview cards showing theme changes
   - Smooth animated transitions
```

### 2.8 Consent Screen

**Visual Strategy:** Trust-building with sacred symbolism

```
Layers:
1. Background: Soft cosmic gradient
   - Non-distracting
   - Trustworthy purple tones

2. Content:
   - flower_of_life.png as trust symbol
   - Clean typography
   - Toggle switches for permissions

3. Action Buttons:
   - Primary: Cosmic gradient button
   - Secondary: Outline button
```

---

## 3. Background System Architecture

### 3.1 Dynamic Background Selector

**Smart Context-Aware Background Selection**

```kotlin
enum class SpiritualContext {
    MEDITATION,      // Deep, calming
    ENERGY,          // Vibrant, flowing
    ANALYSIS,        // Focused, geometric
    CONNECTION,      // Dual-tone, merging
    PEACEFUL,        // Soft, minimal
    CELEBRATION,     // Bright, aurora
    GROUNDING        // Earth tones
}

enum class TimeOfDay {
    DAWN,      // 5am-8am: Soft golden tones
    MORNING,   // 8am-12pm: Bright, energetic
    AFTERNOON, // 12pm-5pm: Balanced
    EVENING,   // 5pm-8pm: Warm, transitional
    NIGHT,     // 8pm-5am: Deep cosmic
}

enum class Season {
    SPRING,  // Fresh greens, renewal
    SUMMER,  // Bright golds, fire
    AUTUMN,  // Warm oranges, grounding
    WINTER   // Deep blues, introspection
}
```

### 3.2 Background Rotation Strategy

**Daily Background Cycle:**
```
Monday:     Cosmic gradient (deep introspection)
Tuesday:    Sacred geometry (Mars energy, structure)
Wednesday:  Energy flow (Mercury communication)
Thursday:   Golden hour (Jupiter expansion)
Friday:     Aurora/tantric (Venus love)
Saturday:   Earth connection (Saturn grounding)
Sunday:     Starfield (Sun radiance)
```

**Moon Phase Integration:**
```
New Moon:        Dark cosmic with subtle stars
Waxing Crescent: Gentle crescents in background
First Quarter:   Balanced light/dark
Waxing Gibbous:  Bright energy building
Full Moon:       Brilliant moonlight overlay, max luminosity
Waning Gibbous:  Soft glow receding
Last Quarter:    Balanced returning to dark
Waning Crescent: Quiet transition
```

### 3.3 Procedural vs. Static Backgrounds

**Use Procedural (Canvas-based) When:**
- Animation is core to experience (home screen starfield)
- Personalization needed (compatibility dual-colors)
- Performance is critical (smooth 60fps required)

**Use Static Images When:**
- High visual fidelity needed (profile headers)
- Complex artwork (zodiac constellations)
- Memory efficient for static screens

---

## 4. Icon System & Mapping

### 4.1 Icon Selection Rules

**Hierarchy:**
1. Vector icons (built-in) for interactive elements
2. Generated images for decorative/atmospheric
3. Hybrid approach for special features

### 4.2 Zodiac Icon Mapping

```kotlin
data class ZodiacIconSet(
    val vectorIcon: @Composable () -> Unit,     // For buttons, chips
    val constellationArt: String,               // For backgrounds, headers
    val element: Element,
    val seasonalPalette: List<Color>
)

val zodiacIcons = mapOf(
    ZodiacSign.ARIES to ZodiacIconSet(
        vectorIcon = { AriesIcon() },
        constellationArt = "zodiac_aries_constellation.png",
        element = Element.FIRE,
        seasonalPalette = ZodiacElementColors.FireSignColors
    ),
    // ... all 12 signs
)
```

**Usage Pattern:**
- **Chips/Buttons:** Vector icon (24dp, colored)
- **Profile Headers:** Constellation art (blurred background)
- **Compatibility Cards:** Vector icon + constellation overlay

### 4.3 Chakra Icon Mapping

```kotlin
data class ChakraIconSet(
    val vectorIcon: @Composable () -> Unit,
    val visualization: String,               // Full chakra art
    val color: Color,
    val sanskritName: String,
    val element: ChakraElement?
)

val chakraIcons = listOf(
    ChakraIconSet(
        vectorIcon = { RootChakraIcon() },
        visualization = "chakra_root_muladhara.png",
        color = ChakraRed,
        sanskritName = "Muladhara",
        element = ChakraElement.EARTH
    ),
    // ... all 7 chakras
)
```

**Progressive Chakra Display:**
```
Level 1 (Small):  Vector icon only (24dp)
Level 2 (Medium): Vector icon with glow effect (48dp)
Level 3 (Large):  Full visualization image (256dp)
Level 4 (Hero):   Animated full visualization with particle effects
```

### 4.4 Moon Phase Icon Mapping

```kotlin
enum class MoonPhase(
    val phase: String,
    val vectorIcon: @Composable () -> Unit,
    val illustrationAsset: String,
    val spiritualMeaning: String
) {
    NEW_MOON(
        "New Moon",
        { NewMoonIcon() },
        "moon_new_moon.png",
        "New beginnings, setting intentions"
    ),
    WAXING_CRESCENT(
        "Waxing Crescent",
        { WaxingMoonIcon() },
        "moon_waxing_crescent.png",
        "Building energy, manifestation"
    ),
    // ... all 8 phases
}
```

**Real-Time Moon Phase:**
```kotlin
fun getCurrentMoonPhase(): MoonPhase {
    // Calculate based on date/time
    // Return appropriate phase
}

// Usage in UI:
val currentPhase = getCurrentMoonPhase()
currentPhase.vectorIcon() // Show icon
Image(currentPhase.illustrationAsset) // Show detailed art
```

### 4.5 Action Icon Consistency

**Standard Actions:**
```kotlin
object SpiritualActionIcons {
    val Add = { SpiritElementIcon() }           // Spirit element for "add"
    val Edit = { MercuryIcon() }                // Mercury for communication/edit
    val Delete = { SaturnIcon() }               // Saturn for removal/boundaries
    val Share = { VenusIcon() }                 // Venus for sharing/connection
    val Search = { ThirdEyeChakraIcon() }       // Third eye for seeking
    val Favorite = { HeartChakraIcon() }        // Heart for favorites
    val Settings = { FlowerOfLifeIcon() }       // Sacred geometry for settings
    val Compatibility = { VesicaPiscisIcon() }  // Union for compatibility
    val Profile = { SunIcon() }                 // Sun for self/identity
}
```

---

## 5. Seasonal & Contextual Imagery

### 5.1 Seasonal Background System

**Spring (March 20 - June 20):**
```kotlin
object SpringVisuals {
    val backgrounds = listOf(
        "background_energy_renewal.png",
        "background_fresh_awakening.png"
    )
    val palette = ZodiacElementColors.AirSignColors +
                  ZodiacElementColors.EarthSignColors
    val dominantChakra = ChakraGreen // Heart chakra
    val moonPhaseEmphasis = MoonPhase.WAXING_CRESCENT
}
```

**Summer (June 21 - September 22):**
```kotlin
object SummerVisuals {
    val backgrounds = listOf(
        "background_golden_radiance.png",
        "background_solar_power.png"
    )
    val palette = ZodiacElementColors.FireSignColors
    val dominantChakra = ChakraYellow // Solar plexus
    val moonPhaseEmphasis = MoonPhase.FULL_MOON
}
```

**Autumn (September 23 - December 20):**
```kotlin
object AutumnVisuals {
    val backgrounds = listOf(
        "background_harvest_grounding.png",
        "background_sacred_transition.png"
    )
    val palette = listOf(GroundingBrown, TempleBronze, SacredGold)
    val dominantChakra = ChakraRed // Root chakra
    val moonPhaseEmphasis = MoonPhase.WANING_CRESCENT
}
```

**Winter (December 21 - March 19):**
```kotlin
object WinterVisuals {
    val backgrounds = listOf(
        "background_deep_cosmos.png",
        "background_mystical_night.png"
    )
    val palette = ZodiacElementColors.WaterSignColors
    val dominantChakra = ChakraIndigo // Third eye
    val moonPhaseEmphasis = MoonPhase.NEW_MOON
}
```

### 5.2 Contextual Image Selection

**User Profile Context:**
```kotlin
fun selectProfileBackground(profile: SpiritualProfile): BackgroundConfig {
    return when {
        // Dominant element drives background
        profile.dominantElement == Element.FIRE ->
            BackgroundConfig.energetic(FireOrange, ChakraRed)

        profile.dominantElement == Element.WATER ->
            BackgroundConfig.flowing(WaterTeal, DeepTeal)

        profile.dominantElement == Element.EARTH ->
            BackgroundConfig.grounding(EarthGreen, GroundingBrown)

        profile.dominantElement == Element.AIR ->
            BackgroundConfig.ethereal(AirCyan, StardustBlue)

        // Dominant chakra influences overlay
        profile.dominantChakra == 7 ->
            BackgroundConfig.withChakraOverlay(ChakraCrown)

        else -> BackgroundConfig.balanced()
    }
}
```

### 5.3 Zodiac Season Highlights

**Current Zodiac Season Integration:**
```kotlin
fun getZodiacSeasonVisuals(): ZodiacSeasonConfig {
    val currentSign = getCurrentZodiacSeason()
    return ZodiacSeasonConfig(
        constellationBackground = "zodiac_${currentSign.name.lowercase()}_constellation.png",
        accentColor = zodiacIcons[currentSign]!!.seasonalPalette.first(),
        elementSymbol = zodiacIcons[currentSign]!!.element
    )
}

// Usage:
// During Leo season (July 23 - Aug 22):
// - Leo constellation appears subtly in home background
// - Fire element colors emphasized
// - Sun icon more prominent
```

---

## 6. Chakra Progression Visualization

### 6.1 Vertical Chakra Column

**Full-Body Chakra Display (Profile Screen):**

```kotlin
@Composable
fun ChakraProgressionColumn(
    chakraStates: List<ChakraState>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chakraStates.forEachIndexed { index, state ->
            ChakraRow(
                chakra = chakraIcons[index],
                state = state,
                position = index
            )
        }
    }
}

@Composable
fun ChakraRow(
    chakra: ChakraIconSet,
    state: ChakraState,
    position: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Navigate to chakra detail */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Vector icon
        Box(modifier = Modifier.size(48.dp)) {
            chakra.vectorIcon()

            // Energy level indicator
            if (state.energyLevel > 0.7f) {
                // High energy: pulsing glow
                Canvas(Modifier.fillMaxSize()) {
                    drawCircle(
                        color = chakra.color.copy(alpha = 0.3f),
                        radius = size.minDimension / 2f * 1.2f
                    )
                }
            }
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = chakra.sanskritName,
                style = MaterialTheme.typography.titleMedium
            )

            // Energy bar
            LinearProgressIndicator(
                progress = state.energyLevel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = chakra.color
            )
        }

        // Optional: Full visualization preview
        if (state.isExpanded) {
            AsyncImage(
                model = chakra.visualization,
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
        }
    }
}
```

### 6.2 Chakra Energy Flow Animation

**Animated Energy Rising:**

```kotlin
@Composable
fun ChakraEnergyFlowAnimation(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val energyPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val chakraPositions = listOf(
            Offset(size.width / 2f, size.height * 0.9f),  // Root
            Offset(size.width / 2f, size.height * 0.75f), // Sacral
            Offset(size.width / 2f, size.height * 0.6f),  // Solar
            Offset(size.width / 2f, size.height * 0.45f), // Heart
            Offset(size.width / 2f, size.height * 0.3f),  // Throat
            Offset(size.width / 2f, size.height * 0.15f), // Third Eye
            Offset(size.width / 2f, size.height * 0.05f)  // Crown
        )

        // Draw chakra points
        chakraPositions.forEachIndexed { index, position ->
            drawCircle(
                color = SpiritualColors.ChakraColors[index],
                radius = 12f,
                center = position
            )
        }

        // Draw energy flow line
        val currentSegment = (energyPosition * 6).toInt()
        val segmentProgress = (energyPosition * 6) % 1f

        if (currentSegment < 6) {
            val start = chakraPositions[currentSegment]
            val end = chakraPositions[currentSegment + 1]
            val currentPos = Offset(
                start.x + (end.x - start.x) * segmentProgress,
                start.y + (end.y - start.y) * segmentProgress
            )

            // Glowing energy particle
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        SpiritualColors.ChakraColors[currentSegment]
                    )
                ),
                radius = 8f,
                center = currentPos
            )

            // Energy trail
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SpiritualColors.ChakraColors[currentSegment].copy(alpha = 0.6f),
                        SpiritualColors.ChakraColors[currentSegment].copy(alpha = 0.0f)
                    )
                ),
                start = start,
                end = currentPos,
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }
    }
}
```

### 6.3 Chakra Balance Visualization

**Radial Chakra Balance:**

```kotlin
@Composable
fun ChakraBalanceWheel(
    chakraLevels: List<Float>, // 0.0 to 1.0 for each chakra
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(240.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = size.minDimension / 2f * 0.8f

        // Draw each chakra as a spoke
        chakraLevels.forEachIndexed { index, level ->
            val angle = (index * 360f / 7f - 90f) * (PI / 180f).toFloat()
            val endX = center.x + cos(angle) * maxRadius * level
            val endY = center.y + sin(angle) * maxRadius * level

            // Spoke line
            drawLine(
                color = SpiritualColors.ChakraColors[index],
                start = center,
                end = Offset(endX, endY),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )

            // End point
            drawCircle(
                color = SpiritualColors.ChakraColors[index],
                radius = 8f,
                center = Offset(endX, endY)
            )

            // Chakra icon at max radius
            val labelX = center.x + cos(angle) * (maxRadius + 30f)
            val labelY = center.y + sin(angle) * (maxRadius + 30f)
            // Draw icon here using drawIntoCanvas
        }

        // Connect points to form shape
        val path = Path().apply {
            chakraLevels.forEachIndexed { index, level ->
                val angle = (index * 360f / 7f - 90f) * (PI / 180f).toFloat()
                val x = center.x + cos(angle) * maxRadius * level
                val y = center.y + sin(angle) * maxRadius * level

                if (index == 0) moveTo(x, y)
                else lineTo(x, y)
            }
            close()
        }

        // Fill shape with gradient
        drawPath(
            path = path,
            brush = Brush.radialGradient(
                colors = SpiritualColors.ChakraColors.map { it.copy(alpha = 0.3f) },
                center = center
            )
        )

        // Center circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, MysticPurple.copy(alpha = 0.5f))
            ),
            radius = 20f,
            center = center
        )
    }
}
```

---

## 7. Sacred Geometry in Analysis

### 7.1 Compatibility Score Visualization

**Nested Sacred Circles:**

```kotlin
@Composable
fun CompatibilityScoreRings(
    totalScore: Float,          // 0.0 to 1.0
    numerologyScore: Float,
    astrologyScore: Float,
    chakraScore: Float,
    elementalScore: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(280.dp)) {
        val center = Offset(size.width / 2f, size.height / 2f)

        // Outer ring: Elemental
        drawCompatibilityRing(
            center = center,
            radius = size.minDimension / 2f * 0.9f,
            progress = elementalScore,
            color = EarthGreen,
            strokeWidth = 12f
        )

        // Second ring: Chakra
        drawCompatibilityRing(
            center = center,
            radius = size.minDimension / 2f * 0.7f,
            progress = chakraScore,
            brush = Brush.sweepGradient(SpiritualColors.ChakraColors),
            strokeWidth = 12f
        )

        // Third ring: Astrology
        drawCompatibilityRing(
            center = center,
            radius = size.minDimension / 2f * 0.5f,
            progress = astrologyScore,
            color = CosmicViolet,
            strokeWidth = 12f
        )

        // Inner ring: Numerology
        drawCompatibilityRing(
            center = center,
            radius = size.minDimension / 2f * 0.3f,
            progress = numerologyScore,
            color = SacredGold,
            strokeWidth = 12f
        )

        // Center: Total score with sacred geometry
        // Draw Flower of Life at 20% opacity
        drawFlowerOfLife(
            center = center,
            radius = size.minDimension / 2f * 0.15f,
            color = MysticPurple.copy(alpha = 0.2f)
        )

        // Total score text (drawn separately with Text composable)
    }

    // Overlaid text
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${(totalScore * 100).toInt()}%",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

fun DrawScope.drawCompatibilityRing(
    center: Offset,
    radius: Float,
    progress: Float,
    color: Color? = null,
    brush: Brush? = null,
    strokeWidth: Float
) {
    // Background ring
    drawCircle(
        color = Color.Gray.copy(alpha = 0.2f),
        radius = radius,
        center = center,
        style = Stroke(width = strokeWidth)
    )

    // Progress arc
    drawArc(
        color = color,
        brush = brush,
        startAngle = -90f,
        sweepAngle = 360f * progress,
        useCenter = false,
        topLeft = Offset(center.x - radius, center.y - radius),
        size = Size(radius * 2, radius * 2),
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
}
```

### 7.2 Metatron's Cube Analysis Grid

**Sacred Geometry Overlay for Deep Analysis:**

```kotlin
@Composable
fun MetatronAnalysisOverlay(
    dataPoints: List<AnalysisPoint>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension / 2f * 0.8f

        // Draw Metatron's Cube structure
        val points = mutableListOf<Offset>()
        points.add(center)

        // 6 outer points
        for (i in 0..5) {
            val angle = (i * 60f) * (PI / 180f).toFloat()
            val point = Offset(
                center.x + cos(angle) * radius,
                center.y + sin(angle) * radius
            )
            points.add(point)
        }

        // Draw all connecting lines
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                drawLine(
                    color = SpiritualPurple.copy(alpha = 0.15f),
                    start = points[i],
                    end = points[j],
                    strokeWidth = 1f
                )
            }
        }

        // Draw circles at points
        points.forEachIndexed { index, point ->
            drawCircle(
                color = SpiritualPurple.copy(alpha = 0.3f),
                radius = 6f,
                center = point
            )

            // Map data points to geometry points
            if (index < dataPoints.size) {
                val dataPoint = dataPoints[index]
                drawCircle(
                    color = dataPoint.color,
                    radius = 12f * dataPoint.magnitude,
                    center = point,
                    alpha = 0.6f
                )
            }
        }
    }
}

data class AnalysisPoint(
    val category: String,
    val magnitude: Float,  // 0.0 to 1.0
    val color: Color
)
```

### 7.3 Sri Yantra Tantric Compatibility

**Sacred Tantric Diagram for Relationship Analysis:**

```kotlin
@Composable
fun SriYantraCompatibility(
    masculineEnergy: Float,
    feminineEnergy: Float,
    unionEnergy: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(320.dp)) {
        // Background Sri Yantra image
        AsyncImage(
            model = "sri_yantra.png",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f),
            contentDescription = null
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f

            // Upward triangles (masculine)
            val upTriangleSize = radius * 0.8f * masculineEnergy
            val upPath = Path().apply {
                moveTo(center.x, center.y - upTriangleSize)
                lineTo(center.x + upTriangleSize * 0.866f, center.y + upTriangleSize / 2f)
                lineTo(center.x - upTriangleSize * 0.866f, center.y + upTriangleSize / 2f)
                close()
            }
            drawPath(
                path = upPath,
                brush = Brush.linearGradient(
                    colors = listOf(
                        FireOrange.copy(alpha = 0.4f),
                        ChakraRed.copy(alpha = 0.3f)
                    )
                )
            )

            // Downward triangles (feminine)
            val downTriangleSize = radius * 0.8f * feminineEnergy
            val downPath = Path().apply {
                moveTo(center.x, center.y + downTriangleSize)
                lineTo(center.x + downTriangleSize * 0.866f, center.y - downTriangleSize / 2f)
                lineTo(center.x - downTriangleSize * 0.866f, center.y - downTriangleSize / 2f)
                close()
            }
            drawPath(
                path = downPath,
                brush = Brush.linearGradient(
                    colors = listOf(
                        WaterTeal.copy(alpha = 0.4f),
                        DeepTeal.copy(alpha = 0.3f)
                    )
                )
            )

            // Center bindu (union point)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = unionEnergy),
                        SacredGold.copy(alpha = unionEnergy * 0.6f),
                        Color.Transparent
                    )
                ),
                radius = 30f * unionEnergy,
                center = center
            )

            drawCircle(
                color = SacredGold,
                radius = 8f,
                center = center
            )
        }
    }
}
```

---

## 8. Avatar Customization System

### 8.1 Avatar Component Structure

```kotlin
@Composable
fun SpiritualAvatar(
    profileId: String,
    avatarType: AvatarType = AvatarType.DEFAULT,
    size: Dp = 128.dp,
    showEnergyAura: Boolean = true,
    showFrame: Boolean = false,
    showCompatibilityHeart: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Layer 1: Energy aura ring (background)
        if (showEnergyAura) {
            AsyncImage(
                model = "avatar_energy_aura_ring.png",
                modifier = Modifier.size(size * 1.1f),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = getAuraColor(profileId),
                    blendMode = BlendMode.Modulate
                )
            )
        }

        // Layer 2: Avatar image
        when (avatarType) {
            AvatarType.DEFAULT -> {
                val gender = getProfileGender(profileId)
                val defaultAvatar = when (gender) {
                    Gender.MASCULINE -> "avatar_default_masculine.png"
                    Gender.FEMININE -> "avatar_default_feminine.png"
                    Gender.NON_BINARY -> "avatar_default_nonbinary.png"
                }
                AsyncImage(
                    model = defaultAvatar,
                    modifier = Modifier.size(size),
                    contentDescription = null
                )
            }
            AvatarType.CUSTOM -> {
                AsyncImage(
                    model = getCustomAvatarUri(profileId),
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape),
                    contentDescription = null
                )
            }
            AvatarType.INITIALS -> {
                InitialsAvatar(
                    initials = getProfileInitials(profileId),
                    backgroundColor = getAuraColor(profileId),
                    size = size
                )
            }
        }

        // Layer 3: Sacred circle frame
        if (showFrame) {
            AsyncImage(
                model = "avatar_frame_sacred_circle.png",
                modifier = Modifier.size(size * 1.15f),
                contentDescription = null
            )
        }

        // Layer 4: Compatibility heart overlay (top-right)
        if (showCompatibilityHeart) {
            AsyncImage(
                model = "avatar_overlay_compatibility_heart.png",
                modifier = Modifier
                    .size(size * 0.3f)
                    .align(Alignment.TopEnd),
                contentDescription = null
            )
        }
    }
}

enum class AvatarType {
    DEFAULT,    // Generated cosmic silhouette
    CUSTOM,     // User uploaded photo
    INITIALS    // Text initials with gradient
}

@Composable
fun InitialsAvatar(
    initials: String,
    backgroundColor: Color,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = 0.8f),
                        backgroundColor
                    )
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.take(2).uppercase(),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
    }
}
```

### 8.2 Aura Color Mapping

**Dynamic Aura Based on Spiritual Profile:**

```kotlin
fun getAuraColor(profile: SpiritualProfile): Color {
    return when {
        // Primary based on dominant chakra
        profile.dominantChakra != null ->
            SpiritualColors.ChakraColors[profile.dominantChakra - 1]

        // Secondary based on element
        profile.dominantElement == Element.FIRE -> FireOrange
        profile.dominantElement == Element.WATER -> WaterTeal
        profile.dominantElement == Element.EARTH -> EarthGreen
        profile.dominantElement == Element.AIR -> AirCyan

        // Tertiary based on zodiac
        profile.sunSign != null ->
            zodiacIcons[profile.sunSign]!!.seasonalPalette.first()

        // Default
        else -> MysticPurple
    }
}

fun getAuraIntensity(profile: SpiritualProfile): Float {
    // Calculate based on profile completeness and energy levels
    val completeness = profile.calculateCompleteness()
    val energyLevel = profile.chakraStates.average { it.energyLevel }
    return (completeness * 0.5f + energyLevel * 0.5f).coerceIn(0.3f, 1.0f)
}
```

### 8.3 Avatar Customization Flow

**Step-by-Step Avatar Selection:**

```kotlin
@Composable
fun AvatarCustomizationScreen(
    onAvatarSelected: (AvatarConfig) -> Unit
) {
    var selectedType by remember { mutableStateOf(AvatarType.DEFAULT) }
    var selectedFrame by remember { mutableStateOf(true) }
    var selectedAura by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Preview
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(NightSky, CosmicViolet)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            SpiritualAvatar(
                profileId = currentProfileId,
                avatarType = selectedType,
                size = 200.dp,
                showEnergyAura = selectedAura,
                showFrame = selectedFrame
            )
        }

        // Type selection
        Text("Avatar Type", style = MaterialTheme.typography.titleLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AvatarTypeOption(
                type = AvatarType.DEFAULT,
                label = "Cosmic",
                icon = { SpiritElementIcon(size = 48.dp) },
                isSelected = selectedType == AvatarType.DEFAULT,
                onClick = { selectedType = AvatarType.DEFAULT }
            )

            AvatarTypeOption(
                type = AvatarType.CUSTOM,
                label = "Photo",
                icon = { /* Camera icon */ },
                isSelected = selectedType == AvatarType.CUSTOM,
                onClick = { selectedType = AvatarType.CUSTOM }
            )

            AvatarTypeOption(
                type = AvatarType.INITIALS,
                label = "Initials",
                icon = { /* Text icon */ },
                isSelected = selectedType == AvatarType.INITIALS,
                onClick = { selectedType = AvatarType.INITIALS }
            )
        }

        // Frame toggle
        SwitchRow(
            label = "Sacred Circle Frame",
            checked = selectedFrame,
            onCheckedChange = { selectedFrame = it }
        )

        // Aura toggle
        SwitchRow(
            label = "Energy Aura",
            checked = selectedAura,
            onCheckedChange = { selectedAura = it }
        )

        // Save button
        Button(
            onClick = {
                onAvatarSelected(
                    AvatarConfig(selectedType, selectedFrame, selectedAura)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Save Avatar")
        }
    }
}
```

---

## 9. Loading States & Animations

### 9.1 Spiritual Loading Indicators

**Chakra Sequence Loader:**

```kotlin
@Composable
fun ChakraLoadingIndicator(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val chakraIndex by infiniteTransition.animateInt(
        initialValue = 0,
        targetValue = 7,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SpiritualColors.ChakraColors.forEachIndexed { index, color ->
            val scale = if (index == chakraIndex % 7) 1.2f else 0.8f
            val alpha = if (index == chakraIndex % 7) 1f else 0.4f

            Canvas(
                modifier = Modifier
                    .size(16.dp)
                    .scale(scale)
            ) {
                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = size.minDimension / 2f
                )
            }
        }
    }
}
```

**Sacred Geometry Spinner:**

```kotlin
@Composable
fun FlowerOfLifeLoader(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier.size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = "logo_flower_of_life.png",
            modifier = Modifier
                .fillMaxSize()
                .rotate(rotation)
                .alpha(pulseAlpha),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                color = MysticPurple,
                blendMode = BlendMode.SrcIn
            )
        )
    }
}
```

**Moon Phase Loader:**

```kotlin
@Composable
fun MoonPhaseLoader(
    modifier: Modifier = Modifier
) {
    val phases = listOf(
        "moon_new_moon.png",
        "moon_waxing_crescent.png",
        "moon_first_quarter.png",
        "moon_waxing_gibbous.png",
        "moon_full_moon.png",
        "moon_waning_gibbous.png",
        "moon_last_quarter.png",
        "moon_waning_crescent.png"
    )

    var currentPhase by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(200)
            currentPhase = (currentPhase + 1) % phases.size
        }
    }

    Box(modifier = modifier.size(64.dp)) {
        AsyncImage(
            model = phases[currentPhase],
            modifier = Modifier.fillMaxSize(),
            contentDescription = "Loading"
        )
    }
}
```

### 9.2 Profile Loading Skeleton

**Spiritual Shimmer Effect:**

```kotlin
@Composable
fun ProfileLoadingSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        NightSky.copy(alpha = 0.3f),
                        CosmicViolet.copy(alpha = 0.1f)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Avatar skeleton
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
                .shimmerEffect()
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.3f))
        )

        // Name skeleton
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(32.dp)
                .align(Alignment.CenterHorizontally)
                .shimmerEffect()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        )

        // Info cards skeleton
        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .shimmerEffect()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    val transition = rememberInfiniteTransition()
    val shimmer by transition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    this.drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    Color.White.copy(alpha = 0.3f),
                    Color.Transparent
                ),
                start = Offset(size.width * shimmer, 0f),
                end = Offset(size.width * (shimmer + 0.3f), size.height)
            )
        )
    }
}
```

### 9.3 Compatibility Analysis Loading

**Energy Convergence Animation:**

```kotlin
@Composable
fun CompatibilityLoadingAnimation(
    profile1Name: String,
    profile2Name: String,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        TantricRose.copy(alpha = 0.1f),
                        Color.Transparent,
                        SpiritualPurple.copy(alpha = 0.1f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val avatar1Pos = Offset(
                center.x - 100f + progress * 50f,
                center.y
            )
            val avatar2Pos = Offset(
                center.x + 100f - progress * 50f,
                center.y
            )

            // Energy particles flowing
            for (i in 0..10) {
                val particleProgress = (progress + i * 0.1f) % 1f
                val particleX = avatar1Pos.x + (avatar2Pos.x - avatar1Pos.x) * particleProgress
                val particleY = center.y + sin(particleProgress * PI.toFloat() * 2) * 30f

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 1f - particleProgress),
                            TantricRose.copy(alpha = 0.5f * (1f - particleProgress))
                        )
                    ),
                    radius = 6f * (1f - particleProgress),
                    center = Offset(particleX, particleY)
                )
            }

            // Avatar placeholders
            drawCircle(
                color = TantricRose.copy(alpha = 0.4f),
                radius = 50f,
                center = avatar1Pos
            )
            drawCircle(
                color = SpiritualPurple.copy(alpha = 0.4f),
                radius = 50f,
                center = avatar2Pos
            )

            // Connection line
            drawLine(
                brush = Brush.linearGradient(
                    colors = listOf(TantricRose, SpiritualPurple),
                    start = avatar1Pos,
                    end = avatar2Pos
                ),
                start = avatar1Pos,
                end = avatar2Pos,
                strokeWidth = 2f,
                alpha = 0.3f
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Analyzing cosmic compatibility...",
                style = MaterialTheme.typography.titleMedium,
                color = MysticPurple
            )
            Spacer(modifier = Modifier.height(8.dp))
            ChakraLoadingIndicator()
        }
    }
}
```

---

## 10. Implementation Components

### 10.1 Core Component Files

**Create these files in `/core/ui/src/main/java/com/spiritatlas/core/ui/`:**

1. **`imaging/SpiritualBackgroundImage.kt`**
2. **`imaging/DynamicIconProvider.kt`**
3. **`imaging/ChakraVisualization.kt`**
4. **`imaging/ZodiacImageMapper.kt`**
5. **`imaging/SpiritualImageLoader.kt`**
6. **`imaging/AvatarSystem.kt`**

### 10.2 SpiritualBackgroundImage.kt

```kotlin
package com.spiritatlas.core.ui.imaging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.spiritatlas.core.ui.theme.*
import java.time.LocalDateTime

/**
 * Smart background image selector that chooses appropriate spiritual backgrounds
 * based on context, time, season, and user preferences.
 */
@Composable
fun SpiritualBackgroundImage(
    context: BackgroundContext = BackgroundContext.DEFAULT,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null,
    content: @Composable () -> Unit = {}
) {
    val backgroundAsset = remember(context) {
        selectBackgroundForContext(context)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(backgroundAsset),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )

        content()
    }
}

enum class BackgroundContext {
    DEFAULT,
    SPLASH,
    HOME,
    PROFILE_LIST,
    PROFILE_DETAIL,
    COMPATIBILITY,
    ANALYSIS,
    MEDITATION,
    SETTINGS
}

fun selectBackgroundForContext(context: BackgroundContext): String {
    val now = LocalDateTime.now()
    val hour = now.hour
    val dayOfWeek = now.dayOfWeek.value

    return when (context) {
        BackgroundContext.SPLASH -> {
            // Alternate between variants
            if (dayOfWeek % 2 == 0)
                "backgrounds/splash_screen_deep_space.png"
            else
                "backgrounds/splash_screen_aurora.png"
        }

        BackgroundContext.HOME -> {
            // Procedural starfield (use composable instead)
            "procedural:starfield"
        }

        BackgroundContext.PROFILE_LIST -> {
            "backgrounds/sacred_geometry_pattern.png"
        }

        BackgroundContext.PROFILE_DETAIL -> {
            // Dynamic based on profile zodiac (handled separately)
            "backgrounds/cosmic_gradient.png"
        }

        BackgroundContext.COMPATIBILITY -> {
            "procedural:cosmic_connection"
        }

        BackgroundContext.ANALYSIS -> {
            "backgrounds/metatron_analysis.png"
        }

        BackgroundContext.MEDITATION -> {
            when {
                hour < 6 -> "backgrounds/deep_night.png"
                hour < 12 -> "backgrounds/morning_energy.png"
                hour < 18 -> "backgrounds/afternoon_balance.png"
                else -> "backgrounds/evening_calm.png"
            }
        }

        BackgroundContext.SETTINGS -> {
            "backgrounds/minimal_cosmic.png"
        }

        BackgroundContext.DEFAULT -> {
            "backgrounds/cosmic_gradient.png"
        }
    }
}

/**
 * Seasonal background variant selector
 */
fun getSeasonalBackground(): String {
    val month = LocalDateTime.now().monthValue
    return when {
        month in 3..5 -> "backgrounds/spring_renewal.png"
        month in 6..8 -> "backgrounds/summer_radiance.png"
        month in 9..11 -> "backgrounds/autumn_grounding.png"
        else -> "backgrounds/winter_cosmos.png"
    }
}

/**
 * Time-of-day background selector
 */
fun getTimeBasedBackground(): String {
    val hour = LocalDateTime.now().hour
    return when {
        hour in 5..7 -> "backgrounds/dawn_awakening.png"
        hour in 8..11 -> "backgrounds/morning_energy.png"
        hour in 12..16 -> "backgrounds/afternoon_balance.png"
        hour in 17..19 -> "backgrounds/golden_hour.png"
        else -> "backgrounds/night_cosmos.png"
    }
}
```

### 10.3 DynamicIconProvider.kt

```kotlin
package com.spiritatlas.core.ui.imaging

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.domain.model.*

/**
 * Context-aware icon provider that selects appropriate icons
 * based on content type, user preferences, and visual hierarchy.
 */
object DynamicIconProvider {

    /**
     * Get zodiac icon (vector or image) based on display context
     */
    @Composable
    fun ZodiacIcon(
        sign: ZodiacSign,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        modifier: Modifier = Modifier
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL -> {
                // Use vector icon
                sign.vectorIcon(modifier = modifier)
            }
            IconDisplayMode.VECTOR_LARGE -> {
                sign.vectorIcon(modifier = modifier)
            }
            IconDisplayMode.ART_BACKGROUND -> {
                // Use constellation art
                Image(
                    painter = rememberAsyncImagePainter(sign.constellationAsset),
                    contentDescription = sign.name,
                    modifier = modifier,
                    contentScale = ContentScale.Crop,
                    alpha = 0.3f
                )
            }
            IconDisplayMode.ART_HERO -> {
                Image(
                    painter = rememberAsyncImagePainter(sign.constellationAsset),
                    contentDescription = sign.name,
                    modifier = modifier,
                    contentScale = ContentScale.Fit
                )
            }
        }
    }

    /**
     * Get chakra icon with appropriate detail level
     */
    @Composable
    fun ChakraIcon(
        chakra: Chakra,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        showGlow: Boolean = true,
        modifier: Modifier = Modifier
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL -> {
                chakra.vectorIcon(
                    modifier = modifier,
                    size = 24.dp,
                    glowEffect = showGlow
                )
            }
            IconDisplayMode.VECTOR_LARGE -> {
                chakra.vectorIcon(
                    modifier = modifier,
                    size = 64.dp,
                    glowEffect = showGlow
                )
            }
            IconDisplayMode.ART_BACKGROUND,
            IconDisplayMode.ART_HERO -> {
                AsyncImage(
                    model = chakra.visualizationAsset,
                    contentDescription = chakra.sanskritName,
                    modifier = modifier
                )
            }
        }
    }

    /**
     * Get element icon
     */
    @Composable
    fun ElementIcon(
        element: Element,
        size: Dp = 24.dp,
        showGlow: Boolean = true,
        modifier: Modifier = Modifier
    ) {
        when (element) {
            Element.FIRE -> FireElementIcon(modifier, size, showGlow)
            Element.WATER -> WaterElementIcon(modifier, size, showGlow)
            Element.EARTH -> EarthElementIcon(modifier, size, showGlow)
            Element.AIR -> AirElementIcon(modifier, size, showGlow)
        }
    }

    /**
     * Get moon phase icon
     */
    @Composable
    fun MoonPhaseIcon(
        phase: MoonPhase,
        displayMode: IconDisplayMode = IconDisplayMode.VECTOR_SMALL,
        modifier: Modifier = Modifier
    ) {
        when (displayMode) {
            IconDisplayMode.VECTOR_SMALL,
            IconDisplayMode.VECTOR_LARGE -> {
                phase.vectorIcon(modifier = modifier)
            }
            IconDisplayMode.ART_BACKGROUND,
            IconDisplayMode.ART_HERO -> {
                AsyncImage(
                    model = phase.illustrationAsset,
                    contentDescription = phase.phaseName,
                    modifier = modifier
                )
            }
        }
    }

    /**
     * Get action icon for common actions
     */
    @Composable
    fun ActionIcon(
        action: SpiritualAction,
        size: Dp = 24.dp,
        tint: Color? = null,
        modifier: Modifier = Modifier
    ) {
        val icon: @Composable () -> Unit = when (action) {
            SpiritualAction.ADD -> { { SpiritElementIcon(modifier, size) } }
            SpiritualAction.EDIT -> { { MercuryIcon(modifier, size) } }
            SpiritualAction.DELETE -> { { SaturnIcon(modifier, size) } }
            SpiritualAction.SHARE -> { { VenusIcon(modifier, size) } }
            SpiritualAction.SEARCH -> { { ThirdEyeChakraIcon(modifier, size) } }
            SpiritualAction.FAVORITE -> { { HeartChakraIcon(modifier, size) } }
            SpiritualAction.SETTINGS -> { { FlowerOfLifeIcon(modifier, size) } }
            SpiritualAction.COMPATIBILITY -> { { VesicaPiscisIcon(modifier, size) } }
            SpiritualAction.PROFILE -> { { SunIcon(modifier, size) } }
        }

        icon()
    }
}

enum class IconDisplayMode {
    VECTOR_SMALL,      // 24dp vector icon
    VECTOR_LARGE,      // 64dp+ vector icon
    ART_BACKGROUND,    // Full image as background
    ART_HERO           // Full image as hero element
}

enum class SpiritualAction {
    ADD, EDIT, DELETE, SHARE, SEARCH,
    FAVORITE, SETTINGS, COMPATIBILITY, PROFILE
}

// Extension properties for domain models
val ZodiacSign.vectorIcon: @Composable (Modifier) -> Unit
    get() = when (this) {
        ZodiacSign.ARIES -> { mod -> AriesIcon(mod) }
        ZodiacSign.TAURUS -> { mod -> TaurusIcon(mod) }
        ZodiacSign.GEMINI -> { mod -> GeminiIcon(mod) }
        ZodiacSign.CANCER -> { mod -> CancerIcon(mod) }
        ZodiacSign.LEO -> { mod -> LeoIcon(mod) }
        ZodiacSign.VIRGO -> { mod -> VirgoIcon(mod) }
        ZodiacSign.LIBRA -> { mod -> LibraIcon(mod) }
        ZodiacSign.SCORPIO -> { mod -> ScorpioIcon(mod) }
        ZodiacSign.SAGITTARIUS -> { mod -> SagittariusIcon(mod) }
        ZodiacSign.CAPRICORN -> { mod -> CapricornIcon(mod) }
        ZodiacSign.AQUARIUS -> { mod -> AquariusIcon(mod) }
        ZodiacSign.PISCES -> { mod -> PiscesIcon(mod) }
    }

val ZodiacSign.constellationAsset: String
    get() = "zodiac/constellation_${name.lowercase()}.png"

val Chakra.vectorIcon: @Composable (Modifier, Dp, Boolean) -> Unit
    get() = when (number) {
        1 -> { mod, size, glow -> RootChakraIcon(mod, size, glowEffect = glow) }
        2 -> { mod, size, glow -> SacralChakraIcon(mod, size, glowEffect = glow) }
        3 -> { mod, size, glow -> SolarPlexusChakraIcon(mod, size, glowEffect = glow) }
        4 -> { mod, size, glow -> HeartChakraIcon(mod, size, glowEffect = glow) }
        5 -> { mod, size, glow -> ThroatChakraIcon(mod, size, glowEffect = glow) }
        6 -> { mod, size, glow -> ThirdEyeChakraIcon(mod, size, glowEffect = glow) }
        7 -> { mod, size, glow -> CrownChakraIcon(mod, size, glowEffect = glow) }
        else -> { mod, size, glow -> RootChakraIcon(mod, size, glowEffect = glow) }
    }

val Chakra.visualizationAsset: String
    get() = "chakras/chakra_${sanskritName.lowercase()}.png"

val MoonPhase.vectorIcon: @Composable (Modifier) -> Unit
    get() = when (this) {
        MoonPhase.NEW_MOON -> { mod -> NewMoonIcon(mod) }
        MoonPhase.WAXING_CRESCENT -> { mod -> WaxingMoonIcon(mod) }
        MoonPhase.FIRST_QUARTER -> { mod -> FullMoonIcon(mod) } // Simplified
        MoonPhase.WAXING_GIBBOUS -> { mod -> FullMoonIcon(mod) }
        MoonPhase.FULL_MOON -> { mod -> FullMoonIcon(mod) }
        MoonPhase.WANING_GIBBOUS -> { mod -> WaningMoonIcon(mod) }
        MoonPhase.LAST_QUARTER -> { mod -> WaningMoonIcon(mod) }
        MoonPhase.WANING_CRESCENT -> { mod -> WaningMoonIcon(mod) }
    }

val MoonPhase.illustrationAsset: String
    get() = "moon_phases/moon_${name.lowercase().replace(' ', '_')}.png"
```

### 10.4 ChakraVisualization.kt

**(Full implementation provided in Section 6 above)**

### 10.5 ZodiacImageMapper.kt

```kotlin
package com.spiritatlas.core.ui.imaging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spiritatlas.domain.model.ZodiacSign
import com.spiritatlas.core.ui.theme.ZodiacElementColors
import java.time.LocalDate
import java.time.Month

/**
 * Maps zodiac signs to their visual representations and provides
 * date-based zodiac detection for seasonal imagery.
 */
object ZodiacImageMapper {

    /**
     * Get zodiac sign from birth date
     */
    fun getZodiacSign(month: Int, day: Int): ZodiacSign {
        return when {
            (month == 3 && day >= 21) || (month == 4 && day <= 19) -> ZodiacSign.ARIES
            (month == 4 && day >= 20) || (month == 5 && day <= 20) -> ZodiacSign.TAURUS
            (month == 5 && day >= 21) || (month == 6 && day <= 20) -> ZodiacSign.GEMINI
            (month == 6 && day >= 21) || (month == 7 && day <= 22) -> ZodiacSign.CANCER
            (month == 7 && day >= 23) || (month == 8 && day <= 22) -> ZodiacSign.LEO
            (month == 8 && day >= 23) || (month == 9 && day <= 22) -> ZodiacSign.VIRGO
            (month == 9 && day >= 23) || (month == 10 && day <= 22) -> ZodiacSign.LIBRA
            (month == 10 && day >= 23) || (month == 11 && day <= 21) -> ZodiacSign.SCORPIO
            (month == 11 && day >= 22) || (month == 12 && day <= 21) -> ZodiacSign.SAGITTARIUS
            (month == 12 && day >= 22) || (month == 1 && day <= 19) -> ZodiacSign.CAPRICORN
            (month == 1 && day >= 20) || (month == 2 && day <= 18) -> ZodiacSign.AQUARIUS
            else -> ZodiacSign.PISCES
        }
    }

    /**
     * Get current zodiac season
     */
    fun getCurrentZodiacSeason(): ZodiacSign {
        val now = LocalDate.now()
        return getZodiacSign(now.monthValue, now.dayOfMonth)
    }

    /**
     * Render zodiac constellation as background
     */
    @Composable
    fun ZodiacConstellationBackground(
        sign: ZodiacSign,
        modifier: Modifier = Modifier,
        alpha: Float = 0.3f
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                "zodiac/constellation_${sign.name.lowercase()}.png"
            ),
            contentDescription = "${sign.name} constellation",
            modifier = modifier.fillMaxSize().alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }

    /**
     * Render zodiac with element colors
     */
    @Composable
    fun ZodiacElementCard(
        sign: ZodiacSign,
        modifier: Modifier = Modifier
    ) {
        Box(modifier = modifier) {
            val element = sign.element
            val colors = when (element) {
                Element.FIRE -> ZodiacElementColors.FireSignColors
                Element.EARTH -> ZodiacElementColors.EarthSignColors
                Element.AIR -> ZodiacElementColors.AirSignColors
                Element.WATER -> ZodiacElementColors.WaterSignColors
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = colors.take(2)
                        )
                    )
            )

            ZodiacConstellationBackground(sign = sign, alpha = 0.4f)

            // Icon overlay
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                DynamicIconProvider.ZodiacIcon(
                    sign = sign,
                    displayMode = IconDisplayMode.VECTOR_LARGE,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}
```

---

## 11. Design Principles

### 11.1 Visual Hierarchy

**Primary > Secondary > Tertiary:**
1. **Primary:** Vector icons for interaction (24-48dp)
2. **Secondary:** Generated images for atmosphere (backgrounds, overlays)
3. **Tertiary:** Procedural graphics for dynamic effects (animations, particles)

### 11.2 Performance Guidelines

**Image Loading Strategy:**
```kotlin
object ImageLoadingConfig {
    // Preload critical assets
    val preloadAssets = listOf(
        "logo_flower_of_life.png",
        "avatar_default_*.png",
        "backgrounds/cosmic_gradient.png"
    )

    // Lazy load decorative assets
    val lazyLoadAssets = listOf(
        "zodiac/constellation_*.png",
        "chakras/chakra_*.png"
    )

    // Cache configuration
    val memoryCache = 0.25 // 25% of available memory
    val diskCache = 100 * 1024 * 1024 // 100MB
}
```

**Optimize for:**
- Background images: WebP format, max 1080x1920
- Icons: PNG with transparency
- Constellation art: Compressed PNG, 400x400
- Chakra visualizations: High-quality PNG, 512x512

### 11.3 Accessibility

**Color Contrast:**
- All text over images: Minimum 4.5:1 contrast
- Use overlay gradients for readability
- Provide high-contrast theme option

**Alternative Icons:**
- All vector icons have semantic labels
- Image-based icons include contentDescription
- Support for reduced motion (disable animations)

### 11.4 Consistency Rules

**Spacing:**
- Icon padding: 8dp minimum around interactive icons
- Card spacing: 16dp between content cards
- Section spacing: 24dp between major sections

**Sizing:**
- Small icons: 24dp
- Medium icons: 48dp
- Large icons: 64dp
- Hero images: 256dp+

**Corners:**
- Small elements: 8dp radius
- Medium cards: 12dp radius
- Large surfaces: 16dp radius
- Full-screen: 0dp (sharp edges)

---

## Conclusion

This visual system architecture creates a cohesive, spiritually resonant experience by:

1. **Intelligent Asset Usage:** Every image serves a purpose, from atmospheric backgrounds to functional icons
2. **Dynamic Adaptation:** Visuals adapt to time, season, user profile, and context
3. **Performance Balance:** Mix of static, procedural, and animated graphics
4. **Spiritual Depth:** Sacred geometry, chakra systems, and cosmic imagery throughout
5. **User Personalization:** Avatars, auras, and themes reflect individual spiritual profiles

**Next Steps:**
1. Generate remaining 36 images (from 63 to 99)
2. Implement core components (SpiritualBackgroundImage, DynamicIconProvider, etc.)
3. Create image loading utilities and caching system
4. Test on various devices and screen sizes
5. Gather user feedback on visual impact and spiritual resonance

---

**Document Maintenance:**
- Update as new images are added
- Revise based on user testing
- Expand with new spiritual systems (I Ching, Tarot, etc.)
- Maintain alignment with brand evolution

**Version History:**
- v1.0 (2025-12-09): Initial comprehensive architecture
