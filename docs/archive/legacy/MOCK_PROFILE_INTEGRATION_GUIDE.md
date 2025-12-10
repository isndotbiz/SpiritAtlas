# Mock Profile Initialization Guide

## Overview

The `MockProfileInitializer` class provides comprehensive mock data for SpiritAtlas development and testing. It pre-populates the database with 2 complete UserProfile objects containing all 36 fields from the UserProfile model.

## Mock Profiles

### Profile 1: Sarah Chen
- **Archetype**: Feminine-core spiritual teacher and healer
- **Birth**: June 21, 1988 (Summer Solstice) at 3:33 AM in San Francisco, CA
- **Heritage**: Chinese-American (Buddhist-Taoist traditions)
- **Personality**: INFJ, Secure attachment, Quality Time love language
- **Energy**: Feminine-core tantric, Spiritual-focused intimacy
- **Purpose**: Guide others through spiritual awakening using ancient wisdom

**All 36 Fields Populated**:
- Full birth details with coordinates (San Francisco: 37.7749°N, 122.4194°W)
- Complete family lineage (mother: Lin Chen, father: David Chen)
- Physical attributes (165cm, A+ blood, right-handed, dark brown eyes)
- Timing cycles (first breath, conception date, first cry, first steps)
- Environmental factors (Full Moon, Summer Solstice energy, UCSF Medical Center)
- Compatibility metrics (INFJ, secure attachment, shared practices)
- AI enrichment report (comprehensive spiritual analysis)

### Profile 2: Marcus Rivera
- **Archetype**: Masculine-core entrepreneur and transformational leader
- **Birth**: December 15, 1985 at 11:47 PM in Miami, FL
- **Heritage**: Puerto Rican (Taíno, Spanish, West African - Catholic-Santeria)
- **Personality**: ENTJ, Anxious-preoccupied attachment, Acts of Service love language
- **Energy**: Masculine-core tantric, Physical-focused intimacy
- **Purpose**: Build transformative businesses that empower communities

**All 36 Fields Populated**:
- Full birth details with coordinates (Miami: 25.7617°N, 80.1918°W)
- Complete family lineage (mother: Isabella Maria Rivera, father: Carlos Rivera Santos)
- Physical attributes (183cm, O+ blood, left-handed, hazel-brown eyes)
- Timing cycles (quick first breath, conception date, early first steps)
- Environmental factors (New Moon, tropical storm, Winter Solstice approaching)
- Compatibility metrics (ENTJ, anxious-preoccupied, individual paths)
- AI enrichment report (comprehensive spiritual analysis)

## Integration Options

### Option 1: Automatic Initialization in SplashViewModel (RECOMMENDED)

Automatically initialize mock profiles on first app launch.

**File**: `/app/src/main/java/com/spiritatlas/app/SplashViewModel.kt`

```kotlin
package com.spiritatlas.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spiritatlas.data.mock.MockProfileInitializer // ADD THIS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mockProfileInitializer: MockProfileInitializer // ADD THIS
) : ViewModel() {

    companion object {
        private const val SPLASH_DURATION = 2800L
        private const val ANIMATION_STEP = 16L
        private const val ONBOARDING_PREF_KEY = "onboarding_completed"
        private const val MOCK_PROFILES_INITIALIZED_KEY = "mock_profiles_initialized" // ADD THIS
        private const val PREFS_NAME = "spirit_atlas_prefs"
    }

    // ... existing fields ...

    init {
        startAnimation()
        initializeMockProfilesIfNeeded() // ADD THIS
    }

    // ADD THIS METHOD
    /**
     * Initialize mock profiles on first launch for development/testing
     */
    private fun initializeMockProfilesIfNeeded() {
        viewModelScope.launch {
            val alreadyInitialized = sharedPreferences.getBoolean(MOCK_PROFILES_INITIALIZED_KEY, false)

            if (!alreadyInitialized) {
                val createdCount = mockProfileInitializer.initializeMockProfiles()

                if (createdCount > 0) {
                    sharedPreferences.edit()
                        .putBoolean(MOCK_PROFILES_INITIALIZED_KEY, true)
                        .apply()
                }
            }
        }
    }

    // ... rest of existing code ...
}
```

**Advantages**:
- Runs automatically on first app launch
- Non-blocking (runs in background during splash)
- One-time initialization (won't duplicate profiles)
- Clean separation of concerns

### Option 2: Manual Initialization via Settings Screen

Add a developer option to manually initialize/reset mock profiles.

**Create**: `/feature/settings/src/main/java/com/spiritatlas/feature/settings/DeveloperSettingsScreen.kt`

```kotlin
@Composable
fun DeveloperSettingsScreen(
    mockProfileInitializer: MockProfileInitializer = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var initStatus by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            scope.launch {
                val count = mockProfileInitializer.initializeMockProfiles()
                initStatus = "Created $count mock profiles"
            }
        }) {
            Text("Initialize Mock Profiles")
        }

        Button(onClick = {
            scope.launch {
                mockProfileInitializer.clearMockProfiles()
                initStatus = "Mock profiles cleared"
            }
        }) {
            Text("Clear Mock Profiles")
        }

        if (initStatus.isNotEmpty()) {
            Text(initStatus, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
```

**Advantages**:
- Full control over when profiles are created
- Easy to reset during testing
- Visible to developers
- Can be hidden in production builds

### Option 3: Test-Only Initialization

Use mock profiles only in unit/integration tests.

**Example Test**:
```kotlin
@Test
fun testCompatibilityWithMockProfiles() = runTest {
    // Initialize mock profiles
    mockProfileInitializer.initializeMockProfiles()

    // Get profiles
    val profiles = userRepository.getAllProfiles()
    assertEquals(2, profiles.size)

    val sarah = profiles.find { it.id == MockProfileInitializer.SARAH_CHEN_ID }
    val marcus = profiles.find { it.id == MockProfileInitializer.MARCUS_RIVERA_ID }

    assertNotNull(sarah)
    assertNotNull(marcus)

    // Run compatibility analysis
    val compatibility = compatibilityEngine.analyze(sarah!!, marcus!!)
    assertTrue(compatibility.overallScore > 0.0)

    // Cleanup
    mockProfileInitializer.clearMockProfiles()
}
```

**Advantages**:
- No production impact
- Isolated test environment
- Reproducible test data

## Usage Examples

### Check if Mock Profiles Exist

```kotlin
@Inject
lateinit var mockProfileInitializer: MockProfileInitializer

// In ViewModel or Repository
suspend fun checkMockProfiles() {
    val (sarahExists, marcusExists) = mockProfileInitializer.mockProfilesExist()

    if (sarahExists && marcusExists) {
        Log.d("SpiritAtlas", "Both mock profiles are present")
    }
}
```

### Access Mock Profiles

```kotlin
@Inject
lateinit var userRepository: UserRepository

suspend fun loadMockProfiles() {
    val sarah = userRepository.getProfileById(MockProfileInitializer.SARAH_CHEN_ID)
    val marcus = userRepository.getProfileById(MockProfileInitializer.MARCUS_RIVERA_ID)

    sarah?.let { profile ->
        println("Sarah's birth place: ${profile.birthPlace?.city}")
        println("Completed fields: ${profile.profileCompletion.completedFields}/36")
        println("Accuracy level: ${profile.profileCompletion.accuracyLevel}")
    }
}
```

### Clear Mock Profiles

```kotlin
suspend fun resetMockData() {
    mockProfileInitializer.clearMockProfiles()
    println("Mock profiles removed from database")
}
```

## Field Coverage Verification

Both mock profiles contain all 36 UserProfile fields:

### System Fields (4)
- ✅ id
- ✅ profileName
- ✅ createdAt
- ✅ lastModified

### Core Identity (4)
- ✅ name
- ✅ displayName
- ✅ birthDateTime
- ✅ birthPlace

### Additional Names (4)
- ✅ middleName
- ✅ nickname
- ✅ spiritualName
- ✅ maidenName

### Family & Ancestry (4)
- ✅ motherName
- ✅ fatherName
- ✅ ancestry
- ✅ familyTradition

### Physical & Energetic (6)
- ✅ gender
- ✅ bloodType
- ✅ dominantHand
- ✅ eyeColor
- ✅ height
- ✅ birthWeight

### Timing & Cycles (3)
- ✅ firstBreath
- ✅ conceptionDate
- ✅ firstCry

### Environmental (4)
- ✅ weatherConditions
- ✅ moonPhase
- ✅ hospitalName
- ✅ seasonalEnergy

### Life Patterns (2)
- ✅ firstWord
- ✅ firstSteps

### Compatibility Fields (9)
- ✅ loveLanguage
- ✅ personalityType
- ✅ attachmentStyle
- ✅ sexualEnergy
- ✅ communicationStyle
- ✅ conflictResolution
- ✅ intimacyPreference
- ✅ spiritualConnection
- ✅ lifePurposeAlignment

### AI Enrichment (2)
- ✅ enrichmentResult
- ✅ enrichmentGeneratedAt

**Total: 36/36 fields populated** ✨

## Testing Compatibility Analysis

With both profiles fully populated, you can test:

1. **Numerology Compatibility**: Different name systems (Chaldean vs Pythagorean)
2. **Astrological Compatibility**: Different birth times, locations, moon phases
3. **Elemental Balance**: Fire/Water energies, Yin/Yang dynamics
4. **Relationship Patterns**: Secure vs Anxious-preoccupied attachment styles
5. **Communication Styles**: Emotional vs Direct, Collaborating vs Confronting
6. **Spiritual Alignment**: Shared practices vs Individual paths
7. **Love Languages**: Quality Time vs Acts of Service
8. **Intimacy Preferences**: Spiritual-focused vs Physical-focused

## BuildConfig Integration (Optional)

For automatic initialization in DEBUG builds only:

```kotlin
init {
    startAnimation()

    // Only initialize mock profiles in debug builds
    if (BuildConfig.DEBUG) {
        initializeMockProfilesIfNeeded()
    }
}
```

## Logging

The MockProfileInitializer provides detailed logging:

```
D/MockProfileInitializer: Starting mock profile initialization...
D/MockProfileInitializer: Creating Sarah Chen profile...
D/MockProfileInitializer: Sarah Chen profile created successfully
D/MockProfileInitializer: Creating Marcus Rivera profile...
D/MockProfileInitializer: Marcus Rivera profile created successfully
D/MockProfileInitializer: Mock profile initialization complete. Created 2 new profiles.
```

## Best Practices

1. **First Launch**: Initialize automatically in SplashViewModel
2. **Development**: Add manual controls in Settings screen
3. **Testing**: Use in unit/integration tests with cleanup
4. **Production**: Disable automatic initialization or use BuildConfig.DEBUG flag
5. **Cleanup**: Always call `clearMockProfiles()` after tests

## Troubleshooting

### Profiles Not Appearing
- Check Logcat for initialization logs
- Verify UserRepository is properly injected
- Ensure database migrations are up to date

### Duplicate Profiles
- MockProfileInitializer checks for existing IDs before creating
- Use `clearMockProfiles()` to remove and re-initialize

### Missing Fields
- All 36 fields are guaranteed to be populated
- Check `profileCompletion.completedFields` should equal 36
- Verify `accuracyLevel` is `MAXIMUM`

## File Locations

- **Initializer**: `/data/src/main/java/com/spiritatlas/data/mock/MockProfileInitializer.kt`
- **UserProfile Model**: `/domain/src/main/java/com/spiritatlas/domain/model/UserProfile.kt`
- **UserRepository**: `/domain/src/main/java/com/spiritatlas/domain/repository/UserRepository.kt`
- **Integration Point**: `/app/src/main/java/com/spiritatlas/app/SplashViewModel.kt`

---

**Generated for SpiritAtlas Development** ✨
