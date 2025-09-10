package com.spiritatlas.data.settings;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AiSettingsRepositoryImpl_Factory implements Factory<AiSettingsRepositoryImpl> {
  private final Provider<Context> contextProvider;

  public AiSettingsRepositoryImpl_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AiSettingsRepositoryImpl get() {
    return newInstance(contextProvider.get());
  }

  public static AiSettingsRepositoryImpl_Factory create(Provider<Context> contextProvider) {
    return new AiSettingsRepositoryImpl_Factory(contextProvider);
  }

  public static AiSettingsRepositoryImpl newInstance(Context context) {
    return new AiSettingsRepositoryImpl(context);
  }
}
