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
public final class ProfileListViewModel_Factory implements Factory<ProfileListViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  public ProfileListViewModel_Factory(Provider<UserRepository> userRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
  }

  @Override
  public ProfileListViewModel get() {
    return newInstance(userRepositoryProvider.get());
  }

  public static ProfileListViewModel_Factory create(
      Provider<UserRepository> userRepositoryProvider) {
    return new ProfileListViewModel_Factory(userRepositoryProvider);
  }

  public static ProfileListViewModel newInstance(UserRepository userRepository) {
    return new ProfileListViewModel(userRepository);
  }
}
