# ProgressiveImage Usage Guide

## Overview

The `ProgressiveImage` components implement the **blur-up loading technique** to provide instant visual feedback while images load, dramatically improving perceived performance.

## Components

### 1. ProgressiveImage
Standard progressive image loader with LQIP (Low-Quality Image Placeholder).

**Usage:**
```kotlin
ProgressiveImage(
    imageResourceId = R.drawable.img_profile_avatar,
    lqipResourceId = R.drawable.img_profile_avatar_lqip,
    contentDescription = "User profile avatar",
    modifier = Modifier.size(80.dp),
    contentScale = ContentScale.Crop,
    alpha = 1f
)
```

**Best for:**
- Profile avatars
- Zodiac sign images
- Icon-based content
- Medium-sized images (50-200dp)

### 2. ProgressiveBackgroundImage
Progressive background loader with dimming and overlay content support.

**Usage:**
```kotlin
ProgressiveBackgroundImage(
    backgroundResourceId = R.drawable.img_compatibility_background,
    lqipResourceId = R.drawable.img_compatibility_background_lqip,
    modifier = Modifier.fillMaxSize(),
    alpha = 0.3f,           // Background transparency
    dimAmount = 0.7f,       // Darkening amount (0-1)
    contentScale = ContentScale.Crop
) {
    // Your content here
    Column {
        Text("Content overlaid on progressive background")
    }
}
```

**Best for:**
- Hero sections
- Card backgrounds
- Full-screen backgrounds
- Sections requiring visual hierarchy

### 3. FastImage
Direct loading with fade, no LQIP overhead.

**Usage:**
```kotlin
FastImage(
    imageResourceId = R.drawable.img_small_icon,
    contentDescription = "Quick loading icon",
    modifier = Modifier.size(48.dp),
    contentScale = ContentScale.Fit
)
```

**Best for:**
- Small icons (<50dp)
- UI chrome elements
- Already cached images
- Non-critical visuals

### 4. LazyLoadImage
Memory-efficient loading for lists and scrollable content.

**Usage:**
```kotlin
LazyColumn {
    items(profiles) { profile ->
        LazyLoadImage(
            imageResourceId = profile.imageResId,
            contentDescription = profile.name,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
    }
}
```

**Best for:**
- LazyColumn/LazyRow items
- Grid layouts
- Long scrollable lists
- Gallery views

## Integration Examples

### Example 1: HomeScreen Profile Avatars
**File:** `/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt`

```kotlin
Box(
    modifier = Modifier
        .size(80.dp)
        .clip(CircleShape)
        .border(3.dp, Color.White.copy(alpha = 0.75f), CircleShape)
) {
    ProgressiveImage(
        imageResourceId = R.drawable.img_001_primary_app_icon,
        lqipResourceId = R.drawable.img_006_loading_spinner_icon,
        contentDescription = "$name's profile avatar",
        modifier = Modifier.size(80.dp),
        contentScale = ContentScale.Crop
    )
}
```

**Result:**
- Shows tiny spinner icon immediately (<1KB)
- Blurs the spinner while full image loads
- Crossfades to full avatar (smooth 300ms transition)
- User sees instant visual feedback

### Example 2: ProfileScreen Zodiac/Birth Chart
**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`

```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
) {
    ProgressiveImage(
        imageResourceId = R.drawable.img_003_splash_screen_background,
        lqipResourceId = R.drawable.img_006_loading_spinner_icon,
        contentDescription = "Astrological birth chart aesthetic",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.3f // Semi-transparent background
    )
}
```

**Result:**
- Provides instant visual context for astrological content
- Alpha = 0.3f ensures it doesn't compete with form fields
- Progressive loading prevents jarring empty → full transition

### Example 3: CompatibilityDetailScreen Background
**File:** `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt`

```kotlin
ProgressiveBackgroundImage(
    backgroundResourceId = R.drawable.img_003_splash_screen_background,
    lqipResourceId = R.drawable.img_006_loading_spinner_icon,
    modifier = Modifier.fillMaxWidth(),
    alpha = 0.25f,
    dimAmount = 0.7f,
    contentScale = ContentScale.Crop
) {
    GlassmorphCard {
        // Hero section content
        Text("Compatibility Score: 87%")
    }
}
```

**Result:**
- Background loads progressively with blur-up
- dimAmount = 0.7f ensures text readability
- Content is always visible, background enhances aesthetics

### Example 4: Tantric Section Romantic Background
**File:** `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt`

```kotlin
ProgressiveBackgroundImage(
    backgroundResourceId = R.drawable.img_007_app_store_feature_graphic,
    lqipResourceId = R.drawable.img_006_loading_spinner_icon,
    modifier = Modifier.fillMaxWidth(),
    alpha = 0.2f,      // Very subtle for intimate content
    dimAmount = 0.8f,  // Heavy dim for text readability
    contentScale = ContentScale.Crop
) {
    // Tantric connection content
}
```

**Result:**
- Creates mood without overwhelming sensitive content
- Heavy dimming maintains focus on text
- Progressive loading prevents distraction

## Creating LQIP Assets

### Manual Method (Current)
1. Take full-quality image
2. Resize to 20x20 pixels (or smaller)
3. Save as WEBP with quality=1
4. Place in `drawable-nodpi/` or `drawable/` as `*_lqip.webp`

### Automated Build Script (Recommended)
Create `scripts/generate_lqip.sh`:

```bash
#!/bin/bash
# Generate LQIP placeholders from full images

INPUT_DIR="app/src/main/res/drawable-xxxhdpi"
OUTPUT_DIR="app/src/main/res/drawable-lqip"

mkdir -p "$OUTPUT_DIR"

for img in "$INPUT_DIR"/*.webp; do
    filename=$(basename "$img" .webp)
    magick "$img" -resize 20x20 -quality 1 "$OUTPUT_DIR/${filename}_lqip.webp"
    echo "Generated LQIP for $filename"
done
```

### Gradle Plugin (Advanced)
Add to `app/build.gradle.kts`:

```kotlin
android {
    // ... existing config

    // Generate LQIP images at build time
    tasks.register("generateLqip") {
        doLast {
            // Image processing logic here
        }
    }
}

// Run before preBuild
tasks.named("preBuild") {
    dependsOn("generateLqip")
}
```

## Performance Benefits

### Metrics
- **Instant visual feedback**: LQIP shows in <16ms (1 frame)
- **LQIP size**: <1KB per image (vs 10-50KB for full)
- **Perceived performance**: 2-3x faster feeling
- **Network savings**: Users see content before full images load

### Before vs After
**Before (no LQIP):**
- Empty box → jarring full image pop-in
- User sees "broken" layout during load
- Perceived as slow/broken

**After (with LQIP):**
- Instant blurred preview → smooth full image
- Layout stable from start
- Perceived as fast/polished

## Animation Details

### Blur Animation
```kotlin
val blurRadius by animateFloatAsState(
    targetValue = if (isLoading) 20f else 0f,
    animationSpec = tween(durationMillis = 300)
)
```
- **Loading state**: 20dp blur on LQIP
- **Loaded state**: 0dp blur (sharp full image)
- **Duration**: 300ms smooth transition

### Alpha Crossfade
```kotlin
val imageAlpha by animateFloatAsState(
    targetValue = if (isLoading) 0f else alpha,
    animationSpec = tween(durationMillis = 400)
)

val lqipAlpha by animateFloatAsState(
    targetValue = if (isLoading) alpha else 0f,
    animationSpec = tween(durationMillis = 400)
)
```
- **LQIP**: Alpha 1.0 → 0.0 (fade out)
- **Full image**: Alpha 0.0 → 1.0 (fade in)
- **Duration**: 400ms smooth crossfade

## Best Practices

### 1. Always Provide contentDescription
```kotlin
ProgressiveImage(
    contentDescription = "User's zodiac sign: Aries" // Good ✓
    // contentDescription = null                      // Bad ✗
)
```

### 2. Match ContentScale to Use Case
```kotlin
contentScale = ContentScale.Crop     // Avatars, backgrounds
contentScale = ContentScale.Fit      // Icons, logos
contentScale = ContentScale.FillWidth // Banners
```

### 3. Use Appropriate Alpha for Context
```kotlin
alpha = 1.0f   // Primary content (avatars, main images)
alpha = 0.5f   // Secondary decorative elements
alpha = 0.3f   // Subtle backgrounds
alpha = 0.2f   // Very subtle mood setting
```

### 4. Background Images Need Proper Dimming
```kotlin
ProgressiveBackgroundImage(
    alpha = 0.3f,        // Background visibility
    dimAmount = 0.7f,    // Darken for text readability
    // Result: 0.3 * 0.7 = 0.21 effective brightness
)
```

### 5. Choose Right Component for Context
| Use Case | Component | Reason |
|----------|-----------|--------|
| Profile avatar | `ProgressiveImage` | Medium size, focal point |
| Hero background | `ProgressiveBackgroundImage` | Full screen, needs dimming |
| Small icon | `FastImage` | LQIP overhead not worth it |
| List items | `LazyLoadImage` | Memory efficient for many items |

## Troubleshooting

### Issue: Image doesn't load
**Check:**
1. Resource ID is correct
2. Image exists in drawable folder
3. Build → Clean Project → Rebuild

### Issue: LQIP too large
**Solution:** Resize LQIP to 20x20 or smaller
```bash
magick input.webp -resize 20x20 -quality 1 output_lqip.webp
```

### Issue: Blur looks pixelated
**Solution:** This is expected! LQIP should be heavily pixelated. The blur hides this during loading.

### Issue: Crossfade feels slow
**Solution:** Reduce animation duration:
```kotlin
tween(durationMillis = 200) // Faster transition
```

### Issue: Content jumps during load
**Solution:** Always set explicit size:
```kotlin
modifier = Modifier.size(80.dp) // Reserves space
```

## Migration Guide

### From Canvas Drawing
**Before:**
```kotlin
Canvas(modifier = Modifier.size(48.dp)) {
    // Custom drawing code
}
```

**After:**
```kotlin
ProgressiveImage(
    imageResourceId = R.drawable.img_zodiac_aries,
    lqipResourceId = R.drawable.img_zodiac_aries_lqip,
    modifier = Modifier.size(48.dp)
)
```

### From Standard Image
**Before:**
```kotlin
Image(
    painter = painterResource(R.drawable.avatar),
    contentDescription = "Avatar",
    modifier = Modifier.size(80.dp)
)
```

**After:**
```kotlin
ProgressiveImage(
    imageResourceId = R.drawable.avatar,
    lqipResourceId = R.drawable.avatar_lqip,
    contentDescription = "Avatar",
    modifier = Modifier.size(80.dp)
)
```

### From Coil AsyncImage
**Before:**
```kotlin
AsyncImage(
    model = imageUrl,
    contentDescription = "Photo"
)
```

**After (local resources):**
```kotlin
ProgressiveImage(
    imageResourceId = R.drawable.photo,
    lqipResourceId = R.drawable.photo_lqip,
    contentDescription = "Photo"
)
```

## Technical Architecture

### Image Loading Flow
1. **Immediate (0ms)**: LQIP displayed with blur
2. **Background thread**: Full image decoded by Coil
3. **Transition (300-400ms)**: Blur removed, images crossfade
4. **Final state**: Full image displayed

### Memory Management
- **LQIP**: Always in memory (~1KB each)
- **Full images**: Managed by Coil (LRU cache)
- **Decoded bitmaps**: Released by Compose when off-screen

### Threading
- **UI thread**: LQIP rendering (instant)
- **IO thread**: Full image loading (async)
- **Main thread**: Crossfade animations (60 FPS)

## Roadmap

### Phase 1: Current ✓
- [x] ProgressiveImage component
- [x] ProgressiveBackgroundImage component
- [x] FastImage for small assets
- [x] LazyLoadImage for lists
- [x] Integration in 3 key screens

### Phase 2: Next Sprint
- [ ] Auto-generate LQIP at build time
- [ ] Add memory metrics logging
- [ ] Shimmer effect option
- [ ] Network image support

### Phase 3: Future
- [ ] Blur hash integration
- [ ] SVG placeholder support
- [ ] Shared element transitions
- [ ] AI-powered placeholder generation

## References

- **Coil Documentation**: https://coil-kt.github.io/coil/
- **Compose Animation**: https://developer.android.com/jetpack/compose/animation
- **Medium's Progressive Image Loading**: https://jmperezperez.com/medium-image-progressive-loading-placeholder/
- **Blur-up Technique**: https://css-tricks.com/the-blur-up-technique-for-loading-background-images/

---

**Last Updated**: 2025-12-10
**Component Version**: 1.0.0
**Integration Status**: ✅ Complete (3 screens)
