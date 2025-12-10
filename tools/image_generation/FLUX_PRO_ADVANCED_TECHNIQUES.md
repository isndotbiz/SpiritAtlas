# FLUX 1.1 Pro Advanced Prompting Techniques (2025)

## Table of Contents
1. [FLUX 1.1 Pro vs 1.0 - New Capabilities](#flux-11-pro-vs-10---new-capabilities)
2. [Advanced Prompt Engineering Patterns](#advanced-prompt-engineering-patterns)
3. [Negative Prompts Best Practices](#negative-prompts-best-practices)
4. [Style Keywords Library](#style-keywords-library)
5. [Aspect Ratio Optimization](#aspect-ratio-optimization)
6. [Multi-Concept Composition](#multi-concept-composition)
7. [Advanced Lighting Techniques](#advanced-lighting-techniques)
8. [Material Rendering](#material-rendering)
9. [Sacred Geometry Precision Techniques](#sacred-geometry-precision-techniques)
10. [Spiritual/Mystical Aesthetics](#spiritualmystical-aesthetics)
11. [Ultra & Raw Modes](#ultra--raw-modes)
12. [Common Mistakes to Avoid](#common-mistakes-to-avoid)
13. [Exceptional Output Examples](#exceptional-output-examples)

---

## FLUX 1.1 Pro vs 1.0 - New Capabilities

### Speed Improvements
- **3-6x faster generation**: FLUX 1.1 Pro generates images in less than 5 seconds
- **2x faster than updated FLUX 1.0**: The original FLUX 1.0 Pro was also updated to be 2x faster
- **10-second Ultra mode**: Even at 4x resolution (4MP), generation remains under 10 seconds

### Quality Enhancements
- **Superior textures and details**: Significantly improved fine detail rendering
- **Better prompt adherence**: More accurate interpretation of complex prompts
- **Enhanced diversity**: Greater variety in generated outputs
- **Top benchmark performance**: Highest Elo score in Artificial Analysis image arena

### New Modes

#### Ultra Mode
- **4x higher resolution**: Up to 4MP without speed compromise
- **2.5x faster than competitors**: Industry-leading high-resolution generation
- **Maintained prompt adherence**: Quality doesn't degrade at higher resolutions

#### Raw Mode
- **Hyper-realistic, candid-style images**: Less synthetic, more natural aesthetic
- **Enhanced diversity in human subjects**: More varied and authentic representations
- **Superior lighting and textures**: Particularly in nature photography
- **Genuine photographic feel**: Captures candid photography aesthetics

### Prompt Upsampling
- **LLM-powered expansion**: Automatically enriches prompts with detail
- **Greater output variety**: Generates more creative interpretations
- **Enhanced realism**: Adds composition and detail improvements
- **Trade-off**: Less predictable direction, more creative freedom

---

## Advanced Prompt Engineering Patterns

### 1. Front-Loading Priority Structure

**Critical Elements First**:
```
[Main Subject] → [Key Action] → [Critical Style] → [Essential Context] → [Secondary Details]
```

**Example**:
```
Ancient meditating yogi, levitating in lotus position, ethereal bioluminescent aura,
Himalayan cave at dawn, sacred geometric mandalas floating around,
soft volumetric god rays, mystical atmosphere, ultra-detailed, 8K
```

### 2. Layered Prompt Architecture

Build prompts in distinct layers for maximum control:

**Foundation Layer** (Required):
- Subject (who/what)
- Action (doing what)
- Style (artistic approach)
- Context (where/when)

**Visual Layer** (Recommended):
- Lighting conditions
- Color palette
- Composition rules
- Camera angles

**Technical Layer** (Optional):
- Camera settings
- Lens specifications
- Resolution details
- Post-processing style

**Atmospheric Layer** (Enhancement):
- Mood descriptors
- Emotional tone
- Environmental atmosphere
- Symbolic elements

**Example of Layered Prompt**:
```
Foundation: A celestial goddess with flowing cosmic hair
Visual: bathed in purple and gold nebula light, rule of thirds composition
Technical: shot on Canon EOS R5, 85mm f/1.4, shallow depth of field
Atmospheric: ethereal, transcendent mood, divine presence, spiritual awakening energy
```

### 3. Natural Language Approach

FLUX 1.1 Pro excels with conversational descriptions:

**Instead of**: `woman, portrait, beautiful, detailed eyes, 8k`

**Use**: `A close-up portrait of a contemplative woman with expressive, detailed eyes that tell a story, captured in stunning 8K clarity with natural lighting`

### 4. Emphasis Without Weights

FLUX doesn't support prompt weights like `(keyword:1.5)`. Use natural emphasis:

- "with emphasis on [element]"
- "with a strong focus on [element]"
- "primarily featuring [element]"
- "dominated by [element]"
- "highlighted [element]"

**Example**:
```
A mystical forest scene with emphasis on the glowing bioluminescent mushrooms,
featuring a strong focus on their intricate organic patterns and ethereal blue-green light
```

### 5. Photography-Specific Structure

For photorealistic results, follow this order:

```
[Subject] → [Background] → [Lighting] → [Camera Settings]
```

**Example**:
```
Portrait of a spiritual teacher, ancient temple background with carved stone pillars,
golden hour lighting with warm rim light, shot on iPhone 16 Pro Max, f/2.8, 50mm,
professional photography, ultra-detailed
```

### 6. Text Rendering Technique

For readable text in images:

```
A [medium] featuring the text "[EXACT TEXT]" in [style] font, [placement], [additional context]
```

**Example**:
```
A mystical book cover featuring the text "SPIRIT ATLAS" in elegant serif gold lettering,
centered at the top, against a cosmic mandala background with sacred geometry patterns
```

---

## Negative Prompts Best Practices

### Important: FLUX 2 Does NOT Support Negative Prompts

**For FLUX 2**: Focus on describing what you WANT, not what you DON'T want.

### FLUX 1 Negative Prompt Implementation

For FLUX 1.x (dev/schnell), negative prompts work through community extensions:

#### Setup Requirements
- FLUX model (1.0 or 1.1)
- ComfyUI or similar interface
- sd-dynamic-thresholding extension

#### Optimal CFG Scale Settings

**Realistic Images**:
- CFG Scale: 2-3
- Interpolate Phi: 0.6-0.7

**Artistic Renderings**:
- CFG Scale: 4-6
- Interpolate Phi: 0.8-0.9

**Abstract/Experimental**:
- CFG Scale: 7+
- Interpolate Phi: 0.9+

#### Effective Negative Prompt Categories

**Quality Issues**:
```
blurry, out of focus, low resolution, pixelated, jpeg artifacts,
compression artifacts, noise, grainy, distorted
```

**Unwanted Elements**:
```
text, watermarks, logos, signatures, dates, frames, borders,
multiple subjects (if single intended), duplicate features
```

**Lighting Problems**:
```
overexposed, underexposed, harsh shadows, flat lighting,
unnatural lighting, oversaturated, washed out colors
```

**Anatomical Issues (for portraits)**:
```
distorted proportions, extra limbs, deformed hands,
merged fingers, asymmetrical features, unnatural poses
```

**Style Conflicts**:
```
cartoon, anime, 3D render (if photorealistic intended),
modern elements (if ancient/historical), synthetic appearance
```

#### Speed Optimization

Apply negative prompts only to initial steps (first 20-40% of generation) to maintain composition while speeding up generation.

#### Sacred Geometry Specific Negatives

```
asymmetric patterns, irregular geometry, broken lines,
incomplete shapes, cluttered composition, chaotic arrangement
```

### Pro Tip: Use Positive Framing Instead

**Instead of**: `no blur, no grain, no distortion`
**Use**: `crystal clear, ultra sharp focus, pristine detail, perfect geometry`

---

## Style Keywords Library

### Photorealistic Styles
- `professional photography`
- `photorealistic`
- `hyperrealistic`
- `ultra-realistic`
- `studio quality`
- `8K resolution`
- `award-winning photography`
- `National Geographic style`
- `commercial photography`
- `editorial photography`

### Artistic Styles
- `oil painting`
- `watercolor painting`
- `digital art`
- `concept art`
- `matte painting`
- `impressionist style`
- `art nouveau`
- `surrealist artwork`
- `fine art photography`
- `cinematic artwork`

### Spiritual/Mystical Styles
- `visionary art`
- `sacred art`
- `mystical illustration`
- `spiritual artwork`
- `transcendental art`
- `esoteric illustration`
- `cosmic art`
- `metaphysical art`
- `shamanic vision`
- `divine imagery`

### Technical Quality Enhancers
- `ultra-detailed`
- `highly detailed`
- `intricate details`
- `sharp focus`
- `crystal clear`
- `high dynamic range (HDR)`
- `ray-traced lighting`
- `volumetric lighting`
- `subsurface scattering`
- `physically based rendering`

### Artist References (Use Sparingly)
- `in the style of Alex Grey` (visionary, sacred geometry)
- `in the style of Android Jones` (digital cosmic art)
- `in the style of Amanda Sage` (spiritual feminine)
- `in the style of Alphonse Mucha` (art nouveau, decorative)
- `in the style of Greg Rutkowski` (fantasy, detailed)

**Note**: FLUX understands artist names but may interpret them loosely. Be specific with additional descriptors.

---

## Aspect Ratio Optimization

### FLUX Capabilities
- Supports extensive aspect ratios from 1:1 to ultra-wide 21:9
- Handles dynamic megapixel targeting
- Requires dimensions in 32px increments
- Never go below 1024px minimum edge length

### Recommended Resolutions

#### Standard Aspect Ratios

**1:1 Square** (1024x1024)
- Social media posts
- Profile pictures
- Product catalogs
- Balanced compositions

**4:5 Portrait** (832x1216)
- Instagram portraits
- Mobile-optimized content
- Vertical storytelling
- Standing figures

**16:9 Landscape** (1344x768)
- Hero images
- Desktop wallpapers
- Video thumbnails
- Wide scenes

**19:13 Landscape** (1216x832)
- Standard photography
- Horizontal compositions
- Landscape scenes

**7:4 Wide Landscape** (1344x768)
- Panoramic views
- Cinematic shots
- Wide environmental scenes

**12:5 Ultra-Wide** (1536x640)
- Banner images
- Website headers
- Extreme panoramas

### Strategic Aspect Ratio Selection

**For Sacred Geometry**:
- 1:1 for mandalas, yantras, circular patterns
- 4:3 for balanced compositions
- 2:3 portrait for vertical designs

**For Portraits**:
- 4:5 for social media
- 2:3 for classic portrait
- 1:1 for profile pictures

**For Landscapes**:
- 16:9 for cinematic
- 3:2 for photography
- 21:9 for ultra-wide panoramas

**For Products**:
- 1:1 for e-commerce
- 4:5 for mobile shopping
- 16:9 for lifestyle context

### Quality Tips by Resolution

**1024x1024 and up**: Full detail, professional quality
**1344x768+**: Ideal for complex scenes with multiple elements
**1536x640**: Best for extreme wide shots, may lose vertical detail

**Always specify**: Match aspect ratio to intended use case for optimal results.

---

## Multi-Concept Composition

### FLUX.1 Kontext: Advanced Blending System

FLUX.1 Kontext extends FLUX 1.1 Pro with image-to-image capabilities for seamless multi-concept composition.

### Key Features

**Text + Image Prompting**:
- Combine text descriptions with reference images
- Extract visual concepts and modify them
- Produce coherent renderings from multiple sources

**Object Identity Preservation**:
- Maintain specific characteristics across contexts
- Transport objects between scenes naturally
- Preserve unique details while adapting to new environments

**Targeted Modifications**:
- Modify specific elements without affecting the rest
- Local edits with composition preservation
- Seamless integration of changes

**Character Consistency**:
- Maintain character appearance across multiple images
- Consistent product representation in different scenarios
- Reliable visual narrative continuity

### Multi-Image Fusion Workflow

**2 Image Blend**:
```
Primary image (70% weight) + Secondary image (30% weight) + Text prompt describing desired fusion
```

**3-4 Image Blend**:
```
Add more reference latents - trade-offs scale linearly with each additional encode
```

**Example Workflow**:
1. Reference Image 1: Sacred geometry mandala pattern
2. Reference Image 2: Portrait of meditating person
3. Reference Image 3: Cosmic nebula background
4. Text Prompt: "Combine these elements into a unified spiritual portrait with the mandala forming an aura around the person against the cosmic background"

### Practical Applications

**Product Marketing**:
- Same product in different environments
- Consistent branding across contexts
- Multiple lifestyle scenarios

**Storytelling**:
- Character consistency across scenes
- Visual narrative coherence
- World-building with recurring elements

**Spiritual Art**:
- Combine symbols with portraits
- Merge sacred geometry with landscapes
- Integrate cosmic elements with earthly subjects

### Best Practices

1. **Use high-quality reference images**: Better input = better fusion
2. **Clear text instructions**: Describe HOW to blend, not just what to blend
3. **Hierarchical approach**: Primary concept + supporting elements
4. **Test weight ratios**: Start with 70/30, adjust based on results
5. **Maintain consistent lighting**: Reference images with similar lighting blend better

---

## Advanced Lighting Techniques

### Fundamental Lighting Keywords

**Directional Lighting**:
- `rim light` - Highlights edges, creates depth
- `back lighting` - Silhouette and glow effects
- `side lighting` - Dramatic shadows, texture emphasis
- `directional key light` - Primary light source direction
- `three-point lighting` - Professional portrait setup

**Natural Lighting**:
- `golden hour` - Warm, soft, low-angle sunlight
- `blue hour` - Cool twilight tones
- `overcast lighting` - Soft, diffused, even
- `dappled sunlight` - Light through leaves
- `window light` - Natural indoor illumination

**Atmospheric Lighting**:
- `volumetric lighting` - Visible light rays/beams
- `god rays` - Divine light shafts through clouds/atmosphere
- `atmospheric perspective` - Depth through light haze
- `caustics` - Light patterns through water/glass
- `light bloom` - Soft glow around bright sources

**Studio Lighting**:
- `studio lighting` - Controlled, professional setup
- `soft box lighting` - Diffused, even illumination
- `beauty lighting` - Flattering portrait setup
- `dramatic lighting` - High contrast, shadows
- `high-key lighting` - Bright, minimal shadows

### Spiritual/Mystical Lighting

**Divine/Ethereal**:
- `divine light` - Heavenly, transcendent quality
- `ethereal glow` - Soft, otherworldly radiance
- `celestial lighting` - Cosmic, starlit quality
- `aurora borealis` - Natural mystic light display
- `bioluminescent glow` - Organic, living light

**Energy/Aura**:
- `aura glow` - Energy field around subjects
- `chakra light` - Colored energy centers
- `energy emanation` - Radiating power/force
- `luminous aura` - Bright spiritual field
- `prana visualization` - Life force energy light

**Sacred/Mystical**:
- `candlelight ambiance` - Warm, flickering ritual light
- `sacred fire light` - Ritual flame illumination
- `moonlight illumination` - Cool, mystical lunar light
- `starlight shimmer` - Subtle cosmic sparkle
- `prismatic light` - Rainbow spectrum effects

### Advanced Lighting Combinations

**Mystic Portrait**:
```
A spiritual master in meditation, rim light outlining the silhouette,
volumetric god rays from above, soft ethereal glow emanating from the body,
subtle bioluminescent aura, golden hour background, cinematic lighting
```

**Sacred Space**:
```
Ancient temple interior, dramatic directional key light from high windows,
volumetric light beams cutting through atmospheric haze, soft candlelight
ambiance in the shadows, mystical purple-blue rim lighting, divine atmosphere
```

**Cosmic Portrait**:
```
Celestial being floating in space, aurora borealis colors dancing around,
starlight shimmer across the skin, energy emanation from the third eye,
soft prismatic light refraction through cosmic dust, ethereal backlight
```

### Lighting for Material Rendering

**Glass/Crystal**:
```
caustic light patterns, light refraction, internal glow,
prismatic dispersion, subsurface scattering
```

**Metal**:
```
specular highlights, reflective surfaces, metallic sheen,
anisotropic reflection, polished metal gleam
```

**Energy/Plasma**:
```
self-illuminating, energy glow, electrical arcs, plasma tendrils,
intense core light fading to edges
```

**Organic/Natural**:
```
subsurface scattering, soft diffuse lighting, organic translucency,
natural bounce light, subtle color bleeding
```

### Pro Tips

1. **Layer multiple lighting types**: Combine 2-3 for depth
2. **Specify intensity**: Use "soft," "intense," "subtle," "dramatic"
3. **Direction matters**: "from above," "from behind," "from the side"
4. **Color temperature**: "warm," "cool," "neutral," specific colors
5. **Time of day**: Implies lighting quality automatically

---

## Material Rendering

### Glass and Crystal

**Basic Glass**:
```
transparent glass, light refraction, reflective surface,
crystal clear, pristine clarity, smooth surface
```

**Advanced Glass**:
```
volumetric glass material, subsurface light scattering,
caustic light patterns refracting through, internal reflections,
prismatic color separation, physically accurate glass rendering,
ray-traced reflections
```

**Mystical Crystal**:
```
luminous crystal structure, internal energy glow, prismatic light dispersion,
ethereal transparency, sacred geometric lattice visible within,
bioluminescent core, magical crystalline properties
```

### Metal Materials

**Polished Metal**:
```
polished chrome surface, mirror-like reflections,
specular highlights, metallic sheen, high reflectivity
```

**Ancient Metal**:
```
aged bronze patina, verdigris oxidation, weathered copper,
historical metal texture, time-worn surface, antique metal finish
```

**Mystical Metal**:
```
luminous gold material, soft golden glow emanating from surface,
sacred metal with divine properties, celestial metallic sheen,
energy-infused metal, spiritually charged material
```

**Technical Metal**:
```
anisotropic brushed metal, directional reflections,
physically based metal rendering (PBR), accurate metallic response,
environmental reflections, ray-traced metal surface
```

### Energy and Plasma

**Basic Energy**:
```
glowing energy, luminous power, radiating light,
energy field, pulsing glow
```

**Advanced Energy**:
```
volumetric energy tendrils, plasma-like wisps,
electrical arcs dancing across surface,
self-illuminating energy with natural falloff,
particle effects surrounding, aurora-like energy ribbons
```

**Mystical Energy**:
```
prana energy visualization, chi flow patterns,
kundalini serpent energy rising, chakra energy vortex,
spiritual power manifestation, aetheric energy currents,
astral light phenomena
```

**Cosmic Energy**:
```
nebula-like energy clouds, stellar plasma formations,
cosmic dust with light interaction, galaxy energy swirls,
dark matter visualization, quantum energy fields
```

### Cosmic and Celestial Materials

**Nebula/Space**:
```
cosmic nebula clouds, stellar dust illuminated from within,
gaseous formations with light emission, space plasma,
volumetric cosmic fog, interstellar medium
```

**Stars and Light**:
```
stellar light source, star formation regions,
stellar corona glow, pulsar energy beams,
cosmic light rays across space, celestial radiance
```

**Galaxy/Universe**:
```
spiral galaxy structure, dark matter distribution,
cosmic web filaments, gravitational lensing effects,
universe fabric visualization, spacetime distortion
```

### Organic and Natural Materials

**Bioluminescence**:
```
bioluminescent organisms, organic light emission,
natural glow from living tissue, deep sea bioluminescence,
fungal luminescence, firefly-like points of light
```

**Natural Elements**:
```
flowing water with light interaction, translucent flower petals,
tree bark texture with moss, natural stone with mineral deposits,
organic material with subsurface scattering
```

**Sacred Natural**:
```
ancient wood with spiritual energy, living tree with consciousness,
sacred stone with power, holy water blessed properties,
elemental material manifestation
```

### Advanced Material Combinations

**Crystal Temple**:
```
Massive crystal formations in ancient temple,
transparent quartz with internal sacred geometry visible,
light refracting through creating rainbow caustics on stone walls,
some crystals with internal bioluminescent glow,
aged gold metal inlays with patina,
volumetric god rays illuminating floating dust particles
```

**Cosmic Entity**:
```
Celestial being formed from nebula material,
body composed of glowing cosmic dust and stellar plasma,
internal galaxy spiral visible through translucent form,
energy tendrils of aurora-like light,
skin surface showing constellation patterns that emit soft light,
surrounded by particle effects like microscopic stars
```

**Sacred Artifact**:
```
Ancient meditation sphere made of layered materials:
outer shell of transparent crystal with sacred geometry etched inside,
middle layer of flowing liquid gold that moves like mercury,
core of pure white light energy pulsing rhythmically,
resting on aged bronze pedestal with patina,
soft volumetric glow emanating outward
```

### Material Rendering Pro Tips

1. **Combine physical accuracy with mysticism**: `physically based rendering with spiritual energy properties`
2. **Layer materials**: Transparent over solid, energy over physical
3. **Specify lighting interaction**: How light behaves with material
4. **Use technical terms**: "subsurface scattering," "anisotropic reflection," "caustics"
5. **Reference real phenomena**: "aurora borealis colors," "deep sea bioluminescence"

---

## Sacred Geometry Precision Techniques

### Fundamental Sacred Geometry Forms

**Basic Shapes**:
- `Flower of Life` - Overlapping circles forming petals
- `Seed of Life` - Seven circles in hexagonal pattern
- `Metatron's Cube` - 13 circles connected by lines
- `Sri Yantra` - Nine interlocking triangles
- `Vesica Piscis` - Two circles overlapping
- `Golden Spiral` - Fibonacci/phi spiral
- `Platonic Solids` - Tetrahedron, cube, octahedron, dodecahedron, icosahedron

**Advanced Patterns**:
- `64 Tetrahedron Grid` - Star tetrahedron matrix
- `Torus field` - Donut-shaped energy pattern
- `Merkaba` - Star tetrahedron (3D Star of David)
- `Tree of Life` - Kabbalistic spheres and paths
- `Labyrinth pattern` - Sacred walking meditation paths
- `Mandala` - Circular sacred design
- `Yantra` - Geometric meditation diagram

### Precision Keywords

**Geometric Accuracy**:
- `mathematically perfect geometry`
- `precise sacred proportions`
- `golden ratio proportions (1.618)`
- `perfect symmetry`
- `sacred mathematical ratios`
- `geometric precision`
- `exact angle measurements`
- `perfectly aligned vertices`

**Structure and Form**:
- `interconnected geometric patterns`
- `nested geometric layers`
- `fractal geometric repetition`
- `self-similar patterns at multiple scales`
- `geometric lattice structure`
- `crystalline geometric framework`
- `harmonic geometric resonance`

**Dimensional Depth**:
- `multi-dimensional geometry`
- `three-dimensional sacred forms`
- `geometric depth with perspective`
- `layered geometric planes`
- `foreground and background geometric elements`
- `spatial geometry arrangement`

### Sacred Geometry Color Systems

**Traditional Chakra Colors**:
```
Root (red), Sacral (orange), Solar Plexus (yellow),
Heart (green), Throat (blue), Third Eye (indigo),
Crown (violet/white)
```

**Cosmic Sacred Palette**:
```
Deep space purple, nebula pink, stellar blue,
cosmic gold, celestial silver, void black,
divine white, aurora green
```

**Earth Sacred Palette**:
```
Sacred turquoise, temple gold, meditation purple,
natural emerald, spiritual amber, blessed white,
grounding brown, ritual crimson
```

### Integration Techniques

**Geometric Aura**:
```
Portrait of [subject], surrounded by luminous Flower of Life pattern forming aura,
sacred geometry in perfect golden ratio proportions,
geometric lines glowing with soft ethereal light,
nested geometric layers creating depth,
mathematically precise symmetry
```

**Environmental Integration**:
```
Ancient temple chamber walls covered in precise Sri Yantra patterns,
carved geometric reliefs with perfect sacred proportions,
light filtering through geometric window patterns creating sacred shadows,
floor mandala design in pristine mathematical accuracy,
three-dimensional Metatron's Cube suspended in center
```

**Cosmic Geometry**:
```
Universe visualization showing sacred geometry as universal structure,
galaxies arranged in Flower of Life formation,
golden spiral patterns in cosmic distribution,
Platonic solids as fundamental universe building blocks,
fractal geometric patterns repeating across scales from quantum to cosmic
```

**Energy Geometry**:
```
Visible energy field showing Merkaba sacred geometry,
rotating three-dimensional star tetrahedron made of light,
energy flowing through geometric pathways,
64 tetrahedron grid structure with nodes glowing,
torus field energy circulation pattern,
golden ratio spiral energy flow
```

### Advanced Sacred Geometry Prompts

**Meditation Mandala**:
```
Intricate meditation mandala with mathematically perfect symmetry,
concentric circles containing sacred geometric patterns,
Flower of Life at center transitioning to Metatron's Cube at outer rings,
colored in traditional chakra gradient from red center to violet outer edge,
precise line work with perfect radial symmetry,
subtle golden ratio proportions throughout,
soft ethereal glow from geometric elements,
ultra-detailed, 8K, sacred art style
```

**Crystal Geometric Array**:
```
Suspended array of transparent crystal Platonic solids,
each solid (tetrahedron, cube, octahedron, dodecahedron, icosahedron)
perfectly formed with exact mathematical proportions,
arranged in sacred geometric configuration,
light refracting through creating rainbow caustics,
internal Flower of Life patterns visible within crystals,
floating in void space with subtle galaxy background,
volumetric god rays illuminating,
hyperrealistic rendering, pristine detail
```

**Living Geometry Portal**:
```
Massive circular portal formed from golden Sri Yantra geometry,
nine interlocking triangles in perfect mathematical precision,
lines made of luminous liquid gold energy,
center point showing view into cosmic dimension,
geometric patterns animated with subtle pulsing glow,
sacred proportions creating harmonic resonance,
surrounded by nested Flower of Life framework,
ethereal particles orbiting geometric structures,
mystical atmosphere, divine lighting
```

### Precision Checklist

- [ ] Specify exact geometric form by name
- [ ] Request "mathematically perfect" or "sacred proportions"
- [ ] Define symmetry type (radial, bilateral, etc.)
- [ ] Describe layering and nesting if applicable
- [ ] Specify if 2D or 3D representation
- [ ] Include golden ratio reference for natural harmony
- [ ] Describe how geometry interacts with light
- [ ] Define color system aligned with sacred traditions
- [ ] Request "ultra-detailed" for fine geometric lines
- [ ] Specify if static or implying energy/motion

---

## Spiritual/Mystical Aesthetics

### Core Mystical Elements

**Archetypal Imagery**:
- Divine beings and deities
- Spiritual teachers and masters
- Angelic or celestial entities
- Cosmic consciousness representations
- Shamanic power animals
- Mythological figures
- Ancestral spirits

**Sacred Symbols**:
- Om/Aum symbol
- Ankh (Egyptian life symbol)
- Yin-Yang (Taoist duality)
- Third eye / Ajna chakra
- Lotus flower (enlightenment)
- Tree of Life (Kabbalah/universal)
- Ouroboros (eternal cycle)
- Infinity symbol

**Mystical Settings**:
- Ancient temples and sacred sites
- Meditation caves and hermitages
- Astral planes and dimensions
- Cosmic realms and heavens
- Energy vortexes and power spots
- Ceremonial ritual spaces
- Shamanic journey landscapes

### Aesthetic Style Keywords

**Visionary Art Style**:
```
visionary art style, transcendental artwork, consciousness expansion visualization,
psychedelic sacred art, multidimensional perception, spiritual awakening imagery
```

**Eastern Spiritual Aesthetics**:
```
Tibetan thangka painting style, Hindu deity aesthetics, Buddhist mandala art,
Taoist immortal imagery, Zen minimalist spirituality, yogic tradition visualization
```

**Western Mystical Aesthetics**:
```
Hermetic symbolism, Kabbalistic mysticism, Christian mystical art,
Gnostic spiritual imagery, Rosicrucian esoteric art, alchemical transformation symbolism
```

**Indigenous Spiritual Aesthetics**:
```
shamanic vision aesthetics, indigenous medicine art, tribal spiritual patterns,
dreamtime aboriginal style, Native American spiritual art, earth-based spirituality
```

**Modern Mystical Aesthetics**:
```
contemporary spiritual art, New Age mysticism, cosmic consciousness art,
quantum spirituality visualization, unified field imagery, holistic universe art
```

### Color Psychology for Spiritual Art

**High Vibration Colors**:
- Violet/Purple: Crown chakra, divine connection, spiritual mastery
- White/Gold: Pure consciousness, enlightenment, divine light
- Indigo/Deep Blue: Third eye, intuition, cosmic awareness
- Bright Cyan/Turquoise: Spiritual communication, higher truth

**Grounding and Balance**:
- Deep Green: Heart chakra, healing, nature connection
- Earthy Brown: Root chakra, grounding, earth connection
- Warm Orange: Sacral energy, creativity, life force
- Rose Pink: Divine love, compassion, heart opening

**Cosmic and Transcendent**:
- Deep Space Purple: Cosmic consciousness, universe connection
- Nebula Colors: Multi-dimensional awareness, cosmic creation
- Aurora Colors: Energy field visualization, light body activation
- Galaxy Blues and Pinks: Infinite potential, stellar consciousness

### Mystical Atmosphere Building

**Ethereal and Light**:
```
ethereal atmosphere, transcendent mood, light-filled environment,
heavenly ambiance, angelic presence, divine realm feeling,
celestial serenity, peaceful spiritual energy
```

**Deep and Mysterious**:
```
mystical atmosphere, ancient wisdom feeling, esoteric ambiance,
mysterious spiritual depth, hidden knowledge energy,
occult mystery mood, secret teaching environment
```

**Powerful and Transformative**:
```
powerful spiritual energy, transformative atmosphere,
intense consciousness shift feeling, awakening energy surge,
kundalini activation mood, spiritual breakthrough moment,
enlightenment realization environment
```

**Sacred and Reverent**:
```
sacred ceremony atmosphere, reverent spiritual mood,
holy sanctuary feeling, blessed environment,
devotional energy, temple sanctity, ritual sacred space
```

### Advanced Spiritual Composition

**Chakra Activation Portrait**:
```
Close-up portrait of meditating figure in lotus position,
seven glowing chakras aligned along spine visible through translucent body,
each chakra with traditional color (red root to violet crown) and spinning mandala pattern,
golden kundalini serpent energy rising through chakras,
aura showing rainbow prismatic light,
sacred geometry patterns (Flower of Life) surrounding head as energy field,
ethereal volumetric light, third eye emitting beam of indigo light,
peaceful transcendent expression, cosmic starfield background,
visionary art style, ultra-detailed, spiritual awakening aesthetic
```

**Cosmic Meditation Space**:
```
Ancient stone meditation platform floating in cosmic void,
figure in deep meditation surrounded by rotating Merkaba sacred geometry made of light,
massive Sri Yantra mandala horizontal beneath like cosmic floor,
galaxies and nebulae visible in every direction forming natural mandala pattern,
golden ratio spiral of cosmic dust swirling around scene,
aurora borealis colors dancing through space,
ethereal god rays from distant stars,
Platonic solids of crystal floating in orbit,
profound spiritual atmosphere, infinite consciousness visualization,
8K detail, mystical realism
```

**Temple of Light**:
```
Grand inner sanctum of crystal temple,
massive Flower of Life pattern in floor made of precious gems catching light,
walls covered in Metatron's Cube sacred geometry carved in luminous stone,
central altar with levitating sphere of pure energy contained in golden framework,
columned architecture with Tree of Life pillars showing Kabbalistic spheres,
dome ceiling depicting entire universe in mandala formation,
soft divine light filtering through geometric stained glass creating rainbow caustics,
incense smoke forming mystical patterns,
candles creating warm ambiance mixed with cool ethereal glow,
profound sacred atmosphere, ancient wisdom feeling,
architectural photography style, pristine detail
```

**Astral Journey Visualization**:
```
Ethereal figure ascending through dimensional layers,
body becoming increasingly translucent and light-filled with each level,
chakras glowing brighter through translucent form,
aura expanding from body colors to cosmic nebula colors,
third eye projecting beam opening portal to higher dimensions,
sacred geometric patterns forming pathways through space,
past lives and soul memories appearing as transparent overlays,
spirit guides in angelic form appearing from light,
cosmic background shifting from earthly to stellar to pure light dimensions,
trailing rainbow light body energy, silver cord connecting to physical realm below,
transcendent blissful expression, consciousness expansion visualization,
visionary art meets photorealism, ultra-detailed mystical journey
```

### Spiritual Art Integration Checklist

- [ ] Define spiritual tradition or universal approach
- [ ] Include relevant sacred symbols (2-3 max for clarity)
- [ ] Specify chakra colors if energy work related
- [ ] Describe divine or transcendent lighting
- [ ] Include sacred geometry appropriate to tradition
- [ ] Define mystical atmosphere and mood
- [ ] Balance earthly and cosmic elements
- [ ] Specify if portrait, scene, or abstract
- [ ] Request ultra-detail for intricate elements
- [ ] Choose photorealism, artistic, or mixed style

---

## Ultra & Raw Modes

### Ultra Mode: 4x Resolution

**When to Use Ultra**:
- Large format prints
- Detailed product photography
- Intricate sacred geometry requiring extreme precision
- Professional commercial work
- Billboard and large display graphics
- Fine art reproductions

**Ultra Mode Specifications**:
- **Resolution**: Up to 4MP (4x standard)
- **Speed**: ~10 seconds per generation
- **Quality**: Maintains full prompt adherence at high resolution
- **Cost**: $0.06 per image
- **Performance**: 2.5x faster than comparable high-res offerings

**Ultra Mode Prompt Adjustments**:
```
Since Ultra provides 4x more pixels, you can request even finer details:

Standard: "intricate mandala pattern"
Ultra: "intricate mandala pattern with individually visible sacred geometric line work,
micro-detail showing subtle texture variations in each element,
pristine clarity allowing extreme zoom without quality loss"
```

**Ultra Example Prompt**:
```
ULTRA MODE: Massive crystal sacred geometry installation,
Flower of Life pattern 20 feet wide made of transparent quartz crystals,
each crystal showing internal sacred geometry lattice visible at microscopic detail,
light refracting through creating thousands of rainbow caustic patterns on walls,
gold metal framework with visible engraving of Sanskrit mantras,
aged patina showing individual oxide patterns,
dust particles in volumetric light beams individually visible,
stone temple floor showing every grain and mineral deposit,
architectural photography, 8K scan quality, museum documentation level detail
```

### Raw Mode: Hyper-Realistic Photography

**When to Use Raw**:
- Candid photography aesthetic
- Natural, unpolished look
- Diverse human subject representation
- Nature photography realism
- Documentary style imagery
- Less synthetic, more authentic feel

**Raw Mode Characteristics**:
- Less "AI perfect" appearance
- More natural lighting and textures
- Greater diversity in human subjects
- Enhanced realism in nature/organic subjects
- Candid photography feel
- Superior lighting authenticity

**Raw vs Standard**:

Standard: Polished, "perfect" aesthetic, controlled appearance
Raw: Natural, candid, imperfect realism, authentic textures

**Raw Mode Prompt Style**:
```
Emphasize natural, authentic elements:

RAW MODE: Elderly spiritual teacher in meditation,
natural skin texture with visible pores and life lines showing wisdom,
imperfect lighting from nearby window creating realistic shadows,
candid moment captured authentically, worn clothing with natural fabric wrinkles,
simple temple background with genuine age and weathering,
documentary photography style, unposed natural moment,
shot on digital camera capturing reality, genuine human presence
```

**Raw Mode Example Prompts**:

**Raw Portrait**:
```
RAW MODE: Young woman in meditation by forest stream,
natural skin with freckles and imperfections visible,
hair slightly messy from wind, authentic casual expression,
morning light creating natural realistic shadows,
real forest environment with organic chaos,
clothing naturally wrinkled and worn, candid peaceful moment,
shot on iPhone camera, documentary style, genuine human meditation practice
```

**Raw Nature/Landscape**:
```
RAW MODE: Ancient stone circle at dawn,
natural weathering visible on every stone surface,
moss and lichen growth patterns authentically irregular,
grass with natural variations in height and color,
real morning mist settled in low areas,
imperfect golden hour lighting with actual atmospheric haze,
documentary landscape photography, National Geographic authenticity,
captured as found in nature without digital perfection
```

### Prompt Upsampling: LLM-Enhanced Prompts

**What is Prompt Upsampling**:
- Automatic prompt expansion using LLM
- Adds creative details and enrichment
- Increases output variety and realism
- Enhances composition automatically
- More unpredictable but often superior results

**When to Use Upsampling**:
- Need creative interpretation
- Want variety in similar prompts
- Seeking enhanced realism
- Exploring creative directions
- Don't need exact control

**When NOT to Use Upsampling**:
- Need precise control
- Specific client requirements
- Consistent style across set
- Exact composition matters
- Brand guidelines must be followed

**Example of Upsampling Effect**:

Your Prompt:
```
A peaceful meditation scene with chakras
```

Upsampled Prompt (example):
```
A serene and contemplative meditation scene featuring a practitioner in lotus position,
seven luminous chakras visibly aligned and glowing along the spine from root to crown,
each chakra displaying its traditional color and subtle spinning mandala pattern,
soft ambient lighting creating an atmosphere of inner peace,
minimalist meditation space with natural elements suggesting a sacred sanctuary,
gentle bokeh effect in background, photorealistic rendering with spiritual undertones,
captured in the golden hour with warm gentle illumination
```

**Pro Tips for Upsampling**:

1. Start with clear but brief prompts (1-2 sentences)
2. Include key must-have elements explicitly
3. Test multiple generations with same seed
4. Use for inspiration, refine for production
5. Combine with your detailed prompts for best results

### Mode Comparison Table

| Feature | Standard | Ultra | Raw | Standard + Upsampling |
|---------|----------|-------|-----|----------------------|
| Resolution | 1MP | 4MP | 1MP | 1MP |
| Aesthetic | Polished | Polished | Candid/Natural | Enhanced Polished |
| Speed | ~3-5s | ~10s | ~3-5s | ~5-7s |
| Cost | Standard | +Premium | Standard | Standard |
| Best For | General use | Large format | Documentary | Creative variety |
| Control | High | High | High | Lower (auto-enhanced) |
| Realism | AI Polished | AI Polished | Camera Real | Enhanced |

---

## Common Mistakes to Avoid

### 1. Using Prompt Weights from Other Models

**Mistake**:
```
(sacred geometry:1.5), ((glowing chakras:1.8)), [background:0.5]
```

**FLUX doesn't support these syntax types. Use natural language instead.**

**Correct**:
```
Heavily emphasizing the sacred geometry patterns, with strong focus on brightly glowing chakras,
while keeping the background subtle and understated
```

### 2. "White Background" Phrase

**Mistake**:
```
Product photo of crystal, white background, clean
```

**This phrase consistently causes blurry images in FLUX.**

**Correct**:
```
Product photo of crystal, clean neutral backdrop, pristine white studio environment,
minimal distraction, sharp focus
```

### 3. Over-Stylization with Multiple Style Tags

**Mistake**:
```
photorealistic, oil painting, digital art, watercolor, anime style,
cinematic, concept art, trending on artstation
```

**Too many style keywords create confusion and inconsistency.**

**Correct**:
```
Professional photography with subtle painterly qualities, cinematic composition
```

**Rule**: Use 1-2 primary style keywords max.

### 4. Vague or Disorganized Prompts

**Mistake**:
```
Make a spiritual person with light and sacred geometry and cosmos and meditation
```

**Lacks structure and specific direction.**

**Correct**:
```
Portrait of spiritual teacher meditating in lotus position, luminous chakras aligned along spine,
Flower of Life sacred geometry forming aura, cosmic starfield background with nebula colors,
ethereal divine lighting, peaceful expression, photorealistic, ultra-detailed
```

### 5. Neglecting Spatial Relationships

**Mistake**:
```
Crystal sphere, mandala, temple, figure meditating
```

**Doesn't explain how elements relate spatially.**

**Correct**:
```
Ancient temple interior with meditating figure in foreground,
large mandala pattern visible on wall behind them,
crystal sphere hovering in mid-air between camera and figure,
creating sense of depth and three-dimensional space
```

### 6. Flat Lighting Descriptions

**Mistake**:
```
Good lighting
```

**Too vague to produce interesting lighting.**

**Correct**:
```
Golden hour directional key light from left, subtle rim light highlighting edges,
soft volumetric god rays filtering through atmosphere,
creating depth and dimensional lighting
```

### 7. Missing Technical Camera Details (Photorealistic Work)

**Mistake**:
```
Photo of person meditating
```

**Lacks photography specifics for realism.**

**Correct**:
```
Portrait of person meditating, shot on Canon EOS R5, 85mm f/1.4 lens,
shallow depth of field, professional photography, sharp focus on subject,
natural bokeh in background
```

### 8. Inconsistent Detail Levels

**Mistake**:
```
Ultra-detailed face, background
```

**Massive detail requested for one element, vague for others.**

**Correct**:
```
Ultra-detailed face showing skin texture and pores,
intricate mandala background with precise geometric patterns,
foreground elements in sharp focus, background softly blurred
```

### 9. Text and Logo Warping

**Mistake**:
```
Book cover with title "SPIRIT ATLAS - A Journey Through Consciousness and Cosmic Awakening"
```

**Long text strings often warp or become illegible.**

**Correct**:
```
Book cover with title "SPIRIT ATLAS" in large bold serif font,
subtitle "A Journey Through Consciousness" in smaller elegant text below,
clean typography, centered placement, legible text rendering
```

**Tips**:
- Keep text short
- Use ALL CAPS for better rendering
- Request clean backgrounds behind text
- Specify font style

### 10. Ignoring Aspect Ratio for Use Case

**Mistake**:
Using 1:1 square for a website hero image (should be 16:9)

**Correct**:
Match aspect ratio to intended use (see Aspect Ratio Optimization section)

### 11. Overusing Negative Prompts (FLUX 2)

**Mistake**:
```
FLUX 2 generation with negative prompt: blurry, distorted, ugly, bad anatomy...
```

**FLUX 2 doesn't support negative prompts.**

**Correct (positive framing)**:
```
Crystal clear focus, perfect proportions, beautiful composition,
pristine geometric accuracy, professional quality
```

### 12. Forgetting Context for Mystical Elements

**Mistake**:
```
Chakras, aura, sacred geometry
```

**Elements floating without subject or context.**

**Correct**:
```
Meditating yogi with seven glowing chakras visible along the spine,
rainbow aura emanating outward, Flower of Life sacred geometry
pattern surrounding the figure like an energy field,
temple meditation hall setting
```

### Mistake Avoidance Checklist

- [ ] NO prompt weight syntax like (keyword:1.5)
- [ ] NO "white background" phrase - use alternatives
- [ ] Maximum 1-2 style keywords
- [ ] Organized, structured prompt flow
- [ ] Clear spatial relationships between elements
- [ ] Specific lighting descriptions (not just "good lighting")
- [ ] Camera details for photorealistic work
- [ ] Consistent detail levels across all elements
- [ ] Short, ALL CAPS text for better rendering
- [ ] Aspect ratio matches intended use case
- [ ] Positive framing instead of negatives (FLUX 2)
- [ ] Context provided for mystical elements

---

## Exceptional Output Examples

### Example 1: Photorealistic Spiritual Portrait

**Prompt**:
```
Portrait of serene meditation master, elderly man with deep wisdom lines,
eyes gently closed in peaceful meditation, sitting in lotus position,
seven luminous chakras glowing along spine (red root, orange sacral, yellow solar plexus,
green heart, blue throat, indigo third eye, violet crown),
subtle golden aura emanating from body, Flower of Life sacred geometry pattern
faintly visible in aura field, simple meditation room with stone walls,
soft golden hour window light from left creating gentle rim lighting,
volumetric light rays visible in air, shot on Canon EOS R5, 85mm f/1.4,
shallow depth of field, photorealistic, ultra-detailed skin texture,
peaceful transcendent atmosphere, professional portrait photography
```

**Expected Results**:
- Hyperrealistic human subject with visible pores and skin detail
- Accurate chakra placement and traditional colors
- Natural photographic lighting with professional quality
- Subtle mystical elements integrated realistically
- Sharp focus on subject, natural bokeh background

**Use Case**: Meditation app promotional material, spiritual book cover, yoga studio decor

---

### Example 2: Sacred Geometry Mandala Art

**Prompt**:
```
Intricate circular mandala sacred art, perfect radial symmetry,
central seed of life pattern transitioning to flower of life at middle ring,
outer ring featuring Metatron's cube elements with platonic solids at cardinal points,
colored in cosmic palette (deep purple, nebula pink, stellar blue, sacred gold),
gradient flowing from warm center to cool outer edge,
each geometric line glowing with soft ethereal light,
mathematically perfect proportions using golden ratio,
ultra-detailed line work with pristine precision, layered geometric depth,
subtle texture suggesting ancient stone carving, 8K resolution,
mystical sacred art style, meditative focal point, spiritual artwork
```

**Expected Results**:
- Mathematically precise geometric patterns
- Perfect symmetry without distortion
- Smooth color gradients with spiritual color palette
- Clean, detailed line work visible at high zoom
- Suitable for meditation or print

**Use Case**: Meditation focal point, spiritual center decor, sacred art prints, app backgrounds

---

### Example 3: Cosmic Meditation Scene

**Prompt**:
```
Floating meditation platform in cosmic void, lone figure in lotus position
at center with body becoming translucent and filled with starlight,
massive spiral galaxy directly behind creating natural halo effect,
rotating Merkaba (star tetrahedron) of golden light surrounding meditator,
64 tetrahedron grid structure visible as energy field extending outward,
nebula clouds in purple, pink, and blue surrounding scene,
aurora borealis colors flowing through space like energy ribbons,
golden ratio spiral of cosmic dust sweeping through composition,
distant stars and galaxies forming natural mandala pattern in space,
ethereal god rays from distant stellar sources, platform showing Sri Yantra carved in stone,
consciousness expansion visualization, infinite space atmosphere,
photorealistic rendering with mystical elements, 8K, profound spiritual scene
```

**Expected Results**:
- Seamless blend of photorealistic and mystical elements
- Accurate sacred geometry (Merkaba, Sri Yantra) in cosmic setting
- Rich cosmic colors with natural astronomical aesthetics
- Sense of infinite space and transcendence
- Balanced composition using sacred proportions

**Use Case**: Meditation music album cover, spiritual documentary screenshot, consciousness exploration visuals

---

### Example 4: Crystal Temple Architecture

**Prompt**:
```
Grand interior of ancient crystal temple, massive chamber with soaring ceiling,
walls made of giant transparent quartz crystals with sacred Flower of Life patterns
etched throughout catching light, floor showing enormous Sri Yantra mandala
inlaid with precious gems (ruby, sapphire, emerald, topaz, amethyst),
central levitating sphere of pure white energy contained in golden geometric framework
showing platonic solid structure, twelve pillars arranged in circle each representing
zodiac sign with constellation patterns in gold inlay, pillar capitals carved with
tree of life Kabbalistic spheres, cathedral ceiling depicting entire universe
in mandala formation with galaxies spiraling outward, soft divine light filtering through
geometric crystal skylights creating rainbow caustics on walls and floor,
volumetric god rays cutting through atmospheric mist, floating dust particles catching light,
ancient aged gold details with patina, scale showing massive architecture dwarfing any visitors,
wide-angle architectural photography, 16mm lens perspective, ultra-detailed,
sacred atmosphere, mystical realism, pristine 8K clarity
```

**Expected Results**:
- Stunning architectural scale and grandeur
- Multiple sacred geometry elements integrated naturally
- Sophisticated lighting with caustics and volumetric effects
- Rich material rendering (crystal, gold, gems, stone)
- Photorealistic yet profoundly spiritual atmosphere

**Use Case**: Fantasy/spiritual game environment concept, visionary art exhibition piece, large-format print

---

### Example 5: Chakra Energy Visualization

**Prompt**:
```
Close-up torso view of meditating figure with body rendered as translucent luminous form,
seven chakras perfectly aligned along spine each with distinct characteristics:
Root chakra (red, four-petal lotus mandala, grounding energy at base),
Sacral chakra (orange, six-petal lotus, creative flowing energy),
Solar plexus chakra (yellow, ten-petal lotus, radiating power),
Heart chakra (green with pink inner glow, twelve-petal lotus, loving energy),
Throat chakra (blue, sixteen-petal lotus, communication beams),
Third eye chakra (indigo, two-petal lotus, consciousness beam projecting from forehead),
Crown chakra (violet and white, thousand-petal lotus, connection to cosmos above),
golden kundalini serpent energy spiraling up through chakras,
each chakra showing spinning vortex motion with appropriate rotation,
energy channels (nadis) visible as subtle golden threads through translucent body,
rainbow prismatic aura surrounding entire figure graduated from red at bottom to violet at top,
Flower of Life sacred geometry overlaid on aura as energy field structure,
dark cosmic background with subtle stars allowing chakras to glow prominently,
hands in chin mudra (thumb and index finger touching), peaceful meditative expression,
visionary art style, spiritual energy visualization, ultra-detailed,
yogic tradition accuracy, consciousness activation aesthetic
```

**Expected Results**:
- Accurate chakra positions, colors, and traditional symbols
- Sophisticated energy visualization with motion implied
- Translucent body effect showing internal energy system
- Beautiful color gradients and glow effects
- Anatomically correct figure in proper meditation posture

**Use Case**: Yoga/meditation instruction materials, energy healing charts, spiritual education, wellness app interface

---

### Example 6: Nature Mysticism Scene (Raw Mode)

**Prompt**:
```
RAW MODE: Ancient redwood forest meditation spot at dawn,
natural morning mist settled between massive tree trunks,
simple meditation cushion on mossy ground showing real organic texture and weathering,
small natural rock circle around cushion arranged in sacred geometry (not too perfect),
early golden light filtering through forest canopy creating authentic dappled lighting,
tiny bioluminescent mushrooms growing on nearby fallen log (genuine species accurate),
spider webs catching morning dew with individual droplets visible,
wild ferns and natural forest understory in organic chaos,
one aged wooden prayer flag hanging from branch showing real fabric weathering,
candid authentic forest environment captured as found in nature,
documentary photography style, shot on digital camera, natural imperfections,
forest sounds implied, peaceful genuine spiritual practice in nature,
National Geographic realism, 8K environmental portrait
```

**Expected Results**:
- Authentic natural environment without AI polish
- Real forest lighting and atmospheric effects
- Organic chaos of nature, not artificially arranged
- Genuine material textures (moss, wood, fabric)
- Documentary-style capture of real spiritual practice

**Use Case**: Nature meditation app, outdoor yoga retreat materials, documentary film still, authentic spiritual practice

---

### Example 7: Mystical Product Photography

**Prompt**:
```
Professional product photo of meditation singing bowl on natural stone surface,
aged brass bowl with traditional etched Om symbol and eight-spoke dharma wheel,
genuine patina showing years of ceremonial use, wooden striker resting against bowl,
simple backdrop showing soft gradient from warm beige to cool gray (avoiding pure white),
studio lighting setup: main soft box from upper left creating gentle highlight on brass,
subtle fill light from right preventing harsh shadows, rim light from behind
separating bowl from background, minimal reflections on polished stone surface,
depth of field isolating bowl as main focus with background softly blurred,
few ritual elements subtly staged (incense stick, small crystal) out of focus behind,
natural materials only (stone, wood, metal), commercial product photography,
clean professional composition, shot on Canon EOS R5, 100mm macro, f/5.6,
pristine detail showing brass texture, e-commerce quality, meditation product aesthetic
```

**Expected Results**:
- Professional commercial photography quality
- Accurate material rendering (brass, wood, stone)
- Clean composition with intentional lighting
- Sharp focus on product with controlled depth
- Subtle spiritual aesthetic without overwhelming product

**Use Case**: E-commerce product listings, meditation product catalogs, spiritual retail, brand photography

---

## Prompt Template Library

### Universal Template

```
[Subject with specific details] + [Action/Pose/State] + [Primary environment/setting] +
[Lighting description] + [Sacred/mystical elements if applicable] +
[Style keywords (1-2 max)] + [Camera/technical specs if photorealistic] +
[Mood/atmosphere] + [Quality keywords]
```

### Quick Reference Cards

**Photorealistic Portrait**:
```
[Subject description], [pose/expression], [environment],
[lighting: golden hour/studio/natural], shot on [camera] [lens] f/[aperture],
[depth of field], professional photography, ultra-detailed, [mood]
```

**Sacred Geometry Art**:
```
[Geometric form name], perfect [symmetry type], [color palette],
[additional geometric elements], [glow/energy effects],
mathematically precise, golden ratio proportions, ultra-detailed line work,
8K, sacred art style
```

**Mystical Scene**:
```
[Subject] in [sacred location], [mystical elements present],
[lighting: divine/ethereal/god rays], [sacred geometry integrated how],
[cosmic/natural elements], [energy visualization], visionary art style,
spiritual atmosphere, ultra-detailed
```

**Product Photography**:
```
Professional product photo of [item], [surface/setting],
[lighting setup description], [depth of field], clean composition,
shot on [camera] [lens], e-commerce quality, [aesthetic style]
```

---

## Final Pro Tips

1. **Test systematically**: Change one variable at a time to understand impact
2. **Build a swipe file**: Save prompts that work exceptionally well
3. **Layer complexity gradually**: Start simple, add detail in iterations
4. **Trust FLUX's intelligence**: Natural language works better than keyword stuffing
5. **Match mode to purpose**: Standard/Ultra/Raw each excel at different use cases
6. **Sacred geometry needs precision**: Always request "mathematically perfect" and "golden ratio"
7. **Lighting makes or breaks mystical mood**: Invest prompt tokens in lighting description
8. **Material rendering requires specifics**: Name the material physics (subsurface scattering, caustics, etc.)
9. **Aspect ratio matters**: 1:1 for mandalas, 16:9 for scenes, 4:5 for portraits
10. **When in doubt, add "ultra-detailed, 8K, professional quality"**: These universally improve outputs

---

## Resources and Attribution

This guide is based on comprehensive research of FLUX 1.1 Pro capabilities and community best practices as of 2025.

### Primary Sources

- [14 Essential FLUX.1 Prompts: Tested Templates & Prompt Tips (2025)](https://skywork.ai/blog/flux1-prompts-tested-templates-tips-2025/)
- [FLUX.1 Prompt Guide](https://www.giz.ai/flux-1-prompt-guide/)
- [FLUX.1 Prompt Guide: Pro Tips and Common Mistakes to Avoid](https://getimg.ai/blog/flux-1-prompt-guide-pro-tips-and-common-mistakes-to-avoid)
- [Flux Prompting Ultimate Guide: Effective Prompts for FLUX.1 dev & schnell](https://skywork.ai/blog/flux-prompting-ultimate-guide-flux1-dev-schnell/)
- [Top 10 Prompts for Flux 1.1 Pro: Best Image Generation Ideas](https://aimlapi.com/blog/top-10-prompts-for-flux-1-1-pro-best-image-generation-ideas)
- [How to Design Prompts for Flux 1.1 Pro](https://fluxproweb.com/blog/detail/How-to-Design-Prompts-for-Flux-1-1-Pro-0a16b61b16e6/)
- [Introduction to Flux 1.1 Pro AI and Crafting Effective Prompts](https://flux-ai.io/blog/detail/Introduction-to-Flux-1-1-Pro-AI-and-Crafting-Effective-Prompts-f43e98aa15ed/)
- [Prompting Guide - Text to Image - Quick Reference - Black Forest Labs](https://docs.bfl.ai/guides/prompting_summary)
- [FLUX.1 Zero to Pro Prompt Guide](https://rentprompts.com/blog/flux1-zero-to-pro-prompt-guide)

### FLUX 1.1 Pro Capabilities

- [Flux 1.1 Pro Is Here — Better and Faster Image Generation](https://www.fluxlabs.ai/blog/flux-11-pro-is-here--better-and-faster-image-generation)
- [Flux 1.1 Pro vs Flux 1. How much better is the new release](https://medium.com/@thomas_reid/flux-1-1-pro-vs-flux-1-f0a599224764)
- [MimicPC - An In-Depth Comparison of All Flux Models Available](https://www.mimicpc.com/learn/an-in-depth-comparison-of-all-flux-models)
- [Announcing FLUX1.1 [pro] by BFL and FLUX [dev] speed improvements](https://blog.fal.ai/announcing-flux-1-1-pro/)
- [Black Forest Labs FLUX.1 Kontext [pro] and FLUX1.1 [pro] Now Available in Azure AI Foundry](https://techcommunity.microsoft.com/blog/azure-ai-foundry-blog/black-forest-labs-flux-1-kontext-pro-and-flux1-1-pro-now-available-in-azure-ai-f/4434659)

### Negative Prompts

- [How to Write Negative Prompts in FLUX](https://medium.com/towards-agi/how-to-write-negative-prompts-in-flux-e4305c9e7333)
- [Master Negative Prompts in FLUX: A Comprehensive Guide](https://merlio.app/blog/negative-prompts-in-flux-guide)
- [How to Write Negative Prompts in FLUX](http://anakin.ai/blog/how-to-write-negative-prompts-in-flux/)

### Style and Lighting Techniques

- [14 Essential FLUX.1 Prompts: Tested Templates & Prompt Tips (2025)](https://skywork.ai/blog/flux1-prompts-tested-templates-tips-2025/)
- [How to Use Flux 1 Krea for Stunning Photorealistic Image Generation](https://fluxproweb.com/blog/detail/How-to-Use-Flux-1-Krea-for-Stunning-Photorealistic-Image-Generation-945cca85615c/)

### Sacred Geometry and Mystical Aesthetics

- [Sacred Geometry Generator - Flux Image](https://flux-image.com/generator/sacred-geometry)
- [Mastering Flux AI Prompts: Techniques, Tips & Best Examples](https://aimodelflux.com/prompts/)

### Multi-Concept Composition

- [FLUX.1 Kontext | Black Forest Labs](https://bfl.ai/models/flux-kontext)
- [FLUX.1 Kontext [pro] | Image to Image](https://fal.ai/models/fal-ai/flux-pro/kontext)
- [Introducing FLUX.1 Kontext: Instruction-based image editing with AI](https://runware.ai/blog/introducing-flux1-kontext-instruction-based-image-editing-with-ai)
- [Flux Kontext Multi - AI-Powered Multi-Image Fusion & Editing Platform](https://kontextflux.com/flux-kontext-multi)
- [FLUX.1 Kontext models: Character consistency and precise image editing](https://www.together.ai/blog/flux-1-kontext)

### Aspect Ratio Optimization

- [Flux.1 Fine Tuning: Best Practices & Settings](https://blog.segmind.com/flux1-fine-tuning-best-practices/)
- [Optimal Width & Height Guide for AI Image Models](https://www.promptingpixels.com/tutorial/width-height)
- [Image Resolutions With Flux.1 Dev Model (Compared)](https://blog.segmind.com/image-resolutions-with-flux-1-dev-model-compared/)

### Common Mistakes

- [FLUX.1 Prompt Guide: Pro Tips and Common Mistakes to Avoid](https://getimg.ai/blog/flux-1-prompt-guide-pro-tips-and-common-mistakes-to-avoid)
- [14 Essential FLUX.1 Prompts: Tested Templates & Prompt Tips (2025)](https://skywork.ai/blog/flux1-prompts-tested-templates-tips-2025/)

### Photorealistic Examples

- [Best 15 Realistic FLUX Prompts You Can Try Out Now](https://medium.com/towards-agi/best-15-realistic-flux-prompts-you-can-try-out-now-3f3b2b085ccb)
- [Top 10 Prompts for Flux.1: Master the Art of AI](https://aimlapi.com/blog/master-the-art-of-ai-top-10-prompts-for-flux-1-by-black-forests-labs)
- [Mastering FLUX: Top Prompts for Stunning AI-Generated Images](https://www.3daistudio.com/blog/the-best-flux-black-forest-labs-prompts-for-good-results-in-ai-image-generation)
- [Best 15 Realistic FLUX Prompts You Can Try Right Now](https://anakin.ai/blog/realistic-flux-prompts/)
- [Mastering FLUX.1 Prompts for High-Quality Realistic Portraits](https://www.nextdiffusion.ai/blogs/mastering-ai-portrait-prompts-with-flux1-for-realistic-images)

### Ultra and Raw Modes

- [FLUX Pro 1.1 Ultra In-Depth Look: 4MP Resolution & Raw Mode](https://stablediffusion3.net/blog--flux-pro-11-ultra-indepth-look-4mp-resolution-raw-mode-53771)
- [Introducing FLUX1.1 [pro] Ultra and Raw Modes](https://blackforestlabs.ai/flux-1-1-ultra/)
- [FLUX1.1 [pro] Ultra Mode - Black Forest Labs](https://docs.bfl.ai/flux_models/flux_1_1_pro_ultra_raw)
- [How to Generate High-Quality Images with FLUX 1.1 Pro](https://ai-flow.net/blog/generate-images-with-flux-1-1-pro/)

---

**Document Version**: 1.0
**Last Updated**: December 2025
**For**: SpiritAtlas Image Generation Project

---

## Quick Start Checklist

When creating a new FLUX 1.1 Pro prompt, use this checklist:

- [ ] Front-load most important elements
- [ ] Use natural language, not keyword stuffing
- [ ] Maximum 1-2 style keywords
- [ ] Include specific lighting (not just "good lighting")
- [ ] Avoid "white background" phrase
- [ ] NO prompt weights - use natural emphasis
- [ ] Spatial relationships clearly defined
- [ ] Aspect ratio matches use case
- [ ] For sacred geometry: request "mathematically perfect" and "golden ratio"
- [ ] For photorealistic: include camera and lens specs
- [ ] For mystical: describe spiritual atmosphere
- [ ] Add "ultra-detailed, 8K" for quality
- [ ] Choose appropriate mode: Standard/Ultra/Raw/Upsampling

**Remember**: FLUX 1.1 Pro understands you like a human artist. Describe what you want as if explaining to a skilled photographer or artist, with technical precision where needed.

---
