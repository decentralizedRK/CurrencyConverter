package com.sampleapps.currencyconverter.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItem
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyExchangeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: CurrencyExchangeDatabase
    private lateinit var dao: CurrencyExchangeDao

    @Before
    fun setup(){
        database=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
        CurrencyExchangeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao=database.currencyExchangeDao()
    }

    @After
    fun close(){
        database.close()
    }

    @Test
    fun insertCurrencyExchangeTest() = runBlocking(Dispatchers.Main){
        val currency =CurrencyExchange("RK",12000000.0)
        dao.insert(currency)

        val currencyExchangeList=dao.getRates().getOrAwaitValue()
        if(currencyExchangeList[0].currencyName==currency.currencyName &&
            currencyExchangeList[0].rate==currency.rate){
            assert(true)
        }else assert(false)
    }

}