package com.spiritatlas.feature.home;

import com.spiritatlas.domain.ai.AiSettingsRepository;
import com.spiritatlas.domain.repository.ConsentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<AiSettingsRepository> aiSettingsRepositoryProvider;

  private final Provider<ConsentRepository> consentRepositoryProvider;

  public HomeViewModel_Factory(Provider<AiSettingsRepository> aiSettingsRepositoryProvider,
      Provider<ConsentRepository> consentRepositoryProvider) {
    this.aiSettingsRepositoryProvider = aiSettingsRepositoryProvider;
    this.consentRepositoryProvider = consentRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(aiSettingsRepositoryProvider.get(), consentRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<AiSettingsRepository> aiSettingsRepositoryProvider,
      Provider<ConsentRepository> consentRepositoryProvider) {
    return new HomeViewModel_Factory(aiSettingsRepositoryProvider, consentRepositoryProvider);
  }

  public static HomeViewModel newInstance(AiSettingsRepository aiSettingsRepository,
      ConsentRepository consentRepository) {
    return new HomeViewModel(aiSettingsRepository, consentRepository);
  }
}
