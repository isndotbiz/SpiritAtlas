package com.spiritatlas.domain.model

import java.time.LocalDateTime

data class UserProfile(
    val id: String,
    val name: String,
    val birthDateTime: LocalDateTime,
    val birthPlace: BirthPlace,
    val preferences: UserPreferences = UserPreferences()
)

data class BirthPlace(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

data class UserPreferences(
    val usesSiderealZodiac: Boolean = true,
    val preferredHouseSystem: HouseSystem = HouseSystem.WHOLE_SIGN,
    val themeSkinTone: Int = 0 // For UI theming only
)

enum class HouseSystem {
    WHOLE_SIGN,
    PLACIDUS,
    EQUAL_HOUSE
}


