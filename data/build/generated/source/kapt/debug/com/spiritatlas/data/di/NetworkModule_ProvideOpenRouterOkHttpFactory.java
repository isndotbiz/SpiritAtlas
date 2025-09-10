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
public final class NetworkModule_ProvideOpenRouterOkHttpFactory implements Factory<OkHttpClient> {
  private final Provider<HttpLoggingInterceptor> loggingProvider;

  public NetworkModule_ProvideOpenRouterOkHttpFactory(
      Provider<HttpLoggingInterceptor> loggingProvider) {
    this.loggingProvider = loggingProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOpenRouterOkHttp(loggingProvider.get());
  }

  public static NetworkModule_ProvideOpenRouterOkHttpFactory create(
      Provider<HttpLoggingInterceptor> loggingProvider) {
    return new NetworkModule_ProvideOpenRouterOkHttpFactory(loggingProvider);
  }

  public static OkHttpClient provideOpenRouterOkHttp(HttpLoggingInterceptor logging) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOpenRouterOkHttp(logging));
  }
}
