package com.minimalisticapps.priceconverter.room.dao

import androidx.room.*
import com.minimalisticapps.priceconverter.data.repository.priceconverter.Shitcoin
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreen
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreenWithRate
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceConverterDao {
    @Query("SELECT * FROM shitcoins ORDER BY name")
    fun fetchShitcoins(): Flow<List<Shitcoin>>

    @Transaction
    @Query("SELECT * FROM shitcoins_on_screen")
    fun fetchAllShitcoinOnScreen(): Flow<List<ShitcoinOnScreenWithRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShitcoin(coin: Shitcoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShitcoinOnScreen(coin: ShitcoinOnScreen)

    @Delete
    suspend fun deleteShitcoinOnScreen(shitcoinOnScreen: ShitcoinOnScreen)

    @Update
    fun updateShitcoinOnScreen(shitcoinOnScreen: ShitcoinOnScreen)
}
