# ComfyUI Flux Batch Generation Guide

Complete guide for automating batch image generation with Flux models and LoRAs using ComfyUI.

---

## Table of Contents

1. [ComfyUI Overview](#1-comfyui-overview)
2. [Windows Installation](#2-windows-installation)
3. [Flux Model Integration](#3-flux-model-integration)
4. [LoRA Integration](#4-lora-integration)
5. [Batch Processing Setup](#5-batch-processing-setup)
6. [API & Automation](#6-api--automation)
7. [Example Workflow JSON](#7-example-workflow-json)
8. [Python Automation Scripts](#8-python-automation-scripts)
9. [Prompt Template System](#9-prompt-template-system)
10. [Production Tips](#10-production-tips)

---

## 1. ComfyUI Overview

### What is ComfyUI?

ComfyUI is a powerful node-based interface for Stable Diffusion and other AI image generation models. Instead of traditional text-based interfaces, ComfyUI uses a visual graph system where nodes represent operations (loading models, encoding prompts, sampling, saving images) that are connected to create workflows.

### Node-Based Architecture

**Key Concepts:**
- **Nodes**: Individual operations or components (e.g., Load Checkpoint, KSampler, Save Image)
- **Connections**: Data flows between nodes through connections (e.g., MODEL, CLIP, VAE, LATENT, IMAGE)
- **Inputs/Outputs**:
  - Inputs: Left side of nodes (receive data)
  - Outputs: Right side of nodes (send data)
  - Parameters: Center controls (node-specific settings)

**Node Flow Example:**
```
Load Checkpoint → CLIP Text Encode (positive) → KSampler → VAE Decode → Save Image
                → CLIP Text Encode (negative) ↗
                → Empty Latent Image ↗
```

### JSON Workflow Format

ComfyUI workflows are saved as JSON files with two main formats:

1. **Workflow Format**: Human-readable format including UI positioning
2. **API Format**: Streamlined format for programmatic execution (Save as API Format)

**Basic Structure:**
```json
{
  "1": {
    "class_type": "CheckpointLoaderSimple",
    "inputs": {
      "ckpt_name": "flux1-dev-fp8.safetensors"
    }
  },
  "2": {
    "class_type": "CLIPTextEncode",
    "inputs": {
      "text": "Your prompt here",
      "clip": ["1", 1]
    }
  }
}
```

**Key Features:**
- Workflows save all node configurations, connections, and parameter values
- Can be embedded in generated PNG/WebP files for sharing
- JSON format allows programmatic modification for automation
- Compatible with SD 1.x, 2.x, SDXL, Flux, and other models

---

## 2. Windows Installation

### Prerequisites

**Hardware Requirements:**
- NVIDIA GPU with at least 4GB VRAM (8GB+ recommended for Flux)
- 16GB+ RAM recommended
- 50GB+ free disk space for models

**Software Requirements:**
- Windows 10/11
- NVIDIA GPU drivers (latest recommended)
- CUDA 12.8 (automatically installed with modern PyTorch)

### Installation Methods

#### Method 1: ComfyUI Desktop (Easiest)

1. Download ComfyUI Desktop from the official repository
2. Run the installer
3. Launch ComfyUI Desktop
4. GPU detection and CUDA setup happen automatically

**Advantages:**
- Simplest installation
- Automatic updates
- Built-in model manager
- No command line required

#### Method 2: Standalone Installation

1. **Download Pre-packaged Version:**
   ```bash
   # Download from: https://github.com/comfyanonymous/ComfyUI/releases
   # Extract to desired location (e.g., C:\ComfyUI)
   ```

2. **Run ComfyUI:**
   ```bash
   cd C:\ComfyUI
   run_nvidia_gpu.bat
   ```

3. **Access Web Interface:**
   - Open browser to `http://127.0.0.1:8188`

#### Method 3: Automated Installer with Conda

```bash
# Clone automated installer
git clone https://github.com/HurbaLurba/comfyui-windows-installer.git
cd comfyui-windows-installer

# Run installation script
install.bat

# The script will:
# - Create Conda environment "ComfyUI" with Python 3.12.11
# - Install PyTorch with CUDA 12.8 (falls back to 12.6 if needed)
# - Install all dependencies
# - Set up ComfyUI
```

### Verify GPU Detection

After installation, check if CUDA is working:

1. Open ComfyUI web interface
2. Check the console/terminal window for GPU information
3. You should see something like: `NVIDIA GeForce RTX 4090 - 24GB VRAM`

**Troubleshooting:**
- If GPU not detected, verify NVIDIA drivers are installed
- Check CUDA version compatibility
- Ensure GPU has sufficient VRAM

### Custom Node Installation

**Method 1: ComfyUI Manager (Recommended)**

1. Install ComfyUI Manager:
   ```bash
   cd C:\ComfyUI\custom_nodes
   git clone https://github.com/ltdrdata/ComfyUI-Manager.git
   ```

2. Restart ComfyUI

3. Click "Manager" button in web interface

4. Install custom nodes from the catalog

**Method 2: Manual Installation**

```bash
cd C:\ComfyUI\custom_nodes
git clone [custom-node-repository-url]
# Restart ComfyUI
```

**Essential Custom Nodes for Batch Processing:**
- **ComfyUI-Manager**: Node management interface
- **rgthree-comfy**: LoRA Loader Stack and workflow utilities
- **efficiency-nodes-comfyui**: LoRA Stacker and efficient nodes
- **ComfyUI_PromptIterator**: Iterate through multiple prompts
- **ComfyUI-KikoTools**: Batch prompts from file

---

## 3. Flux Model Integration

### Understanding Flux Models

Flux is a next-generation image synthesis model with two main variants:
- **Flux.1 [dev]**: High-quality, suitable for most use cases
- **Flux.1 [schnell]**: Faster generation, fewer steps

**Model Formats:**
- **Full FP16**: Highest quality, ~24GB VRAM required
- **FP8**: Reduced precision, ~12GB VRAM, minimal quality loss
- **NF4**: Quantized, ~6GB VRAM, some quality trade-off

### Model Installation

1. **Download Flux Models:**
   - Place checkpoint files in: `ComfyUI/models/checkpoints/`
   - Or separate components:
     - Diffusion model: `ComfyUI/models/unet/`
     - CLIP text encoders: `ComfyUI/models/clip/`
     - VAE: `ComfyUI/models/vae/`

2. **Required Files:**
   - Checkpoint: `flux1-dev-fp8.safetensors` (or similar)
   - Text Encoders:
     - `t5xxl_fp16.safetensors` (or `t5xxl_fp8_e4m3fn.safetensors` for lower VRAM)
     - `clip_l.safetensors`

### Flux Workflow Configuration

#### Essential Nodes for Flux

1. **CheckpointLoaderSimple** or **DualCLIPLoader + UNETLoader**
   ```json
   "1": {
     "class_type": "CheckpointLoaderSimple",
     "inputs": {
       "ckpt_name": "flux1-dev-fp8.safetensors"
     }
   }
   ```

2. **CLIPTextEncode** for prompts
   ```json
   "2": {
     "class_type": "CLIPTextEncode",
     "inputs": {
       "text": "Your positive prompt",
       "clip": ["1", 1]
     }
   },
   "3": {
     "class_type": "CLIPTextEncode",
     "inputs": {
       "text": "Your negative prompt",
       "clip": ["1", 1]
     }
   }
   ```

3. **ModelSamplingFlux** (Optional - for advanced control)
   ```json
   "4": {
     "class_type": "ModelSamplingFlux",
     "inputs": {
       "model": ["1", 0],
       "max_shift": 1.15,
       "base_shift": 0.5,
       "width": 1024,
       "height": 1024
     }
   }
   ```

4. **FluxGuidance** (Replaces CFG)
   ```json
   "5": {
     "class_type": "FluxGuidance",
     "inputs": {
       "conditioning": ["2", 0],
       "guidance": 3.5
     }
   }
   ```

5. **KSampler** or **SamplerCustomAdvanced**
   ```json
   "6": {
     "class_type": "KSampler",
     "inputs": {
       "model": ["1", 0],
       "seed": 123456,
       "steps": 20,
       "cfg": 1.0,
       "sampler_name": "euler",
       "scheduler": "simple",
       "positive": ["5", 0],
       "negative": ["3", 0],
       "latent_image": ["7", 0]
     }
   }
   ```

6. **EmptyLatentImage**
   ```json
   "7": {
     "class_type": "EmptyLatentImage",
     "inputs": {
       "width": 1024,
       "height": 1024,
       "batch_size": 1
     }
   }
   ```

7. **VAEDecode**
   ```json
   "8": {
     "class_type": "VAEDecode",
     "inputs": {
       "samples": ["6", 0],
       "vae": ["1", 2]
     }
   }
   ```

8. **SaveImage**
   ```json
   "9": {
     "class_type": "SaveImage",
     "inputs": {
       "images": ["8", 0],
       "filename_prefix": "FluxOutput"
     }
   }
   ```

### Critical Sampler Settings for Flux

**Guidance Scale:**
- **CFG must be set to 1.0** (Flux doesn't use traditional CFG)
- Use **FluxGuidance** node instead with guidance values:
  - Short prompts: `4.0`
  - Medium prompts: `3.5` (recommended default)
  - Long/creative prompts: `1.0-1.5`

**Steps:**
- **Flux.1 [dev]**: 20-50 steps
  - 20 steps: Good quality, faster
  - 30-40 steps: Better quality
  - 50+ steps: Diminishing returns
- **Flux.1 [schnell]**: 4-8 steps (designed for speed)

**Sampler & Scheduler:**
- Recommended sampler: `euler` or `dpmpp_2m`
- Recommended scheduler: `simple` or `normal`
- For Flux, simpler samplers often work better

**Resolution:**
- Native resolution: 1024x1024
- Supported: 512x512 to 2048x2048
- Non-square resolutions work well (e.g., 1024x1536)

### Memory Optimization

For limited VRAM:
1. Use FP8 versions: `t5xxl_fp8_e4m3fn.safetensors`
2. Enable VAE tiling (custom node required)
3. Reduce batch size to 1
4. Use NF4 quantized models

---

## 4. LoRA Integration

### What are LoRAs?

LoRA (Low-Rank Adaptation) are small, specialized model adaptations that add specific styles, characters, or concepts to base models without requiring full model retraining.

**Benefits:**
- Small file size (typically 10-200MB vs multi-GB checkpoints)
- Stackable (use multiple LoRAs together)
- Adjustable strength
- Can be easily shared and combined

### LoRA Installation

1. **Download LoRA Files:**
   - Place `.safetensors` LoRA files in: `ComfyUI/models/loras/`

2. **Organization (Recommended):**
   ```
   ComfyUI/models/loras/
   ├── styles/
   │   ├── anime_style.safetensors
   │   └── realistic_style.safetensors
   ├── characters/
   │   └── character_lora.safetensors
   └── concepts/
       └── concept_lora.safetensors
   ```

### Single LoRA Loading

**Basic LoRALoader Node:**
```json
"10": {
  "class_type": "LoraLoader",
  "inputs": {
    "model": ["1", 0],
    "clip": ["1", 1],
    "lora_name": "styles/anime_style.safetensors",
    "strength_model": 1.0,
    "strength_clip": 1.0
  }
}
```

**Parameters:**
- `strength_model`: Affects image generation (0.0-1.0+)
- `strength_clip`: Affects text encoding (0.0-1.0+)
- Values above 1.0 can be used for stronger effects (not always recommended)

**Node Connection:**
- Input: Takes MODEL and CLIP from checkpoint loader
- Output: Outputs modified MODEL and CLIP to sampler

### Multiple LoRA Stacking

#### Method 1: Chain LoRALoader Nodes

```json
"10": {
  "class_type": "LoraLoader",
  "inputs": {
    "model": ["1", 0],
    "clip": ["1", 1],
    "lora_name": "first_lora.safetensors",
    "strength_model": 0.8,
    "strength_clip": 0.8
  }
},
"11": {
  "class_type": "LoraLoader",
  "inputs": {
    "model": ["10", 0],
    "clip": ["10", 1],
    "lora_name": "second_lora.safetensors",
    "strength_model": 0.7,
    "strength_clip": 0.7
  }
},
"12": {
  "class_type": "LoraLoader",
  "inputs": {
    "model": ["11", 0],
    "clip": ["11", 1],
    "lora_name": "third_lora.safetensors",
    "strength_model": 0.6,
    "strength_clip": 0.6
  }
}
```

**Note:** Each LoRA feeds into the next, modifying the model progressively.

#### Method 2: LoRA Stacker Node (Recommended)

**Install:** `efficiency-nodes-comfyui` custom node

```json
"10": {
  "class_type": "LoRA Stacker",
  "inputs": {
    "input_mode": "simple",
    "lora_count": 3,
    "lora_name_1": "first_lora.safetensors",
    "lora_wt_1": 0.8,
    "model_str_1": 0.8,
    "clip_str_1": 0.8,
    "lora_name_2": "second_lora.safetensors",
    "lora_wt_2": 0.7,
    "model_str_2": 0.7,
    "clip_str_2": 0.7,
    "lora_name_3": "third_lora.safetensors",
    "lora_wt_3": 0.6,
    "model_str_3": 0.6,
    "clip_str_3": 0.6
  }
}
```

**Advantages:**
- Cleaner workflow
- Easier to enable/disable individual LoRAs
- Better visualization of all LoRAs at once

#### Method 3: Lora Loader Stack (rgthree)

**Install:** `rgthree-comfy` custom node

**Features:**
- Visual stack interface
- Easy reordering
- Individual bypass toggles
- Preset management

### LoRA Weight Management

**Best Practices:**

1. **Test LoRAs Individually First:**
   - Load each LoRA solo at different weights (0.3, 0.5, 0.7, 1.0)
   - Understand each LoRA's "character" and influence

2. **Stacking Guidelines:**
   - Avoid setting all strengths to 1.0 (causes visual artifacts)
   - Typical stacking values: 0.6-0.8 per LoRA
   - Reduce strength if a LoRA dominates the output

3. **Adjustment Strategy:**
   - If LoRA dominates prompts: Reduce `strength_clip` first
   - If visual style is too strong: Reduce `strength_model`
   - Start conservative (0.5-0.7) and increase as needed

4. **Compatibility:**
   - Not all LoRAs work well together
   - Test combinations before batch processing
   - Some LoRAs conflict in style or concept

### Flux-Specific LoRA Considerations

- Flux LoRAs are often trained differently than SDXL/SD1.5 LoRAs
- FluxGuidance affects LoRA influence
- Lower guidance (1.0-2.0) may require higher LoRA strength
- Higher guidance (3.5-4.0) may require lower LoRA strength

### Bypassing/Disabling LoRAs

**In ComfyUI Web Interface:**
- Right-click LoRA node
- Select "Bypass" (or press Ctrl+B)
- Node becomes semi-transparent when bypassed

**In JSON Workflow:**
- Cannot directly bypass via JSON
- Instead, set strengths to 0.0:
  ```json
  "strength_model": 0.0,
  "strength_clip": 0.0
  ```

---

## 5. Batch Processing Setup

### Understanding Batch Processing in ComfyUI

ComfyUI supports multiple batch processing approaches:

1. **Batch Size**: Generate multiple images from one prompt
2. **Queue Count**: Run the same workflow multiple times
3. **Prompt Iteration**: Generate images with different prompts
4. **Wildcards**: Auto-generate prompt variations

### Method 1: Batch Size in Empty Latent Image

**Simple but Limited:**

```json
"7": {
  "class_type": "EmptyLatentImage",
  "inputs": {
    "width": 1024,
    "height": 1024,
    "batch_size": 4
  }
}
```

**Behavior:**
- Generates 4 images with the same prompt and different seeds
- All images generated in one execution
- Same filename with different numbers

**Limitations:**
- Same prompt for all images
- Limited control over individual outputs

### Method 2: Queue Count (Manual)

**In Web Interface:**
1. Set up workflow
2. Below "Queue Prompt" button, expand "Extra Options"
3. Set "Queue Prompt Count" to desired number (e.g., 99)
4. Click "Queue Prompt"

**Result:**
- Queues 99 separate executions
- Each uses same workflow/prompt
- Seed varies (if using random seed)

**Limitations:**
- Still uses same prompt for all
- Requires custom nodes for prompt variation

### Method 3: Auto Queue Feature

**Enable Continuous Generation:**

1. Expand "Extra Options" menu
2. Enable "Auto Queue"
3. Workflow automatically restarts when queue reaches zero

**Use Case:**
- Continuous generation for testing
- Creating large datasets
- Combined with prompt randomization

### Method 4: Prompt Iterator (Dynamic Prompts)

**Install:** `ComfyUI_PromptIterator` custom node

**Features:**
- Iterate through multiple prompts from a list
- Automatic filename generation per prompt
- Progress tracking

**Usage:**

1. **Create Prompt List File:**
   ```text
   prompt1.txt:
   ---
   Beautiful sunset over mountains, vibrant colors
   ---
   Serene forest lake, misty morning
   ---
   Futuristic cityscape, neon lights
   ```

2. **Load in Workflow:**
   ```json
   "11": {
     "class_type": "PromptIterator",
     "inputs": {
       "prompt_file": "prompt1.txt",
       "current_index": 0
     }
   }
   ```

3. **Connect to CLIPTextEncode:**
   ```json
   "2": {
     "class_type": "CLIPTextEncode",
     "inputs": {
       "text": ["11", 0],
       "clip": ["1", 1]
     }
   }
   ```

4. **Queue Multiple Times:**
   - Set Queue Count to number of prompts
   - Each execution uses next prompt in file

### Method 5: Batch Prompts Node (KikoTools)

**Install:** `ComfyUI-KikoTools` custom node

**BatchPrompts Node:**

```json
"11": {
  "class_type": "BatchPrompts",
  "inputs": {
    "prompts": "Beautiful sunset over mountains\n---\nSerene forest lake\n---\nFuturistic cityscape",
    "separator": "---",
    "index": 0
  }
}
```

**Features:**
- Load prompts from multi-line string or file
- Separator defines prompt boundaries (default: `---`)
- Returns both positive and negative prompt components
- Index auto-increments per execution

### Method 6: ComfyUI Job Iterator

**Install:** `comfyui-job-iterator` custom node

**Features:**
- Advanced batch job management
- CSV integration for parameter variations
- Supports varying multiple parameters simultaneously
- Automatic progress tracking

**Example CSV Structure:**
```csv
prompt,steps,guidance,lora_strength
"Sunset over mountains",25,3.5,0.8
"Forest lake at dawn",30,3.0,0.7
"Cyberpunk cityscape",40,4.0,0.9
```

### Method 7: Wildcards (Advanced)

**Install:** `ComfyUI-DynamicPrompts` custom node

**Wildcard Syntax:**
```
A {beautiful|stunning|gorgeous} {sunset|sunrise} over {mountains|ocean|desert}
```

**Features:**
- Randomly selects variations from options
- Combinatorial explosion for massive variations
- Works with batch processing
- Can load options from external files

**Wildcard Files:**
```
ComfyUI/wildcards/adjectives.txt:
beautiful
stunning
gorgeous
vibrant
serene
```

**Usage in Prompt:**
```
A __adjectives__ landscape with __colors__ sky
```

### Output Organization for Batch Processing

#### Filename Prefix Customization

**Basic Prefix:**
```json
"9": {
  "class_type": "SaveImage",
  "inputs": {
    "images": ["8", 0],
    "filename_prefix": "flux_batch"
  }
}
```

**Result:** `flux_batch_00001.png`, `flux_batch_00002.png`, etc.

#### Advanced Filename Formatting

**Convert filename_prefix to Input:**
1. Right-click "Save Image" node → filename_prefix
2. Select "Convert to input"
3. Connect to a text primitive or custom node

**Using Special Tokens:**

Install custom nodes that support filename formatting (e.g., `ComfyUI_Soze`, `wlsh_nodes`)

**Build Filename String (WLSH):**
```json
"10": {
  "class_type": "Build Filename String (WLSH)",
  "inputs": {
    "project_name": "spirit_atlas_tarot",
    "timestamp": "%Y%m%d_%H%M%S",
    "model_name": true,
    "seed": true,
    "counter": true,
    "separator": "_"
  }
}
```

**Result:** `spirit_atlas_tarot_20251209_143022_flux1dev_seed123456_0001.png`

**Folder Organization:**
```json
"10": {
  "class_type": "Output Filename",
  "inputs": {
    "project_name": "SpiritAtlas",
    "subfolder": "tarot_cards",
    "date_format": "%Y-%m-%d",
    "filename": "card"
  }
}
```

**Result:** `SpiritAtlas/tarot_cards/2025-12-09-card_00001.png`

### Progress Tracking

**Built-in Progress:**
- ComfyUI web interface shows:
  - Current queue position
  - Number of steps completed
  - Estimated time remaining

**Custom Tracking:**
- Use custom nodes for detailed logging
- Monitor console/terminal output
- Check `ComfyUI/output/` folder for completed images

### Best Practices for Large Batches

1. **Test First:**
   - Generate 3-5 images with settings before full batch
   - Verify quality, composition, LoRA effects

2. **Save Workflow:**
   - Save workflow JSON before starting large batch
   - Keep backup in case of interruption

3. **Monitor Resources:**
   - Watch GPU VRAM usage
   - Monitor disk space (images can accumulate)
   - Check temperature if running long batches

4. **Incremental Batches:**
   - For 99 images, consider 3-4 batches of 25-33
   - Allows mid-course corrections
   - Reduces risk of wasted compute on errors

5. **Seed Management:**
   - Use random seed (set seed to `-1` or use random)
   - Or use sequential seeds for reproducibility

6. **Error Handling:**
   - If workflow fails mid-batch, note last successful image
   - Adjust starting index/counter for remaining prompts

---

## 6. API & Automation

### ComfyUI API Overview

ComfyUI exposes a REST API and WebSocket API for programmatic control:

**Base URL:** `http://127.0.0.1:8188` (default)

**Key Endpoints:**
- `POST /prompt` - Submit workflow to queue
- `GET /history` - Get execution history
- `GET /queue` - Get current queue status
- `GET /view` - Retrieve generated images
- `WebSocket ws://127.0.0.1:8188/ws` - Real-time progress

### API Format Workflow

**Exporting API Format:**

1. In ComfyUI web interface
2. Click "Save" button dropdown
3. Select "Save (API Format)"
4. Save as `workflow_api.json`

**Difference from Regular Workflow:**
- Removes UI-specific data (node positions, etc.)
- Streamlined for programmatic use
- Nodes referenced by ID only

### Python API Libraries

#### Option 1: Native HTTP + WebSocket

**Pros:**
- No dependencies beyond `websocket-client`
- Full control
- Official examples in ComfyUI repo

**Cons:**
- More boilerplate code
- Manual JSON manipulation

#### Option 2: ComfyScript

**Install:**
```bash
pip install comfy-script
```

**Features:**
- Pythonic API for ComfyUI
- Mix Python code with nodes
- Run workflows without web UI
- Easy loops and conditionals

**Example:**
```python
from comfy_script import ComfyScript

with ComfyScript():
    model, clip, vae = CheckpointLoaderSimple("flux1-dev-fp8.safetensors")

    for prompt in prompts:
        conditioning = CLIPTextEncode(prompt, clip)
        latent = EmptyLatentImage(1024, 1024, 1)
        samples = KSampler(model, 123, 20, 1.0, "euler", "simple",
                           conditioning, conditioning, latent)
        image = VAEDecode(samples, vae)
        SaveImage(image, f"output_{prompt[:20]}")
```

#### Option 3: ComfyUI-to-Python Extension

**Features:**
- Converts workflows to Python code
- Button in web interface
- Executable Python scripts
- Importable as modules

**Workflow:**
1. Design workflow in ComfyUI
2. Click "Save as Script" button
3. Get Python code equivalent
4. Modify for batch processing

#### Option 4: comfy_api_simplified

**Install:**
```bash
pip install comfy-api-simplified
```

**Features:**
- Simple parameter modification
- Helper methods for common tasks
- Workflow reuse

**Example:**
```python
from comfy_api_simplified import Workflow

wf = Workflow("workflow_api.json")
wf.set_node_param("positive", "text", "New prompt here")
wf.set_node_param("KSampler", "seed", 42)
wf.set_node_param("Empty Latent Image", "batch_size", 2)
wf.queue()
```

### Building Custom API Scripts

#### Basic WebSocket API Pattern

**Required Imports:**
```python
import json
import urllib.request
import urllib.parse
import websocket
import uuid
```

**Core Functions:**

```python
def queue_prompt(prompt, server_address="127.0.0.1:8188", client_id=None):
    """Submit workflow to ComfyUI queue"""
    data = {
        "prompt": prompt,
        "client_id": client_id
    }

    req = urllib.request.Request(
        f"http://{server_address}/prompt",
        data=json.dumps(data).encode('utf-8'),
        headers={'Content-Type': 'application/json'}
    )

    response = urllib.request.urlopen(req)
    return json.loads(response.read())

def connect_websocket(server_address="127.0.0.1:8188", client_id=None):
    """Establish WebSocket connection for progress tracking"""
    ws = websocket.WebSocket()
    ws.connect(f"ws://{server_address}/ws?clientId={client_id}")
    return ws

def get_image(filename, subfolder, folder_type, server_address="127.0.0.1:8188"):
    """Retrieve generated image"""
    params = {
        "filename": filename,
        "subfolder": subfolder,
        "type": folder_type
    }
    url = f"http://{server_address}/view?{urllib.parse.urlencode(params)}"
    response = urllib.request.urlopen(url)
    return response.read()

def track_progress(ws, prompt_id):
    """Monitor generation progress via WebSocket"""
    while True:
        msg = ws.recv()
        if isinstance(msg, str):
            message = json.loads(msg)

            if message['type'] == 'executing':
                data = message['data']
                if data['node'] is None and data['prompt_id'] == prompt_id:
                    break  # Execution complete

            elif message['type'] == 'progress':
                data = message['data']
                print(f"Progress: {data['value']}/{data['max']}")
```

#### Complete Batch Processing Script

```python
import json
import os
from pathlib import Path

class ComfyUIBatchProcessor:
    def __init__(self, workflow_path, server_address="127.0.0.1:8188"):
        self.server_address = server_address
        self.client_id = str(uuid.uuid4())

        # Load workflow
        with open(workflow_path, 'r') as f:
            self.workflow = json.load(f)

        # Connect WebSocket
        self.ws = connect_websocket(server_address, self.client_id)

    def set_prompt(self, node_id, text):
        """Update prompt text in workflow"""
        self.workflow[node_id]["inputs"]["text"] = text

    def set_seed(self, node_id, seed):
        """Update seed in workflow"""
        self.workflow[node_id]["inputs"]["seed"] = seed

    def set_filename_prefix(self, node_id, prefix):
        """Update filename prefix in workflow"""
        self.workflow[node_id]["inputs"]["filename_prefix"] = prefix

    def set_lora_strength(self, node_id, model_strength, clip_strength):
        """Update LoRA strengths"""
        self.workflow[node_id]["inputs"]["strength_model"] = model_strength
        self.workflow[node_id]["inputs"]["strength_clip"] = clip_strength

    def generate_image(self, prompt_text, seed, output_prefix):
        """Generate single image with parameters"""
        # Update workflow
        self.set_prompt("2", prompt_text)  # Adjust node ID as needed
        self.set_seed("6", seed)
        self.set_filename_prefix("9", output_prefix)

        # Queue prompt
        result = queue_prompt(self.workflow, self.server_address, self.client_id)
        prompt_id = result['prompt_id']

        print(f"Queued: {prompt_id} - {prompt_text[:50]}")

        # Track progress
        track_progress(self.ws, prompt_id)

        return prompt_id

    def batch_generate(self, prompts, output_dir="outputs", base_seed=None):
        """Generate multiple images from prompt list"""
        results = []

        for i, prompt in enumerate(prompts):
            seed = base_seed + i if base_seed else -1  # -1 for random
            prefix = f"{output_dir}/image_{i:04d}"

            try:
                prompt_id = self.generate_image(prompt, seed, prefix)
                results.append({
                    "index": i,
                    "prompt": prompt,
                    "prompt_id": prompt_id,
                    "status": "success"
                })
            except Exception as e:
                print(f"Error on image {i}: {e}")
                results.append({
                    "index": i,
                    "prompt": prompt,
                    "status": "error",
                    "error": str(e)
                })

        return results

# Usage
if __name__ == "__main__":
    prompts = [
        "Beautiful sunset over mountains",
        "Serene forest lake at dawn",
        "Futuristic cityscape with neon lights"
    ]

    processor = ComfyUIBatchProcessor("flux_workflow_api.json")
    results = processor.batch_generate(prompts, output_dir="spirit_atlas_cards")

    # Save results log
    with open("generation_log.json", "w") as f:
        json.dump(results, f, indent=2)
```

### Advanced: Dynamic Parameter Substitution

**Template Workflow with Placeholders:**

```json
{
  "2": {
    "class_type": "CLIPTextEncode",
    "inputs": {
      "text": "{{POSITIVE_PROMPT}}",
      "clip": ["1", 1]
    }
  },
  "6": {
    "class_type": "KSampler",
    "inputs": {
      "seed": "{{SEED}}",
      "steps": "{{STEPS}}",
      "cfg": 1.0,
      "guidance": "{{GUIDANCE}}"
    }
  },
  "10": {
    "class_type": "LoraLoader",
    "inputs": {
      "lora_name": "{{LORA_NAME}}",
      "strength_model": "{{LORA_STRENGTH}}",
      "strength_clip": "{{LORA_STRENGTH}}"
    }
  }
}
```

**Python Template Substitution:**

```python
def load_workflow_template(template_path):
    """Load workflow as template string"""
    with open(template_path, 'r') as f:
        return f.read()

def render_workflow(template, **kwargs):
    """Substitute placeholders with values"""
    rendered = template
    for key, value in kwargs.items():
        placeholder = f"{{{{{key}}}}}"
        rendered = rendered.replace(placeholder, str(value))
    return json.loads(rendered)

# Usage
template = load_workflow_template("workflow_template.json")

workflow = render_workflow(
    template,
    POSITIVE_PROMPT="Epic fantasy landscape",
    SEED=12345,
    STEPS=30,
    GUIDANCE=3.5,
    LORA_NAME="fantasy_style.safetensors",
    LORA_STRENGTH=0.8
)

# Queue the rendered workflow
queue_prompt(workflow)
```

### Production-Ready API Patterns

#### Retry Logic

```python
import time

def queue_prompt_with_retry(prompt, max_retries=3, retry_delay=5):
    """Queue prompt with automatic retries"""
    for attempt in range(max_retries):
        try:
            return queue_prompt(prompt)
        except Exception as e:
            if attempt < max_retries - 1:
                print(f"Retry {attempt + 1}/{max_retries} after error: {e}")
                time.sleep(retry_delay)
            else:
                raise
```

#### Queue Management

```python
def wait_for_queue_space(max_queue_size=5, check_interval=5):
    """Wait until queue has space"""
    while True:
        response = urllib.request.urlopen(f"http://{server_address}/queue")
        queue_data = json.loads(response.read())

        current_queue = len(queue_data.get('queue_pending', []))

        if current_queue < max_queue_size:
            break

        print(f"Queue full ({current_queue} items), waiting...")
        time.sleep(check_interval)
```

#### Progress Callbacks

```python
def track_progress_with_callback(ws, prompt_id, callback=None):
    """Track progress with custom callback function"""
    while True:
        msg = ws.recv()
        if isinstance(msg, str):
            message = json.loads(msg)

            if message['type'] == 'progress':
                progress_data = message['data']
                if callback:
                    callback(progress_data)

            elif message['type'] == 'executing':
                data = message['data']
                if data['node'] is None and data['prompt_id'] == prompt_id:
                    break

# Usage
def progress_callback(data):
    percent = (data['value'] / data['max']) * 100
    print(f"Generation: {percent:.1f}%")

track_progress_with_callback(ws, prompt_id, callback=progress_callback)
```

---

## 7. Example Workflow JSON

### Complete Flux + LoRA Workflow for Batch Generation

This example workflow includes:
- Flux FP8 model loading
- Multiple LoRA stacking
- Parameterized prompt handling
- Batch processing capability
- Organized output naming

```json
{
  "1": {
    "class_type": "CheckpointLoaderSimple",
    "inputs": {
      "ckpt_name": "flux1-dev-fp8.safetensors"
    },
    "_meta": {
      "title": "Load Flux Checkpoint"
    }
  },

  "2": {
    "class_type": "CLIPTextEncode",
    "inputs": {
      "text": "PLACEHOLDER_POSITIVE_PROMPT",
      "clip": ["1", 1]
    },
    "_meta": {
      "title": "Positive Prompt",
      "description": "Replace PLACEHOLDER_POSITIVE_PROMPT with actual prompt via API"
    }
  },

  "3": {
    "class_type": "CLIPTextEncode",
    "inputs": {
      "text": "low quality, blurry, distorted, watermark, signature",
      "clip": ["1", 1]
    },
    "_meta": {
      "title": "Negative Prompt"
    }
  },

  "4": {
    "class_type": "LoraLoader",
    "inputs": {
      "model": ["1", 0],
      "clip": ["1", 1],
      "lora_name": "first_lora.safetensors",
      "strength_model": 0.8,
      "strength_clip": 0.8
    },
    "_meta": {
      "title": "Load LoRA 1",
      "description": "Primary style LoRA"
    }
  },

  "5": {
    "class_type": "LoraLoader",
    "inputs": {
      "model": ["4", 0],
      "clip": ["4", 1],
      "lora_name": "second_lora.safetensors",
      "strength_model": 0.7,
      "strength_clip": 0.7
    },
    "_meta": {
      "title": "Load LoRA 2",
      "description": "Secondary concept LoRA"
    }
  },

  "6": {
    "class_type": "LoraLoader",
    "inputs": {
      "model": ["5", 0],
      "clip": ["5", 1],
      "lora_name": "third_lora.safetensors",
      "strength_model": 0.6,
      "strength_clip": 0.6
    },
    "_meta": {
      "title": "Load LoRA 3",
      "description": "Tertiary detail LoRA"
    }
  },

  "7": {
    "class_type": "FluxGuidance",
    "inputs": {
      "conditioning": ["2", 0],
      "guidance": 3.5
    },
    "_meta": {
      "title": "Flux Guidance",
      "description": "Guidance scale for Flux (replaces CFG)"
    }
  },

  "8": {
    "class_type": "EmptyLatentImage",
    "inputs": {
      "width": 1024,
      "height": 1024,
      "batch_size": 1
    },
    "_meta": {
      "title": "Empty Latent",
      "description": "Starting latent image"
    }
  },

  "9": {
    "class_type": "KSampler",
    "inputs": {
      "model": ["6", 0],
      "seed": -1,
      "steps": 25,
      "cfg": 1.0,
      "sampler_name": "euler",
      "scheduler": "simple",
      "positive": ["7", 0],
      "negative": ["3", 0],
      "latent_image": ["8", 0]
    },
    "_meta": {
      "title": "KSampler",
      "description": "Main sampling node. Seed -1 = random. CFG must be 1.0 for Flux."
    }
  },

  "10": {
    "class_type": "VAEDecode",
    "inputs": {
      "samples": ["9", 0],
      "vae": ["1", 2]
    },
    "_meta": {
      "title": "VAE Decode",
      "description": "Decode latent to image"
    }
  },

  "11": {
    "class_type": "SaveImage",
    "inputs": {
      "images": ["10", 0],
      "filename_prefix": "flux_output"
    },
    "_meta": {
      "title": "Save Image",
      "description": "Replace filename_prefix via API for organized batches"
    }
  }
}
```

### Simplified API Format (Minimal)

For programmatic use, the API format is more concise:

```json
{
  "1": {
    "class_type": "CheckpointLoaderSimple",
    "inputs": {"ckpt_name": "flux1-dev-fp8.safetensors"}
  },
  "2": {
    "class_type": "CLIPTextEncode",
    "inputs": {"text": "{{PROMPT}}", "clip": ["1", 1]}
  },
  "3": {
    "class_type": "CLIPTextEncode",
    "inputs": {"text": "low quality, blurry", "clip": ["1", 1]}
  },
  "4": {
    "class_type": "LoraLoader",
    "inputs": {
      "model": ["1", 0], "clip": ["1", 1],
      "lora_name": "{{LORA_1}}", "strength_model": 0.8, "strength_clip": 0.8
    }
  },
  "5": {
    "class_type": "LoraLoader",
    "inputs": {
      "model": ["4", 0], "clip": ["4", 1],
      "lora_name": "{{LORA_2}}", "strength_model": 0.7, "strength_clip": 0.7
    }
  },
  "6": {
    "class_type": "FluxGuidance",
    "inputs": {"conditioning": ["2", 0], "guidance": 3.5}
  },
  "7": {
    "class_type": "EmptyLatentImage",
    "inputs": {"width": 1024, "height": 1024, "batch_size": 1}
  },
  "8": {
    "class_type": "KSampler",
    "inputs": {
      "model": ["5", 0], "seed": {{SEED}}, "steps": {{STEPS}},
      "cfg": 1.0, "sampler_name": "euler", "scheduler": "simple",
      "positive": ["6", 0], "negative": ["3", 0], "latent_image": ["7", 0]
    }
  },
  "9": {
    "class_type": "VAEDecode",
    "inputs": {"samples": ["8", 0], "vae": ["1", 2]}
  },
  "10": {
    "class_type": "SaveImage",
    "inputs": {"images": ["9", 0], "filename_prefix": "{{OUTPUT_PREFIX}}"}
  }
}
```

### Node ID Reference Guide

When modifying workflows programmatically, you'll need to identify nodes by their ID and class type:

**Common Node IDs to Modify:**

| Purpose | Typical Class Type | Node ID | Parameter |
|---------|-------------------|---------|-----------|
| Positive Prompt | CLIPTextEncode | `"2"` | `"text"` |
| Negative Prompt | CLIPTextEncode | `"3"` | `"text"` |
| Random Seed | KSampler | `"9"` | `"seed"` (use -1 for random) |
| Steps | KSampler | `"9"` | `"steps"` |
| Guidance | FluxGuidance | `"7"` | `"guidance"` |
| LoRA 1 Strength | LoraLoader | `"4"` | `"strength_model"`, `"strength_clip"` |
| Output Filename | SaveImage | `"11"` | `"filename_prefix"` |

**Finding Node IDs:**

1. Open workflow in ComfyUI
2. Enable Developer Mode (Settings → Developer Mode)
3. Node IDs appear in node titles
4. Or examine JSON file directly

---

## 8. Python Automation Scripts

### Complete Production Script for 99 Images

This script demonstrates a complete batch generation system for 99 images with different prompts.

```python
#!/usr/bin/env python3
"""
ComfyUI Flux Batch Generator
Generates 99 images with custom prompts using Flux + LoRAs
"""

import json
import os
import time
import uuid
import urllib.request
import urllib.parse
import websocket
from pathlib import Path
from datetime import datetime
from typing import List, Dict, Optional


class ComfyUIFluxBatchGenerator:
    """
    Automated batch image generation for ComfyUI with Flux models
    """

    def __init__(
        self,
        workflow_path: str,
        server_address: str = "127.0.0.1:8188",
        output_dir: str = "outputs"
    ):
        """
        Initialize batch generator

        Args:
            workflow_path: Path to workflow JSON (API format)
            server_address: ComfyUI server address
            output_dir: Directory for output images
        """
        self.server_address = server_address
        self.client_id = str(uuid.uuid4())
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(parents=True, exist_ok=True)

        # Load workflow template
        with open(workflow_path, 'r') as f:
            self.workflow_template = f.read()

        # Connect WebSocket
        self.ws = self._connect_websocket()

        # Statistics
        self.stats = {
            "total": 0,
            "success": 0,
            "failed": 0,
            "start_time": None,
            "end_time": None
        }

    def _connect_websocket(self) -> websocket.WebSocket:
        """Establish WebSocket connection"""
        ws = websocket.WebSocket()
        ws_url = f"ws://{self.server_address}/ws?clientId={self.client_id}"
        ws.connect(ws_url)
        return ws

    def _queue_prompt(self, workflow: dict) -> dict:
        """Submit workflow to ComfyUI queue"""
        data = {
            "prompt": workflow,
            "client_id": self.client_id
        }

        req = urllib.request.Request(
            f"http://{self.server_address}/prompt",
            data=json.dumps(data).encode('utf-8'),
            headers={'Content-Type': 'application/json'}
        )

        response = urllib.request.urlopen(req)
        return json.loads(response.read())

    def _track_progress(self, prompt_id: str) -> bool:
        """
        Monitor generation progress via WebSocket

        Returns:
            bool: True if successful, False if error
        """
        while True:
            msg = self.ws.recv()

            if isinstance(msg, str):
                message = json.loads(msg)

                # Check for completion
                if message['type'] == 'executing':
                    data = message['data']
                    if data['node'] is None and data['prompt_id'] == prompt_id:
                        return True

                # Check for errors
                elif message['type'] == 'execution_error':
                    print(f"Error in execution: {message}")
                    return False

                # Progress updates
                elif message['type'] == 'progress':
                    data = message['data']
                    value = data['value']
                    max_val = data['max']
                    percent = (value / max_val) * 100
                    print(f"  Progress: {percent:.1f}% ({value}/{max_val})", end='\r')

    def render_workflow(
        self,
        prompt: str,
        seed: int = -1,
        steps: int = 25,
        guidance: float = 3.5,
        lora_configs: Optional[List[Dict]] = None,
        output_prefix: str = "output"
    ) -> dict:
        """
        Render workflow from template with parameters

        Args:
            prompt: Positive prompt text
            seed: Random seed (-1 for random)
            steps: Number of sampling steps
            guidance: Flux guidance scale
            lora_configs: List of LoRA configs [{"name": "file.safetensors", "strength": 0.8}, ...]
            output_prefix: Output filename prefix

        Returns:
            Rendered workflow dictionary
        """
        # Default LoRA config
        if lora_configs is None:
            lora_configs = []

        # Substitute parameters
        workflow_str = self.workflow_template
        workflow_str = workflow_str.replace("{{PROMPT}}", prompt.replace('"', '\\"'))
        workflow_str = workflow_str.replace("{{SEED}}", str(seed))
        workflow_str = workflow_str.replace("{{STEPS}}", str(steps))
        workflow_str = workflow_str.replace("{{GUIDANCE}}", str(guidance))
        workflow_str = workflow_str.replace("{{OUTPUT_PREFIX}}", output_prefix)

        # Handle LoRA configs
        for i, lora_config in enumerate(lora_configs, start=1):
            workflow_str = workflow_str.replace(
                f"{{{{LORA_{i}}}}}",
                lora_config.get("name", "")
            )
            workflow_str = workflow_str.replace(
                f"{{{{LORA_{i}_STRENGTH}}}}",
                str(lora_config.get("strength", 0.8))
            )

        return json.loads(workflow_str)

    def generate_single(
        self,
        prompt: str,
        index: int,
        **kwargs
    ) -> Dict:
        """
        Generate single image

        Args:
            prompt: Image prompt
            index: Image index number
            **kwargs: Additional parameters for render_workflow

        Returns:
            Result dictionary
        """
        # Prepare output prefix
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        output_prefix = f"{self.output_dir.name}/image_{index:04d}_{timestamp}"

        # Render workflow
        workflow = self.render_workflow(
            prompt=prompt,
            output_prefix=output_prefix,
            **kwargs
        )

        # Queue and track
        try:
            result = self._queue_prompt(workflow)
            prompt_id = result['prompt_id']

            print(f"\n[{index}] Queued: {prompt_id}")
            print(f"     Prompt: {prompt[:70]}{'...' if len(prompt) > 70 else ''}")

            success = self._track_progress(prompt_id)

            return {
                "index": index,
                "prompt": prompt,
                "prompt_id": prompt_id,
                "success": success,
                "output_prefix": output_prefix
            }

        except Exception as e:
            print(f"\n[{index}] ERROR: {e}")
            return {
                "index": index,
                "prompt": prompt,
                "success": False,
                "error": str(e)
            }

    def generate_batch(
        self,
        prompts: List[str],
        start_index: int = 0,
        base_seed: Optional[int] = None,
        delay_between: float = 1.0,
        **kwargs
    ) -> List[Dict]:
        """
        Generate batch of images

        Args:
            prompts: List of prompts
            start_index: Starting index for numbering
            base_seed: Base seed (incremented per image, or None for random)
            delay_between: Delay in seconds between submissions
            **kwargs: Additional parameters for render_workflow

        Returns:
            List of result dictionaries
        """
        self.stats["total"] = len(prompts)
        self.stats["start_time"] = datetime.now()

        results = []

        print(f"\n{'='*70}")
        print(f"Starting batch generation: {len(prompts)} images")
        print(f"Output directory: {self.output_dir}")
        print(f"{'='*70}\n")

        for i, prompt in enumerate(prompts):
            # Calculate seed
            if base_seed is not None:
                seed = base_seed + i
            else:
                seed = -1  # Random

            # Generate image
            result = self.generate_single(
                prompt=prompt,
                index=start_index + i,
                seed=seed,
                **kwargs
            )

            results.append(result)

            # Update stats
            if result.get("success"):
                self.stats["success"] += 1
            else:
                self.stats["failed"] += 1

            # Progress summary
            print(f"\n  Progress: {i+1}/{len(prompts)} completed")
            print(f"  Success: {self.stats['success']} | Failed: {self.stats['failed']}")

            # Delay before next
            if i < len(prompts) - 1:
                time.sleep(delay_between)

        self.stats["end_time"] = datetime.now()

        return results

    def save_results(self, results: List[Dict], filename: str = "generation_log.json"):
        """Save generation results to JSON file"""
        log_path = self.output_dir / filename

        log_data = {
            "metadata": {
                "generated_at": datetime.now().isoformat(),
                "total_images": self.stats["total"],
                "successful": self.stats["success"],
                "failed": self.stats["failed"],
                "duration_seconds": (
                    self.stats["end_time"] - self.stats["start_time"]
                ).total_seconds() if self.stats["end_time"] else None
            },
            "results": results
        }

        with open(log_path, 'w') as f:
            json.dump(log_data, f, indent=2)

        print(f"\n{'='*70}")
        print(f"Results saved to: {log_path}")
        print(f"{'='*70}\n")

    def print_summary(self):
        """Print generation summary"""
        duration = (self.stats["end_time"] - self.stats["start_time"]).total_seconds()
        avg_time = duration / self.stats["total"] if self.stats["total"] > 0 else 0

        print(f"\n{'='*70}")
        print("GENERATION SUMMARY")
        print(f"{'='*70}")
        print(f"Total Images:    {self.stats['total']}")
        print(f"Successful:      {self.stats['success']}")
        print(f"Failed:          {self.stats['failed']}")
        print(f"Success Rate:    {(self.stats['success']/self.stats['total']*100):.1f}%")
        print(f"Total Duration:  {duration:.1f}s ({duration/60:.1f}m)")
        print(f"Avg per Image:   {avg_time:.1f}s")
        print(f"{'='*70}\n")


def load_prompts_from_file(filepath: str) -> List[str]:
    """
    Load prompts from text file

    Supports formats:
    - One prompt per line
    - Prompts separated by "---"
    - JSON array
    """
    with open(filepath, 'r') as f:
        content = f.read()

    # Try JSON first
    try:
        prompts = json.loads(content)
        if isinstance(prompts, list):
            return prompts
    except json.JSONDecodeError:
        pass

    # Try separator format
    if '---' in content:
        prompts = [p.strip() for p in content.split('---') if p.strip()]
        return prompts

    # Default: one per line
    prompts = [line.strip() for line in content.splitlines() if line.strip()]
    return prompts


# ============================================================================
# MAIN EXECUTION
# ============================================================================

if __name__ == "__main__":
    # Configuration
    WORKFLOW_PATH = "flux_lora_workflow_api.json"
    PROMPTS_FILE = "prompts.txt"
    OUTPUT_DIR = "spirit_atlas_outputs"

    # Generation settings
    SETTINGS = {
        "steps": 25,
        "guidance": 3.5,
        "base_seed": None,  # None for random, or integer for reproducible
        "lora_configs": [
            {"name": "tarot_style.safetensors", "strength": 0.8},
            {"name": "spiritual_art.safetensors", "strength": 0.7}
        ]
    }

    # Load prompts
    print("Loading prompts...")
    prompts = load_prompts_from_file(PROMPTS_FILE)
    print(f"Loaded {len(prompts)} prompts\n")

    # Initialize generator
    generator = ComfyUIFluxBatchGenerator(
        workflow_path=WORKFLOW_PATH,
        output_dir=OUTPUT_DIR
    )

    # Generate batch
    results = generator.generate_batch(
        prompts=prompts,
        **SETTINGS
    )

    # Save results
    generator.save_results(results)

    # Print summary
    generator.print_summary()
```

### Simplified Quick-Start Script

For simple use cases, here's a minimal script:

```python
#!/usr/bin/env python3
"""Simple ComfyUI batch script"""

import json
import urllib.request
import websocket
import uuid

# Configuration
SERVER = "127.0.0.1:8188"
WORKFLOW_FILE = "workflow_api.json"
PROMPTS = [
    "Beautiful sunset over mountains",
    "Serene forest lake at dawn",
    "Futuristic cityscape with neon lights"
]

# Load workflow
with open(WORKFLOW_FILE, 'r') as f:
    workflow_template = json.load(f)

# Generate images
for i, prompt in enumerate(PROMPTS):
    # Update workflow
    workflow = workflow_template.copy()
    workflow["2"]["inputs"]["text"] = prompt  # Positive prompt node
    workflow["9"]["inputs"]["filename_prefix"] = f"output_{i:03d}"  # Save node

    # Queue
    data = {"prompt": workflow, "client_id": str(uuid.uuid4())}
    req = urllib.request.Request(
        f"http://{SERVER}/prompt",
        json.dumps(data).encode('utf-8'),
        {'Content-Type': 'application/json'}
    )
    response = urllib.request.urlopen(req)
    result = json.loads(response.read())

    print(f"Queued {i+1}/{len(PROMPTS)}: {result['prompt_id']}")

print("All prompts queued!")
```

---

## 9. Prompt Template System

### Prompt Format for Claude Integration

When working with another Claude instance to generate prompts, use a structured format:

#### Template Structure

```markdown
# Image Generation Prompt Template

## Required Fields
- **Card Name**: [Name of the card/concept]
- **Primary Subject**: [Main subject description]
- **Art Style**: [Artistic style keywords]
- **Mood/Atmosphere**: [Emotional tone]
- **Color Palette**: [Dominant colors]
- **Composition**: [Layout/framing]
- **Details**: [Specific elements to include]

## Optional Fields
- **Negative Elements**: [Things to avoid]
- **Reference Style**: [Artistic influences]
- **Symbolic Elements**: [Metaphorical items]

## Output Format
Provide a single-paragraph prompt combining all elements, optimized for Flux model.

---

## Example

**Card Name**: The Hermit
**Primary Subject**: Solitary robed figure holding glowing lantern
**Art Style**: Mystical, ethereal, spiritual illustration
**Mood/Atmosphere**: Introspective, serene, enlightened
**Color Palette**: Deep purples, midnight blues, golden lantern light
**Composition**: Centered figure on mountain peak, looking toward stars
**Details**: Flowing robes, ornate lantern, star-filled sky, misty mountains

**Generated Prompt**:
A solitary robed figure standing on a misty mountain peak, holding an ornate glowing lantern with golden light, mystical and ethereal spiritual illustration style, deep purple and midnight blue color palette, looking toward a star-filled sky, introspective and serene atmosphere, flowing robes with intricate patterns, ethereal glow surrounding the figure
```

### Structured JSON Format

For programmatic use:

```json
{
  "prompts": [
    {
      "id": "hermit_01",
      "card_name": "The Hermit",
      "positive_prompt": "A solitary robed figure standing on a misty mountain peak...",
      "negative_prompt": "blurry, low quality, distorted, watermark, signature",
      "parameters": {
        "steps": 30,
        "guidance": 3.5,
        "seed": null,
        "loras": [
          {"name": "tarot_mystical.safetensors", "strength": 0.8},
          {"name": "ethereal_glow.safetensors", "strength": 0.6}
        ]
      }
    }
  ]
}
```

### Claude Prompt Generation Workflow

**Prompt for Claude (Prompt Generator):**

```
I need you to generate 99 detailed image prompts for a tarot card-inspired spiritual art series. Each prompt should:

1. Be optimized for Flux image generation model
2. Focus on visual description (not story)
3. Include:
   - Primary subject with detailed appearance
   - Artistic style (ethereal, mystical, spiritual)
   - Mood and atmosphere
   - Color palette (specific colors)
   - Composition and framing
   - Key symbolic elements
4. Be 50-100 words long
5. Use descriptive, visual language

Output format: JSON array with this structure:
{
  "prompts": [
    {
      "id": "card_001",
      "title": "The Hermit",
      "prompt": "Full prompt text here...",
      "negative": "low quality, blurry, distorted"
    }
  ]
}

Card themes to cover:
- Major Arcana tarot cards (22 cards)
- Zodiac signs (12 cards)
- Planetary symbols (10 cards)
- Elemental representations (4 cards)
- Chakra visualizations (7 cards)
- Sacred geometry (10 cards)
- Mythological figures (15 cards)
- Nature spirits (19 cards)

Total: 99 unique prompts

Please generate all 99 prompts maintaining consistent artistic style but unique visual elements for each.
```

### Prompt Quality Guidelines

**For Claude-Generated Prompts:**

1. **Be Specific**: "ornate golden lantern with intricate filigree" > "lantern"
2. **Use Visual Language**: Focus on appearance, not meaning
3. **Include Lighting**: "soft ethereal glow", "dramatic side lighting"
4. **Specify Colors**: "deep indigo blue", "warm amber glow" > "blue", "orange"
5. **Composition Details**: "centered composition", "rule of thirds", "dramatic angle"
6. **Texture & Materials**: "flowing silk robes", "weathered stone", "crystalline surface"
7. **Atmosphere**: "misty", "ethereal", "atmospheric haze"
8. **Avoid Abstracts**: "wisdom", "enlightenment" → Convert to visual metaphors

**Good Example:**
```
A serene figure in flowing indigo robes meditating in lotus position, surrounded by seven glowing chakra orbs ascending from root to crown, ethereal spiritual illustration style, soft bioluminescent glow, deep purple and teal color palette, centered composition with radiating light mandala behind figure, mystical atmosphere with floating particles of light, detailed energy patterns and sacred geometry, photorealistic rendering with painterly touches
```

**Bad Example:**
```
A person meditating with chakras, spiritual and enlightened
```

### Batch Prompt File Format

**Simple Text File (prompts.txt):**
```
A solitary robed figure standing on a misty mountain peak, holding an ornate glowing lantern
---
A powerful figure seated on a throne, holding scales of justice, ethereal and divine
---
Twin pillars with a veiled priestess between them, holding sacred scroll
```

**JSON Format (prompts.json):**
```json
[
  "A solitary robed figure standing on a misty mountain peak, holding an ornate glowing lantern",
  "A powerful figure seated on a throne, holding scales of justice, ethereal and divine",
  "Twin pillars with a veiled priestess between them, holding sacred scroll"
]
```

**Advanced JSON with Metadata (prompts_advanced.json):**
```json
{
  "metadata": {
    "project": "SpiritAtlas Tarot Series",
    "created": "2025-12-09",
    "total_count": 99,
    "style": "Ethereal mystical spiritual art",
    "default_settings": {
      "steps": 30,
      "guidance": 3.5,
      "negative": "low quality, blurry, distorted, watermark, signature, text"
    }
  },
  "prompts": [
    {
      "id": "001_hermit",
      "category": "major_arcana",
      "title": "The Hermit",
      "prompt": "A solitary robed figure...",
      "custom_settings": {
        "guidance": 3.8,
        "loras": [
          {"name": "mystical_style.safetensors", "strength": 0.85}
        ]
      }
    }
  ]
}
```

---

## 10. Production Tips

### Optimization Strategies

#### VRAM Management

**For 8GB VRAM:**
- Use FP8 models (`flux1-dev-fp8.safetensors`)
- Use FP8 text encoders (`t5xxl_fp8_e4m3fn.safetensors`)
- Batch size: 1
- Resolution: Up to 1024x1024
- LoRAs: 2-3 maximum

**For 12GB VRAM:**
- Can use FP16 text encoder (`t5xxl_fp16.safetensors`)
- Batch size: 1-2
- Resolution: Up to 1536x1536
- LoRAs: 3-5 comfortably

**For 24GB+ VRAM:**
- Full FP16 models for best quality
- Batch size: 2-4
- Resolution: Up to 2048x2048
- LoRAs: Unlimited (within reason)

#### Speed Optimization

1. **Reduce Steps**: 20-25 steps often sufficient for Flux dev
2. **Use Schnell**: Flux schnell with 4-8 steps for drafts
3. **Optimize LoRAs**: Fewer LoRAs = faster generation
4. **Queue Management**: Keep queue at 5-10 items for smooth flow
5. **Resolution**: Generate at 1024x1024, upscale separately if needed

### Quality Assurance

#### Pre-Batch Testing

1. **Test with 3-5 samples** before full batch
2. **Verify**:
   - LoRA effects are visible and balanced
   - Prompts are being interpreted correctly
   - Output quality meets standards
   - No artifacts or errors

#### Mid-Batch Monitoring

1. **Check outputs periodically** (every 10-15 images)
2. **Watch for**:
   - Quality degradation
   - Repeated errors
   - VRAM issues
   - Disk space

#### Post-Generation Review

1. **Automated checks**:
   ```python
   def check_image_quality(image_path):
       # Check file size
       size = os.path.getsize(image_path)
       if size < 50000:  # Suspiciously small
           return False

       # Check dimensions
       from PIL import Image
       img = Image.open(image_path)
       if img.size != (1024, 1024):
           return False

       return True
   ```

2. **Manual review** of random samples

### Error Handling

#### Common Issues & Solutions

**1. Out of Memory**
- Solution: Reduce batch size, use FP8 models, restart ComfyUI
- Prevention: Monitor VRAM usage

**2. Model Not Found**
- Solution: Check file paths, ensure models are in correct folders
- Prevention: Verify paths before batch

**3. WebSocket Disconnection**
- Solution: Reconnect WebSocket, resume from last successful index
- Prevention: Keep connection alive, handle disconnects gracefully

**4. Prompt Parsing Errors**
- Solution: Escape special characters, validate JSON
- Prevention: Sanitize prompts before sending

#### Resilient Batch Script Pattern

```python
def resilient_batch_generate(prompts, max_retries=3):
    results = []
    failed_indices = []

    for i, prompt in enumerate(prompts):
        retry_count = 0
        success = False

        while retry_count < max_retries and not success:
            try:
                result = generate_single(prompt, i)
                if result.get("success"):
                    results.append(result)
                    success = True
                else:
                    retry_count += 1
                    time.sleep(5)
            except Exception as e:
                print(f"Error on attempt {retry_count + 1}: {e}")
                retry_count += 1
                time.sleep(10)

        if not success:
            failed_indices.append(i)
            print(f"Failed permanently: index {i}")

    # Retry failed
    if failed_indices:
        print(f"\nRetrying {len(failed_indices)} failed images...")
        for i in failed_indices:
            try:
                result = generate_single(prompts[i], i)
                results.append(result)
            except:
                pass

    return results
```

### File Organization

#### Recommended Structure

```
project/
├── workflows/
│   ├── flux_base_workflow.json
│   ├── flux_lora_workflow_api.json
│   └── templates/
│       └── flux_template.json
├── prompts/
│   ├── tarot_cards.txt
│   ├── zodiac_signs.txt
│   └── all_prompts.json
├── outputs/
│   ├── 2025-12-09_batch_001/
│   │   ├── image_0001.png
│   │   ├── image_0002.png
│   │   └── generation_log.json
│   └── 2025-12-09_batch_002/
├── scripts/
│   ├── batch_generator.py
│   ├── prompt_loader.py
│   └── quality_checker.py
└── logs/
    └── generation_history.json
```

#### Automated Organization Script

```python
def organize_outputs(source_dir, project_name):
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    batch_dir = Path(f"outputs/{project_name}/{timestamp}")
    batch_dir.mkdir(parents=True, exist_ok=True)

    # Move images
    for img_file in Path(source_dir).glob("*.png"):
        img_file.rename(batch_dir / img_file.name)

    return batch_dir
```

### Performance Benchmarks

**Typical Generation Times (RTX 4090):**

| Configuration | Steps | Time per Image |
|--------------|-------|----------------|
| Flux Dev FP8, 1024x1024 | 20 | ~15s |
| Flux Dev FP8, 1024x1024 | 30 | ~22s |
| Flux Dev FP8 + 2 LoRAs, 1024x1024 | 25 | ~20s |
| Flux Dev FP16, 1024x1024 | 30 | ~30s |
| Flux Schnell FP8, 1024x1024 | 4 | ~5s |

**99 Images Estimate:**
- At 20s per image: ~33 minutes
- At 25s per image: ~41 minutes
- At 30s per image: ~50 minutes

### Best Practices Checklist

**Before Batch:**
- [ ] Test workflow with 3-5 sample prompts
- [ ] Verify all LoRAs are loaded and effective
- [ ] Check available disk space (2-5GB for 99 images)
- [ ] Ensure GPU drivers are up to date
- [ ] Save workflow JSON as backup

**During Batch:**
- [ ] Monitor GPU temperature (keep under 80°C)
- [ ] Check outputs every 10-15 images
- [ ] Watch for error messages in console
- [ ] Keep log of any issues

**After Batch:**
- [ ] Review sample of outputs for quality
- [ ] Check generation_log.json for failures
- [ ] Organize files into project folders
- [ ] Back up successful workflow JSON
- [ ] Document any issues for next batch

### Scaling to Larger Batches

**For 500+ Images:**

1. **Split into multiple sessions**
   - 99 images per session
   - Allows adjustments between batches

2. **Use multiple workers** (advanced)
   - Run multiple ComfyUI instances on different GPUs
   - Distribute prompts across workers

3. **Cloud deployment**
   - Use services like RunPod, Vast.ai
   - Parallel generation on multiple GPUs

4. **Automated monitoring**
   - Set up alerting for failures
   - Automatic retries
   - Progress notifications

---

## Additional Resources

### Official Documentation
- [ComfyUI GitHub](https://github.com/comfyanonymous/ComfyUI)
- [ComfyUI Official Docs](https://docs.comfy.org/)
- [Workflow JSON Schema](https://docs.comfy.org/specs/workflow_json)

### Community Resources
- [ComfyUI Wiki](https://comfyui-wiki.com/)
- [Flux Examples](https://comfyanonymous.github.io/ComfyUI_examples/flux/)
- [ComfyUI Workflows Repository](https://github.com/aimpowerment/comfyui-workflows)

### Custom Nodes
- [ComfyUI Manager](https://github.com/ltdrdata/ComfyUI-Manager)
- [rgthree-comfy](https://github.com/rgthree/rgthree-comfy)
- [efficiency-nodes-comfyui](https://github.com/jags111/efficiency-nodes-comfyui)
- [ComfyUI-DynamicPrompts](https://github.com/adieyal/comfyui-dynamicprompts)

### API Tools
- [ComfyScript](https://github.com/Chaoses-Ib/ComfyScript)
- [ComfyUI-to-Python Extension](https://github.com/pydn/ComfyUI-to-Python-Extension)
- [Modal ComfyUI Examples](https://modal.com/docs/examples/comfyapp)

### Model Resources
- Flux Models: [Black Forest Labs](https://blackforestlabs.ai/)
- LoRAs: [Civitai](https://civitai.com/), [HuggingFace](https://huggingface.co/)

---

## Appendix: Quick Reference

### Essential Commands

```bash
# Start ComfyUI (Windows)
run_nvidia_gpu.bat

# Start ComfyUI (Linux/Mac)
python main.py --listen 0.0.0.0

# Install custom node
cd ComfyUI/custom_nodes
git clone [node-repository-url]

# Check queue status
curl http://127.0.0.1:8188/queue

# Clear queue
curl -X POST http://127.0.0.1:8188/queue/clear
```

### Node Parameter Quick Reference

**Flux Critical Settings:**
- CFG: Always 1.0
- Guidance: 1.0-4.0 (3.5 recommended)
- Steps: 20-50 for dev, 4-8 for schnell
- Sampler: euler, dpmpp_2m
- Scheduler: simple, normal

**LoRA Stacking:**
- Typical strength: 0.6-0.8 when stacking
- Test solo at 0.5, 0.7, 1.0 first
- Reduce clip_strength if dominating prompts
- Reduce model_strength if style too strong

**Batch Processing:**
- Queue count: Set in Extra Options
- Batch size: In EmptyLatentImage node
- Seed: -1 for random
- Filename prefix: For organized outputs

### Troubleshooting Flowchart

```
Issue: Generation failed
├─ Check ComfyUI console for errors
├─ Verify model files exist
│  └─ Check ComfyUI/models/ folders
├─ Test with simpler workflow (no LoRAs)
│  ├─ Works → LoRA issue
│  └─ Fails → Model or settings issue
├─ Check VRAM usage
│  └─ High → Use FP8, reduce batch size
└─ Restart ComfyUI

Issue: Low quality outputs
├─ Increase steps (try 30-40)
├─ Adjust guidance (try 3.0-4.0)
├─ Check LoRA strengths
│  └─ Too high → Reduce to 0.6-0.7
├─ Verify prompt quality
│  └─ Add more specific details
└─ Test different samplers

Issue: Slow generation
├─ Use FP8 models
├─ Reduce steps (minimum 20)
├─ Remove unnecessary LoRAs
├─ Lower resolution
└─ Check GPU temperature/throttling
```

---

**End of Guide**

This comprehensive guide covers everything needed to set up and run automated batch image generation with ComfyUI, Flux models, and LoRAs. For the 99-image generation task, follow these key steps:

1. Install ComfyUI on Windows with GPU support
2. Download Flux FP8 model and desired LoRAs
3. Create workflow in ComfyUI and export as API format
4. Prepare 99 prompts (via Claude or manual)
5. Use provided Python script to automate generation
6. Monitor progress and review outputs

Total estimated time: 1-2 hours setup + 30-60 minutes generation.
