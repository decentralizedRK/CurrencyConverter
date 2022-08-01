package com.sampleapps.currencyconverter.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sampleapps.currencyconverter.Entity.CurrencyExchange

@Database(entities = [CurrencyExchange::class], version = 1, exportSchema = false)
 abstract class CurrencyExchangeDatabase : RoomDatabase() {

    abstract fun currencyExchangeDao(): CurrencyExchangeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CurrencyExchangeDatabase? = null

        fun getDatabase(context: Context): CurrencyExchangeDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyExchangeDatabase::class.java,
                    "word_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
