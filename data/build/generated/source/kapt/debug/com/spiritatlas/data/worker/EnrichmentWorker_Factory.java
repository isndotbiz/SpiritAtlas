package com.spiritatlas.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.spiritatlas.domain.ai.AiTextProvider;
import com.spiritatlas.domain.repository.ConsentRepository;
import com.spiritatlas.domain.repository.UserRepository;
import dagger.internal.DaggerGenerated;
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
public final class EnrichmentWorker_Factory {
  private final Provider<AiTextProvider> aiProvider;

  private final Provider<ConsentRepository> consentRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public EnrichmentWorker_Factory(Provider<AiTextProvider> aiProvider,
      Provider<ConsentRepository> consentRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.aiProvider = aiProvider;
    this.consentRepositoryProvider = consentRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public EnrichmentWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, aiProvider.get(), consentRepositoryProvider.get(), userRepositoryProvider.get());
  }

  public static EnrichmentWorker_Factory create(Provider<AiTextProvider> aiProvider,
      Provider<ConsentRepository> consentRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new EnrichmentWorker_Factory(aiProvider, consentRepositoryProvider, userRepositoryProvider);
  }

  public static EnrichmentWorker newInstance(Context appContext, WorkerParameters workerParams,
      AiTextProvider aiProvider, ConsentRepository consentRepository,
      UserRepository userRepository) {
    return new EnrichmentWorker(appContext, workerParams, aiProvider, consentRepository, userRepository);
  }
}
