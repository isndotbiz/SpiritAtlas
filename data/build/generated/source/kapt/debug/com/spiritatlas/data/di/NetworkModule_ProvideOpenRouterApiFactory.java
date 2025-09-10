package com.spiritatlas.data.di;

import com.spiritatlas.data.ai.OpenRouterApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideOpenRouterApiFactory implements Factory<OpenRouterApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideOpenRouterApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OpenRouterApi get() {
    return provideOpenRouterApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideOpenRouterApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideOpenRouterApiFactory(retrofitProvider);
  }

  public static OpenRouterApi provideOpenRouterApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOpenRouterApi(retrofit));
  }
}
