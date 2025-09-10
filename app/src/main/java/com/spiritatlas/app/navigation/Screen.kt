package com.spiritatlas.app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProfileList : Screen("profile_list")
    object Profile : Screen("profile/{profileId}") {
        fun createRoute(profileId: String? = null): String {
            return "profile/${profileId ?: "new"}"
        }
    }
    object ProfileComparison : Screen("profile_comparison/{profileId1}/{profileId2}") {
        fun createRoute(profileId1: String, profileId2: String): String {
            return "profile_comparison/$profileId1/$profileId2"
        }
    }
    object EnrichmentResult : Screen("enrichment_result/{profileId}") {
        fun createRoute(profileId: String): String {
            return "enrichment_result/$profileId"
        }
    }
    object Consent : Screen("consent")
}


