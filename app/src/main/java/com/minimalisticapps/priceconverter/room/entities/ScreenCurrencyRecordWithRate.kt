package com.minimalisticapps.priceconverter.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.minimalisticapps.priceconverter.data.repository.priceconverter.CurrencyRate

data class ScreenCurrencyRecordWithRate(
    @Embedded
    val screenCurrencyRecord: ScreenCurrencyRecord,

    @Relation(
        parentColumn = "code",
        entity = CurrencyRate::class,
        entityColumn = "code"
    )
    val currencyRate: CurrencyRate
)
