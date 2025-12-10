# QUICK START: Image Generation

**Status**: ✅ ALL SYSTEMS READY - AWAITING FAL_KEY
**Budget**: $1.00 for 20 production images

---

## 🚀 FASTEST PATH TO GENERATION

### Step 1: Install Dependencies (30 seconds)
```bash
pip install fal-client
```

### Step 2: Set API Key (1 minute)
```bash
# Get key from: https://fal.ai/dashboard/keys
export FAL_KEY='your-fal-api-key-here'
```

### Step 3: Test with Smallest Script (2 minutes, $0.15)
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
python3 generate_tantric_beautified.py
```

### Step 4: Run All Scripts (20 minutes, $1.00)
```bash
python3 generate_hero_backgrounds.py        # $0.30, 6 images
python3 generate_sacred_geometry.py         # $0.25, 5 images
python3 generate_energy_flow_beautified.py  # $0.15, 3 images
python3 generate_beautified_relationship_images.py  # $0.15, 3 images
python3 generate_tantric_beautified.py      # $0.15, 3 images
```

### Step 5: Verify Output
```bash
find generated_images -name "*.png" | wc -l
# Expected: 20 images
```

---

## 📊 SCRIPT OVERVIEW

| # | Script | Images | Cost | Priority | Time |
|---|--------|--------|------|----------|------|
| 1 | `generate_hero_backgrounds.py` | 6 | $0.30 | HIGH | 3 min |
| 2 | `generate_sacred_geometry.py` | 5 | $0.25 | HIGH | 2.5 min |
| 3 | `generate_energy_flow_beautified.py` | 3 | $0.15 | MED | 3 min |
| 4 | `generate_beautified_relationship_images.py` | 3 | $0.15 | MED | 3 min |
| 5 | `generate_tantric_beautified.py` | 3 | $0.15 | LOW | 3 min |
| **TOTAL** | | **20** | **$1.00** | | **~15 min** |

---

## ✅ VERIFICATION CHECKLIST

### Pre-Generation
- [ ] Python 3.8+ installed (`python3 --version`)
- [ ] fal-client installed (`pip list | grep fal-client`)
- [ ] FAL_KEY environment variable set (`echo $FAL_KEY`)
- [ ] In correct directory (`pwd` shows `.../tools/image_generation`)

### Post-Generation
- [ ] 20 PNG files created
- [ ] 5 manifest JSON files created
- [ ] No error messages in output
- [ ] Total cost matches $1.00
- [ ] Visual quality check (open sample images)

---

## 🆘 TROUBLESHOOTING

### "fal_client not installed"
```bash
pip install fal-client
```

### "FAL_KEY environment variable not set"
```bash
export FAL_KEY='your-key-here'
```

### "Rate limit exceeded"
Wait 1 minute, then increase delays:
```python
time.sleep(2)  # Change from 0.5 to 2 in scripts
```

### Script fails mid-generation
Just re-run it - manifests prevent duplicates

---

## 📁 OUTPUT STRUCTURE

```
generated_images/
├── hero_backgrounds/
│   ├── hero_001_cosmic_awakening.png
│   ├── ... (6 images)
│   └── hero_backgrounds_manifest.json
├── sacred_geometry/
│   ├── sacred_geometry_01_flower_of_life.png
│   ├── ... (5 images)
│   └── sacred_geometry_manifest.json
├── energy_flow_beautified/
│   └── ... (3 images + manifest)
├── beautified_relationship/
│   └── ... (3 images + manifest)
└── tantric_beautified/
    └── ... (3 images + manifest)
```

---

## 💰 COST BREAKDOWN

- **Per Image**: $0.05 (FLUX 1.1 Pro)
- **Test Run** (tantric only): $0.15
- **Full Generation**: $1.00
- **Account Minimum**: $5.00 (one-time at fal.ai)

---

## 🔗 REFERENCES

- **Full Report**: `IMAGE_GENERATION_READINESS.md`
- **FAL Dashboard**: https://fal.ai/dashboard
- **Get API Key**: https://fal.ai/dashboard/keys
- **Model Docs**: https://fal.ai/models/fal-ai/flux-pro

---

**Ready to start?** Run Step 1 above! 🚀
