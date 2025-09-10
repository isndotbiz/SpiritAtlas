package com.spiritatlas.feature.profile;

/**
 * ViewModel for managing multiple spiritual profiles
 * Supports browsing, searching, and deleting profiles ✨
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0015B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u00020\rH\u0002J\u0006\u0010\u0012\u001a\u00020\rJ\u000e\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0010R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/spiritatlas/feature/profile/ProfileListViewModel;", "Landroidx/lifecycle/ViewModel;", "userRepository", "Lcom/spiritatlas/domain/repository/UserRepository;", "(Lcom/spiritatlas/domain/repository/UserRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/spiritatlas/feature/profile/ProfileListViewModel$ProfileListUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "deleteProfile", "profileId", "", "loadProfiles", "refresh", "searchProfiles", "query", "ProfileListUiState", "profile_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileListViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.spiritatlas.domain.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.spiritatlas.feature.profile.ProfileListViewModel.ProfileListUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.spiritatlas.feature.profile.ProfileListViewModel.ProfileListUiState> uiState = null;
    
    @javax.inject.Inject()
    public ProfileListViewModel(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.repository.UserRepository userRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.spiritatlas.feature.profile.ProfileListViewModel.ProfileListUiState> getUiState() {
        return null;
    }
    
    /**
     * Load all profiles from the repository
     */
    private final void loadProfiles() {
    }
    
    /**
     * Delete a specific profile
     */
    public final void deleteProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId) {
    }
    
    /**
     * Search profiles by name
     */
    public final void searchProfiles(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    /**
     * Clear any error messages
     */
    public final void clearError() {
    }
    
    /**
     * Refresh the profiles list
     */
    public final void refresh() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J9\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\bH\u00d6\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\f\u00a8\u0006\u001b"}, d2 = {"Lcom/spiritatlas/feature/profile/ProfileListViewModel$ProfileListUiState;", "", "profiles", "", "Lcom/spiritatlas/domain/model/UserProfile;", "isLoading", "", "errorMessage", "", "searchQuery", "(Ljava/util/List;ZLjava/lang/String;Ljava/lang/String;)V", "getErrorMessage", "()Ljava/lang/String;", "()Z", "getProfiles", "()Ljava/util/List;", "getSearchQuery", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "profile_debug"})
    public static final class ProfileListUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.spiritatlas.domain.model.UserProfile> profiles = null;
        private final boolean isLoading = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String errorMessage = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String searchQuery = null;
        
        public ProfileListUiState(@org.jetbrains.annotations.NotNull()
        java.util.List<com.spiritatlas.domain.model.UserProfile> profiles, boolean isLoading, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.spiritatlas.domain.model.UserProfile> getProfiles() {
            return null;
        }
        
        public final boolean isLoading() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getErrorMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSearchQuery() {
            return null;
        }
        
        public ProfileListUiState() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.spiritatlas.domain.model.UserProfile> component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.spiritatlas.feature.profile.ProfileListViewModel.ProfileListUiState copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.spiritatlas.domain.model.UserProfile> profiles, boolean isLoading, @org.jetbrains.annotations.Nullable()
        java.lang.String errorMessage, @org.jetbrains.annotations.NotNull()
        java.lang.String searchQuery) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}