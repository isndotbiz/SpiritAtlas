package com.spiritatlas.data.repository;

import com.spiritatlas.data.database.dao.UserProfileDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class UserRepositoryImpl_Factory implements Factory<UserRepositoryImpl> {
  private final Provider<UserProfileDao> userProfileDaoProvider;

  public UserRepositoryImpl_Factory(Provider<UserProfileDao> userProfileDaoProvider) {
    this.userProfileDaoProvider = userProfileDaoProvider;
  }

  @Override
  public UserRepositoryImpl get() {
    return newInstance(userProfileDaoProvider.get());
  }

  public static UserRepositoryImpl_Factory create(Provider<UserProfileDao> userProfileDaoProvider) {
    return new UserRepositoryImpl_Factory(userProfileDaoProvider);
  }

  public static UserRepositoryImpl newInstance(UserProfileDao userProfileDao) {
    return new UserRepositoryImpl(userProfileDao);
  }
}
