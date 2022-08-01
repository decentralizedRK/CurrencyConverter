package com.sampleapps.currencyconverter.api

import retrofit2.Response
import retrofit2.http.GET

interface CurrencyRateApi {
    @GET("latest.json?app_id=6f1449e9ed734a1cbf140b5cd6fb0acc")
    suspend fun fetchRates():Response<String>
}