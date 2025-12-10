# Energy Flow Beautification - Quick Start Guide

## 1-Minute Setup (When FAL_KEY Available)

### Step 1: Set Environment Variable
```bash
export FAL_KEY="your_fal_api_key_here"
```

### Step 2: Navigate to Directory
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
```

### Step 3: Run Generation
```bash
python3 generate_energy_flow_beautified.py
```

### Step 4: Wait for Prompt
The script will display:
- Generation plan (3 images)
- Total cost ($0.15)
- Press ENTER to confirm and start

---

## What Gets Generated

3 beautified energy flow images:

1. **Compatibility Energy Field** (1080×1920)
   - Dual-soul energy fusion
   - Corona discharge effects
   - Seed: 777

2. **Torus Energy Field** (1024×1024)
   - Toroidal energy vortex
   - Flowing plasma streams
   - Seed: 888

3. **Chakra Energy Flow** (512×512)
   - 7-chakra circulation
   - Figure-eight patterns
   - Seed: 999

---

## Output Location

```
generated_images/energy_flow_beautified/
├── 027_compatibility_energy_field_v2_beautified.png
├── 059_torus_energy_field_v2_beautified.png
├── 111_chakra_energy_flow_v2_beautified.png
└── manifest.json
```

---

## Budget

**Total Cost:** $0.15 (3 images × $0.05 each)

---

## Dependencies (Already Installed)

- ✅ `fal-client`
- ✅ `requests`
- ✅ Python 3

---

## Troubleshooting

**"FAL_KEY not set" error:**
```bash
export FAL_KEY="your_api_key"
```

**Import errors:**
```bash
pip install fal-client requests
```

**Permission denied:**
```bash
chmod +x generate_energy_flow_beautified.py
```

---

## Expected Generation Time

- Per image: ~10-15 seconds
- Total time: ~1-2 minutes
- Includes 2-second delays between images

---

## Verification Checklist

After generation completes:

- [ ] 3 PNG files in output directory
- [ ] manifest.json shows "successful": 3
- [ ] Each image file size: ~1-5 MB
- [ ] Total cost matches: $0.15

---

*Ready to generate when FAL_KEY is available!*
