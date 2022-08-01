package com.sampleapps.currencyconverter.model

import java.util.*

data class CurrencyRate(
    val base: String,
    val disclaimer: String,
    val license: String,
    val rates: TreeMap<String,Double>,
    val timestamp: Int
)