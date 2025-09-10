package com.spiritatlas.data.ai;

import com.spiritatlas.domain.ai.AiSettingsRepository;
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
public final class CombinedAiProvider_Factory implements Factory<CombinedAiProvider> {
  private final Provider<OpenRouterProvider> openRouterProvider;

  private final Provider<OllamaProvider> ollamaProvider;

  private final Provider<AiSettingsRepository> aiSettingsRepositoryProvider;

  public CombinedAiProvider_Factory(Provider<OpenRouterProvider> openRouterProvider,
      Provider<OllamaProvider> ollamaProvider,
      Provider<AiSettingsRepository> aiSettingsRepositoryProvider) {
    this.openRouterProvider = openRouterProvider;
    this.ollamaProvider = ollamaProvider;
    this.aiSettingsRepositoryProvider = aiSettingsRepositoryProvider;
  }

  @Override
  public CombinedAiProvider get() {
    return newInstance(openRouterProvider.get(), ollamaProvider.get(), aiSettingsRepositoryProvider.get());
  }

  public static CombinedAiProvider_Factory create(Provider<OpenRouterProvider> openRouterProvider,
      Provider<OllamaProvider> ollamaProvider,
      Provider<AiSettingsRepository> aiSettingsRepositoryProvider) {
    return new CombinedAiProvider_Factory(openRouterProvider, ollamaProvider, aiSettingsRepositoryProvider);
  }

  public static CombinedAiProvider newInstance(OpenRouterProvider openRouterProvider,
      OllamaProvider ollamaProvider, AiSettingsRepository aiSettingsRepository) {
    return new CombinedAiProvider(openRouterProvider, ollamaProvider, aiSettingsRepository);
  }
}
