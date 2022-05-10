package com.minimalisticapps.priceconverter.data.remote.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Coins")
data class BitPayExchangeRate(
    @ColumnInfo(name = "code")
    var code: String,
    @ColumnInfo(name = "rate")
    @SerializedName("value")
    val rate: Double,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "unit")
    @SerializedName("unit")
    val unit: String,
    @ColumnInfo(name = "one_shit_coin_value")
    var oneShitCoinValue: Double,
    @ColumnInfo(name = "one_shit_coin_value_string")
    var oneShitCoinValueString: String,

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeDouble(rate)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(unit)
        parcel.writeDouble(oneShitCoinValue)
        parcel.writeString(oneShitCoinValueString)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BitPayExchangeRate> {
        override fun createFromParcel(parcel: Parcel): BitPayExchangeRate {
            return BitPayExchangeRate(parcel)
        }

        override fun newArray(size: Int): Array<BitPayExchangeRate?> {
            return arrayOfNulls(size)
        }
    }

}