package com.example.assignmentlisted.dependencyInjection

import com.example.assignmentlisted.data.network.ApiService
import com.example.assignmentlisted.data.network.OAuthInterceptor
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi():Moshi = Moshi.Builder().run { add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun providesApiService(moshi: Moshi,okHttpClient: OkHttpClient): ApiService = Retrofit.Builder().run {
        baseUrl(ApiService.BASE_URL)
        addConverterFactory(MoshiConverterFactory.create(moshi)).client(okHttpClient).build()
    }.create(ApiService::class.java)


    @Provides
    @Singleton
    fun providesOKHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(
            OAuthInterceptor("Bearer",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
        )
        ).addInterceptor(interceptor).build()
    }
}