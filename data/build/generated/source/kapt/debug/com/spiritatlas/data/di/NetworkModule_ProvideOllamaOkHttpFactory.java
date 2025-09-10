package com.spiritatlas.data.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideOllamaOkHttpFactory implements Factory<OkHttpClient> {
  private final Provider<HttpLoggingInterceptor> loggingProvider;

  public NetworkModule_ProvideOllamaOkHttpFactory(
      Provider<HttpLoggingInterceptor> loggingProvider) {
    this.loggingProvider = loggingProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOllamaOkHttp(loggingProvider.get());
  }

  public static NetworkModule_ProvideOllamaOkHttpFactory create(
      Provider<HttpLoggingInterceptor> loggingProvider) {
    return new NetworkModule_ProvideOllamaOkHttpFactory(loggingProvider);
  }

  public static OkHttpClient provideOllamaOkHttp(HttpLoggingInterceptor logging) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOllamaOkHttp(logging));
  }
}
