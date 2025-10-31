package com.example.meowmate.di

import android.content.Context
import androidx.room.Room
import com.example.meowmate.BuildConfig
import com.example.meowmate.data.CatsRepositoryImpl
import com.example.meowmate.data.local.CatsDb
import com.example.meowmate.data.remote.TheCatApi
import com.example.meowmate.domain.repository.CatsRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()

    @Provides @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides @Singleton
    fun provideCatApi(retrofit: Retrofit): TheCatApi =
        retrofit.create(TheCatApi::class.java)

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): CatsDb =
        Room.databaseBuilder(ctx, CatsDb::class.java, "cats.db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideDao(db: CatsDb) = db.dao()

    // API key din BuildConfig
    @Provides @Singleton
    fun provideApiKey(): () -> String? = { BuildConfig.CAT_API_KEY }

    @Provides @Singleton
    fun provideRepository(
        api: TheCatApi,
        db: CatsDb,
        apiKey: () -> String?
    ): CatsRepository = CatsRepositoryImpl(api, db.dao(), apiKey)
}
