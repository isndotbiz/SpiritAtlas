package com.spiritatlas.feature.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/spiritatlas/feature/profile/ProfileComparisonViewModel;", "Landroidx/lifecycle/ViewModel;", "userRepository", "Lcom/spiritatlas/domain/repository/UserRepository;", "(Lcom/spiritatlas/domain/repository/UserRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/spiritatlas/feature/profile/ProfileComparisonUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadProfiles", "", "profileId1", "", "profileId2", "profile_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileComparisonViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.spiritatlas.feature.profile.ProfileComparisonUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.spiritatlas.feature.profile.ProfileComparisonUiState> uiState = null;
    
    @javax.inject.Inject()
    public ProfileComparisonViewModel(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.spiritatlas.feature.profile.ProfileComparisonUiState> getUiState() {
        return null;
    }
    
    public final void loadProfiles(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId1, @org.jetbrains.annotations.NotNull()
    java.lang.String profileId2) {
    }
}