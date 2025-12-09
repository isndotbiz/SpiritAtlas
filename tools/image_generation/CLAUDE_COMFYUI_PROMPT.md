# Prompt for Claude to Generate SpiritAtlas Images via ComfyUI

**Purpose:** This prompt should be given to another Claude instance that has access to a ComfyUI server to generate all 99 SpiritAtlas spiritual UI assets.

---

## INSTRUCTIONS FOR CLAUDE

You are working with a ComfyUI server that has Flux models and LoRAs installed for generating spiritual/mystical artwork. Your task is to generate 99 high-quality images for the SpiritAtlas Android application.

### System Configuration

**Hardware:**
- 24GB GPU (RTX 3090/4090)
- Windows environment
- ComfyUI installed with Flux model support

**Available Models:**
- Flux1-schnell (4-8 steps, fast, Apache 2.0 commercial license) - RECOMMENDED
- Flux1.dev (35-50 steps, high quality, non-commercial license)

**Available LoRAs:**
1. **Glowing Bioluminescent World** - For chakra, aura, and energy effects
2. **Occult LoRA** - For sacred geometry and mystical symbols
3. **Cosmic Background** - For nebula and space atmospheres

**Required Settings:**
- CFG Scale: 1.0 (Flux requirement)
- Guidance Scale: 3.5
- Sampler: Euler or DPM++ 2M
- Format: PNG

### Your Tasks

1. **Load the Prompt Library**
   - Read the file: `IMAGE_GENERATION_PROMPTS_99_LOCAL.md`
   - This contains all 99 prompts organized in 11 categories

2. **Configure ComfyUI Workflow**
   - Set up batch generation workflow with Flux model
   - Configure LoRA loading nodes as specified per prompt
   - Set output directory: `generated_images/local_flux/`
   - Enable automatic file naming with category prefixes

3. **Generate Images in Batches**
   - Process one category at a time (11 categories total)
   - Monitor VRAM usage between batches
   - Save outputs with descriptive filenames
   - Track progress and any errors

4. **Quality Assurance**
   - Verify each image meets quality standards
   - Check transparency where specified (PNG alpha channel)
   - Verify colors match the SpiritAtlas palette
   - Re-generate any failed or low-quality images

5. **Organization**
   - Create category subfolders: `01_app_icons/`, `02_buttons/`, etc.
   - Generate manifest JSON with metadata for each image
   - Document any deviations or adjustments made

### SpiritAtlas Color Palette (Must Be Accurate)

```
Purples:
- #6B21A8 (Cosmic Violet)
- #7C3AED (Mystic Purple) - PRIMARY
- #8B5CF6 (Spiritual Purple)

Blues:
- #1E1B4B (Night Sky) - DARK BASE
- #3B82F6 (Stardust Blue)
- #14B8A6 (Water Teal)

Golds:
- #D97706 (Sacred Gold) - ACCENT PRIMARY
- #FBBF24 (Aura Gold)
- #B45309 (Temple Bronze)

Pinks:
- #EC4899 (Aurora/Tantric Rose)
- #F87171 (Sensual Coral)

Elements:
- #F97316 (Fire Orange)
- #22C55E (Earth Green)
- #06B6D4 (Air Cyan)

Chakra Colors:
- #EF4444 (Root Red)
- #F97316 (Sacral Orange)
- #FBBF24 (Solar Plexus Yellow)
- #22C55E (Heart Green)
- #3B82F6 (Throat Blue)
- #6366F1 (Third Eye Indigo)
- #8B5CF6 (Crown Violet)
```

### LoRA Usage Guidelines

**For each prompt, check the LoRA specification:**

```yaml
Example:
LoRA: Glowing Bioluminescent (0.7) + Occult (0.6)

This means:
- Load LoRA 1: "Glowing Bioluminescent World" at strength 0.7
- Load LoRA 2: "Occult LoRA" at strength 0.6
- Chain them in sequence in ComfyUI
```

**LoRA Stacking Rules:**
- Maximum 3 LoRAs per image
- Keep combined strength under 1.8
- Load in order specified (most important first)
- If no LoRA specified, generate with base Flux model only

### Generation Settings by Category

| Category | Model | Steps | LoRA Priority |
|----------|-------|-------|---------------|
| App Icons | schnell | 6 | Occult + Bioluminescent |
| Buttons | schnell | 4 | None or minimal |
| Backgrounds | dev | 40 | Cosmic + Bioluminescent |
| Avatars | schnell | 6 | Cosmic |
| Chakras | dev | 45 | Bioluminescent + Occult |
| Sacred Geometry | dev | 50 | Occult primary |
| Zodiac | schnell | 8 | Cosmic |
| Elements | dev | 40 | Bioluminescent |
| Moon Phases | schnell | 6 | Cosmic + Bioluminescent |
| Spiritual Symbols | dev | 45 | Occult + Bioluminescent |
| UI States | schnell | 4 | Minimal |

### File Naming Convention

```
{category_number}_{item_number}_{description}.png

Examples:
01_01_primary_app_icon_lotus_1024x1024.png
02_01_primary_button_glassmorphic_360x120.png
05_01_root_chakra_muladhara_512x512.png
```

### Progress Tracking

Create a progress log as you generate:

```json
{
  "total_images": 99,
  "completed": 0,
  "failed": [],
  "categories": {
    "01_app_icons": {
      "total": 10,
      "completed": 0,
      "images": []
    },
    // ... etc
  },
  "start_time": "2025-12-09T12:00:00Z",
  "estimated_completion": "2025-12-09T13:00:00Z"
}
```

### Expected Output Structure

```
generated_images/local_flux/
├── 01_app_icons/
│   ├── 01_01_primary_app_icon_lotus_1024x1024.png
│   ├── 01_02_primary_app_icon_merkaba_1024x1024.png
│   └── ... (10 images)
├── 02_buttons/
│   ├── 02_01_primary_button_glassmorphic_360x120.png
│   └── ... (12 images)
├── 03_backgrounds/
│   ├── 03_01_home_screen_spiral_galaxy_1080x1920.png
│   └── ... (12 images)
├── 04_avatars/
│   └── ... (10 images)
├── 05_chakras/
│   └── ... (7 images)
├── 06_sacred_geometry/
│   └── ... (8 images)
├── 07_zodiac/
│   └── ... (12 images)
├── 08_elements/
│   └── ... (5 images)
├── 09_moon_phases/
│   └── ... (8 images)
├── 10_spiritual_symbols/
│   └── ... (6 images)
├── 11_ui_states/
│   └── ... (9 images)
├── manifest.json
└── generation_log.json
```

### Quality Checklist (Verify Each Image)

- [ ] Correct dimensions as specified
- [ ] Colors match SpiritAtlas palette
- [ ] Transparency where specified (check alpha channel)
- [ ] No artifacts or distortions
- [ ] Sacred geometry is precise and symmetrical
- [ ] Glowing effects are subtle, not overwhelming
- [ ] Text is readable (for wordmarks)
- [ ] File size is reasonable (under 2MB for most)
- [ ] LoRA effects are visible but not overdone

### Error Handling

**If generation fails:**
1. Log the error with prompt and settings
2. Try reducing LoRA strength by 0.1
3. Try switching from dev to schnell or vice versa
4. Try generating without LoRAs as fallback
5. Document any changes made

**If quality is poor:**
1. Increase steps by 10-15
2. Adjust guidance scale (try 3.0-4.0 range)
3. Try different LoRA combinations
4. Regenerate with adjusted prompt emphasis

### Completion Report

After generating all 99 images, provide a summary:

```markdown
## SpiritAtlas Image Generation Complete

**Total Images Generated:** 99/99
**Success Rate:** 100%
**Total Time:** 45 minutes
**Average Time per Image:** 27 seconds

### Statistics by Category:
1. App Icons & Branding: 10/10 ✓
2. Buttons & Interactive: 12/12 ✓
3. Backgrounds: 12/12 ✓
4. Avatars & Frames: 10/10 ✓
5. Chakra Visualizations: 7/7 ✓
6. Sacred Geometry: 8/8 ✓
7. Zodiac Constellations: 12/12 ✓
8. Element Symbols: 5/5 ✓
9. Moon Phases: 8/8 ✓
10. Spiritual Symbols: 6/6 ✓
11. UI State Elements: 9/9 ✓

### Quality Issues:
- Image 06_03 (Metatron's Cube): Regenerated once - geometry precision improved
- Image 10_02 (Om Symbol): Adjusted LoRA strength from 0.7 to 0.6

### Recommendations:
- All images ready for production use
- Consider upscaling 256x256 and 200x200 images to 512x512 for better quality
- Compress all PNGs with pngquant for smaller file sizes
```

### Important Notes

1. **Commercial Licensing:** Use Flux1-schnell (Apache 2.0) for all images to ensure commercial use rights for Play Store
2. **Consistency:** Maintain visual consistency across all images - same style, color accuracy, quality level
3. **Sacred Accuracy:** Sacred geometry must be mathematically precise (flower of life, metatron's cube, etc.)
4. **Transparency:** Many icons require transparent backgrounds - verify alpha channel exists
5. **Android Compatibility:** All images must work on dark and light backgrounds

### Reference Files Location

All reference files are in: `/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/`

- **Prompts:** `IMAGE_GENERATION_PROMPTS_99_LOCAL.md`
- **Flux Installation Guide:** `FLUX_LOCAL_INSTALLATION_GUIDE.md`
- **LoRA Guide:** `FLUX_LORA_SPIRITUAL_GUIDE.md`
- **ComfyUI Batch Guide:** `COMFYUI_FLUX_BATCH_GUIDE.md`
- **Flux Prompting Guide:** `FLUX_PROMPTING_GUIDE.md`

---

## BEGIN EXECUTION

Start by confirming you have:
1. ✓ Access to ComfyUI server
2. ✓ Flux models loaded (schnell and/or dev)
3. ✓ LoRAs downloaded and installed
4. ✓ Sufficient VRAM (24GB)
5. ✓ Output directory created

Then proceed with batch generation, starting with Category 1 (App Icons & Branding).

Report progress every 10 images and alert if any issues arise.

**EXECUTE NOW**
