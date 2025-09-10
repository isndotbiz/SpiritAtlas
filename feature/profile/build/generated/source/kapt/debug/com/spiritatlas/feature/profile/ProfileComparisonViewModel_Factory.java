package com.spiritatlas.feature.profile;

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
public final class ProfileComparisonViewModel_Factory implements Factory<ProfileComparisonViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public ProfileComparisonViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ProfileComparisonViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static ProfileComparisonViewModel_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new ProfileComparisonViewModel_Factory(userRepositoryProvider);
  }

  public static ProfileComparisonViewModel newInstance(UserRepository userRepository) {
    return new ProfileComparisonViewModel(userRepository);
  }
}
