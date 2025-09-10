package com.spiritatlas.data.ai;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata
@QualifierMetadata("javax.inject.Named")
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
public final class OpenRouterProvider_Factory implements Factory<OpenRouterProvider> {
  private final Provider<Retrofit> retrofitProvider;

  public OpenRouterProvider_Factory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OpenRouterProvider get() {
    return newInstance(retrofitProvider.get());
  }

  public static OpenRouterProvider_Factory create(Provider<Retrofit> retrofitProvider) {
    return new OpenRouterProvider_Factory(retrofitProvider);
  }

  public static OpenRouterProvider newInstance(Retrofit retrofit) {
    return new OpenRouterProvider(retrofit);
  }
}
