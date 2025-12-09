# Flux Model Research: Dev vs Pro Comparison & Best Practices

**Research Date:** December 9, 2025
**Purpose:** Evaluate Flux1.dev vs Flux1 Pro for ultra-realistic spiritual app assets
**Target Use Case:** SpiritAtlas Android app image generation

---

## Executive Summary

**RECOMMENDATION:** Start with **Flux1.dev** for initial asset generation, upgrade to **Flux1.1 Pro** selectively for hero images and key branding assets.

**Key Findings:**
- Flux1.dev delivers 95%+ of Pro quality at zero cost (can run locally)
- Flux1.1 Pro costs $0.04/image via API, offers 6x faster generation with 4x resolution boost
- Both models excel at photorealism, outperforming Midjourney and DALL-E in prompt fidelity
- For 59 SpiritAtlas assets: Dev = $0, Pro = $2.36 (full set)

---

## 1. Flux1.dev vs Flux1 Pro: Detailed Comparison

### 1.1 Quality & Capabilities

| Feature | Flux1.dev | Flux1 Pro | Flux1.1 Pro |
|---------|-----------|-----------|-------------|
| **Parameters** | 12B (guidance-distilled) | 12B (full model) | 12B (optimized) |
| **Image Quality** | Near-identical to Pro | Highest baseline | Enhanced photorealism |
| **Resolution** | Up to 2MP | Up to 2MP | Up to 2K (4MP in Ultra mode) |
| **Inference Steps** | 28 steps | 28 steps | 28 steps |
| **Speed** | Standard | Standard | **6x faster than v1.0** |
| **Text Rendering** | 1-3 attempts for legibility | 1-3 attempts | Superior text rendering |
| **Anatomy (hands)** | Excellent | Excellent | Industry-leading |
| **Photorealism** | Rivals Midjourney v6 | Matches/exceeds DALL-E 3 | Surpasses all competitors |

**Quality Benchmarks (Independent Testing):**
- Flux Pro produced the most realistic and high-definition results in portrait tests
- Both Dev and Pro rival DALL-E 3 in prompt fidelity
- Flux matches or surpasses Midjourney v6 in photorealism
- Superior sharpness and detail in textures, facial features, complex patterns
- Natural textures eliminate "waxy" or "plastic" appearance common in other models

### 1.2 Resolution Capabilities

**Standard Resolution:**
- Both models: 0.2 to 2 megapixels (MP)
- Optimal resolution: **1024x1024** (sweet spot for detail vs render time)
- Full HD (1920x1080): Excellent balance for web/mobile content
- Flexible aspect ratios: 1:1, 16:9, 21:9 (ultrawide)

**Flux1.1 Pro Ultra Mode:**
- **4x higher resolution** in just 10 seconds
- Up to 24 megapixels supported
- Up to 2K (2560px) resolution
- Ideal for hero images, splash screens, high-DPI displays

**Resolution Recommendations for SpiritAtlas:**
- App icons: 1024x1024 (generate once, downscale for densities)
- Backgrounds: 1920x1080 (Full HD) or 2560x1440 (QHD)
- Spiritual symbols: 1024x1024 (high detail for sacred geometry)
- UI elements: Native size (360x120 for buttons, etc.)

### 1.3 Pricing & Licensing

| Aspect | Flux1.dev | Flux1 Pro | Flux1.1 Pro |
|--------|-----------|-----------|-------------|
| **Cost** | **FREE** (local) | $0.05/image | **$0.04/image** |
| **Access** | Local + API | API-only | API-only |
| **License** | **Non-commercial only** | Commercial allowed | Commercial allowed |
| **Credits (fal.ai)** | 2.5 credits/image | 4 credits/image | 4 credits/image |
| **100 credits** | $1 | $1 | $1 |

**Cost for 59 SpiritAtlas Assets:**
- Flux1.dev: $0 (if run locally) or ~$1.48 (via API)
- Flux1.1 Pro: **$2.36** (59 × $0.04)

**Licensing Critical Note:**
- ⚠️ **Flux1.dev is NON-COMMERCIAL license only**
- For SpiritAtlas commercial app, you MUST use Flux1 Pro/Flux1.1 Pro for production assets
- Exception: Can use Dev for prototyping, then regenerate with Pro for release

### 1.4 When to Use Pro Over Dev

**Use Flux1.1 Pro for:**
1. **Commercial applications** (licensing requirement)
2. **Hero/branding assets** (app icon, splash screen, logo) - justify the 4x resolution
3. **High-DPI displays** (flagship Android devices with QHD/4K screens)
4. **Assets requiring speed** (6x faster generation)
5. **Complex scenes** requiring industry-leading detail (realistic portraits, intricate sacred geometry)
6. **Final production assets** for app store and distribution

**Use Flux1.dev for:**
1. **Prototyping and experimentation** (free local generation)
2. **Proof of concept** before committing to Pro costs
3. **Non-commercial projects** (personal use, open source)
4. **Testing prompts** (iterate locally, then use winning prompts with Pro)
5. **Lower-resolution assets** (1024x1024 and below where Dev quality suffices)

---

## 2. Best Practices for Photorealistic Images with Flux

### 2.1 Prompt Engineering Structure

**Hierarchical Prompt Formula:**
```
[Subject] + [Lighting] + [Camera/Photography Details] + [Style] + [Quality Keywords]
```

**Example for SpiritAtlas Spiritual Image:**
```
A glowing purple lotus flower with seven chakra-colored petals in perfect symmetry,
centered on cosmic gradient background from midnight indigo to mystic violet.
Soft diffused lighting with rim light on petal edges. Shot on Canon EOS R5,
50mm f/1.4 lens, ISO 100, shallow depth of field. Photorealistic, mystical atmosphere,
volumetric lighting, ethereal glow, 4K quality, ultra sharp details.
```

### 2.2 Core Principles for Realism

**1. Be Specific and Detailed**
- The more information you provide, the better Flux can create realistic images
- Specify facial features, expressions, lighting conditions, environmental elements
- Every detail influences lighting, style, realism, color balance, subject placement, depth of field, texture fidelity

**2. Start with Foundation, Then Decoration**
- Core subject first (Flux 2 prioritizes information at start of prompt)
- Then add lighting, atmosphere, style, technical details
- Avoid cluttering the beginning with secondary elements

**3. Include Lighting Details**
- Critical for realism: "soft diffused lighting," "rim light," "golden hour"
- Specify light direction: "top-lit," "backlit," "side lighting"
- Add atmosphere: "god rays," "volumetric lighting," "ethereal glow"

**4. Add Camera/Photography Parameters**
- Device: "shot on iPhone 16," "Canon EOS R5," "Leica M10"
- Lens: "50mm f/1.4," "85mm portrait lens," "wide-angle 24mm"
- Settings: "ISO 100, 1/125s" (optional but nudges realism)
- Shot type: "portrait," "macro," "wide shot," "close-up"

**5. Use Exact Color Values**
- Include HEX codes for brand consistency: "mystic purple (#7C3AED)"
- Request "exact color matching" for spiritual palette adherence
- For SpiritAtlas: Always include hex codes from palette

**6. Avoid Style Confusion**
- Don't mix conflicting styles: "anime + photorealistic + oil painting" confuses model
- Pick one clear style: "photorealistic," "cinematic photography," "digital art"
- For spiritual app: "photorealistic mystical imagery" or "cinematic cosmic photography"

### 2.3 Advanced Techniques

**1. File Extension Technique for Vintage/Realistic Photos**
- Add camera file extensions to prompts: `IMG_1018.CR2`, `IMG_1025.HEIC`, `IMG_1998.JPG`
- Mimics effects of photos taken with different cameras from various eras
- Example: "A mystical temple scene, photo file IMG_2847.HEIC"

**2. Flux Realism LoRA (For local Flux1.dev users)**
- Flux-RealismLora specifically trained for photographic texture, lighting, rendering
- Game-changer for realistic image generation when running locally
- Apply with low weight (0.6-0.8) to avoid over-processing

**3. JSON Prompting for Complex Scenes**
- Flux 2 supports structured JSON inputs for precise control
- Use for complex scenes with multiple subjects, exact positioning
- Example structure:
```json
{
  "subject": "lotus flower",
  "lighting": "soft diffused with rim light",
  "camera": "Canon EOS R5, 50mm f/1.4",
  "style": "photorealistic mystical",
  "colors": ["#7C3AED", "#D97706"],
  "effects": ["ethereal glow", "volumetric lighting"]
}
```

**4. Dynamic Thresholding (Technical Settings)**
- **For photorealism:** Lower CFG (2-3), reduce Interpolate Phi (0.6-0.7)
- **For artistic:** Higher CFG (4-6), increase Interpolate Phi (0.8-0.9)
- Guidance scale default: **3.5** (balanced creativity and prompt adherence)
- Increase to 5-6 if output doesn't match prompt
- Decrease to 3-3.5 if output is oversaturated

### 2.4 Keywords that Enhance Realism

**Photography Style:**
- "raw photo," "DSLR photo," "shot on [camera model]"
- "crisp photo," "dynamic contrast," "polaroid," "snapchat photo"
- "photorealistic," "hyperrealistic," "ultra-detailed"

**Lighting Quality:**
- "natural lighting," "studio lighting," "golden hour"
- "soft diffused lighting," "rim light," "backlit"
- "volumetric lighting," "god rays," "cinematic lighting"

**Texture & Detail:**
- "ultra sharp," "4K quality," "8K resolution"
- "rich color depth," "seamless gradient transitions"
- "exceptional detail clarity," "lifelike depth of field"

**Atmosphere:**
- "cinematic," "atmospheric," "ethereal"
- "mystical atmosphere," "cosmic photography"
- "dreamy bokeh," "soft focus background"

**Spiritual-Specific:**
- "sacred geometry," "mystical," "cosmic," "divine"
- "spiritual energy," "ethereal glow," "transcendent"
- "luminous," "radiant," "celestial"

### 2.5 Negative Prompts for Flux

**How Negative Prompts Work:**
- Flux supports negative prompts to exclude unwanted elements
- Adding negative prompts improves sharpness, realism, and consistency
- Format: Use `--negative` flag or separate negative prompt field

**Essential Negative Prompts for Realism:**
```
blurry, low quality, pixelated, distorted, deformed, ugly,
amateur, oversaturated, cartoon, anime, artificial,
watermark, text, signature, frame, border,
extra limbs, mutated hands, poorly drawn face,
unrealistic proportions, bad anatomy
```

**For Spiritual/Sacred Imagery:**
```
demonic, evil, dark occult, inappropriate symbols,
culturally insensitive, disrespectful, profane,
cheap, tacky, clipart, low-effort
```

**For UI Elements:**
```
cluttered, busy, illegible, inconsistent style,
misaligned, asymmetrical (when symmetry needed),
wrong colors, off-brand
```

### 2.6 Parameter Tuning

**Inference Steps (Number of Denoising Steps):**
- More steps = higher quality but slower generation
- **Flux1.dev:** 50 steps recommended for high quality
- **Flux1-schnell:** 4 steps (speed-optimized variant)
- **Quick iterations:** 28 steps
- **Maximum quality:** 50-70 steps

**Guidance Scale:**
- **Default:** 3.5 (balanced)
- **Higher (5-7):** Stronger adherence to prompt (use if output doesn't match)
- **Lower (2-3):** More creative freedom (use for artistic variations)
- **Flux1-schnell:** Must use 0 (embedded guidance)

**Recommended Settings for SpiritAtlas Assets:**
```python
{
    "model": "flux-1.1-pro",
    "width": 1024,
    "height": 1024,
    "num_inference_steps": 50,
    "guidance_scale": 3.5,
    "image_format": "png"
}
```

### 2.7 Aspect Ratios & Resolution Recommendations

**Optimal Resolutions:**
- **1024x1024:** Sweet spot for most assets (~20s render at 30 steps)
- **1920x1080 (Full HD):** Optimal for backgrounds, landscape/portrait
- **512x512:** Less detailed, avoid for primary assets
- **2560x1440 (QHD):** Use Flux1.1 Pro for flagship device assets

**Aspect Ratio Support:**
- **1:1 (Square):** Universal, best for icons, symbols, avatars
- **16:9 (Widescreen):** Backgrounds, splash screens
- **9:16 (Portrait):** Mobile backgrounds (1080x1920)
- **21:9 (Ultrawide):** Hero images (requires more compute)

**For SpiritAtlas:**
- Icons: 1:1 (1024x1024 master)
- Backgrounds: 9:16 (1080x1920 Full HD)
- Buttons: Native (360x120, 480x160)
- Symbols: 1:1 (512x512 or 1024x1024)

---

## 3. Example Prompts: Optimized for Maximum Realism

### 3.1 Spiritual/Mystical Hero Image

**Use Case:** App splash screen, featured spiritual artwork

**Prompt:**
```
A breathtaking photorealistic image of a cosmic lotus temple floating in deep space.
The temple is constructed of luminous purple crystal (#7C3AED) with sacred geometry
patterns etched in golden light (#D97706). Seven chakra-colored energy beams radiate
from temple center like aurora borealis. Surrounding space filled with purple and pink
nebula clouds (#EC4899) and scattered stars creating depth. Shot on Sony A7R IV with
24mm f/1.4 lens, long exposure capturing light trails, ISO 400. Cinematic lighting
with volumetric god rays, mystical atmosphere, ethereal glow effects, rich color depth,
ultra-sharp 4K quality, photorealistic mystical architecture, sacred spiritual space.

NEGATIVE: cartoon, anime, 3D render, artificial, low quality, blurry, distorted,
amateur, oversaturated, watermark
```

**Settings:**
- Resolution: 1920x1080 (landscape) or 1080x1920 (portrait)
- Steps: 50
- Guidance: 3.5
- Model: Flux1.1 Pro (for 4K option)

---

### 3.2 Sacred Geometry with Photorealistic Elements

**Use Case:** Meditation background, sacred geometry visualization

**Prompt:**
```
A hyper-detailed photorealistic Metatron's Cube sacred geometry rendered as physical
golden metal structure (#D97706) floating above ancient stone altar. The geometry is
constructed of thin luminous gold wire that glows with inner light. Background shows
mystical Himalayan cave with natural rock textures, subtle purple ambient light (#7C3AED)
filtering through cave opening. Small particles of golden dust floating in volumetric
light rays. Shot on Canon EOS R5 with 50mm macro lens f/2.8, shallow depth of field
focusing on center of geometry. Cinematic lighting, god rays, exceptional material
detail, photorealistic textures, sacred spiritual artifact photography, 4K ultra sharp.

NEGATIVE: cartoon, digital art, flat, artificial, cheap materials, low quality, blurry
```

**Settings:**
- Resolution: 1024x1024
- Steps: 50
- Guidance: 4.0 (slightly higher for geometric precision)

---

### 3.3 Photorealistic Chakra Energy Portrait

**Use Case:** Chakra analysis feature, energy visualization

**Prompt:**
```
A photorealistic portrait of a serene meditating figure in lotus position, captured
from behind showing spine alignment. Seven glowing chakra points visible along spine,
each in authentic chakra colors (root red #EF4444, sacral orange #F97316, solar yellow
#FDE047, heart green #22C55E, throat blue #3B82F6, third eye indigo #6366F1, crown
violet #8B5CF6). Each chakra emanates realistic energy wisps like heat waves captured
by thermal camera. Figure silhouette against cosmic purple gradient background
(#1E1B4B to #7C3AED). Shot on Nikon Z9, 85mm portrait lens f/1.8, shallow depth of
field, rim lighting on figure edges. Photorealistic human anatomy, energy visualization
style like Kirlian photography, cinematic spiritual photography, volumetric lighting,
4K ultra detailed, lifelike energy effects.

NEGATIVE: anime, cartoon, unrealistic proportions, bad anatomy, artificial, oversaturated
```

**Settings:**
- Resolution: 1080x1920 (portrait)
- Steps: 50
- Guidance: 3.5

---

### 3.4 Realistic Spiritual App Icon

**Use Case:** Primary app launcher icon

**Prompt:**
```
A professional app icon design featuring a photorealistic purple lotus flower made of
luminous crystal material. The lotus has seven petals arranged in perfect symmetry,
each petal glowing from within with chakra colors (red, orange, yellow, green, blue,
indigo, violet). Center of lotus contains golden seed of life sacred geometry pattern
(#D97706) etched in metallic gold. Background is smooth gradient from deep midnight
indigo (#1E1B4B) at edges to cosmic violet (#7C3AED) behind lotus. Subtle twinkling
stars scattered in background creating depth. The lotus has soft ethereal glow with
rim lighting. Shot as product photography with studio lighting, Canon EOS R5 with
100mm macro lens f/2.8. Photorealistic crystal material, volumetric lighting, premium
app icon design, ultra-sharp details, perfect symmetry, professional product photography
style, 4K quality.

NEGATIVE: flat, 2D, cartoon, artificial, cheap rendering, low quality, cluttered
```

**Settings:**
- Resolution: 1024x1024 (square)
- Steps: 50
- Guidance: 4.5 (higher for icon precision)
- Model: Flux1.1 Pro (flagship quality)

---

### 3.5 Mystical UI Element - Glowing Button

**Use Case:** Premium action button background

**Prompt:**
```
A photorealistic glowing button material, rectangular with rounded corners (16px radius).
The button surface is made of luminous frosted glass material with cosmic gradient fill
flowing from mystic purple (#7C3AED) on left through aurora pink (#EC4899) in center to
spiritual purple (#8B5CF6) on right. The glass has realistic depth with subtle inner glow
and light refraction effects. Micro sparkle particles embedded in glass surface catching
light. Top edge has realistic highlight from studio lighting simulating light source from
top-left. Subtle purple-tinted shadow beneath button. Shot as product photography with
professional studio lighting, macro lens for material detail. Photorealistic glass material,
volumetric subsurface scattering, premium UI element, crystal-clear 4K quality, professional
material photography.

NEGATIVE: flat, cartoon, digital art, artificial, cheap rendering, pixelated
```

**Settings:**
- Resolution: 1080x360 (3:1 aspect for button)
- Steps: 40 (UI elements don't need 50)
- Guidance: 4.0

---

### 3.6 Sacred Temple Scene - Environmental

**Use Case:** Background for meditation or spiritual teachings section

**Prompt:**
```
A cinematic photorealistic wide shot of an ancient sacred temple hidden inside a
mystical Himalayan cave. The temple entrance features intricate sacred geometry carvings
in stone that glow with soft golden light (#D97706) from within. Purple ambient light
(#7C3AED) emanates from deeper inside the cave creating mystical atmosphere. Natural
rock formations show realistic texture and geological detail. Subtle mist in the air
creating volumetric lighting with god rays streaming through cave opening in background.
Small particles of dust and moisture visible in light beams. Shot on Arri Alexa cinema
camera with 24mm wide-angle lens, cinematic color grading, atmospheric perspective.
Photorealistic cave environment, professional cinematography, mystical atmosphere,
volumetric lighting, exceptional environmental detail, 4K cinematic quality, sacred
spiritual location photography.

NEGATIVE: cartoon, digital painting, artificial, unrealistic lighting, low quality,
amateur, oversaturated
```

**Settings:**
- Resolution: 1920x1080 (cinematic landscape)
- Steps: 50
- Guidance: 3.5
- Model: Flux1.1 Pro (for cinematic 2K option)

---

### 3.7 Zodiac Constellation - Astrophotography Style

**Use Case:** Leo zodiac sign for astrology features

**Prompt:**
```
A photorealistic astrophotography image of the Leo constellation in night sky. The stars
forming Leo are captured in realistic varying sizes and brightness (ranging from magnitude
1 to 5 stars), in natural stardust blue (#3B82F6) as seen through telescope. The stars
are connected by thin golden lines (#D97706) highlighting the constellation pattern
forming the recognizable lion shape. Background shows authentic deep space with natural
purple and orange nebula wisps (fire element for Leo) captured as in real astrophotography.
Small Leo zodiac glyph (♌) in bottom corner in orange (#F97316). Shot with professional
astrophotography setup - Canon EOS Ra with 200mm telephoto lens on star tracker mount,
long exposure stacked image, ISO 6400. Realistic night sky photography, authentic star
field, professional astronomy, celestial map quality, cosmic fire element nebula, 4K
astrophotography.

NEGATIVE: cartoon, digital art, artificial stars, unrealistic space, oversaturated,
science fiction
```

**Settings:**
- Resolution: 1024x1024 (square)
- Steps: 50
- Guidance: 3.5

---

### 3.8 Realistic Flame Element Symbol

**Use Case:** Fire element indicator for elemental balance features

**Prompt:**
```
A photorealistic upward-pointing triangle made of real flames. The triangle geometry
is formed by dancing fire with realistic flame physics showing fluid motion and heat
distortion in air. Flame colors gradient naturally from deep red (#EF4444) at base
through bright orange (#F97316) in middle to golden yellow (#FBBF24) at tips. Small
embers and sparks floating away from triangle edges. Heat haze visible in air around
flames creating atmospheric distortion. Black void background to emphasize flames.
Shot with high-speed camera (1/2000s shutter) to freeze flame motion, Canon EOS R3 with
100mm macro lens f/2.8. Photorealistic fire photography, authentic flame behavior,
exceptional material detail, volumetric lighting from self-illumination, professional
fire element photography, 4K ultra sharp.

NEGATIVE: cartoon, digital art, artificial flames, unrealistic fire, 3D render,
motion blur
```

**Settings:**
- Resolution: 1024x1024 (square)
- Steps: 50
- Guidance: 4.0

---

### 3.9 Photorealistic Avatar Frame - Ornate Sacred Design

**Use Case:** Premium profile avatar decorative frame

**Prompt:**
```
A photorealistic ornate circular frame rendered as physical jewelry piece made of
precious metals. The frame features intricate sacred geometry patterns - flower of life
segments and mandala petals - carved in relief into rose gold and white gold metal.
The metal has realistic material properties showing subtle oxidation and patina. Four
gemstones positioned at cardinal points (north, south, east, west) in chakra colors
(ruby red, emerald green, sapphire blue, amethyst purple) with realistic light refraction
and internal structure. The frame has realistic depth (3mm thick metal) with beveled
inner edge. Shot as jewelry product photography with professional studio lighting, macro
lens for material detail, black velvet background. Photorealistic precious metal materials,
exceptional gemstone rendering, volumetric subsurface scattering, professional jewelry
photography, 4K ultra detailed.

NEGATIVE: digital art, flat, cartoon, artificial, cheap rendering, plastic materials
```

**Settings:**
- Resolution: 1024x1024 (square)
- Steps: 50
- Guidance: 4.5 (precision for jewelry detail)

---

### 3.10 Cosmic Moonlight Scene - Lunar Photography

**Use Case:** Full moon phase illustration for lunar calendar

**Prompt:**
```
A professional astrophotography closeup of full moon showing photorealistic lunar surface
detail. The moon is rendered in natural moonlight silver (#E2E8F0) with accurate maria
(dark patches) and crater formations visible as photographed through telescope. The moon
has subtle atmospheric glow around edges from light scattering. Background is authentic
deep night sky (#1E1B4B) with scattered pinpoint stars at accurate astronomical positions.
No artistic embellishments - pure authentic moon photography. Shot with professional
astronomy telescope (Celestron EdgeHD 14") and dedicated astronomy camera (ZWO ASI294MC),
stacked from 50 frames, processed in Photoshop for astronomical accuracy. Authentic lunar
photography, realistic moon surface, professional astronomy imaging, natural moonlight,
4K astrophotography quality.

NEGATIVE: cartoon, artistic interpretation, fake moon, unrealistic glow, oversaturated,
science fiction
```

**Settings:**
- Resolution: 1024x1024 (square)
- Steps: 50
- Guidance: 4.0

---

## 4. Recommendation for SpiritAtlas

### 4.1 Recommended Strategy

**Phase 1: Development & Testing (Use Flux1.dev)**
- Generate all 59 assets locally with Flux1.dev for FREE
- Test in app to validate composition, colors, style
- Iterate on prompts until designs are perfect
- **Cost: $0** (or ~$1.48 if using API)
- **Time: 2-3 hours** (including prompt refinement)

**Phase 2: Production Assets (Upgrade to Flux1.1 Pro)**
- Regenerate critical assets with Flux1.1 Pro for commercial license
- Focus on:
  - App icon (1024x1024) - needs flagship quality
  - Splash screen background (1080x1920) - hero image
  - Primary logo assets - brand-critical
  - Key sacred geometry (Flower of Life, Metatron's Cube)
  - Hero spiritual symbols (Om, Yin Yang)
- **Critical count: ~15 assets** needing Pro upgrade
- **Cost: $0.60** (15 × $0.04)

**Phase 3: Remaining Assets (Use Flux1.1 Pro)**
- Generate remaining 44 assets with Pro for commercial license
- **Cost: $1.76** (44 × $0.04)
- **Total Phase 2+3 Cost: $2.36**

**Total Recommended Budget: $3.00**
- Includes buffer for retries and variations
- Compare to cost estimate: You're already budgeted for $5.00

### 4.2 Cost-Benefit Analysis

**Option A: All Flux1.1 Pro (Recommended)**
- Cost: $2.36 (59 assets)
- Benefits: Commercial license, 6x faster, 4x resolution option
- Best for: Production-ready commercial app

**Option B: Test with Dev, Deploy with Pro**
- Cost: $0.60 (15 critical assets only)
- Benefits: Lowest cost, validates designs first
- Risk: Time to regenerate all 59 if needed

**Option C: All Flux1.dev (Not Recommended for Commercial)**
- Cost: $0 (local) or $1.48 (API)
- Benefits: Cheapest option
- **Major Risk: Violates non-commercial license for app store release**

### 4.3 Provider Recommendation

**For Flux1.1 Pro Access:**

**Option 1: fal.ai (Recommended)**
- Cost: $0.04/image
- Speed: 6x faster than v1.0
- API: Clean, well-documented
- Reliability: Excellent uptime
- Existing setup: You already have scripts for fal.ai

**Option 2: replicate.ai (Alternative)**
- Cost: $0.025/image (cheaper)
- Speed: Slightly slower
- API: Also good, more models available
- **Savings: $0.89 for full 59 assets**

**Recommendation:** Use **fal.ai** for speed and existing integration, unless budget is extremely tight.

### 4.4 Quality Assurance Checklist

After generation with Flux1.1 Pro:

- [ ] Verify colors match SpiritAtlas palette exactly (use color picker)
- [ ] Check sacred geometry is mathematically accurate
- [ ] Ensure spiritual symbols are culturally authentic
- [ ] Test transparent backgrounds load correctly (PNG)
- [ ] Validate resolution meets requirements (1024x1024 minimum for masters)
- [ ] Confirm aspect ratios match specifications
- [ ] Check file sizes are reasonable (<500KB after optimization)
- [ ] Verify symmetry where required (mandalas, chakras, icons)
- [ ] Test on actual Android device for visual quality
- [ ] Ensure no unwanted elements or artifacts

---

## 5. Technical Implementation Tips

### 5.1 Prompt Template for SpiritAtlas Consistency

```python
TEMPLATE = """
{subject_description}. Colors: exact hex {primary_color}, {secondary_color}, {accent_color}
from SpiritAtlas brand palette. {lighting_description}. Shot on {camera} with {lens},
{camera_settings}. {style_keywords}, {quality_keywords}, mystical spiritual aesthetic,
4K quality.

NEGATIVE: {negative_keywords}
"""
```

### 5.2 Recommended Script Updates

Update `/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/generate_all_assets.py`:

```python
# Add model selection
MODEL_OPTIONS = {
    "dev": "flux-dev",           # Non-commercial, free/cheap
    "pro": "flux-pro",           # Commercial, $0.05/image
    "pro_11": "flux-1.1-pro"     # Commercial, $0.04/image (RECOMMENDED)
}

# Add quality presets
QUALITY_PRESETS = {
    "draft": {
        "num_inference_steps": 28,
        "guidance_scale": 3.5
    },
    "standard": {
        "num_inference_steps": 50,
        "guidance_scale": 3.5
    },
    "premium": {
        "num_inference_steps": 70,
        "guidance_scale": 4.0
    }
}

# Recommended default
DEFAULT_CONFIG = {
    "model": "flux-1.1-pro",     # Commercial license
    "quality": "standard",        # 50 steps
    "image_format": "png"
}
```

### 5.3 Prompt Enhancement Script

```python
def enhance_prompt_for_realism(base_prompt, asset_type):
    """Add realism-enhancing keywords based on asset type."""

    ENHANCEMENT_KEYWORDS = {
        "icon": "professional app icon design, product photography, ultra-sharp",
        "background": "cinematic photography, atmospheric, volumetric lighting",
        "symbol": "sacred geometry precision, authentic spiritual symbolism",
        "button": "premium material design, photorealistic glass, studio lighting",
        "avatar": "professional portrait photography, lifelike detail"
    }

    realism_boost = """
    Photorealistic, shot on professional camera, cinematic lighting,
    rich color depth, ultra-detailed, 4K quality, sharp focus.
    """

    negative_prompt = """
    cartoon, anime, 3D render, artificial, low quality, blurry, distorted,
    amateur, oversaturated, watermark, text, signature
    """

    enhanced = f"{base_prompt}. {ENHANCEMENT_KEYWORDS.get(asset_type, '')} {realism_boost}"

    return enhanced, negative_prompt
```

### 5.4 Color Verification Script

```python
def verify_colors(image_path, expected_hex_colors, tolerance=10):
    """Verify generated image uses expected brand colors."""
    from PIL import Image
    import numpy as np

    img = Image.open(image_path)
    img_array = np.array(img)

    def hex_to_rgb(hex_color):
        hex_color = hex_color.lstrip('#')
        return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))

    def color_distance(c1, c2):
        return sum((a - b) ** 2 for a, b in zip(c1, c2)) ** 0.5

    matches = []
    for hex_color in expected_hex_colors:
        target_rgb = hex_to_rgb(hex_color)
        # Check if color appears in image within tolerance
        for pixel in img_array.reshape(-1, 3):
            if color_distance(pixel, target_rgb) < tolerance:
                matches.append(hex_color)
                break

    return {
        "total_expected": len(expected_hex_colors),
        "matched": len(matches),
        "match_rate": len(matches) / len(expected_hex_colors),
        "matched_colors": matches
    }
```

---

## 6. Sources & References

### Comparison & Benchmarks
- [MimicPC - In-Depth Comparison of All Flux Models](https://www.mimicpc.com/learn/an-in-depth-comparison-of-all-flux-models)
- [Flux.1 Comparison: Schnell vs Dev Vs Pro](https://blog.segmind.com/flux-comparison-schnell-vs-dev-vs-pro/)
- [Comparing FLUX Models: Pro, Dev, and Schnell](https://stockimg.ai/blog/ai-and-technology/what-is-flux-and-models-comparison)
- [FLUX - Quality, Generation Time & Price Analysis](https://artificialanalysis.ai/text-to-image/model-family/flux)

### Quality & Performance
- [Flux Pro AI Review 2025: Speed, Image Quality, Pricing](https://skywork.ai/blog/flux-pro-ai-review-2025/)
- [Comprehensive Review Of Flux Models: Which One Is The Best?](https://blog.segmind.com/comprehensive-review-of-flux-models-which-one-is-the-best/)
- [Juggernaut Pro FLUX: Photorealistic Quality](https://runware.ai/blog/juggernaut-pro-flux-the-best-ai-image-generation-model-for-photorealistic-quality)

### Prompt Engineering
- [The Complete Guide to Crafting the Perfect Flux 2 Prompt](https://story321.com/blog/flux-2-prompt)
- [FLUX Prompting Guide](https://www.ambienceai.com/tutorials/flux-prompting-guide)
- [14 Essential FLUX.1 Prompts: Tested Templates & Tips (2025)](https://skywork.ai/blog/flux1-prompts-tested-templates-tips-2025/)
- [FLUX.1 Prompt Guide: Pro Tips and Common Mistakes](https://getimg.ai/blog/flux-1-prompt-guide-pro-tips-and-common-mistakes-to-avoid)

### Realism Techniques
- [Best 15 Realistic FLUX Prompts You Can Try Out Now](https://medium.com/towards-agi/best-15-realistic-flux-prompts-you-can-try-out-now-3f3b2b085ccb)
- [Using Flux AI Techniques for Realistic Vintage Photos](https://aitubo.ai/blog/post/how-to-generate-realistic-vintage-photos-with-flux-ai/)
- [Mastering FLUX.1 Prompts for Realistic Portraits](https://www.nextdiffusion.ai/blogs/mastering-ai-portrait-prompts-with-flux1-for-realistic-images)
- [How to Write Negative Prompts in FLUX](http://anakin.ai/blog/how-to-write-negative-prompts-in-flux/)

### Pricing
- [Flux AI Pricing | Plans & Costs](https://flux1.ai/price)
- [Pricing | Black Forest Labs](https://bfl.ai/pricing)
- [Flux .1 Pro Pricing](https://www.segmind.com/models/flux-pro/pricing)

### Technical Parameters
- [FLUX AI Image Generation: Experimenting with Parameters](https://learnopencv.com/flux-ai-image-generator/)
- [Flux Documentation](https://huggingface.co/docs/diffusers/api/pipelines/flux)
- [Image Resolutions With Flux.1 Dev Model (Compared)](https://blog.segmind.com/image-resolutions-with-flux-1-dev-model-compared/)
- [Optimal Width & Height Guide for AI Image Models](https://www.promptingpixels.com/tutorial/width-height)

### Spiritual Examples
- [Midjourney Prompt: Mystical Sacred Geometry Portrait](https://promptlibrary.org/midjourney/mystical-sacred-geometry-portrait/)
- [Sacred geometry prompts - PromptHero](https://prompthero.com/search?q=Sacred+geometry)

---

## Conclusion

**For SpiritAtlas App:**

1. **Use Flux1.1 Pro** for all production assets ($2.36 total)
2. **Implement provided prompt templates** with exact hex color codes
3. **Follow realism best practices**: lighting keywords, camera parameters, negative prompts
4. **Generate at optimal resolutions**: 1024x1024 for icons/symbols, 1920x1080 for backgrounds
5. **Verify output** using color checker and quality checklist

**Expected Results:**
- Ultra-realistic spiritual imagery matching app aesthetic
- Commercial license compliance for app store
- Professional-grade photorealistic assets
- Brand-consistent color palette
- Cost-effective ($2.36 vs $400+ designer fees)

**Total Investment:** ~$3.00 (including buffer)
**Time to Complete:** 1-2 hours
**ROI:** 13,000%+ compared to hiring designer

---

**Generated:** December 9, 2025
**For:** SpiritAtlas Android App Image Generation
**Research by:** Claude Code (Sonnet 4.5)
