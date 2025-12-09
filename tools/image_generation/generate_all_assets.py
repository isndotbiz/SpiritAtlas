#!/usr/bin/env python3
"""
SpiritAtlas Batch Image Generation Orchestrator

This script reads IMAGE_GENERATION_PROMPTS.md and generates all 59 assets
using Flux1.dev via fal.ai for ultra-high quality.

Features:
- Parses prompts from markdown with all metadata
- Generates images with progress tracking and ETA
- Organizes output in Android resource structure
- Supports resume on interruption
- Creates manifest of all generated assets
- Allows sample batch generation for review

Usage:
    # Generate 5 sample images for review
    python generate_all_assets.py --sample

    # Generate all 59 images
    python generate_all_assets.py --full

    # Resume interrupted generation
    python generate_all_assets.py --resume

    # Use specific provider
"""

import os
import re
import json
import time
import argparse
from dataclasses import dataclass, asdict
from typing import List, Dict, Optional, Tuple
from pathlib import Path
import hashlib
from datetime import datetime, timedelta

try:
    import fal_client
    FAL_AVAILABLE = True
except ImportError:
    FAL_AVAILABLE = False
    print("⚠️  fal_client not installed. Install with: pip install fal-client")

