package com.codesses.priceconverter.di

import android.content.Context
import androidx.room.Room
import com.codesses.priceconverter.common.utils.AppConstants
import com.codesses.priceconverter.common.utils.PCSharedStorage
import com.codesses.priceconverter.common.utils.SharedPrefHelper
import com.codesses.priceconverter.data.remote.ApiInterface
import com.codesses.priceconverter.data.repo.PriceConverterRepositoryImpl
import com.codesses.priceconverter.domain.repo.PriceConverterRepository
import com.codesses.priceconverter.room.dao.PriceConverterDao
import com.codesses.priceconverter.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val builder = OkHttpClient.Builder()
            builder
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val builder1: Request.Builder = chain.request().newBuilder()
                    chain
                        .proceed(builder1.build())
                })
                .addInterceptor(httpLoggingInterceptor)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideApiInterface(client: OkHttpClient): ApiInterface = Retrofit.Builder()
        .baseUrl(AppConstants.BIT_PAY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build().create(ApiInterface::class.java)


    @Provides
    @Singleton
    fun provideCoinRepository(api: ApiInterface): PriceConverterRepository {
        return PriceConverterRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "db_price_converter"
        ).build()

    @Provides
    @Singleton
    fun provideSharedPrefHelper(): SharedPrefHelper {
        return PCSharedStorage()
    }

    @Provides
    @Singleton
    fun providePriceConverterDao(db: AppDatabase): PriceConverterDao = db.priceConverterDao()

}