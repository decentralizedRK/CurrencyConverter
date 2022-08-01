package com.sampleapps.currencyconverter.ui


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sampleapps.currencyconverter.Entity.CurrencyExchange
import com.sampleapps.currencyconverter.R
import com.sampleapps.currencyconverter.model.CurrencyRate
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

   private lateinit var viewModel: MainActivityViewModel
   private lateinit var amount:EditText
   private lateinit var spinner:Spinner
   private lateinit var convertBtn:Button
   private lateinit var currencyRecycler:RecyclerView
   private lateinit var adapter: CurrencyAdapter
   private lateinit var progressBar: ProgressBar

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUI()
        viewModel.getDataFromAPI()
        viewModel.outputWorkInfo.observe(this,workInfoObserver())
        viewModel.currencyRatesDB.observe(this,dbDataObserver())
        convertBtn.setOnClickListener{
           adapter.updateList(spinner.selectedItem.toString(),
               if(amount.text.toString().isEmpty()) 0.0 else amount.text.toString().toDouble())
           adapter.notifyDataSetChanged()
       }
    }

    private fun initializeUI(){
        progressBar=findViewById(R.id.progressbar)
        progressBar.isVisible=true
        viewModel= ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[MainActivityViewModel ::class.java]
        amount= findViewById(R.id.editTextNumberDecimal)
        spinner= findViewById(R.id.spinner)
        convertBtn=findViewById(R.id.button2)
        convertBtn.isClickable=false
        currencyRecycler = findViewById(R.id.recyclerview)
        currencyRecycler.layoutManager=LinearLayoutManager(this)
        adapter=CurrencyAdapter()
        adapter.also { currencyRecycler.adapter = it }
    }

    /*made public for unit test
    *can be tested using reflect
    */
    private fun getCurrencyNames(list:ArrayList<CurrencyExchange>):ArrayList<String>{
        var arrList=ArrayList<String>()
        for(item in list) arrList.add(item.currencyName)
        return arrList
    }

    private fun setSpinner(list: ArrayList<CurrencyExchange>){
        val arrAdapter= ArrayAdapter(this@MainActivity,R.layout.spinner_item,getCurrencyNames(list))
        spinner.adapter=arrAdapter
        val selectedCurrency=spinner.selectedItem.toString()
        spinner.setSelection(arrAdapter.getPosition(if(selectedCurrency.isNullOrEmpty()) "USD" else selectedCurrency))
    }

    private suspend fun onDataFetchedFromAPISuccess(workInfo: WorkInfo){

        val resultJson:String= workInfo.outputData.keyValueMap[Constants.RATES_FROM_API] as String
        val mapper = jacksonObjectMapper()
        val currencyRate = mapper.readValue<CurrencyRate>(resultJson)
        val currencyExchangeList=ArrayList<CurrencyExchange>()
        for (item in currencyRate.rates)
        {
            currencyExchangeList.add(CurrencyExchange(item.key,item.value))
        }
        viewModel.setDataToDB(currencyExchangeList)
    }

    private fun workInfoObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }
            val workInfo = listOfWorkInfo[0]
            if (workInfo.state.isFinished && workInfo.state==WorkInfo.State.SUCCEEDED) {
                lifecycleScope.launch(){
                    onDataFetchedFromAPISuccess(workInfo)
                }
            }
        }
    }

    private fun dbDataObserver(): Observer<List<CurrencyExchange>> {
        return Observer { list ->

            if (list.isNullOrEmpty()) {
                return@Observer
            }
            setSpinner(list as ArrayList<CurrencyExchange>)
            adapter.updateList(list,spinner.selectedItem.toString(),
                if(amount.text.toString().isEmpty()) 0.0 else amount.text.toString().toDouble())
            adapter.notifyDataSetChanged()
            progressBar.isVisible=false
            convertBtn.isClickable=true
            }
        }
}
