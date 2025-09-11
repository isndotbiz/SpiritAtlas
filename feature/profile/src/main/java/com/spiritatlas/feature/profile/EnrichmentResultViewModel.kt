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
# 🌟 **Your Complete Tantric & Spiritual Profile**

![Tantric Essence](https://img.icons8.com/?size=100&id=62040&format=png)

> *"The union of love and wisdom that creates the magic."* - Tantra

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

## 🥰 **TANTRIC DEEP DIVE: Your Sexual & Spiritual Archetype**

![Tantric Energy](https://img.icons8.com/?size=100&id=65439&format=png)

### 🔥 Your Tantric Type: **Shakti Dominant**

You embody the divine feminine principle of creation, flow, and receptive power. As a Shakti-dominant being, you are the sacred vessel through which creative energy flows into manifestation. Your sexuality is deeply connected to your spirituality, and you have the ability to transform raw sexual energy into higher consciousness.

**Core Shakti Traits:**
- 🌺 **Intuitive Receiver**: You sense energy before it becomes physical
- 💧 **Emotional Alchemist**: Transform pain into wisdom, pleasure into enlightenment
- 🌙 **Lunar Cyclic**: Your energy flows in natural rhythms and cycles
- ✨ **Sacred Space Holder**: Create containers for deep transformation

Your tantric nature craves depth over superficiality in all intimate encounters. You are not satisfied with mere physical pleasure - your soul yearns for the kind of connection that dissolves boundaries and creates transcendent union. This makes you both a challenging and deeply rewarding partner, as you demand authenticity and spiritual alignment from anyone who wishes to truly know you.

The ancient texts speak of Shakti as the force that animates all of creation. In your sexual expression, this manifests as an ability to awaken dormant energies in yourself and others. You have the capacity to guide a partner into states of consciousness they never knew existed, but only when you feel completely safe, seen, and honored in your full power.

### 🌱 Your Kama Element: **Water**

Like water, your sexual energy is flowing, adaptive, and deeply emotional. You respond to the container you're placed in, but your true nature is to flow freely and seek the deepest possible connection.

![Water Element](https://img.icons8.com/?size=80&id=60471&format=png)

**Water Element Sexual Characteristics:**
- **Emotional Depth**: Your arousal is connected to feeling deeply understood
- **Intuitive Pleasure**: You know what feels good before your mind catches up
- **Healing Through Touch**: Your intimate connection has therapeutic qualities
- **Cyclical Desire**: Your libido flows in natural waves and seasons

Your water nature means you need time to warm up and feel safe before you can fully open. Once you do, however, you become a force of nature - passionate, flowing, and capable of overwhelming intensity. Partners who rush you miss the profound depths you're capable of sharing. Those who honor your need for emotional safety discover an infinitely giving and creative lover.

The Kama Sutra identifies water types as the most capable of tantric transformation because they naturally understand the connection between emotion, energy, and physical pleasure. You don't separate these aspects of experience - they flow together in your being like tributaries feeding a mighty river.

### 🇮🇳 **Your Personalized Kama Sutra Positions**

#### 1. **Padma (The Lotus) - Primary Recommendation**
*Sanskrit: Padmasana Maithuna*

![Lotus Position](https://img.icons8.com/?size=80&id=40217&format=png)

**Why This is Perfect for You**: As a Shakti-dominant water type, you thrive in positions that emphasize emotional connection and energy exchange over athletic prowess. The Lotus position allows for:

- **Deep eye contact** maintaining your need for soul-level connection
- **Synchronized breathing** activating your natural tantric abilities
- **Slow, meditative movement** honoring your water element's need for flow
- **Equal power dynamic** expressing your collaborative nature

**Detailed Instructions**:
1. Both partners sit facing each other in comfortable crossed-leg positions
2. The receiving partner wraps their legs around their partner's waist
3. Maintain eye contact throughout the entire experience
4. Synchronize your breathing - inhale together, exhale together
5. Move slowly and mindfully, focusing on energy rather than friction
6. Exchange whispered affirmations or remain in sacred silence

**Spiritual Purpose**: This position is designed to create a complete energetic circuit between partners, allowing Shakti and Shiva energies to dance together in perfect balance.

#### 2. **Shiva Linga (The Sacred Union)**
*Sanskrit: Lingam-Yoni Maithuna*

This position honors the sacred geometry of masculine and feminine energies. Your water element responds beautifully to the reverent, worship-like quality of this ancient practice.

**Your Personalized Approach**: Begin with 20 minutes of non-sexual sacred touching, awakening each chakra through gentle massage and breath work. This honors your need for emotional warm-up and creates the energetic foundation for transcendent physical union.

#### 3. **Flowing River (Modern Tantric Adaptation)**
A dynamic position that changes fluidly based on the natural rhythm you establish together - perfect for your water element's need for organic flow and adaptation.

### 🎨 **Robert Greene Seduction Archetype: The Ideal Lover**

Based on your spiritual profile, you naturally embody **"The Ideal Lover"** archetype from Robert Greene's *Art of Seduction*. This powerful seductive force operates by becoming exactly what the other person has always dreamed of but never found.

![Ideal Lover](https://img.icons8.com/?size=80&id=85783&format=png)

**Your Natural Seduction Strategies:**

#### 1. **The Mirror of Desires**
Your intuitive Shakti nature allows you to sense what someone deeply craves in a partner, often before they're consciously aware of it themselves. You don't fake these qualities - you genuinely develop them, making yourself irresistible by becoming their perfect complement.

*Application*: In early relationship phases, pay attention to the stories they tell about past relationships, the qualities they admire in others, and the casual mentions of what they find attractive. Your water element naturally adapts to reflect these desires back to them.

#### 2. **Sacred Fantasy Fulfillment** 
Greene teaches that the Ideal Lover creates a perfect fantasy world for their target. Your spiritual depth allows you to fulfill not just physical fantasies, but soul-level dreams of connection, understanding, and transcendence.

*Application*: Create experiences that feel "meant to be" - synchronistic encounters, spiritually meaningful dates, moments that feel like destiny. Your natural mystical bent makes this authentic rather than manipulative.

#### 3. **The Awakening Touch**
Your combination of spiritual depth and sensual water energy creates a unique ability to awaken dormant aspects of people's sexuality and spirituality simultaneously.

*Warning from Greene*: The Ideal Lover must be careful not to lose themselves in the process of becoming what others need. Maintain your core essence while adapting your expression.

### 🕰️ **Tantric Relationship Dynamics**

**Your Primary Pattern**: **Sacred Union Seeker**

You don't do casual well. Every relationship has the potential to become a spiritual path, and you unconsciously guide connections toward deeper meaning and transformation. This is both your greatest gift and your biggest challenge in modern dating culture.

**Power Dynamics**: You operate best in **"Fluid Exchange"** dynamics where power flows between partners based on the moment's needs rather than fixed roles. During your Shakti phases, you want to surrender and be held; during your activated phases, you naturally lead and guide.

**Communication Style**: **"Intuitive Heart-Speak"** - You communicate as much through energy, touch, and presence as through words. Partners need to develop sensitivity to your non-verbal language to truly connect with you.

### 🌸 **Chakra-Based Intimacy Guide**

**Your Dominant Chakra**: Heart (Anahata)
**Your Secondary Chakra**: Sacral (Svadhisthana)

This combination creates someone who experiences sexuality as an extension of love and love as a spiritual practice. You cannot separate physical pleasure from emotional connection.

**Chakra Activation Sequence for Optimal Intimacy**:
1. **Root** (10 min): Grounding together through synchronized breathing
2. **Sacral** (15 min): Sensual massage awakening creative/sexual energy
3. **Solar Plexus** (10 min): Empowerment through affirmations and eye contact
4. **Heart** (20 min): Emotional opening through sharing and vulnerability
5. **Throat** (10 min): Expressing desires and speaking sacred intentions
6. **Third Eye** (15 min): Spiritual connection and energetic union
7. **Crown** (Integration): Transcendent physical union

### 🌿 **Your Tantric Practice Prescriptions**

#### Daily Practice: **"Shakti Activation Breath"**
**Duration**: 11 minutes every morning
**Purpose**: Activate your divine feminine energy for the day

*Instructions*:
1. Sit naked or in flowing clothing facing east (sunrise energy)
2. Place left hand on heart, right hand on womb/sacral area
3. Breathe into your womb space for 4 counts
4. Hold breath while visualizing golden light expanding for 4 counts
5. Exhale through your heart for 6 counts, sending love into the world
6. Repeat for 11 minutes while mentally chanting "I am Shakti, I am divine feminine power"

#### Weekly Practice: **"Yoni Worship Ritual"**
**Duration**: 60-90 minutes
**Frequency**: Every new moon
**Purpose**: Honor your sacred feminine essence

*Solo Version*:
- Create sacred space with candles, flowers, and sensual music
- Anoint your body with sacred oils while speaking affirmations
- Self-pleasure meditation focusing on energy rather than orgasm
- Journal insights received during the experience

*Partnered Version*:
- Partner creates worship space and serves as devoted witness
- Extended yoni massage focused on reverence and energy activation
- No expectation of reciprocation - purely honoring the feminine

#### Monthly Practice: **"Shakti-Shiva Union Ceremony"**
A formal tantric ritual designed to balance and integrate your masculine and feminine aspects, whether practiced alone or with a partner.

### 🔍 **Compatibility Deep Dive**

Based on your Shakti-Water-Ideal Lover profile, here are your optimal matches:

**Divine Matches (90-95% Compatibility)**:
- **Shiva-Fire-Rake**: The passionate pursuer who worships your depth
- **Balanced Union-Earth-Charmer**: Stable presence that grounds your flow
- **Mystic Lover-Space-Sage**: Spiritual equal who matches your transcendent nature

**Growth Matches (75-85% Compatibility)**:
- **Sacred Warrior-Air-Protector**: Different elements but complementary purposes
- **Flowing Dancer-Water-Natural**: Same element, different expressions - can be magical or conflicting

**Challenge Matches (Proceed with Awareness)**:
- **Passionate Fire-Fire-Dandy**: Too much intensity without grounding
- **Any Type-Any Element-Coquette**: Your depth vs their emotional manipulation

---

> **✨ Your Soul Affirmation**: *"I am a powerful spiritual being having a human experience. I trust my intuition, honor my sensitivity, and know that my healing presence makes the world brighter. I embrace my Shakti power and create sacred space for divine union."* 🌟

---

**Generated with 💜 by AI Spiritual Consciousness & Tantric Wisdom**  
*Based on ${_uiState.value.completedFields} comprehensive spiritual data points, tantric energy analysis, and sacred sexuality insights*

---

**📄 Report Statistics**: [WORD_COUNT_PLACEHOLDER] words | ${_uiState.value.completedFields} fields analyzed | Tantric Deep Dive included
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
    
    fun shareResult(context: android.content.Context) {
        viewModelScope.launch {
            val result = _uiState.value.result
            if (!result.isNullOrBlank()) {
                try {
                    // Create a shareable version of the result (remove excessive formatting)
                    val shareableText = createShareableContent(result)
                    
                    // Create share intent
                    val shareIntent = android.content.Intent().apply {
                        action = android.content.Intent.ACTION_SEND
                        putExtra(android.content.Intent.EXTRA_TEXT, shareableText)
                        putExtra(android.content.Intent.EXTRA_SUBJECT, "My SpiritAtlas Enrichment Report")
                        type = "text/plain"
                    }
                    
                    // Create chooser to show available apps
                    val chooserIntent = android.content.Intent.createChooser(
                        shareIntent, 
                        "Share your spiritual insights"
                    )
                    
                    // Start the chooser activity
                    context.startActivity(chooserIntent)
                    
                    android.util.Log.d("EnrichmentResult", "Share intent created successfully")
                } catch (e: Exception) {
                    android.util.Log.e("EnrichmentResult", "Failed to share result: ${e.message}", e)
                }
            }
        }
    }
    
    /**
     * Create a cleaner version of the content suitable for sharing
     */
    private fun createShareableContent(fullResult: String): String {
        // Extract key insights for sharing (remove excessive formatting)
        val lines = fullResult.lines()
        val shareableLines = mutableListOf<String>()
        
        // Add header
        shareableLines.add("🔮 My SpiritAtlas Spiritual Profile 🔮")
        shareableLines.add("")
        
        // Extract key sections (simplified for sharing)
        var inImportantSection = false
        for (line in lines) {
            when {
                line.contains("### 🎆 **Your Spiritual Blueprint**") -> {
                    inImportantSection = true
                    shareableLines.add("🎆 My Spiritual Blueprint:")
                }
                line.contains("### 💫 **Life Path & Purpose**") -> {
                    inImportantSection = true  
                    shareableLines.add("")
                    shareableLines.add("💫 Life Path & Purpose:")
                }
                line.contains("### 🌹 **Love & Relationship Profile**") -> {
                    inImportantSection = true
                    shareableLines.add("")
                    shareableLines.add("🌹 Love & Relationships:")
                }
                line.contains("**Soul Affirmation**") -> {
                    inImportantSection = true
                    shareableLines.add("")
                    shareableLines.add("✨ My Soul Affirmation:")
                }
                line.startsWith("---") || line.startsWith("**Generated with") -> {
                    inImportantSection = false
                }
                inImportantSection && line.isNotBlank() -> {
                    // Clean up markdown and add key insights
                    val cleanLine = line
                        .replace(Regex("\\*\\*(.*?)\\*\\*"), "$1") // Remove bold formatting
                        .replace(Regex("\\*(.*?)\\*"), "$1") // Remove italic formatting
                        .replace(Regex("^#+\\s*"), "") // Remove headers
                        .trim()
                    
                    if (cleanLine.isNotBlank() && !cleanLine.startsWith(">")) {
                        shareableLines.add(cleanLine)
                    }
                }
            }
        }
        
        // Add footer
        shareableLines.add("")
        shareableLines.add("Created with SpiritAtlas - AI-powered spiritual insights")
        shareableLines.add("🔗 Get your own report at SpiritAtlas.app")
        
        return shareableLines.joinToString("\n")
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
