# Background Images Quick Reference

## Deployed Background Images

All background images have been successfully deployed to all 5 Android density folders.

### Available Backgrounds

| Image ID | Resource Name | Description | Status |
|----------|---------------|-------------|--------|
| **003** | `R.drawable.img_003_splash_screen_background` | Splash Screen | ✓ DEPLOYED (webp) |
| **009** | `R.drawable.img_009_home_screen_background` | Home Screen | ✓ DEPLOYED (png) |
| **010** | `R.drawable.img_010_profile_creation_background` | Profile Creation | ✓ DEPLOYED (png) |
| **011** | `R.drawable.img_011_compatibility_screen_background` | Compatibility | ✓ DEPLOYED (png) |
| **013** | `R.drawable.img_013_settings_screen_background` | Settings | ✓ DEPLOYED (png) |
| **014** | `R.drawable.img_014_meditation_chakra_screen_background` | Meditation/Chakra | ✓ DEPLOYED (png) |

---

## Quick Integration Examples

### Basic Usage with SimpleSpiritualBackground

```kotlin
import com.spiritatlas.core.ui.components.SimpleSpiritualBackground
import com.spiritatlas.app.R

@Composable
fun YourScreen() {
    SimpleSpiritualBackground(
        backgroundResourceId = R.drawable.img_009_home_screen_background,
        alpha = 0.4f  // Adjust transparency (0.0 - 1.0)
    ) {
        // Your screen content here
        Column(modifier = Modifier.fillMaxSize()) {
            // ...
        }
    }
}
```

### Screen-Specific Examples

#### Home Screen
```kotlin
SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.img_009_home_screen_background,
    alpha = 0.4f
)
```

#### Profile Creation
```kotlin
SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.img_010_profile_creation_background,
    alpha = 0.3f
)
```

#### Compatibility Screen
```kotlin
SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.img_011_compatibility_screen_background,
    alpha = 0.4f
)
```

#### Settings Screen
```kotlin
SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.img_013_settings_screen_background,
    alpha = 0.3f
)
```

#### Meditation/Chakra Screen
```kotlin
SimpleSpiritualBackground(
    backgroundResourceId = R.drawable.img_014_meditation_chakra_screen_background,
    alpha = 0.5f
)
```

---

## File Locations

All backgrounds are deployed to:
```
app/src/main/res/
├── drawable-mdpi/
├── drawable-hdpi/
├── drawable-xhdpi/
├── drawable-xxhdpi/
└── drawable-xxxhdpi/
```

---

## Deployment Stats

- **Total Files:** 30 (6 backgrounds × 5 densities)
- **Resolution:** 1056x1440 at xxxhdpi, scaled proportionally for other densities
- **Format:** PNG (img_009-014), WebP (img_003)
- **Deployment Date:** 2025-12-10

---

## Alpha Recommendations

| Screen Type | Recommended Alpha | Reasoning |
|-------------|-------------------|-----------|
| Splash | 0.4 | Medium visibility, focus on logo |
| Home | 0.4 | Balanced background presence |
| Profile Creation | 0.3 | Subtle, focus on form fields |
| Compatibility | 0.4 | Balanced, mystical atmosphere |
| Settings | 0.3 | Subtle, focus on options |
| Meditation | 0.5 | Strong presence, immersive |

---

## Testing

To test a background:

1. Add the `SimpleSpiritualBackground` wrapper to your screen
2. Build and run the app: `./gradlew installDebug`
3. Verify the background loads on different device densities
4. Adjust alpha value for optimal readability

---

## Re-deployment

If you need to re-deploy or add new backgrounds:

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python3 tools/image_generation/deploy_backgrounds.py
```

Edit the script to add new background images to the `BACKGROUNDS` list.

---

## Performance Notes

- Backgrounds are loaded on-demand per screen
- Android automatically selects appropriate density
- Consider adding Coil or Glide for advanced image loading
- Current xxxhdpi backgrounds: 926KB - 2.0MB each

---

## For More Details

See: `BACKGROUND_DEPLOYMENT_REPORT.md` for complete deployment information.
