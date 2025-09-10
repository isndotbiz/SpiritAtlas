package com.spiritatlas.data.di;

import com.spiritatlas.data.database.SpiritAtlasDatabase;
import com.spiritatlas.data.database.dao.UserProfileDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideUserProfileDaoFactory implements Factory<UserProfileDao> {
  private final Provider<SpiritAtlasDatabase> databaseProvider;

  public DatabaseModule_ProvideUserProfileDaoFactory(
      Provider<SpiritAtlasDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public UserProfileDao get() {
    return provideUserProfileDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideUserProfileDaoFactory create(
      Provider<SpiritAtlasDatabase> databaseProvider) {
    return new DatabaseModule_ProvideUserProfileDaoFactory(databaseProvider);
  }

  public static UserProfileDao provideUserProfileDao(SpiritAtlasDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUserProfileDao(database));
  }
}
