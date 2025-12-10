# Chakra Beautification - Quick Start Guide
## 5-Minute Execution Reference

**Agent:** IMAGE BEAUTIFICATION AGENT 1-3
**Budget:** $0.15 | **Time:** 5-10 minutes | **Quality Target:** 9.8+/10

---

## Prerequisites (One-Time Setup)

```bash
# 1. Install dependencies
pip install fal-client requests

# 2. Install ImageMagick
brew install imagemagick  # macOS

# 3. Get fal.ai API key from: https://fal.ai/dashboard/keys

# 4. Set environment variable
export FAL_KEY="your_fal_api_key_here"
```

---

## Execution (Copy & Paste)

### Step 1: Generate Beautified Chakras (2-3 min, $0.15)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
python3 beautify_chakras.py
```

**Output:** 3 PNG files in `generated_images/beautified_chakras/`

---

### Step 2: Review Images (30 seconds)

```bash
open generated_images/beautified_chakras/
```

**Check:**
- Sharp & vibrant colors ✓
- Sacred geometry visible ✓
- Sanskrit symbols clear ✓
- Score yourself: 9.8+/10? ✓

---

### Step 3: Optimize to WebP (15 seconds)

```bash
python3 optimize_beautified_chakras.py
```

**Output:** 15 WebP files in `app/src/main/res/drawable-*/`

---

### Step 4: Create Comparisons (10 seconds)

```bash
python3 create_chakra_comparison.py
```

**Output:** Before/after images in `generated_images/chakra_comparisons/`

---

### Step 5: Build & Test (2-3 min)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
./gradlew :app:assembleDebug && ./gradlew installDebug
```

**Test:** Open app → View chakras → Verify quality ✓

---

## Troubleshooting

**FAL_KEY error:**
```bash
export FAL_KEY="your_key_here"
```

**ImageMagick error:**
```bash
brew install imagemagick
```

**API fails:**
- Check balance: https://fal.ai/dashboard/billing
- Need $0.15 minimum

---

## What Gets Created

| File | Purpose |
|------|---------|
| `047_sacral_*.png` | Beautified Sacral Chakra |
| `048_solar_plexus_*.png` | Beautified Solar Plexus |
| `050_throat_*.png` | Beautified Throat Chakra |
| 15× `.webp` files | Android multi-density resources |
| `chakra_*_comparison.png` | Before/after comparisons |
| `beautification_manifest.json` | Generation metadata |

---

## Success Checklist

- [ ] 3 images generated (2-3 min)
- [ ] Quality 9.8+/10 (visual review)
- [ ] 15 WebP files created (optimization)
- [ ] Android build succeeds (no errors)
- [ ] Images look great on device
- [ ] Total cost: $0.15 ✓

---

## Full Documentation

- **Planning:** `/CHAKRA_BEAUTIFICATION_PLAN.md`
- **Workflow:** `/CHAKRA_BEAUTIFICATION_WORKFLOW.md`
- **Summary:** `/CHAKRA_BEAUTIFICATION_SUMMARY.md`
- **This Guide:** `/CHAKRA_BEAUTIFICATION_QUICKSTART.md`

---

**Ready to beautify! 🎨**
