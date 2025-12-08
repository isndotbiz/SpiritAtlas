# SpiritAtlas Splash Screen - Visual Guide

## Screen Layout

```
┌─────────────────────────────────────────┐
│                                         │
│         ★   ★     ★    ★                │  ← Twinkling Stars
│    ★          ★        ★    ★           │    (50 total, random positions)
│                                         │
│                                         │
│              ╭───────╮                  │
│            ╱           ╲                │  ← Sacred Geometry
│          ╱    ◯───◯    ╲               │    (3 concentric circles
│         │   ◯   ✦   ◯   │              │     + 6 rotating petals)
│          ╲    ◯───◯    ╱               │
│            ╲           ╱                │
│              ╰───────╯                  │
│                 │                       │
│              [LOGO]                     │  ← Central Logo
│             (Glowing)                   │    (Globe with zodiac ring,
│                 │                       │     particle trails)
│                                         │
│                                         │
│          SpiritAtlas                    │  ← Gradient Text
│                                         │    (Purple→Violet→Blue→Gold)
│                                         │
│     Discover Your Cosmic Blueprint     │  ← Tagline
│                                         │    (Light, faded white)
│    ★       ★      ★         ★           │
│         ★              ★                │
└─────────────────────────────────────────┘
```

## Animation Timeline

```
Time    Animation Stage                      Visual Effect
────────────────────────────────────────────────────────────────
0ms     │ App Launch                        Black screen
        │
100ms   │ Stars appear                      ★ Fade in, begin twinkling
        │
500ms   │ Logo animation starts             Logo scales from 0.5→1.0
        │                                    (with spring overshoot)
1000ms  │ Sacred geometry appears           Circles draw outward
        │                                    Petals begin rotating
1500ms  │ App name types in                 S p i r i t A t l a s
        │                                    (character by character)
2000ms  │ Tagline fades in                  "Discover Your Cosmic..."
        │                                    (smooth fade)
2500ms  │ Screen fade begins                Entire screen → black
        │
2800ms  │ Navigation triggered              → Navigate to next screen
        └
```

## Component Hierarchy

```
SplashScreen
├── Background (Dark gradient box)
│
├── StarField (Canvas layer)
│   ├── Star 1 (x: random, y: random, twinkling)
│   ├── Star 2 (x: random, y: random, twinkling)
│   └── ... (48 more stars)
│
├── Main Content Column
│   ├── Spacer (weight = 1)
│   │
│   ├── Logo Container Box
│   │   ├── SacredGeometryCircles (Canvas)
│   │   │   ├── Outer circle (Purple, rotating)
│   │   │   ├── Middle circle (Violet, rotating)
│   │   │   ├── Inner circle (Blue, rotating)
│   │   │   └── 6 Petals (Gold, rotating around center)
│   │   │
│   │   └── SpiritAtlasLogo (Canvas)
│   │       ├── 3 Aura layers (gradient halos)
│   │       ├── Main globe (radial gradient)
│   │       ├── Globe outline (gold)
│   │       ├── 12 Zodiac dots (rotating ring)
│   │       ├── Center star (white)
│   │       └── 8 Particle trails (rotating slowly)
│   │
│   ├── Spacing (48dp)
│   │
│   ├── GradientAppName
│   │   └── Text "SpiritAtlas" (42sp, gradient)
│   │
│   ├── Spacing (16dp)
│   │
│   ├── TaglineText
│   │   └── Text "Discover Your..." (16sp, white)
│   │
│   └── Spacer (weight = 1)
│
└── Fade Out Overlay (if progress >= 2500ms)
```

## Color Map

```
Background Gradient:
  Top    → #0F0F23 (Deep space blue)
  Middle → #1A1A3E (Slightly lighter blue-purple)
  Bottom → #0F0F23 (Deep space blue)

Stars:
  Color: #FFFFFF (White)
  Alpha: 0.3-1.0 (twinkling)

Sacred Geometry:
  Outer:  #8B5CF6 (Spiritual Purple) @ 30% alpha
  Middle: #7C3AED (Mystic Violet)   @ 40% alpha
  Inner:  #3B82F6 (Cosmic Blue)     @ 50% alpha
  Petals: #FBBF24 (Aura Gold)       @ 20% alpha

Logo:
  Aura layers: #8B5CF6 (Purple) @ decreasing alpha
  Globe gradient:
    Center → #3B82F6 (Cosmic Blue)    @ 80%
    Middle → #8B5CF6 (Spiritual Purple) @ 90%
    Edge   → #7C3AED (Mystic Violet)  @ 100%
  Outline: #FBBF24 (Aura Gold)        @ 60%
  Zodiac: #FBBF24 (Aura Gold)         @ 50%
  Star: #FFFFFF (White)                @ 90%
  Particles: #8B5CF6 (Purple)         @ 30%

Text:
  App name gradient:
    #8B5CF6 → #7C3AED → #3B82F6 → #FBBF24
  Tagline: #FFFFFF @ 70%
```

## Animation Curves

```
Logo Scale:
  ┌─────────────────┐
1.0│        ╱───╲    │  Spring overshoot
0.9│      ╱       ╲  │
0.8│    ╱           ╲│
0.5│───╯             │
  └─────────────────┘
  500ms      1000ms

Stars Twinkle (infinite):
  ┌─────────────────┐
1.0│   ╱╲   ╱╲   ╱╲ │  Sine wave
0.7│  ╱  ╲ ╱  ╲ ╱  ╲│
0.3│─╯    ╰╯    ╰╯  │
  └─────────────────┘
  Continuous @ varying speeds

Geometry Rotation (infinite):
  ┌─────────────────┐
360°│───────────────→│  Linear
  0°│               │
  └─────────────────┘
  40 seconds per cycle

Text Reveal:
  ┌─────────────────┐
1.0│            ╱────│  Linear character count
0.5│       ╱────     │
  0│──────╯          │
  └─────────────────┘
  1500ms     2000ms

Fade In/Out:
  ┌─────────────────┐
1.0│      ╱────╲     │  Smooth ease
0.5│    ╱        ╲   │
  0│───╯          ╰──│
  └─────────────────┘
```

## State Management

```
┌──────────────────┐
│  SplashViewModel │
└────────┬─────────┘
         │
         ├─→ animationProgress: StateFlow<Float>
         │   (Updated every 16ms, range: 0-2800)
         │
         ├─→ navigationEvent: StateFlow<NavigationEvent?>
         │   (Emitted at 2800ms based on onboarding status)
         │
         └─→ SharedPreferences
             ("onboarding_completed" boolean)

         ┌─────────────────────┐
         │  Navigation Events  │
         ├─────────────────────┤
         │ NavigateToHome      │ ← If onboarding done
         │ NavigateToOnboarding│ ← If onboarding needed
         └─────────────────────┘
```

## File Structure

```
app/src/main/java/com/spiritatlas/app/
├── SplashScreen.kt
│   ├── SplashScreen()           [Main composable]
│   ├── StarField()              [Canvas: stars]
│   ├── SacredGeometryCircles()  [Canvas: circles]
│   ├── SpiritAtlasLogo()        [Canvas: logo]
│   ├── GradientAppName()        [Text: app name]
│   ├── TaglineText()            [Text: tagline]
│   ├── Star                     [Data class]
│   └── createStarPath()         [Helper function]
│
├── SplashViewModel.kt
│   ├── SplashViewModel          [ViewModel class]
│   └── NavigationEvent          [Sealed class]
│
└── navigation/
    ├── Screen.kt                [Added: Splash route]
    └── SpiritAtlasNavGraph.kt   [Added: Splash composable]
```

## Responsive Sizing

```
Component               Mobile      Tablet      Notes
────────────────────────────────────────────────────────
Logo size               180dp       220dp       Scales with screen
Star count              50          50          Fixed count
Sacred geometry         280dp       320dp       Based on logo * 1.5
App name font           42sp        48sp        Text scales
Tagline font            16sp        18sp        Text scales
Spacing                 16/48dp     20/60dp     Proportional

All Canvas elements use relative sizing:
- Circles: radius = size.minDimension * factor
- Logo: radius = size.minDimension / 2.5
- Stars: position = size.width/height * random
```

## Performance Notes

```
Component               Render Type    Updates/sec    CPU Impact
──────────────────────────────────────────────────────────────────
Stars                   Canvas         60             Low
Sacred Geometry         Canvas         60             Low
Logo (static parts)     Canvas         60             Low
Logo (rotating parts)   Canvas         60             Medium
Text (gradient)         Compose        1              Low
Background gradient     Compose        0              Minimal
Animation progress      State          60             Low
──────────────────────────────────────────────────────────────────
Total estimated FPS: 60 (smooth on mid-range devices)
```

## Accessibility Considerations

```
Feature                 Current    Recommended
────────────────────────────────────────────────
Reduced motion          ❌         ✓ Check system settings
Screen reader           ❌         ✓ Add content descriptions
Minimum contrast        ✓          ✓ WCAG AA compliant
Touch targets           N/A        N/A (no interaction)
Animation skip          ❌         ✓ Add skip button after 1s
High contrast mode      ❌         ✓ Adjust colors
```
