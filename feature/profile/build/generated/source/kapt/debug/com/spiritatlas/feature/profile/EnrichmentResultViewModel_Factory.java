package com.spiritatlas.feature.profile;

import androidx.work.WorkManager;
import com.spiritatlas.domain.repository.UserRepository;
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
public final class EnrichmentResultViewModel_Factory implements Factory<EnrichmentResultViewModel> {
  private final Provider<WorkManager> workManagerProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public EnrichmentResultViewModel_Factory(Provider<WorkManager> workManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.workManagerProvider = workManagerProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public EnrichmentResultViewModel get() {
    return newInstance(workManagerProvider.get(), userRepositoryProvider.get());
  }

  public static EnrichmentResultViewModel_Factory create(Provider<WorkManager> workManagerProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new EnrichmentResultViewModel_Factory(workManagerProvider, userRepositoryProvider);
  }

  public static EnrichmentResultViewModel newInstance(WorkManager workManager,
      UserRepository userRepository) {
    return new EnrichmentResultViewModel(workManager, userRepository);
  }
}
