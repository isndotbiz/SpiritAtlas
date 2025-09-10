package com.spiritatlas.feature.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a$\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\t\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\n\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\f\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\r\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a$\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a,\u0010\u000f\u001a\u00020\u00012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001a$\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a8\u0010\u001a\u001a\u00020\u00012\b\u0010\u001b\u001a\u0004\u0018\u00010\u00112\u0014\u0010\u001c\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0011\u0012\u0004\u0012\u00020\u00010\b2\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u0011H\u0007\u001a6\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00112\u001c\u0010\"\u001a\u0018\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b$\u00a2\u0006\u0002\b%H\u0007\u001a$\u0010&\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u00a8\u0006\'"}, d2 = {"AccuracyIndicator", "", "completion", "Lcom/spiritatlas/domain/model/ProfileCompletion;", "AdditionalNamesSection", "profile", "Lcom/spiritatlas/domain/model/UserProfile;", "onProfileUpdate", "Lkotlin/Function1;", "CoreIdentitySection", "EnvironmentalSection", "FamilyAncestrySection", "LifePatternsSection", "PhysicalEnergeticSection", "PreferencesSection", "ProfileScreen", "profileId", "", "onNavigateBack", "Lkotlin/Function0;", "viewModel", "Lcom/spiritatlas/feature/profile/ProfileViewModel;", "ProfileSectionTabs", "activeSection", "Lcom/spiritatlas/feature/profile/ProfileSection;", "onSectionSelected", "ProfileTextField", "value", "onValueChange", "label", "placeholder", "SectionCard", "title", "subtitle", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "TimingCyclesSection", "profile_debug"})
public final class ProfileScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProfileScreen(@org.jetbrains.annotations.Nullable()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    com.spiritatlas.feature.profile.ProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AccuracyIndicator(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.ProfileCompletion completion) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileSectionTabs(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.feature.profile.ProfileSection activeSection, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.feature.profile.ProfileSection, kotlin.Unit> onSectionSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CoreIdentitySection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void AdditionalNamesSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FamilyAncestrySection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PhysicalEnergeticSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TimingCyclesSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EnvironmentalSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LifePatternsSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PreferencesSection(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile profile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.spiritatlas.domain.model.UserProfile, kotlin.Unit> onProfileUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SectionCard(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileTextField(@org.jetbrains.annotations.Nullable()
    java.lang.String value, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onValueChange, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String placeholder) {
    }
}