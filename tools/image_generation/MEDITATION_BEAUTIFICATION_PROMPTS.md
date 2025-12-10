# MEDITATION IMAGE BEAUTIFICATION PROMPTS
*Agent 10-12: Peaceful, Serene, and Transcendent Meditation Imagery*

**Mission**: Enhance 3 meditation/mindfulness images with calming visual aesthetics
**Budget**: $0.15 (3 images @ $0.05 each with FLUX 1.1 Pro)
**Style**: Peaceful, serene, transcendent, calming, theta-state inducing

---

## Research Summary: Calming Visual Aesthetics 2025

### Key Findings:
- **Color Palettes**: Soft blues, gentle greens, muted earth tones, soft pastels, deep purples
- **Theta State Colors**: "Tranquil Dawn" (soft pastels + deep purples), "Serene Ocean" (cool blues/greens)
- **Visual Elements**: Minimalist aesthetics, serene landscapes, subtle patterns, gentle lighting
- **Spiritual Symbols**: Lotus flower, mandalas, sacred geometry (peaceful, not intense)
- **Photography Style**: Soft focus, pastel lighting, sunrise moments, gentle gradients

---

## IMAGE 1: Mandala Spiritual Meditation (img_092)

**Current Assessment**:
- Purple/gold mandala on black background
- Intricate geometric patterns
- Good symmetry but very intense/busy
- Could be MORE calming and serene

**Beautification Goal**: Softer, more peaceful mandala with tranquil color palette

### Enhanced FLUX Prompt:

**Dimensions:** 1024x1024
**Style:** Serene meditation mandala, peaceful sacred geometry
**Seed:** 92001 (for reproducibility)

**Prompt:**
```
Peaceful meditation mandala for deep theta state relaxation, soft pastel color palette with gentle purples (#A78BFA), serene blues (#93C5FD), tranquil teals (#5EEAD4), and moonlight silver (#E2E8F0), delicate sacred geometry circles radiating from luminous center, ethereal soft glow rather than intense brightness, subtle gradient from lavender center fading to soft indigo edges, minimalist aesthetic with breathing space between pattern rings, professional spiritual healing art, calming and soothing composition, smooth transitions, gentle lotus petal motifs in outer ring, photorealistic soft lighting suggesting dawn meditation hour, 8K ultra-detailed but NOT overwhelming, serene and inviting energy, perfect for mindfulness practice, subtle depth of field creating peaceful focus on center, therapeutic color breathing visualization, FLUX 1.1 Pro ultra-quality
```

**Key Improvements:**
- Softer pastel palette (less intense purple/gold)
- "Breathing space" between patterns
- Dawn lighting (softer than current stark contrast)
- Therapeutic/healing focus
- Subtle depth of field for calm focus

---

## IMAGE 2: Deep Meditation Theta State (img_114)

**Current Assessment**:
- Meditating figure in cosmic setting
- Purple/violet dominant with starfield
- Strong theta wave visualization
- Good concept but could be MORE peaceful

**Beautification Goal**: More serene meditation pose with tranquil atmosphere

### Enhanced FLUX Prompt:

**Dimensions:** 1080x1920 (vertical/portrait)
**Style:** Tranquil meditation photography, theta brainwave serenity
**Seed:** 114001

**Prompt:**
```
Serene meditation figure in peaceful lotus position, photorealistic but ethereal aesthetic, soft pastel lighting suggesting sunrise meditation hour, gentle color breathing visualization with chakra colors flowing as translucent soft waves (not intense), tranquil dawn sky gradient from pale lavender (#E9D5FF) to soft periwinkle (#BFDBFE) to gentle teal (#99F6E4), minimal starfield with only few twinkling stars (not overwhelming cosmic scene), subtle theta brainwave visualization as delicate sine waves in pale purple (#DDD6FE) floating peacefully around head, professional mindfulness photography quality, soft focus on background creating peaceful depth, meditator's silhouette composed of gentle stardust particles rather than solid form, moonlight silver glow (#E2E8F0) from crown chakra suggesting enlightenment, calm and inviting energy, therapeutic and healing atmosphere, minimalist composition with negative space for visual rest, 8K photographic quality but soft and dreamy, absolutely peaceful and non-intense, perfect for guided meditation backdrop, FLUX 1.1 Pro serene rendering
```

**Key Improvements:**
- Sunrise instead of night (warmer, more hopeful)
- Pastel palette replacing intense purples
- Minimal stars (less cosmic intensity)
- Soft focus for peaceful depth
- Gentle wave patterns (not strong/intense)

---

## IMAGE 3: Meditation Chakra Screen Background (img_014)

**Current Assessment**:
- Rainbow chakra gradient background
- Very vibrant and colorful
- More energetic than calming
- Could be MUCH more subtle for meditation use

**Beautification Goal**: Subtle chakra gradient that's truly calming for meditation interface

### Enhanced FLUX Prompt:

**Dimensions:** 1080x1920 (vertical/portrait)
**Style:** Subtle chakra meditation background, therapeutic color breathing
**Seed:** 14001

**Prompt:**
```
Ultra-subtle vertical chakra gradient background for meditation app interface, extremely soft and calming color breathing visualization, flowing from gentle root rose (#FECDD3) at bottom, through soft sacral peach (#FED7AA), peaceful solar yellow cream (#FEF3C7), serene heart sage green (#BBF7D0), tranquil throat sky blue (#BFDBFE), calming third eye lavender (#DDD6FE), to ethereal crown pale violet (#F3E8FF) at top, seamless smooth transitions with NO harsh lines, very low saturation (pastel watercolor wash effect), professional therapeutic color meditation design, suitable as background for text overlay (high readability maintained), minimalist aesthetic with subtle energy flow suggestion, no overwhelming patterns or intensity, soft diffused lighting throughout, feels like breathing in colors peacefully, 8K smooth gradient rendering with imperceptible color steps, therapeutic and healing energy, perfect for extended meditation sessions without visual fatigue, gentle glow effect at 10% opacity maximum, suitable for mindfulness app UI that promotes deep relaxation, FLUX 1.1 Pro ultra-smooth quality
```

**Key Improvements:**
- MUCH softer pastel versions of chakra colors
- Low saturation (watercolor wash vs intense rainbow)
- Maintains UI readability (important for app background)
- No patterns or distracting elements
- Gentle glow only (not intense flows)
- Therapeutic focus for extended use

---

## Generation Instructions

### Using FLUX 1.1 Pro via fal.ai:

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation

# Create meditation beautification script
python3 << 'EOF'
import os
import time
import requests
from pathlib import Path

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    exit(1)

MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/meditation_beautified")
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

meditation_prompts = [
    {
        "id": "img_092_mandala_beautified",
        "title": "Peaceful Mandala Meditation",
        "width": 1024,
        "height": 1024,
        "seed": 92001,
        "prompt": """Peaceful meditation mandala for deep theta state relaxation, soft pastel color palette with gentle purples (#A78BFA), serene blues (#93C5FD), tranquil teals (#5EEAD4), and moonlight silver (#E2E8F0), delicate sacred geometry circles radiating from luminous center, ethereal soft glow rather than intense brightness, subtle gradient from lavender center fading to soft indigo edges, minimalist aesthetic with breathing space between pattern rings, professional spiritual healing art, calming and soothing composition, smooth transitions, gentle lotus petal motifs in outer ring, photorealistic soft lighting suggesting dawn meditation hour, 8K ultra-detailed but NOT overwhelming, serene and inviting energy, perfect for mindfulness practice, subtle depth of field creating peaceful focus on center, therapeutic color breathing visualization, FLUX 1.1 Pro ultra-quality"""
    },
    {
        "id": "img_114_theta_beautified",
        "title": "Serene Theta Meditation",
        "width": 1080,
        "height": 1920,
        "seed": 114001,
        "prompt": """Serene meditation figure in peaceful lotus position, photorealistic but ethereal aesthetic, soft pastel lighting suggesting sunrise meditation hour, gentle color breathing visualization with chakra colors flowing as translucent soft waves (not intense), tranquil dawn sky gradient from pale lavender (#E9D5FF) to soft periwinkle (#BFDBFE) to gentle teal (#99F6E4), minimal starfield with only few twinkling stars (not overwhelming cosmic scene), subtle theta brainwave visualization as delicate sine waves in pale purple (#DDD6FE) floating peacefully around head, professional mindfulness photography quality, soft focus on background creating peaceful depth, meditator's silhouette composed of gentle stardust particles rather than solid form, moonlight silver glow (#E2E8F0) from crown chakra suggesting enlightenment, calm and inviting energy, therapeutic and healing atmosphere, minimalist composition with negative space for visual rest, 8K photographic quality but soft and dreamy, absolutely peaceful and non-intense, perfect for guided meditation backdrop, FLUX 1.1 Pro serene rendering"""
    },
    {
        "id": "img_014_chakra_beautified",
        "title": "Subtle Chakra Background",
        "width": 1080,
        "height": 1920,
        "seed": 14001,
        "prompt": """Ultra-subtle vertical chakra gradient background for meditation app interface, extremely soft and calming color breathing visualization, flowing from gentle root rose (#FECDD3) at bottom, through soft sacral peach (#FED7AA), peaceful solar yellow cream (#FEF3C7), serene heart sage green (#BBF7D0), tranquil throat sky blue (#BFDBFE), calming third eye lavender (#DDD6FE), to ethereal crown pale violet (#F3E8FF) at top, seamless smooth transitions with NO harsh lines, very low saturation (pastel watercolor wash effect), professional therapeutic color meditation design, suitable as background for text overlay (high readability maintained), minimalist aesthetic with subtle energy flow suggestion, no overwhelming patterns or intensity, soft diffused lighting throughout, feels like breathing in colors peacefully, 8K smooth gradient rendering with imperceptible color steps, therapeutic and healing energy, perfect for extended meditation sessions without visual fatigue, gentle glow effect at 10% opacity maximum, suitable for mindfulness app UI that promotes deep relaxation, FLUX 1.1 Pro ultra-smooth quality"""
    }
]

print("=" * 70)
print("MEDITATION IMAGE BEAUTIFICATION - Agent 10-12")
print("=" * 70)
print(f"\nGenerating 3 peaceful, serene meditation images")
print(f"Model: {MODEL}")
print(f"Budget: $0.15 (3 images @ $0.05 each)")
print(f"Style: Peaceful, serene, transcendent\n")

generated_count = 0
total_cost = 0

for i, prompt_data in enumerate(meditation_prompts, 1):
    print(f"\n[{i}/3] {prompt_data['title']}")
    print(f"  ID: {prompt_data['id']}")
    print(f"  Size: {prompt_data['width']}x{prompt_data['height']}")
    print(f"  Seed: {prompt_data['seed']}")

    try:
        start_time = time.time()

        result = fal_client.subscribe(
            MODEL,
            arguments={
                "prompt": prompt_data['prompt'],
                "image_size": {
                    "width": prompt_data['width'],
                    "height": prompt_data['height']
                },
                "num_inference_steps": SETTINGS["num_inference_steps"],
                "guidance_scale": SETTINGS["guidance_scale"],
                "num_images": 1,
                "enable_safety_checker": True,
                "safety_tolerance": SETTINGS["safety_tolerance"],
                "output_format": "png",
                "seed": prompt_data['seed']
            }
        )

        elapsed = time.time() - start_time

        if result and 'images' in result and result['images']:
            image_url = result['images'][0]['url']
            response = requests.get(image_url)

            filename = f"{prompt_data['id']}.png"
            filepath = OUTPUT_DIR / filename

            with open(filepath, 'wb') as f:
                f.write(response.content)

            generated_count += 1
            total_cost += 0.05

            print(f"  ✅ Generated in {elapsed:.1f}s")
            print(f"  💾 Saved: {filename}")
            print(f"  💰 Cost: $0.05")
        else:
            print(f"  ❌ No image returned")

    except Exception as e:
        print(f"  ❌ Error: {e}")

    time.sleep(0.5)

print("\n" + "=" * 70)
print("MEDITATION BEAUTIFICATION COMPLETE!")
print("=" * 70)
print(f"✅ Successfully generated: {generated_count}/3")
print(f"💰 Total cost: ${total_cost:.2f}")
print(f"📁 Output: {OUTPUT_DIR}")
print("=" * 70)
print("\n✨ Images are peaceful, serene, and transcendent!")
print("🧘 Perfect for meditation and mindfulness interfaces")
EOF
```

---

## Deployment Instructions

After generation, review images and deploy the improved versions:

1. **Review Generated Images**:
   ```bash
   open generated_images/meditation_beautified/
   ```

2. **Compare with Originals**:
   - img_092_mandala_beautified.png vs img_092_mandala_spiritual_meditation.webp
   - img_114_theta_beautified.png vs img_114_deep_meditation_theta_state.webp
   - img_014_chakra_beautified.png vs img_014_meditation_chakra_screen_background.webp

3. **Optimize for Android** (if images are approved):
   ```bash
   cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
   python3 optimize_for_android.py generated_images/meditation_beautified/
   ```

4. **Deploy to App Resources**:
   ```bash
   # Copy optimized versions to appropriate drawable folders
   # Update image references in Compose components if needed
   ```

---

## Quality Checklist

- [ ] Softer, more peaceful color palettes (pastels vs intense)
- [ ] Calming theta-state inducing aesthetics
- [ ] Suitable for extended viewing without fatigue
- [ ] Maintains UI readability (for background use)
- [ ] Serene and transcendent feeling
- [ ] Minimalist with breathing space
- [ ] Gentle lighting and soft focus
- [ ] Therapeutic and healing energy

---

**Research Sources:**
- [Visual Meditation Ideas 2025 - Mindful Meditation & Sleep](https://mindful.net/visual-meditation/)
- [Meditation Designs - 99designs](https://99designs.com/inspiration/designs/meditation)
- [The Best 15 Meditation Color Palette Combinations](https://piktochart.com/tips/meditation-color-palette)
- [Choosing Colors for Your Meditation Room](https://www.color-meanings.com/best-colors-meditation-room/)
- [10 Meditation Photography Prompts](https://www.spiritualchime.com/meditation-photography/)

**Created**: 2025-12-10
**Agent**: IMAGE BEAUTIFICATION AGENT 10-12
**Status**: Ready for generation
