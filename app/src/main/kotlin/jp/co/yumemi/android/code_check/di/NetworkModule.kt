package jp.co.yumemi.android.code_check.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.constant.ApiConfig
import jp.co.yumemi.android.code_check.repository.GitHubAccountRepository
import jp.co.yumemi.android.code_check.service.api.GitHubAccountApiService
import jp.co.yumemi.android.code_check.util.network.NetworkConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the base URL for the API as defined in the [ApiConfig] enum class.
     *
     * This value is used throughout the application to construct API request URLs.
     *
     * @return The base URL string.
     */
    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return ApiConfig.BASE_URL.value
    }

    /**
     * Provides a [GsonConverterFactory] for deserializing JSON responses from the API.
     *
     * Gson is a popular library for efficiently converting between JSON and Kotlin objects.
     *
     * @return A [Converter.Factory] instance for JSON parsing.
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides a configured [OkHttpClient] instance for making network requests.
     *
     * This OkHttpClient is configured with a timeout and a [NetworkConnectivityInterceptor]
     * to check for internet connectivity before making requests. Additional interceptors
     * for authentication or logging can be added here if needed.
     *
     * @param context The application context required for the NetworkConnectivityInterceptor.
     *
     * @return A configured OkHttpClient instance.
     */
    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectivityInterceptor(context))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
        return okHttpClient.build()
    }

    /**
     * Provides a [Retrofit] instance configured for interacting with the API.
     *
     * This Retrofit instance is built using the provided baseUrl, converter factory,
     * and OkHttpClient. It serves as the foundation for making network requests to
     * the API endpoints.
     *
     * @param okHttpClient The configured OkHttpClient instance for making requests.
     * @param baseUrl The base URL for the API.
     * @param converterFactory The converter factory for JSON parsing.
     *
     * @return A configured Retrofit instance.
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
        return retrofit.build()
    }

    /**
     * Provides an instance of the [GitHubAccountApiService] interface for interacting
     * with specific GitHub API endpoints.
     *
     * This method utilizes the provided Retrofit instance to create the ApiService instance
     * through dynamic proxy generation.
     *
     * @param retrofit The configured Retrofit instance for making API requests.
     *
     * @return An instance of the GitHubAccountApiService interface.
     */
    @Singleton
    @Provides
    fun provideGitHubAccountApiService(retrofit: Retrofit): GitHubAccountApiService {
        return retrofit.create(GitHubAccountApiService::class.java)
    }

    /**
     * Provides an instance of the [GitHubAccountRepository] for managing GitHub account data.
     *
     * This repository class encapsulates the logic for fetching and managing data related to
     * GitHub accounts, utilizing the provided GitHubAccountApiService for network communication.
     *
     * @param githubAccountApiService The instance used for interacting with GitHub API endpoints.
     *
     * @return An instance of the GitHubAccountRepository class.
     */
    @Singleton
    @Provides
    fun provideGitHubRepository(githubAccountApiService: GitHubAccountApiService): GitHubAccountRepository {
        return GitHubAccountRepository(githubAccountApiService)
    }
}