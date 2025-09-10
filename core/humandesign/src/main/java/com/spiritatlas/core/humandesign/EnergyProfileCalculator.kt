package com.spiritatlas.core.humandesign

// Generic energy profiling system inspired by various systems
object EnergyProfileCalculator {
    
    enum class EnergyType {
        INITIATOR, // Generic term for starter energy
        BUILDER,   // Generic term for sustained energy
        GUIDE,     // Generic term for guiding energy
        REFINER,   // Generic term for evaluative energy
        OBSERVER   // Generic term for reflective energy
    }
    
    enum class ProfileLine(val number: Int, val description: String) {
        INVESTIGATOR(1, "Foundation seeker"),
        HERMIT(2, "Natural talent"),
        EXPERIMENTER(3, "Trial and error"),
        OPPORTUNIST(4, "Network builder"),
        HERETIC(5, "Solution provider"),
        ROLE_MODEL(6, "Living example")
    }
    
    data class Profile(
        val conscious: ProfileLine,
        val unconscious: ProfileLine
    ) {
        val notation: String get() = "${conscious.number}/${unconscious.number}"
    }
    
    // Simplified calculation based on birth data
    fun calculateEnergyType(day: Int, month: Int, year: Int): EnergyType {
        val sum = (day + month + year) % 5
        return EnergyType.values()[sum]
    }
    
    fun calculateProfile(day: Int, month: Int): Profile {
        val conscious = ProfileLine.values()[(day - 1) % 6]
        val unconscious = ProfileLine.values()[(month - 1) % 6]
        return Profile(conscious, unconscious)
    }
}


