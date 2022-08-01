package com.sampleapps.currencyconverter.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sampleapps.currencyconverter.Entity.CurrencyExchange

@Dao
interface CurrencyExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyExchange:CurrencyExchange)

    @Query("select * from currency_rates order by currencyName ASC")
    fun getRates():LiveData<List<CurrencyExchange>>
}