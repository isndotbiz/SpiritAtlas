# Mock Profile Quick Start

## 1-Minute Integration

### Step 1: Update SplashViewModel

**File**: `/app/src/main/java/com/spiritatlas/app/SplashViewModel.kt`

Add these changes:

```kotlin
// ADD IMPORT
import com.spiritatlas.data.mock.MockProfileInitializer

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mockProfileInitializer: MockProfileInitializer // ADD THIS PARAMETER
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
        initializeMockProfilesIfNeeded() // ADD THIS CALL
    }

    // ADD THIS METHOD
    private fun initializeMockProfilesIfNeeded() {
        viewModelScope.launch {
            val alreadyInitialized = sharedPreferences.getBoolean(
                MOCK_PROFILES_INITIALIZED_KEY,
                false
            )

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

### Step 2: Build and Run

```bash
./gradlew :app:assembleDebug
```

### Step 3: Verify

Check Logcat for:
```
D/MockProfileInitializer: Starting mock profile initialization...
D/MockProfileInitializer: Creating Sarah Chen profile...
D/MockProfileInitializer: Sarah Chen profile created successfully
D/MockProfileInitializer: Creating Marcus Rivera profile...
D/MockProfileInitializer: Marcus Rivera profile created successfully
D/MockProfileInitializer: Mock profile initialization complete. Created 2 new profiles.
```

## What You Get

### Sarah Chen Profile
- **36/36 fields populated**
- **Accuracy Level**: MAXIMUM
- Born: June 21, 1988 (Summer Solstice, Full Moon) in San Francisco
- Spiritual teacher archetype with Buddhist-Taoist heritage
- INFJ personality, secure attachment, feminine-core energy

### Marcus Rivera Profile
- **36/36 fields populated**
- **Accuracy Level**: MAXIMUM
- Born: December 15, 1985 (New Moon, winter) in Miami
- Entrepreneur archetype with Puerto Rican heritage
- ENTJ personality, anxious-preoccupied attachment, masculine-core energy

## Testing Compatibility

```kotlin
val sarah = userRepository.getProfileById(MockProfileInitializer.SARAH_CHEN_ID)
val marcus = userRepository.getProfileById(MockProfileInitializer.MARCUS_RIVERA_ID)

val compatibility = compatibilityEngine.analyze(sarah!!, marcus!!)
```

## Reset Profiles (For Testing)

Add to your ViewModel:

```kotlin
@Inject
lateinit var mockProfileInitializer: MockProfileInitializer

suspend fun resetMockProfiles() {
    mockProfileInitializer.clearMockProfiles()
    mockProfileInitializer.initializeMockProfiles()
}
```

---

**That's it!** Two comprehensive profiles ready for compatibility testing. ✨

See `MOCK_PROFILE_INTEGRATION_GUIDE.md` for detailed documentation.
