# Chakra Image Usage Guide

## Quick Reference

All 7 chakra images are now deployed and ready to use in the SpiritAtlas app.

## File Locations

**Drawable Resources:**
- `/app/src/main/res/drawable-mdpi/img_046-052_*.webp`
- `/app/src/main/res/drawable-hdpi/img_046-052_*.webp`
- `/app/src/main/res/drawable-xhdpi/img_046-052_*.webp`
- `/app/src/main/res/drawable-xxhdpi/img_046-052_*.webp`
- `/app/src/main/res/drawable-xxxhdpi/img_046-052_*.webp`

**Resource Manager:**
- `/app/src/main/java/com/spiritatlas/app/resources/SpiritualResources.kt`

## Chakra Mapping

| Number | Chakra | Sanskrit | File ID | Resource Name |
|--------|--------|----------|---------|---------------|
| 1 | Root | Muladhara | 046 | `img_046_root_chakra_muladhara` |
| 2 | Sacral | Svadhisthana | 047 | `img_047_sacral_chakra_svadhisthana` |
| 3 | Solar Plexus | Manipura | 048 | `img_048_solar_plexus_chakra_manipura` |
| 4 | Heart | Anahata | 049 | `img_049_heart_chakra_anahata` |
| 5 | Throat | Vishuddha | 050 | `img_050_throat_chakra_vishuddha` |
| 6 | Third Eye | Ajna | 051 | `img_051_third_eye_chakra_ajna` |
| 7 | Crown | Sahasrara | 052 | `img_052_crown_chakra_sahasrara` |

## Usage in Code

### Using SpiritualResources (Recommended)

```kotlin
import com.spiritatlas.app.resources.SpiritualResources
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun ChakraDisplay(chakraNumber: Int) {
    // Get resource ID for chakra
    val imageRes = SpiritualResources.getChakraResource(chakraNumber)
    val chakraName = SpiritualResources.getChakraName(chakraNumber)

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = chakraName
    )
}
```

### Direct Resource Access

```kotlin
import com.spiritatlas.app.R

// Direct access (not recommended - use SpiritualResources instead)
val rootChakra = R.drawable.`img_046_root_chakra_muladhara`
val sacralChakra = R.drawable.`img_047_sacral_chakra_svadhisthana`
```

### Loop Through All Chakras

```kotlin
@Composable
fun AllChakrasDisplay() {
    Column {
        (1..7).forEach { chakraNumber ->
            ChakraItem(
                imageRes = SpiritualResources.getChakraResource(chakraNumber),
                name = SpiritualResources.getChakraName(chakraNumber)
            )
        }
    }
}
```

### With LazyColumn

```kotlin
@Composable
fun ChakraList() {
    LazyColumn {
        items(7) { index ->
            val chakraNumber = index + 1
            ChakraCard(
                painter = painterResource(
                    id = SpiritualResources.getChakraResource(chakraNumber)
                ),
                title = SpiritualResources.getChakraName(chakraNumber)
            )
        }
    }
}
```

## Integration Examples

### Meditation Screen

```kotlin
@Composable
fun MeditationScreen(currentChakra: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background chakra visualization
        Image(
            painter = painterResource(
                id = SpiritualResources.getChakraResource(currentChakra)
            ),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = SpiritualResources.getChakraName(currentChakra),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
```

### Profile Chakra Display

```kotlin
@Composable
fun ProfileChakraSection(dominantChakra: Int) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(
                    id = SpiritualResources.getChakraResource(dominantChakra)
                ),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            Column {
                Text("Dominant Chakra")
                Text(
                    text = SpiritualResources.getChakraName(dominantChakra),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
```

### Compatibility Analysis

```kotlin
@Composable
fun ChakraCompatibility(
    person1Chakra: Int,
    person2Chakra: Int,
    compatibilityScore: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ChakraWithLabel(person1Chakra)

        CompatibilityIndicator(compatibilityScore)

        ChakraWithLabel(person2Chakra)
    }
}

@Composable
fun ChakraWithLabel(chakraNumber: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(
                id = SpiritualResources.getChakraResource(chakraNumber)
            ),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = SpiritualResources.getChakraName(chakraNumber),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
```

## Best Practices

1. **Always use SpiritualResources:** Don't hardcode R.drawable references
2. **Provide content descriptions:** For accessibility (or null for decorative)
3. **Use appropriate sizes:** Chakra images are 1024x1024, scale appropriately
4. **Cache painters:** Use `rememberPainter` for repeated renders
5. **Handle edge cases:** SpiritualResources returns default for invalid inputs

## Testing

```kotlin
@Test
fun testChakraResourceMapping() {
    // All chakras should map to valid resource IDs
    (1..7).forEach { chakraNumber ->
        val resourceId = SpiritualResources.getChakraResource(chakraNumber)
        assertTrue(resourceId != 0, "Chakra $chakraNumber should have valid resource")
    }
}

@Test
fun testChakraNames() {
    val expectedNames = listOf(
        "Root (Muladhara)",
        "Sacral (Svadhisthana)",
        "Solar Plexus (Manipura)",
        "Heart (Anahata)",
        "Throat (Vishuddha)",
        "Third Eye (Ajna)",
        "Crown (Sahasrara)"
    )

    expectedNames.forEachIndexed { index, expected ->
        val chakraNumber = index + 1
        assertEquals(expected, SpiritualResources.getChakraName(chakraNumber))
    }
}
```

## Troubleshooting

### Resource not found error
- Ensure you've synced Gradle after deployment
- Clean and rebuild: `./gradlew clean :app:assembleDebug`
- Check R.txt: `grep chakra app/build/intermediates/runtime_symbol_list/debug/processDebugResources/R.txt`

### Image not displaying
- Verify density folder has the file: `ls app/src/main/res/drawable-xhdpi/ | grep chakra`
- Check content description is set (for accessibility testing)
- Verify image size modifier is appropriate

### Wrong image displaying
- Double-check chakraNumber parameter (1-7, not 0-6)
- Verify mapping in SpiritualResources.kt is correct
- Test with known chakra: `getChakraResource(1)` should return root chakra

## Performance Notes

- **File format:** WebP provides ~70% size reduction vs PNG
- **Quality:** 90% WebP is visually lossless
- **Memory:** Android automatically selects appropriate density
- **Caching:** Compose Image caches painters automatically

## Related Components

- `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ChakraVisualization.kt`
- `/core/ui/src/main/java/com/spiritatlas/core/ui/imaging/ChakraVisualization.kt`
- `/core/ui/src/main/java/com/spiritatlas/core/ui/animation/ChakraAnimations.kt`

---

**Deployed by:** IMAGE DEPLOYMENT AGENT 1
**Date:** 2025-12-10
**Status:** Production Ready
