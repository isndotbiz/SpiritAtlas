package com.spiritatlas.data.consent;

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
public final class ConsentManager_Factory implements Factory<ConsentManager> {
  private final Provider<Context> contextProvider;

  public ConsentManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ConsentManager get() {
    return newInstance(contextProvider.get());
  }

  public static ConsentManager_Factory create(Provider<Context> contextProvider) {
    return new ConsentManager_Factory(contextProvider);
  }

  public static ConsentManager newInstance(Context context) {
    return new ConsentManager(context);
  }
}
