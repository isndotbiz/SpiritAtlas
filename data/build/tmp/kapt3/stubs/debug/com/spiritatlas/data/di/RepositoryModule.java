package com.spiritatlas.data.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\'J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\fH\'\u00a8\u0006\r"}, d2 = {"Lcom/spiritatlas/data/di/RepositoryModule;", "", "()V", "bindAiSettingsRepository", "Lcom/spiritatlas/domain/ai/AiSettingsRepository;", "impl", "Lcom/spiritatlas/data/settings/AiSettingsRepositoryImpl;", "bindConsentRepository", "Lcom/spiritatlas/domain/repository/ConsentRepository;", "Lcom/spiritatlas/data/consent/ConsentManager;", "bindUserRepository", "Lcom/spiritatlas/domain/repository/UserRepository;", "Lcom/spiritatlas/data/repository/UserRepositoryImpl;", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @org.jetbrains.annotations.NotNull()
    public abstract com.spiritatlas.domain.repository.ConsentRepository bindConsentRepository(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.consent.ConsentManager impl);
    
    @dagger.Binds()
    @org.jetbrains.annotations.NotNull()
    public abstract com.spiritatlas.domain.repository.UserRepository bindUserRepository(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.repository.UserRepositoryImpl impl);
    
    @dagger.Binds()
    @org.jetbrains.annotations.NotNull()
    public abstract com.spiritatlas.domain.ai.AiSettingsRepository bindAiSettingsRepository(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.settings.AiSettingsRepositoryImpl impl);
}