package com.spiritatlas.feature.home;

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
public final class ConsentDebugViewModel_Factory implements Factory<ConsentDebugViewModel> {
  private final Provider<ConsentRepository> consentRepositoryProvider;

  public ConsentDebugViewModel_Factory(Provider<ConsentRepository> consentRepositoryProvider) {
    this.consentRepositoryProvider = consentRepositoryProvider;
  }

  @Override
  public ConsentDebugViewModel get() {
    return newInstance(consentRepositoryProvider.get());
  }

  public static ConsentDebugViewModel_Factory create(
      Provider<ConsentRepository> consentRepositoryProvider) {
    return new ConsentDebugViewModel_Factory(consentRepositoryProvider);
  }

  public static ConsentDebugViewModel newInstance(ConsentRepository consentRepository) {
    return new ConsentDebugViewModel(consentRepository);
  }
}
