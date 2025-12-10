# Animation Migration Guide

Quick reference for migrating existing animations to optimized versions.

## Quick Migration Checklist

For each animation file, apply these changes:

### 1. Add Config Parameter

```kotlin
// Before
fun Modifier.fadeIn(visible: Boolean): Modifier

// After
fun Modifier.fadeIn(
  visible: Boolean,
  config: AnimationConfig = AnimationConfig()
): Modifier
```

### 2. Use Config for AnimationSpec

```kotlin
// Before
animationSpec = tween(durationMillis = 400)

// After
animationSpec = config.getAnimationSpec(
  baseDuration = 400,
  easing = SpiritualEasing.Gentle
)
```

### 3. Replace Modifiers with GraphicsLayer

```kotlin
// Before ❌ (triggers recomposition)
this.alpha(alpha)
this.scale(scale)
this.rotate(rotation)
this.offset(y = yOffset)

// After ✅ (GPU accelerated)
this.graphicsLayer {
  this.alpha = alpha
  scaleX = scale
  scaleY = scale
  rotationZ = rotation
  translationY = yOffset
}
```

### 4. Adjust Particle Counts

```kotlin
// Before
val particleCount = 30

// After
val particleCount = config.adjustParticleCount(30)
```

### 5. Use Optimized Easing

```kotlin
// Before
easing = FastOutSlowInEasing

// After
easing = SpiritualEasing.Gentle  // or Breath, Float, Pulse, etc.
```

## File-by-File Migration

### SpiritualAnimations.kt

**High Priority Functions:**
- [ ] `fadeInQuick()` → Add config, use graphicsLayer
- [ ] `fadeInMedium()` → Add config, use graphicsLayer
- [ ] `fadeInSlow()` → Add config, use graphicsLayer
- [ ] `scaleButton()` → Add config, use graphicsLayer
- [ ] `scaleCard()` → Add config, use graphicsLayer
- [ ] `breathingAnimation()` → Add config, adjust for reduce motion
- [ ] `pulsingAnimation()` → Add config, adjust for reduce motion
- [ ] `floatingAnimation()` → Add config, use graphicsLayer
- [ ] `continuousRotation()` → Add config, use graphicsLayer

**Can Use Directly:**
- ✅ `SparkleEffect()` - Add particle adjustment
- ✅ `WaveAnimation()` - Add config for reduce motion
- ✅ `CircularWave()` - Add config for reduce motion

### ChakraAnimations.kt

**High Priority:**
- [ ] `SpinningChakraWithParticles()` - Already optimized, just add particle adjustment
- [ ] `ChakraSpinner()` - Add config for reduce motion
- [ ] `ChakraActivation()` - Add config

**Update Particle Systems:**
```kotlin
// In SpinningChakraWithParticles
val particles = remember(particleCount, config) {
  List(config.adjustParticleCount(particleCount)) {
    // particle generation
  }
}
```

### EnergyFlowAnimations.kt

**High Priority:**
- [ ] `EnergyFlowBetweenProfiles()` - Add particle adjustment
- [ ] `CircularEnergyFlow()` - Add particle adjustment
- [ ] `HeartConnection()` - Add config

### ZodiacAnimations.kt

**High Priority:**
- [ ] `ConstellationReveal()` - Add config for timing
- [ ] `ZodiacWheelRotation()` - Add config
- [ ] `StarFieldBackground()` - Add particle adjustment

### PlanetAnimations.kt

**High Priority:**
- [ ] `SolarSystemAnimation()` - Add config, adjust speed
- [ ] `SinglePlanetOrbit()` - Add config
- [ ] `MoonPhasesAnimation()` - Add config

### SacredGeometryAnimations.kt

**High Priority:**
- [ ] `FlowerOfLife()` - Add config
- [ ] `MetatronsCube()` - Add config
- [ ] `SriYantra()` - Add config
- [ ] `GoldenRatioSpiral()` - Add config

### FeedbackAnimations.kt

**Already Optimized:**
- ✅ Uses AnimatedVisibility with proper spring specs
- ✅ Has haptic feedback integration
- [ ] Add config parameter for consistency

## Step-by-Step Migration Example

### Before: fadeInMedium()

```kotlin
fun Modifier.fadeInMedium(
  visible: Boolean = true,
  delay: Int = 0
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = tween(
      durationMillis = AnimationDurations.Medium,
      delayMillis = delay,
      easing = SpiritualEasing.Gentle
    ),
    label = "fadeInMedium"
  )
  this.alpha(alpha)  // ❌ Triggers recomposition
}
```

### After: fadeInMedium()

```kotlin
fun Modifier.fadeInMedium(
  visible: Boolean = true,
  delay: Int = 0,
  config: AnimationConfig = AnimationConfig()  // ✅ Add config
): Modifier = composed {
  val alpha by animateFloatAsState(
    targetValue = if (visible) 1f else 0f,
    animationSpec = config.getAnimationSpec(  // ✅ Use config
      baseDuration = AnimationDurations.Medium,
      easing = SpiritualEasing.Gentle
    ),
    label = "fadeInMedium"
  )
  this.graphicsLayer {  // ✅ Use graphicsLayer
    this.alpha = alpha
  }
}
```

## Testing After Migration

After migrating each function:

1. **Test Normal Mode**: Verify animation works as before
2. **Test Reduce Motion**: Enable accessibility and verify instant behavior
3. **Test Performance**: Check FPS in AnimationPlayground
4. **Test First-Time**: Set `isFirstTime = true` and verify slower timing
5. **Test Particles**: Adjust particle multiplier and verify count changes

## Common Patterns

### Pattern 1: Simple Modifier Animation

```kotlin
fun Modifier.myAnimation(
  config: AnimationConfig = AnimationConfig()
) = composed {
  val value by animateFloatAsState(
    targetValue = target,
    animationSpec = config.getAnimationSpec(
      baseDuration = 400,
      easing = SpiritualEasing.Gentle
    )
  )

  this.graphicsLayer {
    // Apply value to graphics properties
  }
}
```

### Pattern 2: Composable with Particles

```kotlin
@Composable
fun MyParticleEffect(
  config: AnimationConfig = rememberAnimationConfig()
) {
  val particleCount = config.adjustParticleCount(30)

  if (config.reduceMotion) {
    // Show static alternative
    return
  }

  // Render particles
}
```

### Pattern 3: Infinite Animation

```kotlin
fun Modifier.myInfiniteAnimation(
  config: AnimationConfig = AnimationConfig()
) = composed {
  val value = remember { Animatable(0f) }

  LaunchedEffect(Unit) {
    if (!config.reduceMotion) {
      while (true) {
        value.animateTo(
          targetValue = 1f,
          animationSpec = tween(
            durationMillis = config.adjustDuration(1000)
          )
        )
        // Continue animation
      }
    }
  }

  this.graphicsLayer {
    // Apply animated value
  }
}
```

## Performance Checklist

After migration, verify:

- [ ] FPS stays at 60 in AnimationPlayground
- [ ] No janky frames when scrolling
- [ ] Animations work with reduce motion
- [ ] Particle counts adjust with performance tier
- [ ] First-time vs repeated timing works
- [ ] Multiple simultaneous animations are smooth
- [ ] Battery usage is acceptable

## Priority Order

Migrate in this order for maximum impact:

1. **High-frequency animations**: Button presses, fades, scales
2. **List animations**: Stagger effects, scroll reveals
3. **Particle systems**: Chakras, energy flows, sparkles
4. **Complex animations**: Zodiac, planets, sacred geometry
5. **Feedback animations**: Success, error, warning states

## Timeline Estimate

- **Per Simple Function**: 5-10 minutes
- **Per Complex Function**: 15-30 minutes
- **Per Particle System**: 30-60 minutes
- **Total Migration**: 4-8 hours for all files

## Need Help?

- See `OptimizedAnimations.kt` for examples
- Check `AnimationPlayground.kt` for usage patterns
- Test with `AnimationConfig` variations
- Monitor FPS counter for performance issues
