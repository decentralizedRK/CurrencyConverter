package com.sampleapps.currencyconverter.ui

import android.content.Context
import android.util.Log
import androidx.work.*
import com.sampleapps.currencyconverter.api.CurrencyRateApiInstance


class FetchCurrencyRatesWorker(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    private val TAG="FetchCurrencyRatesWorker"
    private var cnt=0;
    override suspend fun doWork(): Result {
        return try{
            Log.d(TAG,"Fetching data$cnt")
            val response = CurrencyRateApiInstance.api.fetchRates()

            if (response.isSuccessful && response.body()!=null){
                Log.d(TAG,"Fetching successful:$cnt")
                cnt++
                Result.success(workDataOf(Pair(Constants.RATES_FROM_API, response.body()!!)))
            }else {
                Log.d(TAG,"Unsuccessful response")
                Result.failure(workDataOf(Constants.ERROR_MSG to "Unsuccessful response"))
            }

        }catch (e:Exception){
            Log.d(TAG,"Exception occurred")
            return Result.failure(workDataOf(Constants.ERROR_MSG to e.message))
        }
    }
}