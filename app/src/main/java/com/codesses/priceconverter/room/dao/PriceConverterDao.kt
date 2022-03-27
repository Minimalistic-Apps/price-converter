package com.codesses.priceconverter.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRate
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceConverterDao {
    @Query("SELECT * FROM coins where type =:type")
    fun fetchAllCoins(type:String = "fiat"): Flow<List<BitPayExchangeRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: BitPayExchangeRate)

    @Query("DELETE FROM coins where code = :code")
    suspend fun deleteCustomerById(code: String)

    @Query("DELETE FROM coins")
    suspend fun deleteAllCustomer()
}
