package com.sampleapps.currencyconverter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import com.sampleapps.currencyconverter.R

class CurrencyAdapter :
    RecyclerView.Adapter<CurrencyListViewHolder>() {
    private val convertedList = ArrayList<CurrencyExchange>()
    private var currentRateList = ArrayList<CurrencyExchange>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_currency_list, parent, false)
        return CurrencyListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        if(convertedList!=null){
        val currentItem = convertedList[position]
        holder.value.text= String.format("%.3f",currentItem.rate)
        holder.currency.text= currentItem.currencyName
        }
    }

    override fun getItemCount(): Int {
        return convertedList.size
    }

    fun updateList(list:List<CurrencyExchange>,conversionFrom:String,amountToConvert: Double){
        var selCurrValInUSD=1.0
        if(list!==null){
            convertedList.clear()
            currentRateList.clear()
            for(item in list){
                if(conversionFrom == item.currencyName){
                    selCurrValInUSD=1.0/item.rate
                    break;
                }
            }
            for (item in list)
            {
                convertedList.add(CurrencyExchange(item.currencyName,amountToConvert*item.rate*selCurrValInUSD))
                currentRateList.add(item)
            }
        }
    }

    fun updateList(conversionFrom:String,amountToConvert: Double){
        convertedList.clear()
        var selCurrValInUSD:Double=1.0
        if(currentRateList!==null){
            for(item in currentRateList){
                if(conversionFrom == item.currencyName){
                    selCurrValInUSD=1.0/item.rate
                    break;
                }
            }
            for (item in currentRateList)
            {
                convertedList.add(CurrencyExchange(item.currencyName,amountToConvert*item.rate*selCurrValInUSD))
            }
        }
    }
}

class CurrencyListViewHolder(listView: View): RecyclerView.ViewHolder(listView) {
    val value:TextView = listView.findViewById(R.id.value)
    val currency:TextView =listView.findViewById(R.id.currencyName)
}