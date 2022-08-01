package com.sampleapps.currencyconverter.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
class CurrencyExchange(@PrimaryKey @ColumnInfo(name = "currencyName") val currencyName: String,
                       @ColumnInfo(name = "rate") val rate:Double)