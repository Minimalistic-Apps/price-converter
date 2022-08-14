package com.minimalisticapps.priceconverter.room.dao

import androidx.room.*
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceConverterDao {
    @Query("SELECT * FROM coins where type !=:type order by name")
    fun fetchAllCoins(type: String = "crypto"): Flow<List<CoinGeckoExchangeRate>>

    @Transaction
    @Query("SELECT * FROM fiat_coin_exchange")
    fun fetchAllFiatCoins(): Flow<List<BitPayCoinWithFiatCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: CoinGeckoExchangeRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiatCoin(coin: FiatCoinExchange)

    @Delete
    suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange)

    @Update
    fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange)


}
