package com.spiritatlas.data.di;

import android.content.Context;
import com.spiritatlas.data.database.SpiritAtlasDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DatabaseModule_ProvideSpiritAtlasDatabaseFactory implements Factory<SpiritAtlasDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideSpiritAtlasDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SpiritAtlasDatabase get() {
    return provideSpiritAtlasDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideSpiritAtlasDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideSpiritAtlasDatabaseFactory(contextProvider);
  }

  public static SpiritAtlasDatabase provideSpiritAtlasDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSpiritAtlasDatabase(context));
  }
}
