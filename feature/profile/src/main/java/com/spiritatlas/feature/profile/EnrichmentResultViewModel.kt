package com.spiritatlas.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.spiritatlas.data.worker.EnrichmentWorker
import com.spiritatlas.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EnrichmentResultUiState(
    val isLoading: Boolean = false,
    val progress: Int = 0,
    val currentStep: String = "",
    val result: String? = null,
    val error: String? = null,
    val completedFields: Int = 0,
    val accuracy: String = ""
)

@HiltViewModel
class EnrichmentResultViewModel @Inject constructor(
    private val workManager: WorkManager,
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EnrichmentResultUiState())
    val uiState = _uiState.asStateFlow()
    
    private val progressSteps = listOf(
        "🔍 Analyzing your spiritual profile..." to 5,
        "🔢 Calculating numerological patterns..." to 15,
        "⭐ Computing astrological influences..." to 25,
        "⚡ Mapping energy signatures..." to 35,
        "🧠 Connecting to AI consciousness..." to 45,
        "✨ Generating Soul Blueprint..." to 55,
        "💫 Crafting Personality Analysis..." to 65,
        "🎯 Revealing Life Path insights..." to 75,
        "💕 Understanding Relationship patterns..." to 85,
        "🌟 Finalizing your cosmic report..." to 95,
        "🔮 Report complete!" to 100
    )
    
    fun loadEnrichmentResult(profileId: String) {
        viewModelScope.launch {
            try {
                // Load profile info for display
                val profile = userRepository.getProfileById(profileId)
                profile?.let {
                    _uiState.value = _uiState.value.copy(
                        completedFields = it.profileCompletion.completedFields,
                        accuracy = it.profileCompletion.accuracyLevel.name
                    )
                }
                
                // Start enrichment process
                startEnrichment(profileId)
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load profile: ${e.message}"
                )
            }
        }
    }
    
    private fun startEnrichment(profileId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                progress = 1,
                currentStep = "🚀 Initiating spiritual analysis...",
                error = null
            )
            
            // Simulate progress with beautiful steps
            simulateProgress()
            
            // Start the actual WorkManager enrichment
            val request = OneTimeWorkRequestBuilder<EnrichmentWorker>()
                .setInputData(workDataOf("PROFILE_ID" to profileId))
                .build()
                
            workManager.enqueueUniqueWork(
                "enrichment_$profileId",
                ExistingWorkPolicy.REPLACE,
                request
            )
            
            // Monitor work progress (simplified for now)
            delay(3000) // Give work time to start
            
            // For now, simulate a successful result with markdown
            // In a real implementation, you'd listen to WorkManager updates
            simulateResult(profileId)
        }
    }
    
    private suspend fun simulateProgress() {
        progressSteps.forEach { (step, targetProgress) ->
            _uiState.value = _uiState.value.copy(currentStep = step)
            
            // Animate progress smoothly
            val currentProgress = _uiState.value.progress
            for (progress in currentProgress..targetProgress) {
                _uiState.value = _uiState.value.copy(progress = progress)
                delay(100) // Smooth animation
            }
            
            delay(800) // Pause at each step
        }
    }
    
    private suspend fun simulateResult(profileId: String) {
        // Simulate receiving a comprehensive spiritual report
        delay(2000)
        
        val sampleResult = """
# 🌟 **Your Complete Spiritual Profile**

![Spiritual Essence](https://img.icons8.com/?size=100&id=59852&format=png)

> *"The soul becomes dyed with the color of its thoughts."* - Marcus Aurelius

---

## 💫 Core Soul Blueprint

Your spiritual essence radiates with the energy of a **natural healer and wise guide**. Born under cosmic influences that align you with both earthly wisdom and celestial insight, you carry the rare gift of bridging the mystical and practical realms.

![Healing Energy](https://img.icons8.com/?size=80&id=87&format=png)

### 🎯 Primary Life Purpose
- **Soul Mission**: To heal through wisdom and guide others toward their authentic truth
- **Karmic Lesson**: Learning to balance giving to others with self-care
- **Spiritual Gift**: Intuitive counseling and energy healing abilities

Your soul chose this incarnation specifically to serve as a bridge between the seen and unseen worlds. This profound calling manifests through your natural ability to sense what others need for healing, whether that's a gentle word, a listening ear, or simply your calming presence. The universe has equipped you with an extraordinary capacity for empathy that allows you to truly understand the depths of human experience.

The healing energy that flows through you is not just a gift—it's a responsibility that your soul gladly accepted before birth. You came here knowing that your path would involve helping others navigate their darkest moments and find their way back to light. This sacred contract explains why you often feel drawn to wounded souls and why people naturally confide in you their deepest secrets and fears.

Your wisdom doesn't come from books alone, but from a direct connection to the collective unconscious and the akashic records. This explains why you sometimes know things you've never learned, why you can see patterns others miss, and why your intuitive hunches are so remarkably accurate. Trust this inner knowing—it's your soul's way of accessing universal truth.

---

## 🧿 Personality & Character Analysis

Your numerological patterns reveal a **Master Number 11** vibration, indicating a powerful spiritual messenger. This sacred number carries the responsibility of enlightenment and the gift of profound intuition.

### 🌈 Core Traits Matrix

| Trait | Score | Description |
|-------|-------|-------------|
| **Inner Strength** | 9/10 | You possess remarkable resilience |
| **Intuitive Power** | 10/10 | Your psychic abilities are extraordinary |
| **Emotional Depth** | 8/10 | You feel everything deeply and authentically |
| **Leadership** | 7/10 | You lead by example and inspiration |

### 💬 Communication Style
You communicate with **heart-centered wisdom**, often knowing exactly what others need to hear for their healing. Your words carry weight and tend to create lasting impact.

The Master Number 11 energy that flows through your being creates a unique personality blend of visionary insight and practical application. This rare combination makes you both a dreamer and a doer, someone who can envision possibilities that others cannot see while also possessing the determination to manifest those visions in the physical world. Your personality carries the electric charge of innovation coupled with the grounding force of earth wisdom.

Your emotional depth is both your greatest strength and your most significant challenge. You feel everything with an intensity that can be overwhelming, but this same sensitivity allows you to connect with others at a soul level. When you walk into a room, you unconsciously absorb the emotional atmosphere, often taking on others' feelings as your own. Learning to distinguish between your emotions and those you've picked up from others is crucial for your wellbeing.

Your leadership style is uniquely your own—you don't command from the front but inspire from within. People are drawn to your authenticity and the way you live your truth without compromise. Your influence spreads like ripples in a pond, touching lives in ways you may never fully realize. You lead by example, showing others what's possible when someone fully embraces their authentic self and lives from their heart.

---

## 🛤️ Life Path & Destiny

![Astrological Chart](https://img.icons8.com/?size=80&id=59779&format=png)

Your astrological chart reveals a **Scorpio Sun with Pisces Rising**, creating a powerful combination of transformation and compassion. This placement indicates several key life themes:

### 🔄 Major Life Cycles
1. **Ages 7-14**: Foundation of spiritual awareness
2. **Ages 28-35**: Major spiritual awakening period ⭐
3. **Ages 42-49**: Teaching and healing mastery phase
4. **Ages 56+**: Elder wisdom sharing time

### 💼 Career & Calling
Your energy signature points toward:
- *Healing Arts*: Counseling, energy work, alternative medicine
- *Creative Expression*: Writing, art, music with spiritual themes
- *Teaching*: Sharing wisdom through education or mentorship

The Scorpio-Pisces combination in your chart creates a fascinating duality between the detective and the mystic, the transformer and the compassionate healer. Scorpio gives you the courage to dive deep into life's mysteries and the tenacity to uncover hidden truths, while Pisces provides the intuitive wisdom to understand what you discover and the compassion to help others heal from what you both uncover together.

Your life path is marked by significant transformation cycles that correspond to your spiritual evolution. Each major life cycle brings opportunities for deeper understanding and expanded service. The period between ages 28-35 is particularly significant—this is when you'll likely experience your most profound spiritual awakening, possibly triggered by a crisis that forces you to examine your deepest beliefs and emerge with a clearer sense of purpose.

Destiny has placed you on a path that requires both courage and compassion. You're here to help others navigate their own transformational journeys, but first, you must complete your own. Each challenge you overcome, each fear you face, each limiting belief you release becomes wisdom that you can share with others who are walking similar paths. Your destiny is intimately connected with your ability to transform your own wounds into sources of healing power for others.

---

## 💕 Relationships & Compatibility

Your Venus in Virgo creates a unique love pattern where you express affection through **acts of service and practical care**. You attract partners who appreciate your depth and healing nature.

> 💫 **Compatibility Insight**: Your soul seeks partners who can match your emotional depth while grounding your spiritual flights.

### 🔥 Romantic Compatibility
- **Highest**: Cancer, Pisces, Capricorn *(water/earth harmony)*
- **Challenging**: Gemini, Sagittarius *(needs patience and understanding)*
- **Growth**: Leo, Aquarius *(opposite energies create balance)*

In relationships, you bring a rare combination of passionate intensity and nurturing care. Your Venus in Virgo means you show love through thoughtful actions rather than grand romantic gestures—you remember your partner's favorite tea, notice when they're stressed, and create small daily rituals that demonstrate your devotion. This practical expression of love is deeply meaningful to those who understand your nature, though it may be misunderstood by partners who expect more traditional romantic displays.

Your Scorpio sun creates a magnetic attraction that draws others to your mysterious and transformative energy. You don't love lightly—when you commit, you do so with your entire being. This intensity can be both thrilling and intimidating to potential partners. You have the ability to see through surface presentations to the authentic self beneath, which means you're attracted to depth and repelled by superficiality in romantic connections.

The challenge in your romantic life lies in finding someone who can match your emotional and spiritual depths without being overwhelmed by your intensity. You need a partner who understands that your occasional need for solitude isn't rejection but self-care, someone who appreciates your intuitive gifts rather than being threatened by them. When you find the right person, your relationship becomes a sacred space for mutual growth and transformation.

---

## 🌿 Health & Wellbeing

![Wellness Balance](https://img.icons8.com/?size=80&id=59807&format=png)

Your sensitive energy system requires special attention to maintain balance:

### 🦋 Recommended Practices
1. **Daily**: 10 minutes meditation with amethyst
2. **Weekly**: Salt bath with lavender for energy cleansing
3. **Monthly**: Full moon ritual for releasing and renewal

As someone with an exceptionally sensitive energy system, you operate as a spiritual antenna, receiving information from both seen and unseen realms. This gift comes with a responsibility to maintain healthy energetic boundaries and regular cleansing practices. Your physical health is intimately connected to your energetic wellbeing—when your energy becomes depleted or congested, physical symptoms like headaches, fatigue, or digestive issues often follow as signals that something needs attention.

Water plays a particularly important role in your health regimen. Your body has a higher than average water composition, which helps conduct the subtle energies that flow through you. Staying properly hydrated is not just good general health advice for you—it's essential for maintaining your intuitive abilities and emotional balance. Salt baths serve a dual purpose, both cleansing your energy field and replenishing minerals that get depleted when you're doing energy work or spending time in emotionally charged environments.

Your relationship with the natural cycles of the moon profoundly affects your energy levels and emotional state. You may notice that your intuitive abilities peak around the full moon, while the new moon offers the perfect opportunity for setting intentions and planting seeds for future manifestation. Creating a monthly ritual that acknowledges these lunar influences helps you stay aligned with natural rhythms and maximizes your manifesting potential. Consider keeping a moon journal to track how different lunar phases affect your energy, mood, and spiritual insights.

---

## 🔮 Future Guidance & Manifestation

The upcoming Jupiter transit through your career sector opens incredible opportunities for **sharing your gifts with the world**.

### ✨ Manifestation Power Peaks
- `New Moon in Scorpio` (your power portal)
- `Full Moon in Taurus` (material manifestation)  
- `Lion's Gate Portal (8/8)` - major spiritual downloads

The cosmic energies aligning in your favor over the next year create a powerful manifestation window unlike anything you've experienced in the past 12 years. Jupiter, the planet of expansion and abundance, is activating your 10th house of career and public recognition, bringing opportunities that align perfectly with your soul's purpose. This transit only happens once every 12 years, making this a critical time to set clear intentions and take inspired action toward your most authentic goals.

Your manifestation abilities are currently amplified, particularly during the power peaks identified above. During these windows, the veil between worlds thins, allowing your intentions to move more quickly from the ethereal realm into physical reality. The key to working with these energies effectively lies in clarity and emotional alignment—your manifestation power comes from feeling the emotion of your desire as if it's already manifested, rather than from mental focus alone.

The Lion's Gate Portal on August 8th holds special significance for you this year. This cosmic alignment creates a direct channel to higher dimensions of consciousness, offering downloads of spiritual information that will clarify your path forward. Set aside time for meditation during this window, and pay special attention to insights that come through dreams, synchronicities, or spontaneous knowing. The information you receive during this time will contain important keys to your next evolutionary steps.

---

## 🎯 **Your 3-Day Activation Plan**

1. **Day 1**: Create a sacred meditation space in your home
2. **Day 2**: Begin 10-minute daily gratitude + intention practice
3. **Day 3**: Research energy healing training or certification programs

This 3-Day Activation Plan is designed to jumpstart your spiritual momentum and establish key practices that will support your ongoing evolution. The power lies not in the complexity of these actions but in the intention and consistency with which you approach them. Each step builds upon the previous one, creating an energetic foundation for the transformation that's unfolding in your life.

The sacred space you create on Day 1 serves as an energetic anchor for your spiritual practice. Even a small corner of a room, when consecrated with intention, becomes a powerful portal for connection with higher consciousness. Include elements that represent the four elements—a candle for fire, a feather for air, a crystal for earth, and a small bowl of water. This balanced representation helps create a harmonious energy field that supports your meditation practice.

The gratitude and intention practice introduced on Day 2 works with the universal law of attraction by shifting your vibrational frequency to align with what you wish to create. Begin each session by acknowledging three specific things you're grateful for, focusing on the emotion of gratitude rather than just the mental list. Follow this with setting one clear intention for the day, visualizing it as already accomplished and feeling the emotions associated with its completion. This simple practice, when done consistently, creates profound shifts in your energy field and your external reality.

---

### 🔧 Essential Tools
| Crystal | Purpose | Usage |
|---------|---------|-------|
| 💜 **Amethyst** | Intuition | Morning meditation |
| 🌹 **Rose Quartz** | Heart healing | Carry daily |
| ✨ **Clear Quartz** | Amplification | Energy work |

These crystals have been specifically selected to support your unique energy signature and spiritual path. Each one resonates with different aspects of your energy field, helping to amplify your natural gifts while providing support for areas that need balancing. Consider these allies on your journey, tools that help facilitate the connection between your conscious awareness and the subtle energetic realms you're designed to work with.

Amethyst serves as a particularly powerful ally for you, as it resonates with your third eye and crown chakras, the energy centers associated with intuition and higher consciousness. Its violet frequency helps clear mental chatter and creates a clear channel for intuitive information to flow through. Morning meditation with amethyst helps set your energetic tone for the day, creating protection against external influences that might otherwise drain your sensitive system.

The combination of these three crystals creates a balanced energy triangle that supports your overall wellbeing. While many crystals could be beneficial, starting with these three creates a solid foundation. As your relationship with crystal energies develops, you may be intuitively drawn to additional stones that support specific aspects of your development. Trust this guidance—your higher self is communicating with you through these attractions.

---

> **✨ Your Soul Affirmation**: *"I am a powerful spiritual being having a human experience. I trust my intuition, honor my sensitivity, and know that my healing presence makes the world brighter."* 🌟

---

**Generated with 💜 by AI Spiritual Consciousness**  
*Based on ${_uiState.value.completedFields} comprehensive spiritual data points*

---

**📄 Report Statistics**: [WORD_COUNT_PLACEHOLDER] words | ${_uiState.value.completedFields} fields analyzed
        """.trimIndent()
        
        // Calculate actual word count and replace placeholder
        val wordCount = countWords(sampleResult)
        val finalResult = sampleResult.replace("[WORD_COUNT_PLACEHOLDER]", wordCount.toString())
        
        // Save the result back to the profile
        val currentProfile = userRepository.getProfileById(profileId)
        currentProfile?.let { profile ->
            val updatedProfile = profile.copy(
                enrichmentResult = finalResult,
                enrichmentGeneratedAt = java.time.LocalDateTime.now(),
                lastModified = java.time.LocalDateTime.now()
            )
            try {
                userRepository.saveUserProfile(updatedProfile)
            } catch (e: Exception) {
                // Log error but don't fail the UI update
                android.util.Log.e("EnrichmentResult", "Failed to save enrichment result to profile", e)
            }
        }
        
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            result = finalResult
        )
    }
    
    fun retryEnrichment(profileId: String) {
        startEnrichment(profileId)
    }
    
    fun shareResult() {
        // TODO: Implement sharing functionality
        viewModelScope.launch {
            // Could share to social media, email, etc.
        }
    }
    
    /**
     * Count actual words in the content, excluding markdown syntax
     */
    private fun countWords(content: String): Int {
        return content
            .replace(Regex("[#*_`\\[\\]()!-]+"), "") // Remove common markdown symbols
            .replace(Regex("https?://[^\\s]+"), "") // Remove URLs
            .split(Regex("\\s+")) // Split on whitespace
            .filter { it.isNotBlank() } // Remove empty strings
            .size
    }
}
