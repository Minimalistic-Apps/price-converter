package com.minimalisticapps.priceconverter.presentation.states

import android.os.Parcel
import android.os.Parcelable
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate

data class CoinsState(
    val isLoading: Boolean = false,
    val coins: List<BitPayExchangeRate> = emptyList(),
    val error: String = "",
    val isRefreshing: Boolean = false
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        TODO("coins"),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isLoading) 1 else 0)
        parcel.writeString(error)
        parcel.writeByte(if (isRefreshing) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinsState> {
        override fun createFromParcel(parcel: Parcel): CoinsState {
            return CoinsState(parcel)
        }

        override fun newArray(size: Int): Array<CoinsState?> {
            return arrayOfNulls(size)
        }
    }
}