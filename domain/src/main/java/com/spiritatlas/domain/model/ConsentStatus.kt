package com.spiritatlas.domain.model

enum class ConsentStatus {
    GRANTED,
    DENIED,
    NOT_ASKED;
    
    val isGranted: Boolean
        get() = this == GRANTED
}


