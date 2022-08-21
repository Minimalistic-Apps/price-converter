package com.minimalisticapps.priceconverter.di

import android.content.Context
import androidx.room.Room
import com.minimalisticapps.priceconverter.common.utils.AppConstants
import com.minimalisticapps.priceconverter.data.remote.bitpay.BitpayApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoingeckoApiInterface
import com.minimalisticapps.priceconverter.data.remote.donationserver.DonationServerApiInterface
import com.minimalisticapps.priceconverter.data.repository.DonationRepository
import com.minimalisticapps.priceconverter.data.repository.priceconverter.PriceConverterRepository
import com.minimalisticapps.priceconverter.data.repository.priceconverter.PriceConverterRepositoryImpl
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.database.AppDatabase
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
            OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val builder: Request.Builder = chain.request().newBuilder()
                    chain.proceed(builder.build())
                }).addInterceptor(httpLoggingInterceptor).build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(client: OkHttpClient): CoingeckoApiInterface = Retrofit.Builder()
        .baseUrl(AppConstants.COINGECTKO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(CoingeckoApiInterface::class.java)

    @Provides
    @Singleton
    fun provideBitpayApi(client: OkHttpClient): BitpayApiInterface = Retrofit.Builder()
        .baseUrl(AppConstants.BITPAY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(BitpayApiInterface::class.java)

    @Provides
    @Singleton
    fun provideCDonationServerApi(client: OkHttpClient): DonationServerApiInterface =
        Retrofit.Builder()
            .baseUrl(AppConstants.DONATION_SERVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DonationServerApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "db_price_converter"
        )
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun providePriceConverterDao(db: AppDatabase): PriceConverterDao = db.priceConverterDao()

    @Provides
    @Singleton
    fun provideCoinRepository(
        coingeckoAPi: CoingeckoApiInterface,
        bitPayApi: BitpayApiInterface,
        priceConverterDao: PriceConverterDao
    ): PriceConverterRepository {
        return PriceConverterRepositoryImpl(
            coingeckoAPi = coingeckoAPi,
            bitPayApi = bitPayApi,
            priceConverterDao = priceConverterDao
        )
    }

    @Provides
    @Singleton
    fun provideDonationRepository(
        donationServerApi: DonationServerApiInterface,
    ): DonationRepository {
        return DonationRepository(donationServerApi = donationServerApi)
    }
}
