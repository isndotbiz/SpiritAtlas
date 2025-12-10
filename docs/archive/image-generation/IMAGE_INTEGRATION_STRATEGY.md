# SpiritAtlas Image Integration Strategy

**Version:** 1.0
**Date:** December 9, 2025
**Goal:** Integrate 99 high-quality FLUX 1.1 Pro images throughout the app to create a visually stunning, world-class spiritual experience

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [App Architecture Overview](#app-architecture-overview)
3. [Image Categories & Allocation](#image-categories--allocation)
4. [Screen-by-Screen Integration Plan](#screen-by-screen-integration-plan)
5. [Component-Level Integration](#component-level-integration)
6. [Implementation Checklist](#implementation-checklist)
7. [Resource Organization](#resource-organization)
8. [Gaps & Additional Image Needs](#gaps--additional-image-needs)

---

## Executive Summary

SpiritAtlas is a comprehensive spiritual profile and compatibility analysis app with 8 feature modules and 17+ screens. This strategy ensures all 99 generated images are meaningfully integrated to create an immersive, beautiful user experience comparable to top-tier apps like Co-Star, The Pattern, and Sanctuary.

**Key Integration Points:**
- **8 Main Screens** with unique backgrounds
- **99 Images** distributed across UI components
- **12 Zodiac Signs** for birth chart visualizations
- **7 Chakras** for energy center displays
- **8 Moon Phases** for lunar calendar features
- **5 Elements** for Ayurvedic dosha indicators
- **12 Buttons** for primary actions
- **10 App Icons** for launcher and features
- **9 UI States** for loading, error, success feedback

---

## App Architecture Overview

### Core Navigation Structure
```
Splash Screen → Home → Features
├── Profile Module
│   ├── Profile Library (List)
│   ├── Profile Creation/Edit
│   ├── Profile Detail View
│   ├── Profile Comparison
│   └── Enrichment Results
├── Compatibility Module
│   ├── Compatibility Screen
│   └── Compatibility Detail
├── Onboarding Module
│   └── Onboarding Flow (3-4 pages)
├── Consent Module
│   └── Consent & Settings
├── Settings Module
│   └── Settings & AI Config
└── Tantric Module
    └── Tantric Content (with consent gate)
```

### Key Feature Areas
1. **Numerology** - Chaldean & Pythagorean calculations
2. **Astrology** - Birth charts, transits, synastry
3. **Ayurveda** - Dosha analysis (Vata, Pitta, Kapha)
4. **Human Design** - Bodygraph visualization
5. **Tantric** - Intimacy insights (consent-gated)
6. **Compatibility** - 12-dimension radar analysis

---

## Image Categories & Allocation

### 1. App Icons (10 images) - PRIORITY: CRITICAL
**Purpose:** Launcher icon, feature section headers, navigation

| Image # | Usage | Location |
|---------|-------|----------|
| 1 | Primary Launcher Icon | `/app/src/main/res/mipmap-*` |
| 2 | Splash Screen Logo | `SplashScreen.kt` - replace Canvas logo |
| 3 | Home Feature Icon | `HomeScreen.kt` - header |
| 4 | Profile Feature Icon | Profile section headers |
| 5 | Compatibility Feature Icon | Compatibility headers |
| 6 | Astrology Feature Icon | Astrology sections |
| 7 | Numerology Feature Icon | Numerology sections |
| 8 | Ayurveda Feature Icon | Ayurveda sections |
| 9 | Chakra Feature Icon | Chakra visualizations |
| 10 | Human Design Feature Icon | Human Design sections |

**Implementation:** Replace current Canvas-drawn logo with image assets

---

### 2. Buttons (12 images) - PRIORITY: HIGH
**Purpose:** Primary action buttons throughout app

| Image # | Button Type | Screens Used |
|---------|-------------|--------------|
| 1 | Primary CTA Button | All screens - "Create Profile", "Save", "Continue" |
| 2 | Secondary Button | "Cancel", "Skip", "Back" actions |
| 3 | Add/Create Button | FAB on Home, "Add Profile" button |
| 4 | Compare Button | Profile Library - "Compare Profiles" |
| 5 | Analyze Button | Compatibility screen - "Analyze Compatibility" |
| 6 | Share Button | Profile Detail, Compatibility Results |
| 7 | Save/Favorite Button | Bookmarking profiles/results |
| 8 | Edit Button | Profile editing, settings |
| 9 | Delete Button | Remove profiles |
| 10 | Enrich Button | AI enrichment triggers |
| 11 | Settings Button | Top bar navigation |
| 12 | Info/Help Button | Tooltips, help sections |

**Current State:** Using Material3 buttons - replace with custom image-backed buttons
**Component:** `core/ui/components/InteractiveButton.kt`, `SpiritualButton` in `ModernComponents.kt`

---

### 3. Backgrounds (12 images) - PRIORITY: CRITICAL
**Purpose:** Screen-specific backgrounds for immersive experience

| Image # | Screen | Current State | Integration |
|---------|--------|---------------|-------------|
| 1 | Splash Screen | Dark gradient with stars | Add nebula/cosmic background |
| 2 | Home Screen | `StarfieldBackground` | Layer mystical space image behind starfield |
| 3 | Profile Library | `SacredGeometryBackground` | Sacred geometry mandala overlay |
| 4 | Profile Creation | Basic background | Calming meditation background |
| 5 | Profile Detail | Default | Personalized based on zodiac/element |
| 6 | Compatibility Screen | `CosmicConnectionBackground` | Enhanced with cosmic threads image |
| 7 | Compatibility Detail | `CosmicConnectionBackground` | Deep space with constellation patterns |
| 8 | Onboarding Pages | Gradient per page | Unique mystical scene per page (4 images total = 4 of 12) |
| 9-12 | Settings, Consent, Tantric, Enrichment | Basic | Subtle spiritual textures |

**Files to Modify:**
- `core/ui/components/CosmicBackgrounds.kt`
- `app/src/main/java/com/spiritatlas/app/SplashScreen.kt`
- All screen composables

---

### 4. Avatars (10 images) - PRIORITY: MEDIUM
**Purpose:** Profile avatars, user representation, avatar frames

| Image # | Usage | Location |
|---------|-------|----------|
| 1-5 | Default Avatar Options | Profile creation - user selects from 5 options |
| 6-10 | Avatar Frames/Borders | Profile Library cards, Profile cards |

**Implementation:**
- Add avatar selection UI to `ProfileScreen.kt`
- Display in `ProfileAvatarCard` component in `HomeScreen.kt`
- Show in profile list items in `ProfileLibraryScreen.kt`

**Current State:** Using first letter of name in circular gradient - replace with selectable avatars

---

### 5. Chakras (7 images) - PRIORITY: HIGH
**Purpose:** Chakra visualization in profile energy analysis

| Image # | Chakra | Color | Usage |
|---------|--------|-------|-------|
| 1 | Root (Muladhara) | Red | Energy center visualization |
| 2 | Sacral (Svadhisthana) | Orange | Energy center visualization |
| 3 | Solar Plexus (Manipura) | Yellow | Energy center visualization |
| 4 | Heart (Anahata) | Green | Energy center visualization |
| 5 | Throat (Vishuddha) | Blue | Energy center visualization |
| 6 | Third Eye (Ajna) | Indigo | Energy center visualization |
| 7 | Crown (Sahasrara) | Violet/White | Energy center visualization |

**Implementation:**
- New component: `ChakraVisualization` in `core/ui/components/`
- Display in Profile Detail when viewing energy analysis
- Show in Compatibility Detail for energetic harmony
- Reference: `docs/BODYGRAPH_VISUALIZATION.md` for integration patterns

**Where to Use:**
- `ProfileScreen.kt` - Chakra section
- `CompatibilityDetailScreen.kt` - Energetic compatibility section
- Home screen daily insights for active chakra today

---

### 6. Sacred Geometry (8 images) - PRIORITY: HIGH
**Purpose:** Visual depth for compatibility analysis, backgrounds, spiritual symbolism

| Image # | Symbol | Usage |
|---------|--------|-------|
| 1 | Flower of Life | Compatibility detail background layer |
| 2 | Metatron's Cube | Profile creation background |
| 3 | Sri Yantra | Meditation/tantric sections |
| 4 | Seed of Life | Loading states, transitions |
| 5 | Vesica Piscis | Connection/compatibility indicators |
| 6 | Golden Spiral | Profile detail decorative element |
| 7 | Platonic Solids | Human Design bodygraph background |
| 8 | Torus Field | Energy flow visualizations |

**Implementation:**
- Layer behind content in `CompatibilityDetailScreen.kt`
- Background options in `SacredGeometryBackground` component
- Animated overlays during loading/transitions
- Integration with existing `SpiritualAnimations.kt`

---

### 7. Zodiac (12 images) - PRIORITY: CRITICAL
**Purpose:** Birth chart visualization, astrological profile display

| Image # | Sign | Usage |
|---------|------|-------|
| 1 | Aries | Birth chart, profile display, compatibility |
| 2 | Taurus | Birth chart, profile display, compatibility |
| 3 | Gemini | Birth chart, profile display, compatibility |
| 4 | Cancer | Birth chart, profile display, compatibility |
| 5 | Leo | Birth chart, profile display, compatibility |
| 6 | Virgo | Birth chart, profile display, compatibility |
| 7 | Libra | Birth chart, profile display, compatibility |
| 8 | Scorpio | Birth chart, profile display, compatibility |
| 9 | Sagittarius | Birth chart, profile display, compatibility |
| 10 | Capricorn | Birth chart, profile display, compatibility |
| 11 | Aquarius | Birth chart, profile display, compatibility |
| 12 | Pisces | Birth chart, profile display, compatibility |

**Implementation:**
- Create `ZodiacIcon` component in `core/ui/components/`
- Display in Profile Detail for Sun/Moon/Rising signs
- Show in Home screen header based on current astrological date
- Use in Compatibility Detail synastry section
- Reference: Existing `SpiritualIcons.kt` has placeholder zodiac drawing code

**Current State:** Icons exist as Canvas-drawn vectors - replace with image assets for higher quality

---

### 8. Elements (5 images) - PRIORITY: MEDIUM
**Purpose:** Ayurvedic dosha indicators, elemental balance visualization

| Image # | Element | Dosha Association | Usage |
|---------|---------|-------------------|-------|
| 1 | Fire | Pitta | Dosha visualization, elemental balance |
| 2 | Water | Kapha | Dosha visualization, elemental balance |
| 3 | Earth | Kapha | Dosha visualization, elemental balance |
| 4 | Air | Vata | Dosha visualization, elemental balance |
| 5 | Ether/Spirit | Vata | Spiritual connection indicator |

**Implementation:**
- New component: `ElementalIcon` in `core/ui/components/`
- Display in Profile Detail Ayurveda section
- Show in Compatibility Detail for elemental harmony
- Use in Home screen "Spiritual Weather" widget

**Files:**
- `ProfileScreen.kt` - Ayurveda section
- `CompatibilityDetailScreen.kt` - Ayurvedic balance section
- `HomeScreen.kt` - Spiritual Weather Widget

---

### 9. Moon Phases (8 images) - PRIORITY: HIGH
**Purpose:** Lunar calendar, timing recommendations, daily insights

| Image # | Phase | Usage |
|---------|-------|-------|
| 1 | New Moon | Daily insights, timing recommendations |
| 2 | Waxing Crescent | Daily insights, timing recommendations |
| 3 | First Quarter | Daily insights, timing recommendations |
| 4 | Waxing Gibbous | Daily insights, timing recommendations |
| 5 | Full Moon | Daily insights, timing recommendations |
| 6 | Waning Gibbous | Daily insights, timing recommendations |
| 7 | Last Quarter | Daily insights, timing recommendations |
| 8 | Waning Crescent | Daily insights, timing recommendations |

**Implementation:**
- Replace `MoonPhaseIcon` Canvas drawing in `HomeScreen.kt` with image
- Create dedicated moon phase calendar screen (new feature)
- Show in Profile Detail for birth moon phase
- Display in Compatibility Detail for lunar compatibility

**Reference:** `docs/MoonPhaseVisualization.md` for existing implementation

---

### 10. Spiritual Symbols (6 images) - PRIORITY: MEDIUM
**Purpose:** Decorative elements, section dividers, spiritual accents

| Image # | Symbol | Usage |
|---------|--------|-------|
| 1 | Om/Aum | Meditation sections, spiritual content |
| 2 | Lotus Flower | Growth/enlightenment indicators |
| 3 | Hamsa Hand | Protection, blessings |
| 4 | Ankh | Life force, vitality indicators |
| 5 | Tree of Life | Connection, growth sections |
| 6 | Mandala | Meditation, centering content |

**Implementation:**
- Section dividers between major content blocks
- Feature headers in Profile Detail
- Decorative accents in Onboarding screens
- Loading state backgrounds

---

### 11. UI States (9 images) - PRIORITY: HIGH
**Purpose:** Loading, error, success, empty states

| Image # | State | Usage |
|---------|-------|-------|
| 1 | Loading - Cosmic | Generic loading indicator |
| 2 | Loading - Analyzing | Compatibility analysis in progress |
| 3 | Loading - Enriching | AI enrichment in progress |
| 4 | Success - Sparkle | Successful save/creation |
| 5 | Success - Celebration | Major achievement (profile complete) |
| 6 | Error - Gentle | Minor errors (validation) |
| 7 | Error - Alert | Critical errors (connection failed) |
| 8 | Empty State - Profiles | No profiles yet |
| 9 | Empty State - Results | No results found |

**Implementation:**
- Integrate with `core/ui/components/ErrorComponents.kt`
- Replace `ShimmerEffects.kt` loading states with animated images
- Update `EmptyProfileLibrary` component in `ProfileLibraryScreen.kt`

**Current State:** Using shimmer effects and basic error icons - upgrade to beautiful image-based states

---

## Screen-by-Screen Integration Plan

### 1. Splash Screen
**File:** `/app/src/main/java/com/spiritatlas/app/SplashScreen.kt`

**Current Elements:**
- Canvas-drawn logo with sacred geometry
- Animated starfield background
- Gradient text for app name

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| App Icon #2 | 1 | Replace Canvas logo with high-quality image |
| Background #1 | 1 | Cosmic nebula background layer |
| Sacred Geometry #4 | 1 | Seed of Life animation during load |

**Implementation:**
```kotlin
// Replace SpiritAtlasLogo composable
@Composable
fun SpiritAtlasLogo() {
    Image(
        painter = painterResource(R.drawable.splash_logo),
        contentDescription = "SpiritAtlas Logo",
        modifier = Modifier.size(180.dp)
    )
}

// Add background layer
Box {
    Image(
        painter = painterResource(R.drawable.background_cosmic_nebula),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.3f
    )
    // Existing starfield on top
    StarField()
}
```

---

### 2. Home Screen
**File:** `/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt`

**Current Elements:**
- Starfield background
- Daily insights card
- Profile quick access
- Transit alerts
- Compatibility quick check
- Spiritual weather widget
- Featured content

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #2 | 1 | Mystical space background behind starfield |
| App Icons #3-9 | 7 | Feature section headers (Profile, Compatibility, etc.) |
| Moon Phase #(current) | 1 | Replace Canvas moon with actual moon phase image |
| Zodiac #(current) | 1 | Current astrological sign in header |
| Buttons #1, #3, #11 | 3 | "Create Profile" FAB, Settings, Info buttons |
| Chakra #(active today) | 1 | Display active chakra for the day in Daily Insights |
| Element #5 | 1 | Spiritual weather visualization |

**Key Changes:**
- Replace `MoonPhaseIcon` Canvas drawing with image
- Add zodiac icon for current sun sign
- Show active chakra in Daily Insights
- Use feature icons in section headers

---

### 3. Onboarding Screen
**File:** `/feature/onboarding/src/main/java/com/spiritatlas/feature/onboarding/OnboardingScreen.kt`

**Current Elements:**
- 3-4 page horizontal pager
- Gradient backgrounds (different per page)
- Floating mystical elements
- Page indicators

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #8 (4 images) | 4 | One unique background per onboarding page |
| Spiritual Symbols #1-4 | 4 | Hero illustration for each page |
| Buttons #1, #2 | 2 | "Next" and "Skip" buttons |

**Onboarding Pages:**
1. **Welcome Page** - Background: Cosmic void, Symbol: Om
2. **Profile Creation** - Background: Sacred geometry, Symbol: Lotus
3. **Features Overview** - Background: Constellation map, Symbol: Tree of Life
4. **Get Started** - Background: Celestial bodies, Symbol: Mandala

---

### 4. Profile Library Screen
**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileLibraryScreen.kt`

**Current Elements:**
- Sacred geometry background
- Profile grid/list with cards
- Quick actions section
- Search functionality
- Selection mode for comparison

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #3 | 1 | Sacred geometry mandala background |
| Avatars #1-5 | 5 | Default avatar options for profiles |
| Avatar Frames #6-10 | 5 | Profile card avatar frames |
| Zodiac #(per profile) | Variable | Show zodiac sign on each profile card |
| Buttons #1, #3, #4, #6, #9 | 5 | Create, Add, Compare, Share, Delete |
| Empty State #8 | 1 | "No profiles yet" state |

**Key Changes:**
- Display avatar with frame on each `EnhancedModernProfileCard`
- Show zodiac icon badge on profile cards
- Use Empty State image when no profiles exist

---

### 5. Profile Creation/Edit Screen
**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`

**Current Elements:**
- Multi-section form (Core Identity, Additional Names, etc.)
- Profile section tabs with icons
- Date/time picker
- Save/clear actions

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #4 | 1 | Calming meditation background |
| Avatars #1-5 | 5 | Avatar selection UI (new) |
| Buttons #1, #2, #8 | 3 | Save, Cancel, Edit buttons |
| App Icons #4, #6, #7, #8, #9, #10 | 6 | Section tab icons (Profile, Astrology, Numerology, etc.) |

**New Feature:** Avatar selection
```kotlin
// Add to CoreIdentitySection
AvatarSelector(
    selectedAvatar = profile.avatarId,
    onAvatarSelected = { avatarId ->
        onProfileUpdate(profile.copy(avatarId = avatarId))
    }
)
```

---

### 6. Profile Detail Screen
**File:** Multiple profile-related screens

**Current Elements:**
- Profile data display
- Numerology calculations
- Astrological chart
- Ayurveda dosha
- Human Design elements
- Chakra information

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #5 | 1 | Personalized based on user's zodiac/element |
| Avatar + Frame | 1 | User's selected avatar with frame |
| Zodiac #(Sun/Moon/Rising) | 3 | Display three major zodiac positions |
| Chakras #1-7 | 7 | Chakra energy visualization |
| Elements #1-5 | 5 | Ayurvedic dosha/elemental balance |
| Sacred Geometry #6 | 1 | Golden Spiral decorative element |
| Buttons #6, #8, #10 | 3 | Share, Edit, Enrich buttons |

**Sections to Enhance:**
- **Astrological Section:** Show zodiac images for Sun/Moon/Rising
- **Chakra Section:** Display all 7 chakra images with activation states
- **Ayurveda Section:** Show dominant element images
- **Background:** Dynamic based on user's sun sign element

---

### 7. Profile Comparison Screen
**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileComparisonScreen.kt`

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Avatar + Frame | 2 | Both profiles being compared |
| Zodiac (both profiles) | 6 | Sun/Moon/Rising for each profile |
| Elements (both profiles) | Variable | Elemental compatibility |
| Sacred Geometry #5 | 1 | Vesica Piscis showing connection |
| Buttons #4 | 1 | "Full Compatibility Analysis" button |

---

### 8. Compatibility Screen
**File:** `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityScreen.kt`

**Current Elements:**
- Cosmic connection background
- Profile selection
- Compatibility score display

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #6 | 1 | Enhanced cosmic threads background |
| Buttons #1, #5 | 2 | "Analyze", primary CTAs |
| Loading #2 | 1 | "Analyzing compatibility" loading state |

---

### 9. Compatibility Detail Screen
**File:** `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt`

**Current Elements:**
- Hero section with profiles
- 12-dimension radar chart
- Synastry highlights
- Numerology match
- Astrological synastry
- Human Design connection
- Ayurvedic balance
- Tantric connection (gated)
- Action plans

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #7 | 1 | Deep space constellation background |
| Avatar + Frame | 2 | Both profiles in hero section |
| Zodiac (synastry) | Variable | Astrological aspect visualization |
| Chakras #1-7 | 7 | Energetic harmony section |
| Elements #1-5 | 5 | Ayurvedic balance visualization |
| Sacred Geometry #1 | 1 | Flower of Life background layer |
| Spiritual Symbol #3 | 1 | Hamsa hand for blessings section |
| Buttons #6, #7 | 2 | Share, Save/Favorite |

**Key Sections:**
- **Hero:** Show both avatars with frames
- **Astrological Synastry:** Display zodiac signs with aspect lines
- **Chakra Compatibility:** Show all 7 chakras for both profiles with harmony indicators
- **Elemental Balance:** Show elemental images with balance scales

---

### 10. Enrichment Result Screen
**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/EnrichmentResultScreen.kt`

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #9 | 1 | Mystical enrichment background |
| Success #5 | 1 | Celebration image for successful enrichment |
| Loading #3 | 1 | "Enriching profile" loading state |
| Spiritual Symbol #2 | 1 | Lotus flower for growth/enlightenment |

---

### 11. Consent & Settings Screens
**Files:**
- `/feature/consent/src/main/java/com/spiritatlas/feature/consent/ConsentScreen.kt`
- `/feature/settings/src/main/java/com/spiritatlas/feature/settings/SettingsScreen.kt`

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #10-11 | 2 | Subtle spiritual textures |
| Buttons #1, #2, #8 | 3 | Save, Cancel, Edit settings |
| App Icon #11 | 1 | Settings feature icon |

---

### 12. Tantric Content Screen
**File:** `/feature/tantric/src/main/java/com/spiritatlas/feature/tantric/TantricContentScreen.kt`

**Image Integration:**
| Image Category | Count | Usage |
|----------------|-------|-------|
| Background #12 | 1 | Sensual, sacred tantric background |
| Sacred Geometry #3 | 1 | Sri Yantra for tantric sections |
| Spiritual Symbol #2, #6 | 2 | Lotus and Mandala for sacred intimacy |

---

## Component-Level Integration

### Core UI Components to Update

#### 1. Button System
**File:** `core/ui/components/InteractiveButton.kt`, `ModernComponents.kt`

**Current:** Material3 buttons with gradients
**Upgrade:** Image-backed buttons with 12 unique designs

```kotlin
@Composable
fun SpiritualImageButton(
    imageType: ButtonImageType,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Image(
            painter = painterResource(getButtonImageResource(imageType)),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )
        Text(text, modifier = Modifier.align(Alignment.Center))
    }
}
```

#### 2. Avatar System (NEW)
**File:** `core/ui/components/AvatarComponents.kt` (create new)

```kotlin
@Composable
fun SpiritualAvatar(
    avatarId: Int,
    frameId: Int = 1,
    size: Dp = 80.dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(size)) {
        // Avatar image
        Image(
            painter = painterResource(getAvatarResource(avatarId)),
            contentDescription = "Avatar",
            modifier = Modifier.fillMaxSize().clip(CircleShape)
        )
        // Frame overlay
        Image(
            painter = painterResource(getFrameResource(frameId)),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AvatarSelector(
    selectedAvatarId: Int?,
    onAvatarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(5) { avatarId ->
            SpiritualAvatar(
                avatarId = avatarId + 1,
                frameId = 1,
                size = 60.dp,
                modifier = Modifier
                    .clickable { onAvatarSelected(avatarId + 1) }
                    .border(
                        width = if (selectedAvatarId == avatarId + 1) 3.dp else 0.dp,
                        color = SpiritualPurple,
                        shape = CircleShape
                    )
            )
        }
    }
}
```

#### 3. Zodiac Icon Component
**File:** `core/ui/components/SpiritualIcons.kt` (update existing)

```kotlin
@Composable
fun ZodiacIcon(
    sign: ZodiacSign,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(getZodiacImageResource(sign)),
        contentDescription = sign.name,
        modifier = modifier.size(size)
    )
}
```

#### 4. Chakra Visualization Component (NEW)
**File:** `core/ui/components/ChakraComponents.kt` (create new)

```kotlin
@Composable
fun ChakraVisualization(
    chakras: List<ChakraState>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        chakras.forEachIndexed { index, chakraState ->
            ChakraRow(
                chakraNumber = index + 1,
                activation = chakraState.activation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ChakraRow(
    chakraNumber: Int,
    activation: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(getChakraImageResource(chakraNumber)),
            contentDescription = getChakraName(chakraNumber),
            modifier = Modifier.size(48.dp),
            alpha = 0.3f + (activation * 0.7f) // Dim if not active
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(getChakraName(chakraNumber), style = MaterialTheme.typography.bodyLarge)
            LinearProgressIndicator(
                progress = { activation },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = getChakraColor(chakraNumber)
            )
        }
    }
}
```

#### 5. Moon Phase Component
**File:** `feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt` (update)

```kotlin
@Composable
fun MoonPhaseIcon(phase: MoonPhase) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(getMoonPhaseImageResource(phase)),
            contentDescription = phase.name,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = phase.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
```

#### 6. Element Icon Component (NEW)
**File:** `core/ui/components/ElementComponents.kt` (create new)

```kotlin
@Composable
fun ElementIcon(
    element: Element,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(getElementImageResource(element)),
        contentDescription = element.name,
        modifier = modifier.size(size)
    )
}

enum class Element {
    FIRE, WATER, EARTH, AIR, ETHER
}
```

#### 7. Sacred Geometry Background Layers
**File:** `core/ui/components/CosmicBackgrounds.kt` (update)

```kotlin
@Composable
fun SacredGeometryBackground(
    geometryType: SacredGeometryType = SacredGeometryType.FLOWER_OF_LIFE,
    alpha: Float = 0.1f,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(getGeometryImageResource(geometryType)),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = alpha
        )
        content()
    }
}

enum class SacredGeometryType {
    FLOWER_OF_LIFE,
    METATRONS_CUBE,
    SRI_YANTRA,
    SEED_OF_LIFE,
    VESICA_PISCIS,
    GOLDEN_SPIRAL,
    PLATONIC_SOLIDS,
    TORUS_FIELD
}
```

#### 8. Enhanced Loading States
**File:** `core/ui/components/ErrorComponents.kt` (update)

```kotlin
@Composable
fun SpiritualLoadingState(
    loadingType: LoadingType = LoadingType.COSMIC,
    message: String = "Loading...",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Arrangement.Center
    ) {
        Image(
            painter = painterResource(getLoadingImageResource(loadingType)),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(message, style = MaterialTheme.typography.bodyLarge)
    }
}

enum class LoadingType {
    COSMIC,
    ANALYZING,
    ENRICHING
}
```

---

## Implementation Checklist

### Phase 1: Critical Assets (Week 1)
**Goal:** Replace key visual elements that users see first

- [ ] **Launcher Icon** - App Icon #1
  - Generate all density variations (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
  - Update `AndroidManifest.xml`
  - Test on multiple device sizes

- [ ] **Splash Screen Logo** - App Icon #2
  - Replace Canvas drawing in `SplashScreen.kt`
  - Add cosmic background (Background #1)
  - Ensure animation timings work with image

- [ ] **Primary CTA Button** - Button #1
  - Create `SpiritualImageButton` component
  - Replace all primary buttons app-wide
  - Test interaction states (pressed, disabled)

- [ ] **Screen Backgrounds** - Backgrounds #1-3
  - Splash, Home, Profile Library backgrounds
  - Ensure proper layering with existing animations
  - Optimize image sizes for performance

- [ ] **Zodiac Icons** - All 12
  - Replace Canvas-drawn icons in `SpiritualIcons.kt`
  - Update Profile Detail display
  - Add to Home screen header

### Phase 2: Core Features (Week 2)
**Goal:** Enhance profile and compatibility screens

- [ ] **Avatar System**
  - Create `AvatarComponents.kt`
  - Implement avatar selection in Profile Creation
  - Update Profile Library cards
  - Add avatar frames

- [ ] **Chakra Visualization**
  - Create `ChakraComponents.kt`
  - Add all 7 chakra images
  - Integrate into Profile Detail
  - Add to Compatibility Detail

- [ ] **Element Icons**
  - Create `ElementComponents.kt`
  - Add all 5 element images
  - Integrate Ayurveda section
  - Update Spiritual Weather widget

- [ ] **Moon Phase Images**
  - Replace Canvas drawing in Home screen
  - Add all 8 moon phase images
  - Create moon phase calendar feature (optional)

- [ ] **Remaining Buttons** - Buttons #2-12
  - Implement all button types
  - Update all screens with image-backed buttons
  - Ensure consistent sizing and spacing

### Phase 3: Visual Polish (Week 3)
**Goal:** Add depth with backgrounds and sacred geometry

- [ ] **Sacred Geometry Layers**
  - Update `SacredGeometryBackground` component
  - Add all 8 sacred geometry images
  - Layer behind Compatibility Detail
  - Add to Profile Creation

- [ ] **Remaining Backgrounds** - Backgrounds #4-12
  - Profile Creation, Detail, Compatibility, etc.
  - Onboarding page backgrounds (4 unique)
  - Settings, Consent, Tantric backgrounds
  - Optimize and compress for performance

- [ ] **Spiritual Symbols**
  - Add all 6 symbol images
  - Use as section dividers
  - Add to Onboarding illustrations
  - Integrate into Tantric content

- [ ] **Feature Section Icons** - App Icons #3-10
  - Add to Home screen feature headers
  - Add to Profile section tabs
  - Use in Settings categories

### Phase 4: UX Enhancement (Week 4)
**Goal:** Improve feedback and empty states

- [ ] **Loading States** - UI States #1-3
  - Replace shimmer effects with image-based loading
  - Add "Analyzing" animation to Compatibility
  - Add "Enriching" animation to AI enrichment

- [ ] **Success States** - UI States #4-5
  - Success animations for save actions
  - Celebration for profile completion
  - Achievement unlocks

- [ ] **Error States** - UI States #6-7
  - Gentle error images for validation
  - Alert images for critical errors
  - Update `ErrorComponents.kt`

- [ ] **Empty States** - UI States #8-9
  - "No profiles yet" image for Profile Library
  - "No results" for searches
  - Onboarding prompts for new users

- [ ] **Avatar Frames** - Avatars #6-10
  - Add frame selection option
  - Different frames for profile tiers
  - Premium frames for enriched profiles

### Phase 5: Testing & Optimization
**Goal:** Ensure performance and visual quality

- [ ] **Image Optimization**
  - Compress all images without quality loss
  - Use WebP format for Android
  - Test loading times on low-end devices
  - Implement lazy loading where appropriate

- [ ] **Density Variations**
  - Ensure all images have proper density folders
  - Test on mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi
  - Verify no blurry or pixelated images

- [ ] **Dark Mode Compatibility**
  - Test all images in dark theme
  - Adjust alpha/blending if needed
  - Ensure readability over backgrounds

- [ ] **Animation Integration**
  - Ensure images work with existing animations
  - No jank or stuttering
  - Smooth transitions between states

- [ ] **Accessibility**
  - Add content descriptions to all images
  - Ensure sufficient contrast
  - Test with TalkBack

---

## Resource Organization

### Directory Structure
```
app/src/main/res/
├── drawable/
│   ├── button_primary.xml (or .webp)
│   ├── button_secondary.xml
│   ├── ... (all 12 buttons)
│   ├── background_splash.webp
│   ├── background_home.webp
│   ├── ... (all 12 backgrounds)
│   ├── zodiac_aries.webp
│   ├── ... (all 12 zodiac signs)
│   ├── chakra_root.webp
│   ├── ... (all 7 chakras)
│   ├── element_fire.webp
│   ├── ... (all 5 elements)
│   ├── moon_new.webp
│   ├── ... (all 8 moon phases)
│   ├── geometry_flower_of_life.webp
│   ├── ... (all 8 sacred geometry)
│   ├── symbol_om.webp
│   ├── ... (all 6 spiritual symbols)
│   ├── avatar_1.webp
│   ├── ... (all 5 avatars)
│   ├── frame_1.webp
│   ├── ... (all 5 frames)
│   ├── loading_cosmic.webp
│   ├── ... (all 9 UI states)
│
├── mipmap-mdpi/
│   └── ic_launcher.webp
├── mipmap-hdpi/
│   └── ic_launcher.webp
├── mipmap-xhdpi/
│   └── ic_launcher.webp
├── mipmap-xxhdpi/
│   └── ic_launcher.webp
└── mipmap-xxxhdpi/
    └── ic_launcher.webp
```

### Naming Convention
- **Buttons:** `button_[type].webp` (e.g., `button_primary.webp`)
- **Backgrounds:** `background_[screen].webp` (e.g., `background_splash.webp`)
- **Zodiac:** `zodiac_[sign].webp` (e.g., `zodiac_aries.webp`)
- **Chakras:** `chakra_[name].webp` (e.g., `chakra_root.webp`)
- **Elements:** `element_[name].webp` (e.g., `element_fire.webp`)
- **Moon Phases:** `moon_[phase].webp` (e.g., `moon_new.webp`)
- **Sacred Geometry:** `geometry_[name].webp` (e.g., `geometry_flower_of_life.webp`)
- **Spiritual Symbols:** `symbol_[name].webp` (e.g., `symbol_om.webp`)
- **Avatars:** `avatar_[number].webp` (e.g., `avatar_1.webp`)
- **Frames:** `frame_[number].webp` (e.g., `frame_1.webp`)
- **UI States:** `state_[type]_[state].webp` (e.g., `state_loading_cosmic.webp`)

### Resource Helper File
**File:** `core/ui/src/main/java/com/spiritatlas/core/ui/resources/SpiritualResources.kt` (create new)

```kotlin
package com.spiritatlas.core.ui.resources

import androidx.annotation.DrawableRes
import com.spiritatlas.core.ui.R

object SpiritualResources {

    // Button Images
    @DrawableRes
    fun getButtonResource(type: ButtonImageType): Int = when (type) {
        ButtonImageType.PRIMARY -> R.drawable.button_primary
        ButtonImageType.SECONDARY -> R.drawable.button_secondary
        ButtonImageType.ADD -> R.drawable.button_add
        // ... etc
    }

    // Zodiac Images
    @DrawableRes
    fun getZodiacResource(sign: ZodiacSign): Int = when (sign) {
        ZodiacSign.ARIES -> R.drawable.zodiac_aries
        ZodiacSign.TAURUS -> R.drawable.zodiac_taurus
        // ... etc
    }

    // Chakra Images
    @DrawableRes
    fun getChakraResource(chakraNumber: Int): Int = when (chakraNumber) {
        1 -> R.drawable.chakra_root
        2 -> R.drawable.chakra_sacral
        // ... etc
        else -> R.drawable.chakra_root
    }

    // Element Images
    @DrawableRes
    fun getElementResource(element: Element): Int = when (element) {
        Element.FIRE -> R.drawable.element_fire
        Element.WATER -> R.drawable.element_water
        // ... etc
    }

    // Moon Phase Images
    @DrawableRes
    fun getMoonPhaseResource(phase: MoonPhase): Int = when (phase.name) {
        "New Moon" -> R.drawable.moon_new
        "Waxing Crescent" -> R.drawable.moon_waxing_crescent
        // ... etc
        else -> R.drawable.moon_new
    }

    // Background Images
    @DrawableRes
    fun getBackgroundResource(screen: Screen): Int = when (screen) {
        Screen.SPLASH -> R.drawable.background_splash
        Screen.HOME -> R.drawable.background_home
        // ... etc
    }

    // Sacred Geometry
    @DrawableRes
    fun getGeometryResource(type: SacredGeometryType): Int = when (type) {
        SacredGeometryType.FLOWER_OF_LIFE -> R.drawable.geometry_flower_of_life
        // ... etc
    }

    // Avatar & Frames
    @DrawableRes
    fun getAvatarResource(avatarId: Int): Int = when (avatarId) {
        1 -> R.drawable.avatar_1
        2 -> R.drawable.avatar_2
        // ... etc
        else -> R.drawable.avatar_1
    }

    @DrawableRes
    fun getFrameResource(frameId: Int): Int = when (frameId) {
        1 -> R.drawable.frame_1
        2 -> R.drawable.frame_2
        // ... etc
        else -> R.drawable.frame_1
    }

    // UI States
    @DrawableRes
    fun getLoadingResource(type: LoadingType): Int = when (type) {
        LoadingType.COSMIC -> R.drawable.state_loading_cosmic
        LoadingType.ANALYZING -> R.drawable.state_loading_analyzing
        LoadingType.ENRICHING -> R.drawable.state_loading_enriching
    }

    @DrawableRes
    fun getSuccessResource(type: SuccessType): Int = when (type) {
        SuccessType.SPARKLE -> R.drawable.state_success_sparkle
        SuccessType.CELEBRATION -> R.drawable.state_success_celebration
    }
}

enum class ButtonImageType {
    PRIMARY, SECONDARY, ADD, COMPARE, ANALYZE, SHARE, SAVE, EDIT, DELETE, ENRICH, SETTINGS, INFO
}

enum class Screen {
    SPLASH, HOME, PROFILE_LIBRARY, PROFILE_CREATION, PROFILE_DETAIL,
    COMPATIBILITY, COMPATIBILITY_DETAIL, ONBOARDING, SETTINGS, CONSENT, TANTRIC
}

enum class LoadingType {
    COSMIC, ANALYZING, ENRICHING
}

enum class SuccessType {
    SPARKLE, CELEBRATION
}
```

---

## Gaps & Additional Image Needs

### Images We Have Covered: 99/99 ✓

### Additional Images Recommended (Beyond 99)

#### 1. **Planetary Icons** (7 additional images)
**Priority:** Medium
**Purpose:** Complete astrological visualization

- Sun
- Moon (separate from moon phases)
- Mercury
- Venus
- Mars
- Jupiter
- Saturn

**Usage:**
- Birth chart in Profile Detail
- Transit alerts in Home screen
- Astrological synastry in Compatibility Detail

**Current State:** Existing `SpiritualIcons.kt` has Canvas-drawn planetary symbols

---

#### 2. **Dosha Icons** (3 additional images)
**Priority:** Medium
**Purpose:** Ayurvedic constitution visualization

- Vata (Air/Ether)
- Pitta (Fire/Water)
- Kapha (Water/Earth)

**Usage:**
- Profile Detail Ayurveda section
- Compatibility Detail Ayurvedic balance

**Workaround:** Can use Element images as proxies (already have Fire, Water, Earth, Air, Ether)

---

#### 3. **Human Design Type Icons** (5 additional images)
**Priority:** Low
**Purpose:** Human Design visualization

- Manifestor
- Generator
- Manifesting Generator
- Projector
- Reflector

**Usage:**
- Profile Detail Human Design section
- Compatibility Detail Human Design connection

**Current State:** Not currently displayed, text-only

---

#### 4. **Numerology Number Icons** (9 additional images)
**Priority:** Low
**Purpose:** Life path number, destiny number visualization

- Numbers 1-9 with spiritual designs

**Usage:**
- Profile Detail Numerology section
- Compatibility Detail Numerology match

**Workaround:** Can use gradient text instead

---

#### 5. **Aspect Glyphs** (12 additional images)
**Priority:** Low
**Purpose:** Astrological aspect lines in synastry chart

- Conjunction
- Sextile
- Square
- Trine
- Opposition
- Quincunx
- Semi-sextile
- Semi-square
- Sesquiquadrate
- Quintile
- Bi-quintile
- Parallel

**Usage:**
- Compatibility Detail astrological synastry visualization

**Current State:** Can use lines/shapes from Canvas

---

#### 6. **Onboarding Illustrations** (4 images - COVERED)
**Status:** Already allocated from Backgrounds #8 (4 images)

---

#### 7. **Achievement/Badge Icons** (Optional)
**Priority:** Low
**Purpose:** Gamification, profile completion milestones

**Usage:**
- Profile completion badges
- Feature unlock indicators
- Sharing achievements

**Current State:** Not planned for MVP

---

### Summary of Additional Needs

| Category | Count | Priority | Status |
|----------|-------|----------|--------|
| Planetary Icons | 7 | Medium | Use Canvas drawings as fallback |
| Dosha Icons | 3 | Medium | Use Element images as proxy |
| Human Design Types | 5 | Low | Text-only for now |
| Numerology Numbers | 9 | Low | Use gradient text |
| Aspect Glyphs | 12 | Low | Use Canvas shapes |
| Achievement Badges | Variable | Low | Future feature |

**Total Additional Recommended:** ~36 images (but not critical for MVP)

---

## Performance Considerations

### Image Optimization Guidelines

1. **Format:** Use WebP for Android (better compression, transparency support)
2. **Sizes:**
   - Backgrounds: Max 1920x1080, compressed to <500KB each
   - Icons: 512x512 for high-res, scaled to 24dp/48dp in code
   - Avatars: 512x512 base, scaled to display size
   - Buttons: Match design specs, typically 320x100 for text buttons

3. **Density Buckets:**
   - xxhdpi: Primary target (most common)
   - xhdpi: Fallback for mid-range devices
   - xxxhdpi: High-end flagship devices
   - hdpi/mdpi: Optional for very old devices

4. **Lazy Loading:**
   - Load backgrounds asynchronously with Coil
   - Preload critical images (splash, launcher icon)
   - Cache decoded bitmaps

5. **Memory Management:**
   - Use `BitmapFactory.Options.inSampleSize` for large backgrounds
   - Implement image caching with LruCache
   - Release bitmaps when screens are destroyed

### Implementation Example (Coil)
```kotlin
// Add to build.gradle.kts
implementation("io.coil-kt:coil-compose:2.5.0")

// In composable
Image(
    painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.drawable.background_home)
            .crossfade(true)
            .memoryCacheKey("background_home")
            .diskCacheKey("background_home")
            .build()
    ),
    contentDescription = null,
    modifier = Modifier.fillMaxSize(),
    contentScale = ContentScale.Crop
)
```

---

## Next Steps

### Immediate Actions

1. **Generate Images:** Use FLUX 1.1 Pro to create all 99 images based on categories
2. **Organize Assets:** Place images in proper resource folders with naming convention
3. **Create Helper Classes:** Build `SpiritualResources.kt` and new component files
4. **Phase 1 Implementation:** Start with critical assets (launcher, splash, primary buttons, backgrounds)

### Design Specifications Needed

For FLUX image generation, specify:
- **Resolution:** 512x512 for icons, 1920x1080 for backgrounds
- **Style:** Mystical, cosmic, spiritual, high-quality digital art
- **Color Palette:** Match existing theme (purples, golds, blues - see `core/ui/theme/Color.kt`)
- **Transparency:** PNG with alpha for icons, frames, and overlays
- **Mood:** Ethereal, calming, powerful, sacred

### Quality Checklist Per Image

- [ ] Matches app's spiritual aesthetic
- [ ] High resolution (no pixelation)
- [ ] Proper transparency where needed
- [ ] Works in both light and dark themes
- [ ] Culturally respectful (especially for sacred symbols)
- [ ] Compressed for performance
- [ ] Multiple density variations generated

---

## Conclusion

This comprehensive strategy ensures all 99 generated images are meaningfully integrated throughout SpiritAtlas, creating a visually stunning app that rivals the best spiritual and astrology apps on the market. By following the phased implementation plan, the team can systematically upgrade the visual experience while maintaining app performance and user experience quality.

**Key Success Metrics:**
- All 99 images integrated and visible in app
- No screen without appropriate imagery
- Performance maintained (no lag or jank)
- User feedback positive on visual appeal
- App feels cohesive and professionally designed

**Estimated Implementation Time:** 4 weeks (with 1 developer)

---

**Document Version:** 1.0
**Last Updated:** December 9, 2025
**Author:** SpiritAtlas Development Team
