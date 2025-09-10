package com.spiritatlas.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.spiritatlas.domain.ai.AiTextProvider;
import com.spiritatlas.domain.repository.ConsentRepository;
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
public final class DataSyncWorker_Factory {
  private final Provider<AiTextProvider> aiProvider;

  private final Provider<ConsentRepository> consentRepositoryProvider;

  public DataSyncWorker_Factory(Provider<AiTextProvider> aiProvider,
      Provider<ConsentRepository> consentRepositoryProvider) {
    this.aiProvider = aiProvider;
    this.consentRepositoryProvider = consentRepositoryProvider;
  }

  public DataSyncWorker get(Context appContext, WorkerParameters params) {
    return newInstance(appContext, params, aiProvider.get(), consentRepositoryProvider.get());
  }

  public static DataSyncWorker_Factory create(Provider<AiTextProvider> aiProvider,
      Provider<ConsentRepository> consentRepositoryProvider) {
    return new DataSyncWorker_Factory(aiProvider, consentRepositoryProvider);
  }

  public static DataSyncWorker newInstance(Context appContext, WorkerParameters params,
      AiTextProvider aiProvider, ConsentRepository consentRepository) {
    return new DataSyncWorker(appContext, params, aiProvider, consentRepository);
  }
}
