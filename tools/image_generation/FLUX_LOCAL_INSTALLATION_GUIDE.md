# Flux Model Local Installation Guide for 24GB GPU Systems (Windows)

**Last Updated:** December 2025

This comprehensive guide covers everything you need to know about installing and running Flux models locally on a Windows system with a 24GB GPU (RTX 3090, RTX 4090, etc.).

---

## Table of Contents

1. [Hardware Requirements](#hardware-requirements)
2. [Flux Model Options](#flux-model-options)
3. [Installation Methods](#installation-methods)
4. [Performance Optimization](#performance-optimization)
5. [Model Download Sources](#model-download-sources)
6. [Commercial Licensing](#commercial-licensing)
7. [Step-by-Step Installation](#step-by-step-installation)

---

## Hardware Requirements

### Recommended 24GB GPU Models

#### RTX 4090 (24GB) - Best Choice
- **VRAM:** 24GB GDDR6X
- **Performance:** Outstanding performance for Flux models
- **Resolution Support:** Up to 2560x1440 comfortably
- **Speed:** Baseline reference (100%)
- **Generation Times:**
  - Q8 GGUF: 15-17 seconds (1024x1024, 20 steps)
  - FP8: ~14 seconds (1024x1024, 20 steps)
  - Full precision: ~35 seconds (1024x1024, 28 steps)
- **Price:** $1,600-2,000 (new)
- **Best For:** Maximum performance, professional use, latest features

#### RTX 3090 (24GB) - Best Value
- **VRAM:** 24GB GDDR6X
- **Performance:** Excellent, ~30% slower than RTX 4090
- **Resolution Support:** Up to 1920x1080 recommended (can handle higher)
- **Speed:** ~70% of RTX 4090 performance
- **Price:** $650-750 (used market)
- **Best For:** Budget-conscious users, hobbyists, same workflows as 4090
- **Value Proposition:** Same VRAM capacity at less than half the price

### VRAM Requirements by Model Format

| Model Format | VRAM Required | Quality | Speed Impact | Best For |
|-------------|---------------|---------|--------------|----------|
| **FP16 (Full Precision)** | ~22GB | 100% | Baseline | Maximum quality, 24GB GPUs |
| **FP8** | ~16GB | 99% | +5-10% | Excellent balance, 16GB+ GPUs |
| **Q8_0 GGUF** | ~12GB | 99% | +10-15% | Great quality, 12GB+ GPUs |
| **Q6_K GGUF** | ~9GB | 95% | +15-20% | Good quality, 12GB GPUs |
| **Q5_K_M GGUF** | ~8GB | 90% | +18-25% | Budget GPUs, 8GB cards |
| **Q4 GGUF** | ~6GB | 85% | +25-30% | Low VRAM, 8GB cards |
| **NF4** | ~10GB | 98% | +10-15% | RTX 3000/4000 series |

**Key Insight:** With a 24GB GPU, you should run **FP16 or FP8** for maximum quality. Q8_0 is the best quantized option if you need extra VRAM headroom for LoRAs or ControlNet.

---

## Flux Model Options

### 1. FLUX.1-schnell (Fast, 4-Step)

**Best For:** Real-time generation, rapid prototyping, commercial use

- **Parameters:** 12 billion
- **Steps:** 4 (ultra-fast)
- **License:** Apache 2.0 (fully commercial)
- **Speed:** Fastest Flux variant
- **Quality:** Good (optimized for speed)
- **Local Installation:** Yes
- **File Size:** ~23.8GB (FP16 transformer) + 9.52GB (T5) + 246MB (CLIP) + 168MB (VAE) = ~34GB total

**Use Cases:**
- Real-time image generation
- Rapid iteration and prototyping
- Commercial projects without licensing fees
- API replacements for cost savings

**Generation Speed (RTX 4090, estimated):**
- 1024x1024: ~3-5 seconds (4 steps)
- 1536x1536: ~7-10 seconds (4 steps)

### 2. FLUX.1-dev (High Quality, Non-Commercial)

**Best For:** Personal projects, research, highest quality generation

- **Parameters:** 12 billion
- **Steps:** 20-50 (flexible)
- **License:** Non-commercial (requires paid license for commercial use)
- **Speed:** Slower than schnell, but excellent quality
- **Quality:** Excellent (best text rendering)
- **Local Installation:** Yes
- **File Size:** ~23.8GB (FP16 transformer) + 9.52GB (T5) + 246MB (CLIP) + 168MB (VAE) = ~34GB total

**Use Cases:**
- Personal art projects
- Research and experimentation
- Learning and education
- Non-commercial portfolio work

**Commercial Licensing:**
- Required for any commercial use
- Starting at $999/month for up to 100,000 images
- Revenue sharing model: 30% for credit-based systems
- Contact Black Forest Labs or partners like Invoke

**Generation Speed (RTX 4090):**
- 1024x1024 @ 20 steps: ~14-17 seconds (FP8/Q8)
- 1024x1024 @ 28 steps: ~35 seconds (FP16)
- 1536x1536 @ 20 steps: ~25-30 seconds

### 3. FLUX.1.1 Pro (API-Only)

**Best For:** Enterprise applications, highest possible quality

- **Parameters:** 12 billion (enhanced)
- **Local Installation:** **NO - API only**
- **License:** Commercial use via API
- **Quality:** Highest quality available
- **Access:** API through Black Forest Labs, freepik, together.ai, fal.ai, Replicate

**Important:** FLUX 1.1 Pro **cannot be installed locally**. It's exclusively available through API endpoints. If you need local installation, use schnell (commercial) or dev (non-commercial with license).

### 4. Specialized Flux Models (2025)

All 12B parameters, local installation available:

- **FLUX.1-Fill-dev:** Inpainting and outpainting
- **FLUX.1-Kontext-dev:** Image editing with context understanding (released 2025)
- **FLUX.1-Canny-dev:** Structure-guided generation (edge detection)
- **FLUX.1-Depth-dev:** Depth-guided generation
- **FLUX.1-Redux-dev:** Image-to-image translation
- **FLUX.2-dev:** Next-generation model (2025)

---

## Installation Methods

### Method 1: ComfyUI (Recommended for Flux)

**Why ComfyUI for Flux?**
- Native Flux support since August 2024
- Best FLUX compatibility through custom nodes
- 10-20% faster than Automatic1111
- More memory-efficient for SDXL/Flux models
- Node-based workflow system
- Active community support for Flux

**Pros:**
- Superior Flux model support
- Better performance and memory efficiency
- Flexible workflow system
- Batch processing capabilities
- Advanced control options

**Cons:**
- Steeper learning curve (10-20 hours to feel comfortable)
- Node-based interface can be intimidating at first
- Less beginner-friendly than Automatic1111

**Best For:**
- Users wanting to work with Flux models specifically
- Those who need advanced workflows
- Users comfortable with visual programming
- Professional/commercial work

### Method 2: Automatic1111 WebUI (Stable Diffusion Forge)

**Why Automatic1111?**
- Traditional interface, very beginner-friendly
- Extensive documentation available
- Largest user community
- Simple for basic image generation

**Pros:**
- Easy to learn and use
- Familiar interface for SD users
- Quick installation (few minutes)
- Great for simple generation tasks

**Cons:**
- Requires specific forks for Flux support
- Limited native Flux compatibility as of 2025
- May not receive official Flux support
- Slower than ComfyUI for Flux models
- Less memory-efficient

**Best For:**
- Beginners to AI image generation
- Simple image generation workflows
- Users primarily working with Stable Diffusion
- Those preferring traditional UI over node-based

### Method 3: Python/Diffusers Direct Installation

**Why Direct Installation?**
- Maximum control and customization
- Programmatic generation capabilities
- Integration into existing Python projects
- Minimal overhead

**Pros:**
- Full control over generation pipeline
- Easy to integrate into applications
- Scriptable and automatable
- No GUI overhead

**Cons:**
- Requires Python programming knowledge
- Manual setup and configuration
- No visual interface
- More troubleshooting required

**Best For:**
- Developers building applications
- API/service integration
- Batch processing scripts
- Custom pipeline requirements

### 2025 Recommendation

**For Windows users working with Flux models: ComfyUI is the clear winner** due to its native Flux support, superior performance, and efficient memory management. The learning curve is worth the investment for anyone serious about Flux generation.

---

## Performance Optimization

### 1. Choosing the Right Quantization

#### For 24GB GPUs (RTX 3090/4090)

**Optimal Choice: FP16 or FP8**
```
Priority: Quality First
├─ FP16: Maximum quality, ~22GB VRAM
├─ FP8: 99% quality, ~16GB VRAM, 5-10% slower
└─ Q8_0: 99% quality, ~12GB VRAM, 10-15% slower (if you need LoRA/ControlNet headroom)
```

**Why FP8 is Perfect for 24GB:**
- Virtually indistinguishable quality from FP16
- Frees up 6-8GB VRAM for other components
- Minimal speed impact (5-10%)
- Best balance of quality and efficiency

**When to Use Q8_0 GGUF:**
- Need 2-4GB extra headroom for LoRAs
- Using multiple ControlNet models
- Complex workflows with many nodes
- Want fastest possible quantized quality

#### Quantization Quality Comparison

**Quality Tiers (Subjective):**
1. **FP16:** 100% - Reference quality
2. **FP8:** 99% - Virtually indistinguishable in normal use
3. **Q8_0:** 99% - Indistinguishable in normal use
4. **Q6_K:** 95% - Excellent, minor differences in fine details
5. **Q5_K_M:** 90% - Good quality, noticeable in detailed areas
6. **Q4:** 85% - Acceptable, visible quality loss

### 2. VRAM Optimization Techniques

#### ComfyUI VRAM Flags

```bash
# Maximum speed (for 24GB GPUs)
python main.py

# Low VRAM mode (shouldn't be needed for 24GB)
python main.py --lowvram

# Normal VRAM mode (balanced)
python main.py --normalvram

# High VRAM mode (use all available)
python main.py --highvram

# CPU offloading (for lower VRAM GPUs)
python main.py --cpu
```

**For 24GB GPUs:** Run ComfyUI without any flags for maximum performance.

#### Advanced Optimization Stack

**For 8GB GPUs (not needed for 24GB but interesting):**
```
Optimization Stack:
1. Q5_K_M quantization → Reduce model VRAM
2. Efficient attention → Reduce attention memory
3. Text encoder offloading → Move T5/CLIP to system RAM
4. VAE offloading → Move VAE to system RAM when not in use
5. Aggressive caching → Reuse computations

Result: Can run Flux 2 on 8GB (would need 24GB without optimizations)
```

### 3. Speed vs Quality Tradeoffs

#### Fast Generation (Flux Schnell)

```yaml
Model: FLUX.1-schnell
Format: FP8 or Q8_0
Steps: 4
Sampler: Euler
CFG Scale: 1.0
Resolution: 1024x1024

Speed: 3-5 seconds (RTX 4090)
Quality: Good, optimized for speed
Use Case: Real-time, prototyping
```

#### Balanced Generation (Flux Dev)

```yaml
Model: FLUX.1-dev
Format: FP8
Steps: 20-25
Sampler: Euler
CFG Scale: 7.0
Resolution: 1024x1024

Speed: 14-20 seconds (RTX 4090)
Quality: Excellent
Use Case: General purpose, best balance
```

#### Maximum Quality (Flux Dev)

```yaml
Model: FLUX.1-dev
Format: FP16
Steps: 35-50
Sampler: Euler or DPM++
CFG Scale: 7.5-8.0
Resolution: 1536x1536

Speed: 60-90 seconds (RTX 4090)
Quality: Maximum possible
Use Case: Final renders, portfolio pieces
```

### 4. Resolution Guidelines by GPU

#### RTX 4090 (24GB)

```
Comfortable Resolutions:
├─ 1024x1024: Instant (any model)
├─ 1536x1536: Fast (recommended)
├─ 2048x2048: Moderate
└─ 2560x1440: Comfortable (FP8/Q8)

Maximum: 3072x3072 (with optimizations)
```

#### RTX 3090 (24GB)

```
Comfortable Resolutions:
├─ 1024x1024: Fast (any model)
├─ 1536x1536: Good
├─ 1920x1080: Recommended maximum
└─ 2048x2048: Possible (FP8/Q8)

Maximum: 2560x1440 (with optimizations)
```

### 5. Advanced Optimization: FP4 (RTX 50 Series Only)

**New in 2025:** NVIDIA Blackwell (RTX 50 series) supports FP4 quantization

```
FP4 Benefits:
- Further VRAM reduction
- Minimal quality degradation
- Optimized with TensorRT Model Optimizer
- Uses PTQ (Post-Training Quantization) and QAT (Quantization-Aware Training)

Availability: RTX 5090, RTX 5080 (Blackwell architecture)
```

---

## Model Download Sources

### Official Black Forest Labs Sources

#### HuggingFace Repositories (Primary Source)

**Main Flux Models:**
- [black-forest-labs/FLUX.1-dev](https://huggingface.co/black-forest-labs/FLUX.1-dev)
- [black-forest-labs/FLUX.1-schnell](https://huggingface.co/black-forest-labs/FLUX.1-schnell)
- [black-forest-labs/FLUX.2-dev](https://huggingface.co/black-forest-labs/FLUX.2-dev)

**Specialized Models:**
- [black-forest-labs/FLUX.1-Fill-dev](https://huggingface.co/black-forest-labs/FLUX.1-Fill-dev) (Inpainting)
- [black-forest-labs/FLUX.1-Kontext-dev](https://huggingface.co/black-forest-labs/FLUX.1-Kontext-dev) (Editing, 2025)
- [black-forest-labs/FLUX.1-Canny-dev](https://huggingface.co/black-forest-labs/FLUX.1-Canny-dev) (Structure-guided)
- [black-forest-labs/FLUX.1-Depth-dev](https://huggingface.co/black-forest-labs/FLUX.1-Depth-dev) (Depth-guided)
- [black-forest-labs/FLUX.1-Redux-dev](https://huggingface.co/black-forest-labs/FLUX.1-Redux-dev) (Image-to-image)

**Quantized Models (Community):**
- [city96/FLUX.1-dev-gguf](https://huggingface.co/city96/FLUX.1-dev-gguf) (All GGUF quantization levels)

#### GitHub Repository
- [black-forest-labs/flux](https://github.com/black-forest-labs/flux) - Official inference code

### File Sizes and Download Times

#### Full Precision Models (FP16)

**Total Download Size: ~34GB**
```
Components:
├─ Transformer (main model): 23.8 GB
├─ T5 Text Encoder: 9.52 GB
├─ CLIP Text Encoder: 246 MB
└─ VAE (autoencoder): 168 MB

Total: ~33.7 GB
```

**Download Time Estimates:**
```
Internet Speed → Download Time
├─ 1 Gbps: ~5 minutes
├─ 500 Mbps: ~9 minutes
├─ 250 Mbps: ~18 minutes
├─ 100 Mbps: ~45 minutes
├─ 50 Mbps: ~90 minutes (1.5 hours)
└─ 25 Mbps: ~180 minutes (3 hours)
```

#### Quantized Models

| Format | Size | Download (100 Mbps) | Quality |
|--------|------|---------------------|---------|
| FP16 | 34 GB | 45 min | 100% |
| FP8 | 17 GB | 23 min | 99% |
| Q8_0 | 12 GB | 16 min | 99% |
| Q6_K | 9 GB | 12 min | 95% |
| Q5_K_M | 8 GB | 11 min | 90% |
| Q4 | 6 GB | 8 min | 85% |
| NF4 | 10 GB | 13 min | 98% |

### Download Methods

#### Method 1: HuggingFace Hub (CLI)

```bash
# Install HuggingFace CLI
pip install huggingface-hub

# Login (optional, for gated models)
huggingface-cli login

# Download Flux.1-dev
huggingface-cli download black-forest-labs/FLUX.1-dev

# Download Flux.1-schnell
huggingface-cli download black-forest-labs/FLUX.1-schnell

# Download specific quantized version
huggingface-cli download city96/FLUX.1-dev-gguf flux1-dev-Q8_0.gguf
```

#### Method 2: Git LFS (Large Files)

```bash
# Install Git LFS
git lfs install

# Clone repository (downloads all files)
git clone https://huggingface.co/black-forest-labs/FLUX.1-dev

# Or clone specific model
git clone https://huggingface.co/black-forest-labs/FLUX.1-schnell
```

#### Method 3: Direct Browser Download

1. Visit HuggingFace model page
2. Click "Files and versions" tab
3. Click download icon next to each file
4. Place files in appropriate ComfyUI folders

**Tip:** Use a download manager like [Free Download Manager](https://www.freedownloadmanager.org/) or [JDownloader](https://jdownloader.org/) for resumable downloads.

---

## Commercial Licensing

### License Summary by Model

| Model | License | Commercial Use | Local Install | Cost |
|-------|---------|----------------|---------------|------|
| **Flux.1-schnell** | Apache 2.0 | ✅ Yes (Free) | ✅ Yes | Free |
| **Flux.1-dev** | Proprietary | ❌ No (License Required) | ✅ Yes | $999/month base |
| **Flux.1.1 Pro** | API-Only | ✅ Yes (Via API) | ❌ No | Pay-per-use |

### Flux.1-schnell (Apache 2.0)

**Full Commercial Freedom**

```yaml
License: Apache 2.0
Commercial Use: Fully allowed
Attribution: Not required (but appreciated)
Modifications: Allowed
Distribution: Allowed
Patent Grant: Yes

Use Cases:
- Commercial products and services
- SaaS applications
- Client work
- Resale of generated images
- No license fees required
```

**Best For:** Businesses and commercial projects requiring fast generation without licensing costs.

### Flux.1-dev (Non-Commercial License)

**Personal Use & Research Only (Default)**

```yaml
License: Proprietary Non-Commercial
Personal Use: Allowed
Research: Allowed
Education: Allowed
Commercial Use: Requires paid license
Portfolio: Allowed (non-commercial)

Restrictions:
- Cannot sell generated images
- Cannot use in commercial products
- Cannot offer as paid service
- Cannot use for client work
```

**Commercial License Required For:**
- Selling generated artwork
- Commercial products/services
- Client work and commissions
- Revenue-generating applications
- Business use of any kind

### Commercial License Pricing (Flux.1-dev)

**Official Black Forest Labs Licensing:**

#### Option 1: Fixed Monthly License
```
Base License: $999/month
Includes: Up to 100,000 generated images/month
Additional images: Contact for pricing
Support: Priority support included
```

#### Option 2: Revenue Sharing Model
```
Revenue Share: 30% of revenue for credit-based systems
Minimum: Contact for details
Best For: Platforms with variable usage
Reporting: Monthly revenue reports required
```

#### Option 3: Enterprise License
```
Pricing: Custom (contact sales)
Includes: Unlimited generation, custom terms
Best For: Large-scale deployments
Contact: Through Black Forest Labs or Invoke
```

**How to Obtain License:**
1. **Direct from Black Forest Labs:** [bfl.ai/licensing](https://bfl.ai/licensing) - Fill out contact form
2. **Through Invoke:** Strategic partnership, streamlined licensing process
3. **Through official partners:** Check Black Forest Labs website for authorized resellers

### Flux.1.1 Pro (API-Only)

**Commercial Use Through API**

```yaml
Model: FLUX.1.1 Pro
Availability: API only (cannot install locally)
License: Commercial use via API
Pricing: Pay-per-image (varies by provider)

Access Points:
├─ Black Forest Labs API: bfl.ai
├─ Freepik API: freepik.com
├─ Together.ai: together.ai
├─ Fal.ai: fal.ai
└─ Replicate: replicate.com

Typical Pricing: $0.01-0.05 per image
Quality: Highest available
Speed: Fastest (optimized inference)
```

**When to Use API vs Local:**
```
Use API (Flux 1.1 Pro) When:
├─ Need highest possible quality
├─ Low/variable volume (< 10k images/month)
├─ No infrastructure management desired
├─ Quick proof of concept
└─ Budget-flexible projects

Use Local (Flux.1-dev with license) When:
├─ High volume (> 10k images/month)
├─ Need data privacy/control
├─ Have GPU infrastructure
├─ Long-term production use
└─ Cost optimization important
```

### License Comparison & Recommendations

#### For Commercial Projects

```
Decision Tree:

Speed Priority + Commercial Use
└─> Flux.1-schnell (Apache 2.0, Free)
    ├─ Pro: Free, fast, commercial-ready
    └─ Con: Lower quality than dev

Quality Priority + Budget < $999/month
└─> Flux.1.1 Pro API
    ├─ Pro: Highest quality, no infrastructure
    └─ Con: Per-image costs, API dependency

Quality Priority + High Volume
└─> Flux.1-dev + Commercial License ($999/month)
    ├─ Pro: Unlimited generation, full control
    └─> Con: Upfront cost, infrastructure needed

Personal/Research
└─> Flux.1-dev (Free, Non-Commercial)
    ├─ Pro: Free, excellent quality
    └─> Con: Cannot commercialize
```

---

## Step-by-Step Installation

### Prerequisites

#### System Requirements

**Hardware:**
- GPU: RTX 3090 (24GB) or RTX 4090 (24GB) recommended
- RAM: 32GB system RAM minimum (64GB recommended)
- Storage: 100GB+ free SSD space
- CPU: Modern 8+ core processor

**Software:**
- Windows 10/11 (64-bit)
- NVIDIA GPU Driver: 545.xx or newer
- Python: 3.10.x or 3.11.x (not 3.12+)
- CUDA Toolkit: 11.8 or 12.1 (installed with PyTorch)
- Git: Latest version
- Visual Studio Build Tools (for compilation)

#### Pre-Installation Checks

```powershell
# Check GPU
nvidia-smi

# Check Python version
python --version

# Check Git
git --version

# Check available disk space
Get-PSDrive C | Select-Object Used,Free
```

### Installation: ComfyUI (Recommended)

#### Step 1: Install Prerequisites

**1.1 Install Python 3.10 or 3.11**

Download from [python.org](https://www.python.org/downloads/)
- ✅ Check "Add Python to PATH" during installation
- Install for all users
- Verify: `python --version`

**1.2 Install Git**

Download from [git-scm.com](https://git-scm.com/download/win)
- Use default settings
- Verify: `git --version`

**1.3 Install Visual Studio Build Tools**

Download from [visualstudio.microsoft.com](https://visualstudio.microsoft.com/downloads/)
- Select "Desktop development with C++"
- Required for compiling Python packages

**1.4 Update NVIDIA Driver**

Download from [nvidia.com/drivers](https://www.nvidia.com/download/index.aspx)
- Get latest Game Ready or Studio driver
- Minimum: 545.xx
- Recommended: Latest available

#### Step 2: Install ComfyUI

**2.1 Clone ComfyUI Repository**

```powershell
# Navigate to your desired installation directory
cd C:\
mkdir AI
cd AI

# Clone ComfyUI
git clone https://github.com/comfyanonymous/ComfyUI.git
cd ComfyUI
```

**2.2 Create Python Virtual Environment**

```powershell
# Create virtual environment
python -m venv venv

# Activate virtual environment
.\venv\Scripts\Activate.ps1

# If you get execution policy error:
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

**2.3 Install PyTorch with CUDA**

```powershell
# For CUDA 12.1 (RTX 4090, recommended)
pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121

# For CUDA 11.8 (RTX 3090, older drivers)
pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu118

# Verify PyTorch CUDA
python -c "import torch; print(f'PyTorch: {torch.__version__}'); print(f'CUDA Available: {torch.cuda.is_available()}'); print(f'CUDA Version: {torch.version.cuda}')"
```

**2.4 Install ComfyUI Dependencies**

```powershell
# Install requirements
pip install -r requirements.txt

# Install additional useful packages
pip install huggingface-hub
```

#### Step 3: Install ComfyUI Manager (Highly Recommended)

```powershell
# Navigate to custom_nodes directory
cd custom_nodes

# Clone ComfyUI Manager
git clone https://github.com/ltdrdata/ComfyUI-Manager.git

# Return to ComfyUI root
cd ..
```

**ComfyUI Manager Benefits:**
- One-click custom node installation
- Model manager for easy downloads
- Update management
- Workflow sharing

#### Step 4: Download Flux Models

**4.1 Create Directory Structure**

```powershell
# Ensure model directories exist
mkdir -p models/unet
mkdir -p models/clip
mkdir -p models/vae
mkdir -p models/checkpoints
```

**4.2 Download Using HuggingFace CLI (Recommended)**

```powershell
# Install HuggingFace CLI (if not already installed)
pip install -U huggingface-hub

# Option A: Download Flux.1-schnell (Apache 2.0, Commercial)
huggingface-cli download black-forest-labs/FLUX.1-schnell --local-dir models/checkpoints/flux-schnell

# Option B: Download Flux.1-dev (Non-commercial without license)
huggingface-cli download black-forest-labs/FLUX.1-dev --local-dir models/checkpoints/flux-dev

# Option C: Download FP8 Quantized (Recommended for 24GB)
huggingface-cli download Kijai/flux-fp8 flux1-dev-fp8.safetensors --local-dir models/unet

# Option D: Download GGUF Q8 (Alternative quantization)
huggingface-cli download city96/FLUX.1-dev-gguf flux1-dev-Q8_0.gguf --local-dir models/unet
```

**4.3 Download Required Text Encoders**

```powershell
# Download T5 XXL encoder (required)
huggingface-cli download google/t5-v1_1-xxl --local-dir models/clip/t5xxl

# Download CLIP-L encoder (required)
huggingface-cli download openai/clip-vit-large-patch14 --local-dir models/clip/clip-l
```

**4.4 Download VAE**

```powershell
# Download Flux VAE
huggingface-cli download black-forest-labs/FLUX.1-schnell ae.safetensors --local-dir models/vae
```

**Alternative: Manual Download**

If CLI download fails:
1. Visit model pages on HuggingFace
2. Click "Files and versions"
3. Download files manually
4. Place in appropriate folders:
   - Transformers → `models/unet/`
   - Text encoders → `models/clip/`
   - VAE → `models/vae/`
   - All-in-one checkpoints → `models/checkpoints/`

#### Step 5: Launch ComfyUI

**5.1 Start ComfyUI**

```powershell
# From ComfyUI root directory
python main.py

# For maximum performance (24GB GPU)
python main.py --highvram

# With preview images
python main.py --preview-method auto

# Listen on network (access from other devices)
python main.py --listen 0.0.0.0
```

**5.2 Access Web Interface**

Open browser and navigate to:
```
http://127.0.0.1:8188
```

**5.3 First Launch Checks**

- ComfyUI Manager should appear in right sidebar
- Check console for any errors
- Verify models loaded successfully

#### Step 6: Load Flux Workflow

**6.1 Download Example Workflows**

Visit these resources for Flux workflows:
- [ComfyUI Examples](https://github.com/comfyanonymous/ComfyUI_examples)
- [Flux Workflows on CivitAI](https://civitai.com/)
- [ComfyUI Wiki - Flux Guide](https://comfyui-wiki.com/)

**6.2 Load Workflow in ComfyUI**

```
1. Download workflow JSON file
2. Drag and drop JSON into ComfyUI interface
3. If nodes are red (missing):
   - Click "Manager" button
   - Select "Install Missing Nodes"
   - Restart ComfyUI
4. Configure model paths if needed
5. Click "Queue Prompt" to generate
```

**6.3 Basic Flux Workflow Structure**

```
Text Prompt
    ↓
CLIP Text Encode (clip-l)
    ↓
T5 Text Encode (t5xxl)
    ↓
Flux Model Loader → Flux Sampler
    ↓
VAE Decode
    ↓
Save Image
```

#### Step 7: Optimize Settings

**7.1 Recommended Settings for RTX 4090 (24GB)**

```yaml
Model Format: FP8 or FP16
Steps: 20-25 (dev) or 4 (schnell)
Sampler: euler
Scheduler: simple
CFG Scale: 7.0 (dev) or 1.0 (schnell)
Resolution: 1024x1024 (start), 1536x1536 (comfortable)
Batch Size: 1
```

**7.2 Recommended Settings for RTX 3090 (24GB)**

```yaml
Model Format: FP8 or Q8_0
Steps: 20-25 (dev) or 4 (schnell)
Sampler: euler
Scheduler: simple
CFG Scale: 7.0 (dev) or 1.0 (schnell)
Resolution: 1024x1024 (recommended)
Batch Size: 1
```

**7.3 Performance Tuning**

```powershell
# For fastest generation (may use more VRAM)
python main.py --highvram --preview-method latent2rgb

# For stability (balanced)
python main.py --normalvram

# For troubleshooting
python main.py --verbose
```

#### Step 8: Create Desktop Shortcut

**8.1 Create Batch File**

Create `launch_comfyui.bat` in ComfyUI directory:

```batch
@echo off
cd /d "%~dp0"
call venv\Scripts\activate.bat
python main.py --highvram --preview-method auto
pause
```

**8.2 Create Shortcut**
- Right-click `launch_comfyui.bat`
- Send to → Desktop (create shortcut)
- Rename to "ComfyUI Flux"

### Installation: Automatic1111 WebUI + Forge

**Note:** As of 2025, ComfyUI is recommended for Flux. However, if you prefer Automatic1111:

#### Step 1: Install Forge (Automatic1111 Fork with Flux Support)

```powershell
# Clone Forge repository
cd C:\AI
git clone https://github.com/lllyasviel/stable-diffusion-webui-forge.git
cd stable-diffusion-webui-forge

# Run installation
.\webui-user.bat
```

#### Step 2: Download Models

Place Flux models in:
```
stable-diffusion-webui-forge/models/Stable-diffusion/
```

#### Step 3: Configure for Flux

Edit `webui-user.bat`:
```batch
set COMMANDLINE_ARGS=--xformers --medvram-sdxl
```

#### Step 4: Launch

```powershell
.\webui-user.bat
```

Access at: `http://127.0.0.1:7860`

**Note:** Flux support in Automatic1111/Forge is less mature than ComfyUI. Some features may not work optimally.

### Installation: Python/Diffusers (Direct)

#### Step 1: Create Project

```powershell
mkdir C:\AI\flux-diffusers
cd C:\AI\flux-diffusers

# Create virtual environment
python -m venv venv
.\venv\Scripts\Activate.ps1

# Install dependencies
pip install torch torchvision torchaudio --index-url https://download.pytorch.org/whl/cu121
pip install diffusers transformers accelerate safetensors huggingface-hub
```

#### Step 2: Create Generation Script

Create `generate_flux.py`:

```python
import torch
from diffusers import FluxPipeline

# Load model
print("Loading Flux model...")
pipe = FluxPipeline.from_pretrained(
    "black-forest-labs/FLUX.1-schnell",  # or FLUX.1-dev
    torch_dtype=torch.bfloat16
)
pipe.to("cuda")

# Generate image
prompt = "A majestic lion standing on a cliff at sunset, photorealistic, 8k"
print(f"Generating: {prompt}")

image = pipe(
    prompt=prompt,
    guidance_scale=0.0,  # For schnell; use 7.0 for dev
    num_inference_steps=4,  # For schnell; use 20-50 for dev
    height=1024,
    width=1024,
    max_sequence_length=256,
).images[0]

# Save image
image.save("output.png")
print("Image saved to output.png")
```

#### Step 3: Run Generation

```powershell
python generate_flux.py
```

**For FP8 Optimization:**

```python
# Use FP8 quantization for faster generation
pipe = FluxPipeline.from_pretrained(
    "black-forest-labs/FLUX.1-dev",
    torch_dtype=torch.float8_e4m3fn  # FP8
)
```

---

## Common Issues & Troubleshooting

### Issue 1: Out of Memory (OOM) Errors

**Symptoms:**
```
RuntimeError: CUDA out of memory
```

**Solutions:**

```yaml
1. Use quantized model:
   - Switch from FP16 to FP8 or Q8_0
   - Reduces VRAM from 22GB to 12-16GB

2. Lower resolution:
   - Start with 1024x1024
   - Avoid 2048+ on first run

3. Enable CPU offloading:
   python main.py --cpu

4. Reduce batch size:
   - Set batch size to 1
   - Disable preview images

5. Close other GPU applications:
   - Check nvidia-smi for other processes
   - Close browsers with hardware acceleration
```

### Issue 2: Slow Generation Speed

**Symptoms:**
- Generation takes minutes instead of seconds
- GPU utilization < 90%

**Solutions:**

```yaml
1. Check GPU usage:
   nvidia-smi -l 1
   # Should show 95-100% GPU utilization

2. Verify correct PyTorch CUDA:
   python -c "import torch; print(torch.cuda.is_available())"
   # Should be True

3. Use optimal launch flags:
   python main.py --highvram  # For 24GB GPUs

4. Update GPU driver:
   # Get latest from nvidia.com

5. Use FP8 instead of FP16:
   # FP8 is faster with minimal quality loss

6. Check thermal throttling:
   nvidia-smi
   # GPU temp should be < 83°C
```

### Issue 3: Model Not Loading

**Symptoms:**
```
Model file not found
Failed to load checkpoint
```

**Solutions:**

```yaml
1. Verify file locations:
   # Check models are in correct directories
   models/unet/         # For transformers
   models/checkpoints/  # For all-in-one files
   models/clip/         # For text encoders
   models/vae/          # For VAE

2. Check file integrity:
   # Verify download completed
   # Check file sizes match expected

3. Re-download models:
   huggingface-cli download black-forest-labs/FLUX.1-schnell

4. Update ComfyUI:
   # Via ComfyUI Manager: Update All
   git pull  # From ComfyUI root

5. Check file permissions:
   # Ensure files are readable
   # Run as administrator if needed
```

### Issue 4: Poor Image Quality

**Symptoms:**
- Blurry images
- Poor text rendering
- Artifacts and errors

**Solutions:**

```yaml
1. Use correct model:
   # Flux.1-dev for quality
   # Flux.1-schnell for speed

2. Increase steps:
   # Dev: 20-50 steps
   # Schnell: 4 steps (don't increase)

3. Use higher quality format:
   FP16 > FP8 > Q8_0 > Q6_K > Q5_K_M

4. Adjust CFG scale:
   # Dev: 7.0-8.0
   # Schnell: 1.0 (don't change)

5. Improve prompts:
   # Add quality tags: "high quality, detailed, 8k"
   # Be specific and descriptive
   # Use positive and negative prompts

6. Check sampler settings:
   # Recommended: euler, simple scheduler
   # Experiment with dpm++, kdpm2
```

### Issue 5: Installation Errors

**Symptoms:**
```
pip install fails
Module not found errors
Compilation errors
```

**Solutions:**

```yaml
1. Install Visual Studio Build Tools:
   # Required for compiling packages
   # Get from visualstudio.microsoft.com

2. Update pip:
   python -m pip install --upgrade pip setuptools wheel

3. Use Python 3.10 or 3.11:
   # Python 3.12+ may have compatibility issues
   python --version

4. Install in clean environment:
   # Create fresh virtual environment
   python -m venv venv_new

5. Check antivirus/firewall:
   # May block downloads or compilation
   # Add exception for Python/ComfyUI directories
```

---

## Best Practices & Tips

### 1. Model Management

```yaml
Organize Your Models:
models/
├── unet/
│   ├── flux-schnell/          # Commercial use
│   ├── flux-dev-fp8/           # Best quality/speed balance
│   ├── flux-dev-q8/            # Extra VRAM headroom
│   └── flux-dev-fp16/          # Maximum quality
├── clip/
│   ├── t5xxl/                  # T5 text encoder
│   └── clip-l/                 # CLIP encoder
├── vae/
│   └── flux-vae/               # VAE decoder
└── checkpoints/
    └── flux-all-in-one/        # Combined models

Naming Convention:
flux-[variant]-[format]-[notes].safetensors

Examples:
flux-schnell-fp8-commercial.safetensors
flux-dev-q8-highquality.safetensors
```

### 2. Prompt Engineering for Flux

```yaml
Effective Flux Prompts:

Structure:
[Subject] [Action] [Setting] [Style] [Quality tags]

Good Examples:
"A majestic lion standing on a cliff at sunset,
photorealistic, highly detailed, 8k, national geographic quality"

"Portrait of a young woman with flowing hair,
Renaissance painting style, oil on canvas, dramatic lighting,
baroque, museum quality"

"Futuristic city skyline at night, neon lights, cyberpunk,
high detail, cinematic composition, 4k"

Tips:
├─ Be specific and descriptive
├─ Include style/medium references
├─ Add quality tags (8k, detailed, etc.)
├─ Use negative prompts for unwanted elements
└─ Experiment with artistic styles
```

### 3. Workflow Optimization

```yaml
Efficient Generation Workflow:

1. Start with schnell for quick previews:
   - 4 steps, fast feedback
   - Iterate on prompts and composition

2. Switch to dev for final renders:
   - 20-50 steps for quality
   - FP8 or FP16 format

3. Use batching for variations:
   - Generate multiple seeds
   - Find best composition

4. Save successful workflows:
   - Export as JSON
   - Document prompt formulas

5. Organize outputs:
   - Structured folder hierarchy
   - Metadata embedded in images
```

### 4. Commercial Use Best Practices

```yaml
For Commercial Projects:

Legal:
├─ Use Flux.1-schnell (Apache 2.0) OR
├─ License Flux.1-dev ($999/month) OR
└─ Use Flux.1.1 Pro API

Documentation:
├─ Keep records of model versions
├─ Document generation settings
├─ Save prompt information
└─ Track licensing compliance

Client Work:
├─ Disclose AI generation to clients
├─ Verify client accepts AI artwork
├─ Include usage rights in contracts
└─ Keep originals and iterations

Quality Control:
├─ Generate multiple variations
├─ Manual review before delivery
├─ Upscale for print quality
└─ Post-process in Photoshop/GIMP
```

### 5. Performance Monitoring

```yaml
Monitor Your System:

GPU Monitoring:
# Real-time GPU stats
nvidia-smi -l 1

# Detailed monitoring
nvidia-smi dmon -s pucvmet

Key Metrics:
├─ GPU Utilization: Should be 95-100% during generation
├─ VRAM Usage: Monitor to prevent OOM
├─ Temperature: Keep < 83°C
├─ Power Draw: Should be near TDP
└─ Clock Speeds: Check for throttling

Tools:
├─ NVIDIA SMI (command line)
├─ GPU-Z (GUI monitoring)
├─ MSI Afterburner (overclocking + monitoring)
└─ HWiNFO64 (comprehensive system monitoring)
```

### 6. Backup & Version Control

```yaml
Protect Your Work:

Git for Workflows:
git init
git add workflows/*.json
git commit -m "Add flux portrait workflow"

Backup Critical Files:
backup/
├── workflows/          # JSON workflows
├── custom-nodes/       # Custom modifications
├── prompts/            # Prompt libraries
└── settings/           # ComfyUI configurations

Cloud Backup:
├─ Google Drive / OneDrive for workflows
├─ GitHub for code and configs
├─ External drive for generated images
└─ Automated daily backups

Version Models:
├─ Keep multiple model versions
├─ Test new versions before replacing
├─ Document model provenance
└─ Track performance differences
```

---

## Additional Resources

### Official Documentation

- **Black Forest Labs:** [bfl.ai](https://bfl.ai/)
- **ComfyUI GitHub:** [github.com/comfyanonymous/ComfyUI](https://github.com/comfyanonymous/ComfyUI)
- **ComfyUI Wiki:** [comfyui-wiki.com](https://comfyui-wiki.com/)
- **HuggingFace Flux:** [huggingface.co/black-forest-labs](https://huggingface.co/black-forest-labs)

### Community Resources

- **ComfyUI Discord:** Active community, troubleshooting, workflow sharing
- **Reddit r/StableDiffusion:** General AI art community
- **Reddit r/ComfyUI:** ComfyUI-specific discussions
- **CivitAI:** Model and workflow sharing

### Learning Resources

- **ComfyUI Tutorials:** YouTube channels dedicated to ComfyUI workflows
- **Flux Prompt Engineering:** Community guides on effective prompting
- **GPU Optimization Guides:** NVIDIA and community optimization tips

### Tools & Utilities

- **ComfyUI Manager:** Essential for node management
- **HuggingFace CLI:** For efficient model downloads
- **Git LFS:** For handling large model files
- **GPU-Z:** GPU monitoring and diagnostics

---

## Conclusion

Running Flux models locally on a 24GB GPU (RTX 3090 or RTX 4090) is highly feasible and provides excellent results. Here's your quick decision guide:

### Quick Decision Guide

```yaml
Your Situation → Recommended Setup

Beginner + Commercial Use:
└─> ComfyUI + Flux.1-schnell (FP8)
    ├─ Why: Free license, fast generation, good quality
    └─ Setup: 2-3 hours

Enthusiast + Personal Projects:
└─> ComfyUI + Flux.1-dev (FP8 or Q8_0)
    ├─ Why: Best quality, no cost, full control
    └─ Setup: 2-3 hours

Professional + Commercial:
└─> ComfyUI + Flux.1-dev (FP8) + Commercial License
    ├─ Why: Unlimited generation, highest control
    ├─ Cost: $999/month
    └─ Alternative: Flux.1-schnell (free, slightly lower quality)

Developer + Integration:
└─> Python/Diffusers + Flux.1-schnell
    ├─ Why: Programmatic control, API-like
    └─ Setup: 1-2 hours

Budget-Conscious:
└─> RTX 3090 + ComfyUI + Flux.1-schnell (Q8_0)
    ├─ Why: Half the GPU cost, same workflows
    └─ Performance: 70% of RTX 4090 speed
```

### Key Takeaways

1. **RTX 4090** is the best choice for maximum performance (~30% faster than 3090)
2. **RTX 3090** offers incredible value at half the cost with same 24GB VRAM
3. **ComfyUI** is the recommended platform for Flux in 2025
4. **FP8 quantization** provides 99% quality with 16GB VRAM usage
5. **Flux.1-schnell** is free for commercial use (Apache 2.0)
6. **Flux.1-dev** requires commercial license for business use ($999/month base)
7. **Flux.1.1 Pro** is API-only, cannot be installed locally

### Next Steps

1. **Verify hardware compatibility** (24GB GPU, 32GB+ RAM)
2. **Install ComfyUI** (2-3 hour process)
3. **Download Flux models** (FP8 recommended for 24GB GPUs)
4. **Load example workflow** and generate your first image
5. **Experiment with prompts** and settings
6. **Review licensing** if commercial use is intended

---

## Sources

This guide was compiled from the following sources:

- [This gist shows how to run Flux on a 24GB 4090 card with Diffusers](https://gist.github.com/sayakpaul/23862a2e7f5ab73dfdcc513751289bea)
- [See What GPU to Use with FLUX.1 AI Image Model | Hardware Corner](https://www.hardware-corner.net/guides/gpu-for-flux-1-image-model/)
- [Best GPU for AI Image & Video Generation 2025 - Complete Buying Guide | Apatero Blog](https://apatero.com/blog/best-gpu-ai-image-video-generation-use-cases-2025)
- [Choosing the Right Flux Model for Your GPU: A Comprehensive Guide + Workflows - Helpful Tiger](https://www.helpfultiger.com/choosing-the-right-flux-model-for-your-gpu-a-comprehensive-guide-workflows/)
- [GPU Buying Guide for AI Art - ComfyUI Focus | ComfyUI Wiki](https://comfyui-wiki.com/en/install/install-comfyui/gpu-buying-guide)
- [RTX 4090 benchmarks - FLUX model](https://github.com/comfyanonymous/ComfyUI/discussions/4571)
- [How to Install and Run Flux 1 schnell or dev in ComfyUI Locally on Windows Computer](https://aleksandarhaber.com/how-to-install-and-run-flux-1-schnell-or-dev-in-comfyui-locally-on-windows-computer/)
- [Flux.1 ComfyUI Guide, workflow and example | ComfyUI Wiki](https://comfyui-wiki.com/en/tutorial/advanced/image/flux/flux-1-dev-t2i)
- [Install ComfyUI and Install Flux 1. Dev in ComfyUI in Windows](https://aleksandarhaber.com/install-comfyui-and-install-flux-1-dev-in-comfyui-in-windows/)
- [How to install Flux AI model on ComfyUI - Stable Diffusion Art](https://stable-diffusion-art.com/flux-comfyui/)
- [How to Run FLUX AI (FLUX.1) Locally — Full Setup Guide (ComfyUI, Forge, Docker)](https://blog.medihertz.com/how-to-run-flux-ai-flux-1-locally-full-setup-guide-comfyui-forge-docker/)
- [ComfyUI Masterclass Part 7: How to Install and Run Flux 1 Dev and Schnell](https://techxplainator.com/comfyui-masterclass-part7-flux1/)
- [FLUX: Installing in ComfyUI/Forge (GGUF/NF4/FP8/FP16)](https://www.stablediffusiontutorials.com/2024/08/flux-installation.html)
- [Flux 2 GGUF Quantized Models - Low VRAM Guide 2025 | Apatero Blog](https://apatero.com/blog/flux-2-gguf-quantized-models-low-vram-guide)
- [city96/FLUX.1-dev-gguf · all K quants comparison using fp16/fp8 t5](https://huggingface.co/city96/FLUX.1-dev-gguf/discussions/15)
- [VRAM Optimization Flags Explained ComfyUI Guide 2025 - Apatero Blog](https://apatero.com/blog/vram-optimization-flags-comfyui-explained-guide-2025)
- [Quantized Models: GGUF vs NF4 vs FP8 vs FP16](https://www.stablediffusiontutorials.com/2025/05/quantized-models-gguf-vs-nf4-vs-fp8-vs.html)
- [Picking the Right Size Brain: FP16, BF16, FP8, GGUF and What They Actually Mean](https://www.instasd.com/post/picking-the-right-size-brain-fp16-bf16-fp8-gguf-and-what-they-actually-mean)
- [GGUF Quantized Models Complete Guide 2025 - Apatero Blog](https://apatero.com/blog/gguf-quantized-models-complete-guide-2025)
- [NVIDIA TensorRT Unlocks FP4 Image Generation for NVIDIA Blackwell GeForce RTX 50 Series GPUs](https://developer.nvidia.com/blog/nvidia-tensorrt-unlocks-fp4-image-generation-for-nvidia-blackwell-geforce-rtx-50-series-gpus/)
- [black-forest-labs (Black Forest Labs)](https://huggingface.co/black-forest-labs)
- [black-forest-labs/FLUX.1-dev · Hugging Face](https://huggingface.co/black-forest-labs/FLUX.1-dev)
- [black-forest-labs/FLUX.1-schnell · Hugging Face](https://huggingface.co/black-forest-labs/FLUX.1-schnell)
- [GitHub - black-forest-labs/flux: Official inference repo for FLUX.1 models](https://github.com/black-forest-labs/flux)
- [LICENSE.md · black-forest-labs/FLUX.1-dev at main](https://huggingface.co/black-forest-labs/FLUX.1-dev/blob/main/LICENSE.md)
- [flux/model_licenses/LICENSE-FLUX1-dev at main · black-forest-labs/flux](https://github.com/black-forest-labs/flux/blob/main/model_licenses/LICENSE-FLUX1-dev)
- [Get a Commercial License for Flux](https://www.invoke.com/get-a-commercial-license-for-flux)
- [flux/model_licenses/LICENSE-FLUX1-schnell at main · black-forest-labs/flux](https://github.com/black-forest-labs/flux/blob/main/model_licenses/LICENSE-FLUX1-schnell)
- [FLUX 1.1 Pro | Black Forest Labs](https://bfl.ai/models/flux-pro)
- [Licensing | Black Forest Labs](https://bfl.ai/licensing)
- [ComfyUI vs Automatic1111 vs Fooocus: Complete 2025 Comparison](https://www.propelrc.com/comfyui-vs-automatic1111-vs-fooocus/)
- [List of Free Local AI Image Generation Software - Fooocus, Automatic1111, ComfyUI & More](https://techtactician.com/list-and-comparison-of-local-stable-diffusion-webui-software/)
- [ComfyUI vs. Automatic1111 vs. Fooocus - I Used All Three](https://techtactician.com/comfyui-vs-automatic1111-vs-fooocus-comparison/)
- [ComfyUI vs Stable Diffusion WebUI: The Ultimate Comparison Guide for 2025](https://www.aifreeapi.com/en/posts/comfyui-vs-stable-diffusion)
- [city96/FLUX.1-dev-gguf · Hugging Face](https://huggingface.co/city96/FLUX.1-dev-gguf)
- [black-forest-labs/FLUX.2-dev · Hugging Face](https://huggingface.co/black-forest-labs/FLUX.2-dev)
- [GPU Benchmark Flux DEV fp8 5090 4090 3090](https://github.com/comfyanonymous/ComfyUI/discussions/9002)
- [RTX 3090 vs 4090 for AI & Deep Learning (2025): Which is Better?](https://www.bestgpusforai.com/gpu-comparison/3090-vs-4090)

---

**Document Version:** 1.0
**Last Updated:** December 9, 2025
**Author:** Compiled for SpiritAtlas Project
**License:** This guide is for informational purposes. All referenced models and software retain their original licenses.
