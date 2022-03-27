package com.codesses.priceconverter.data.remote.dto
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Coins")
data class BitPayExchangeRate(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    @SerializedName("unit")
    val code: String,
    @ColumnInfo(name = "rate")
    @SerializedName("value")
    val rate: Double,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String
)
{
    override fun toString(): String {
        return name
    }
}