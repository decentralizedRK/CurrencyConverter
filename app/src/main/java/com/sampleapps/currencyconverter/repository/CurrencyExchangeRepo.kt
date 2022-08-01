package com.sampleapps.currencyconverter.repository

import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import com.sampleapps.currencyconverter.dao.CurrencyExchangeDao

class CurrencyExchangeRepo(private val currencyExchangeDao: CurrencyExchangeDao) {
    val currencyExchangeList=currencyExchangeDao.getRates()

    suspend fun insertCurrencyRates(currencyExchangeList: ArrayList<CurrencyExchange>){
         for (item in currencyExchangeList)
             currencyExchangeDao.insert(item)
    }
}