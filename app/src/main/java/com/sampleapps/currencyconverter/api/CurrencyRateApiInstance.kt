package com.sampleapps.currencyconverter.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object CurrencyRateApiInstance {
    val api:CurrencyRateApi by lazy{
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CurrencyRateApi::class.java)
    }
}