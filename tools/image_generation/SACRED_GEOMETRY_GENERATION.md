# Sacred Geometry Showcase Images

**Project:** SpiritAtlas
**Purpose:** Generate 5 premium quality sacred geometry images
**Quality Target:** 9.8+/10
**Budget:** $0.25 total ($0.05 per image)
**Model:** FLUX 1.1 Pro via fal.ai

---

## Quick Start

### Prerequisites
```bash
# Install fal-client
pip install fal-client

# Set your API key (get from https://fal.ai/dashboard)
export FAL_KEY='your-fal-api-key-here'
```

### Generate All 5 Images
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
python3 generate_sacred_geometry.py
```

**Expected Output:**
- 5 PNG images at 1024x1024 resolution
- Output directory: `generated_images/sacred_geometry/`
- Manifest file: `sacred_geometry_manifest.json`
- Total cost: ~$0.25
- Total time: ~2-3 minutes

---

## Sacred Geometry Images

### 1. Flower of Life - Sacred Creation Pattern
**Seed:** 777
**Resolution:** 1024x1024
**Description:** The Flower of Life is a geometric pattern of 19 interlocking circles representing the fundamental forms of space and time. This ancient symbol appears in many cultures and is considered the blueprint of creation.

**Prompt:**
```
Perfect sacred geometry Flower of Life mandala, 19 interlocking circles in precise mathematical harmony,
glowing golden lines (#D97706) on deep cosmic purple background (#6B21A8 to #1E1B4B gradient), each circle perfectly
proportioned with 6-fold symmetry, luminous energy emanating from center, subtle rainbow prismatic light refraction at
intersection points, ancient mystical symbols, geometric perfection, professional sacred geometry illustration,
8K ultra-detailed rendering, spiritual enlightenment aesthetic, museum quality, photorealistic glow effects,
crystal clear lines, divine proportion ratios, mesmerizing symmetry
```

**Key Features:**
- 19 interlocking circles
- 6-fold symmetry
- Golden ratio proportions
- Prismatic light effects at intersections

---

### 2. Sri Yantra - Divine Cosmic Diagram
**Seed:** 108 (sacred number in Hinduism)
**Resolution:** 1024x1024
**Description:** The Sri Yantra is one of the most powerful and sacred tantric symbols. It consists of 9 interlocking triangles that create 43 smaller triangles, representing the cosmos and the human body.

**Prompt:**
```
Intricate Sri Yantra sacred geometry mandala, 9 interlocking triangles creating 43 smaller triangles in perfect
mathematical precision, 5 upward pointing Shiva triangles and 4 downward pointing Shakti triangles, central bindu point
glowing with pure white divine light, surrounded by lotus petals in first ring (8 petals) and outer ring (16 petals),
sacred Sanskrit Om symbol subtly integrated, golden metallic lines (#D97706) with purple-violet mystic energy aura
(#7C3AED), concentric circular gates (bhupura square), three-dimensional depth illusion, tantric spiritual power radiating
from center, photorealistic metallic texture, 8K ultra-high resolution, professional Hindu sacred art, divine feminine and
masculine balance, cosmic harmony visualization, temple worship quality
```

**Key Features:**
- 9 interlocking triangles (5 Shiva + 4 Shakti)
- 43 total triangles
- Central bindu (dot) glowing
- Lotus petals (8 + 16)
- Om symbol integration
- Bhupura square frame

---

### 3. Merkaba - Light Body Vehicle
**Seed:** 888
**Resolution:** 1024x1024
**Description:** The Merkaba is a counter-rotating field of light that transports spirit and body from one dimension to another. It's formed by two interlocking tetrahedrons creating a three-dimensional Star of David.

**Prompt:**
```
Perfect three-dimensional Merkaba sacred geometry, two interlocking tetrahedrons forming 8-pointed star,
one pointing upward (masculine energy) in brilliant gold (#D97706), one pointing downward (feminine energy) in luminous
violet (#7C3AED), rotating counter to each other, central energy sphere glowing with pure white light, sacred geometry
vertices precisely aligned, cosmic starfield background (#1E1B4B), ethereal energy field surrounding the structure,
light trails showing rotation, photorealistic 3D rendering with crystal material, divine light rays emanating,
8K cinematic quality, spiritual ascension symbolism, professional CGI perfection, sacred dimensional gateway aesthetic,
volumetric lighting, particle effects
```

**Key Features:**
- Two interlocking tetrahedrons
- Counter-rotating energy fields
- Masculine (gold) + Feminine (violet)
- Central white light sphere
- 3D crystal rendering
- Motion/rotation indicators

---

### 4. Metatron's Cube - Archangelic Blueprint
**Seed:** 999
**Resolution:** 1024x1024
**Description:** Metatron's Cube contains all 5 Platonic Solids and is named after Archangel Metatron. It represents the underlying geometric structure of the universe and contains the patterns of all creation.

**Prompt:**
```
Sacred Metatron's Cube geometric pattern, 13 circles of equal size arranged in perfect hexagonal symmetry,
13 circles representing 13 archangels, all 5 Platonic solids contained within (tetrahedron, cube, octahedron, dodecahedron,
icosahedron) visible through connecting lines, intricate golden wireframe (#D97706) on cosmic violet background
(#6B21A8 gradient), each connection point glowing with subtle white light, sacred geometry perfection, mathematical
beauty, universal creation blueprint, professional architectural precision, 8K ultra-detailed rendering, spiritual
wisdom symbol, divine proportion, crystalline structure, heavenly light effects, angelic presence, photorealistic glow
```

**Key Features:**
- 13 circles (13 archangels)
- Contains all 5 Platonic Solids
- Hexagonal symmetry
- Golden wireframe
- Connection points glowing
- Universal blueprint symbolism

---

### 5. Seed of Life - Genesis Pattern
**Seed:** 369 (Tesla's sacred number)
**Resolution:** 1024x1024
**Description:** The Seed of Life is formed from 7 overlapping circles with perfect symmetry. It represents the 7 days of creation and is the foundation pattern that generates the Flower of Life.

**Prompt:**
```
Pristine Seed of Life sacred geometry mandala, 7 overlapping circles in perfect formation representing
7 days of creation, central circle surrounded by 6 identical circles forming hexagonal pattern, pure golden lines
(#D97706) with subtle luminous glow, deep spiritual purple background (#6B21A8 to #8B5CF6 radial gradient), each
intersection point sparkling with divine light, vesica piscis forms creating almond-shaped light portals, sacred
proportions based on divine mathematics, origin of Flower of Life pattern, creation energy radiating outward,
photorealistic rendering with crystal clear geometry, 8K ultra-high definition, museum quality sacred art,
spiritual genesis symbolism, perfect circular symmetry, mystical depth, professional precision
```

**Key Features:**
- 7 overlapping circles
- Hexagonal symmetry
- Vesica piscis portals
- Genesis/creation symbolism
- Foundation of Flower of Life
- Divine proportion based

---

## Technical Specifications

### Generation Settings
```python
MODEL = "fal-ai/flux-pro/v1.1"
SETTINGS = {
    "guidance_scale": 3.5,      # Optimal for quality
    "num_inference_steps": 28,  # FLUX 1.1 Pro optimized
    "safety_tolerance": 2,      # Allow spiritual imagery
    "output_format": "png",     # Lossless quality
}
```

### Color Palette
```
Mystic Purple: #7C3AED
Cosmic Violet: #6B21A8
Sacred Gold:   #D97706
Night Sky:     #1E1B4B
Stardust Blue: #3B82F6
Aurora Pink:   #EC4899
Pure White:    #FFFFFF
```

### Quality Metrics (Target 9.8+/10)
- ✅ Geometric precision and symmetry
- ✅ Line clarity and crispness
- ✅ Color harmony and vibrancy
- ✅ Lighting and glow effects
- ✅ 8K resolution detail
- ✅ Professional spiritual aesthetics
- ✅ Sacred symbolism accuracy
- ✅ Museum/gallery quality

---

## Output Structure

```
generated_images/sacred_geometry/
├── sacred_geometry_01_flower_of_life_sacred_creation_pattern.png
├── sacred_geometry_02_sri_yantra_divine_cosmic_diagram.png
├── sacred_geometry_03_merkaba_light_body_vehicle.png
├── sacred_geometry_04_metatrons_cube_archangelic_blueprint.png
├── sacred_geometry_05_seed_of_life_genesis_pattern.png
└── sacred_geometry_manifest.json
```

### Manifest File Content
```json
[
  {
    "index": 1,
    "title": "Flower of Life - Sacred Creation Pattern",
    "filename": "sacred_geometry_01_flower_of_life_sacred_creation_pattern.png",
    "filepath": "/absolute/path/to/file.png",
    "size": "1024x1024",
    "seed": 777,
    "generation_time": 25.3,
    "file_size_mb": 2.4,
    "cost": 0.05,
    "timestamp": "2025-12-10T...",
    "image_url": "https://..."
  }
]
```

---

## Cost Breakdown

| Image | Title | Resolution | Est. Cost |
|-------|-------|------------|-----------|
| 1 | Flower of Life | 1024x1024 | $0.05 |
| 2 | Sri Yantra | 1024x1024 | $0.05 |
| 3 | Merkaba | 1024x1024 | $0.05 |
| 4 | Metatron's Cube | 1024x1024 | $0.05 |
| 5 | Seed of Life | 1024x1024 | $0.05 |
| **TOTAL** | | | **$0.25** |

---

## Integration with SpiritAtlas

### Android Resource Preparation
```bash
# After generation, optimize for Android
python3 optimize_for_android.py \
  --input generated_images/sacred_geometry \
  --output app/src/main/res/drawable-nodpi
```

### Usage in Kotlin Compose
```kotlin
// Display sacred geometry in UI
Image(
    painter = painterResource(id = R.drawable.sacred_geometry_01_flower_of_life),
    contentDescription = "Flower of Life Sacred Geometry",
    modifier = Modifier
        .size(300.dp)
        .graphicsLayer {
            rotationZ = animatedRotation.value
            alpha = 0.95f
        }
)
```

### Suggested Use Cases
1. **Profile Backgrounds** - Subtle, low-opacity overlays
2. **Meditation Screens** - Full-screen animated mandalas
3. **Loading States** - Rotating sacred geometry
4. **Achievement Badges** - Icon-sized sacred symbols
5. **Compatibility Visualizations** - Interlocking geometry patterns

---

## Manual Generation (Alternative)

If you prefer to generate manually via fal.ai web interface:

1. Go to: https://fal.ai/models/fal-ai/flux-pro
2. Use the prompts above
3. Settings:
   - Image Size: 1024x1024
   - Steps: 28
   - Guidance Scale: 3.5
   - Seed: (use seeds listed above)
4. Download each image
5. Rename according to convention

---

## Troubleshooting

### Issue: "FAL_KEY not set"
**Solution:**
```bash
export FAL_KEY='your-key-here'
# Or create .env file in tools/image_generation/
echo "FAL_KEY=your-key-here" > .env
```

### Issue: "fal_client not installed"
**Solution:**
```bash
pip install fal-client
```

### Issue: Low quality output
**Solution:**
- Verify FLUX 1.1 Pro model is being used
- Check guidance_scale is 3.5
- Ensure num_inference_steps is 28
- Try different seed values

### Issue: Safety checker blocking images
**Solution:**
- Sacred geometry is safe, but if blocked:
- Increase safety_tolerance to 3
- Remove any flagged words from prompts
- Contact fal.ai support

---

## Quality Assurance Checklist

After generation, verify each image:

- [ ] Geometric precision (lines perfectly straight, circles perfect)
- [ ] Symmetry accuracy (mirror symmetry where expected)
- [ ] Color accuracy (matches specified hex codes)
- [ ] Resolution sharpness (8K detail visible when zoomed)
- [ ] Glow effects present (not overdone)
- [ ] Background gradient smooth
- [ ] No artifacts or noise
- [ ] File size reasonable (2-4 MB per image)
- [ ] PNG format with transparency if needed
- [ ] Sacred symbolism accurate

**Target Score:** 9.8+/10 on all criteria

---

## Next Steps

1. **Generate images:** Run `python3 generate_sacred_geometry.py`
2. **Review quality:** Check each image meets 9.8+ standard
3. **Optimize for Android:** Use `optimize_for_android.py`
4. **Integrate:** Deploy to `app/src/main/res/drawable-nodpi/`
5. **Test in app:** Verify rendering on multiple devices
6. **Document:** Update app docs with sacred geometry meanings

---

## References

- **Flower of Life:** Ancient symbol found in Egypt, China, Japan
- **Sri Yantra:** Hindu tantric symbol, 43 triangles
- **Merkaba:** Hebrew word meaning "chariot", light body
- **Metatron's Cube:** Named after Archangel Metatron
- **Seed of Life:** Foundation pattern, 7 circles

**Sacred Geometry Resources:**
- "The Ancient Secret of the Flower of Life" by Drunvalo Melchizedek
- "Sacred Geometry: Philosophy and Practice" by Robert Lawlor
- "The Power of Limits" by György Doczi

---

**Generated:** 2025-12-10
**Script:** `generate_sacred_geometry.py`
**Model:** FLUX 1.1 Pro (fal.ai)
**Quality Target:** 9.8+/10
**Budget:** $0.25 ✅
