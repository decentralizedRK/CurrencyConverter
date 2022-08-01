package com.sampleapps.currencyconverter.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MainActivityTest{
    @get :Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun testGetCurrencyNames() {
        val mainActivity=MainActivity()
        val currency1 =CurrencyExchange("RK1",12000000.0)
        val currency2 =CurrencyExchange("RK2",13000000.0)
        val listCurrencyExchange = ArrayList<CurrencyExchange>()
        listCurrencyExchange.add(currency1)
        listCurrencyExchange.add(currency2)

        val listStr=ArrayList<String>()
        listStr.add("RK1")
        listStr.add("RK2")
        val result=mainActivity.getCurrencyNames(listCurrencyExchange)
        assertArrayEquals(listStr.toArray(),result.toArray())

    }
}