package com.spiritatlas.feature.consent;

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
public final class ConsentViewModel_Factory implements Factory<ConsentViewModel> {
  private final Provider<ConsentRepository> consentRepositoryProvider;

  private final Provider<AiSettingsRepository> aiSettingsRepositoryProvider;

  public ConsentViewModel_Factory(Provider<ConsentRepository> consentRepositoryProvider,
      Provider<AiSettingsRepository> aiSettingsRepositoryProvider) {
    this.consentRepositoryProvider = consentRepositoryProvider;
    this.aiSettingsRepositoryProvider = aiSettingsRepositoryProvider;
  }

  @Override
  public ConsentViewModel get() {
    return newInstance(consentRepositoryProvider.get(), aiSettingsRepositoryProvider.get());
  }

  public static ConsentViewModel_Factory create(
      Provider<ConsentRepository> consentRepositoryProvider,
      Provider<AiSettingsRepository> aiSettingsRepositoryProvider) {
    return new ConsentViewModel_Factory(consentRepositoryProvider, aiSettingsRepositoryProvider);
  }

  public static ConsentViewModel newInstance(ConsentRepository consentRepository,
      AiSettingsRepository aiSettingsRepository) {
    return new ConsentViewModel(consentRepository, aiSettingsRepository);
  }
}
