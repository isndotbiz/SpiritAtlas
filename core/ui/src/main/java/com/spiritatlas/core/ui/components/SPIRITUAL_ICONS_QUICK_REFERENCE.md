# SpiritAtlas Icons - Quick Reference Card

## Icon Inventory (30+ Icons)

### Profile Types (3)
```kotlin
MasculineIcon()           // Upward triangle (Fire/Yang)
FeminineIcon()            // Downward triangle (Water/Yin)
NonBinaryIcon()           // Hexagram (Balanced union)
```

### Zodiac Signs (12)
```kotlin
// Fire Signs (Orange)
AriesIcon()               // Ram horns
LeoIcon()                 // Lion's mane
SagittariusIcon()         // Archer's arrow

// Earth Signs (Green)
TaurusIcon()              // Bull horns
VirgoIcon()               // Maiden symbol
CapricornIcon()           // Goat with fish tail

// Air Signs (Cyan)
GeminiIcon()              // Roman numeral II
LibraIcon()               // Scales of justice
AquariusIcon()            // Water bearer waves

// Water Signs (Teal)
CancerIcon()              // Crab claws (69)
ScorpioIcon()             // Scorpion tail with arrow
PiscesIcon()              // Two fish connected
```

### Chakras (7)
```kotlin
RootChakraIcon()          // Red - 4 petals + square
SacralChakraIcon()        // Orange - 6 petals + crescent
SolarPlexusChakraIcon()   // Yellow - 10 petals + triangle
HeartChakraIcon()         // Green - 12 petals + hexagram
ThroatChakraIcon()        // Blue - 16 petals + circle
ThirdEyeChakraIcon()      // Indigo - 2 petals + Om
CrownChakraIcon()         // Violet - 1000 petals (radiating)
```

### Elements (5)
```kotlin
FireElementIcon()         // ▲ with gradient (Yellow→Red)
WaterElementIcon()        // ▼ with gradient (Blue→Teal)
EarthElementIcon()        // ▼— with gradient (Green→Brown)
AirElementIcon()          // ▲— with gradient (Cyan→Blue)
SpiritElementIcon()       // ⊛ 8-pointed star (Purple)
```

### Moon Phases (4)
```kotlin
NewMoonIcon()             // Dark circle
WaxingMoonIcon()          // Crescent (right)
FullMoonIcon()            // Bright circle with glow
WaningMoonIcon()          // Crescent (left)
```

### Sacred Geometry (4)
```kotlin
FlowerOfLifeIcon()        // 7 overlapping circles
MetatronsCubeIcon()       // 13 circles connected
SriYantraIcon()           // Interlocking triangles
VesicaPiscisIcon()        // 2 overlapping circles
```

### Planets (6)
```kotlin
SunIcon()                 // ☉ Circle with rays
VenusIcon()               // ♀ Female symbol
MarsIcon()                // ♂ Male symbol
MercuryIcon()             // ☿ Cross + crescent
JupiterIcon()             // ♃ Stylized "4"
SaturnIcon()              // ♄ Cross with tail
```

## Common Patterns

### Basic Usage
```kotlin
// Default
SunIcon()

// Custom size
MoonIcon(size = 32.dp)

// Custom color
HeartChakraIcon(color = TantricRose)

// No glow
FlowerOfLifeIcon(glowEffect = false)
```

### In a Row
```kotlin
Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    AriesIcon()
    Text("Aries")
}
```

### Conditional Rendering
```kotlin
when (sign) {
    ZodiacSign.ARIES -> AriesIcon()
    ZodiacSign.TAURUS -> TaurusIcon()
    // ...
}
```

## Size Guidelines
- **Inline**: 16-20dp
- **Standard**: 24-32dp
- **Feature**: 40-56dp
- **Hero**: 64-128dp
- **Background**: 128dp+

## Default Colors by Category
- **Profile**: `FireOrange`, `WaterTeal`
- **Fire Signs**: `FireOrange`
- **Earth Signs**: `EarthGreen`
- **Air Signs**: `AirCyan`
- **Water Signs**: `WaterTeal`
- **Chakras**: `ChakraRed` → `ChakraCrown` (rainbow)
- **Elements**: Built-in gradients
- **Moon**: `NightSky`, `MoonlightSilver`
- **Sacred**: `SacredGold`, `SpiritualPurple`, `MysticPurple`
- **Planets**: `SacredGold`, `TantricRose`, `ChakraRed`, `AirCyan`, `SpiritualPurple`, `NightSky`

## Helper Function
```kotlin
// Shorthand for Full Moon
MoonIcon(size = 24.dp)  // alias for FullMoonIcon()
```

---
**Total Icons**: 35 unique icons
**File**: `core/ui/components/SpiritualIcons.kt`
**Version**: 1.0
