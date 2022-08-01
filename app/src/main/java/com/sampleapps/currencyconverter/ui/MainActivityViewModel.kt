package com.sampleapps.currencyconverter.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import com.sampleapps.currencyconverter.dao.CurrencyExchangeDatabase
import com.sampleapps.currencyconverter.repository.CurrencyExchangeRepo
import java.util.concurrent.TimeUnit

class MainActivityViewModel(application: Application): AndroidViewModel(application) {

    var  currencyRatesDB:LiveData<List<CurrencyExchange>>
    private var repo: CurrencyExchangeRepo
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfo: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(Constants.OUTPUT_TAG)

    init {
        val dao= CurrencyExchangeDatabase.getDatabase(application).currencyExchangeDao()
        repo=CurrencyExchangeRepo(dao)
        currencyRatesDB= repo.currencyExchangeList
    }

    fun getDataFromAPI(){
        val ratesWorker= OneTimeWorkRequestBuilder<FetchCurrencyRatesWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setPeriodStartTime(30, TimeUnit.MINUTES)
            .addTag(Constants.OUTPUT_TAG)
            .build()
        workManager.enqueue(ratesWorker)
    }

    suspend fun setDataToDB(list: ArrayList<CurrencyExchange>){
        repo.insertCurrencyRates(list)
    }

}