package com.example.minimalisticpriceconverter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.minimalisticpriceconverter.abstractapi.AbstractApiRatesPlugin
import com.example.minimalisticpriceconverter.ratesapiplugin.Callback
import com.example.minimalisticpriceconverter.ratesapiplugin.RatesApiPlugin
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var availableCurrencies = arrayOf<String>()

    private var parentLinearLayout: LinearLayout? = null

    private var currencies: MutableList<String> = mutableListOf()
    private var currencyViews: MutableList<View> = mutableListOf()

    private var ratesBasedInUSD: Map<String, BigDecimal> = mapOf()

    private var ratePlugins: Map<String, RatesApiPlugin> =
        mapOf("AbstractApi" to AbstractApiRatesPlugin())

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    fun deleteBtc(view: View) {
        Toast.makeText(
            this@MainActivity,
            resources.getString(R.string.delete_btc),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        availableCurrencies = resources.getStringArray(R.array.shitcoins)

        setContentView(R.layout.activity_main)

        val currencies = this.getPreferences(Context.MODE_PRIVATE)
            .getString(getString(R.string.currencies_key), "")

        parentLinearLayout = findViewById(R.id.parent_linear_layout)

        if (currencies != null && currencies != "") {
            val currenciesSplit = currencies.split(",")

            for (i in currenciesSplit.indices) {
                val currency = currenciesSplit[i]
                addRowViewAt(i, currency)
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view -> this.onAdd(view) }

        if (isNetworkConnected()) {
            val plugin = ratePlugins["AbstractApi"] ?: throw Exception("Missing plugin AbstractApi")

            val _this = this

            plugin.call("8d8aaf3858904a149ae64f262c772667", object : Callback {
                override fun onSuccess(data: Map<String, BigDecimal>) {
                    ratesBasedInUSD = data

                    for (currencyFromRates in ratesBasedInUSD.keys) {

                        for (i in _this.currencies.indices) {
                            val currencyFromState = _this.currencies[i]
                            if (currencyFromRates == currencyFromState) {
                                val rateText =
                                    currencyViews[i].findViewById<TextView>(R.id.rate)
                                val formattedBtcPrice =
                                    formatBtcPrice(ratesBasedInUSD[currencyFromRates])
                                rateText.text = "1 $currencyFromRates = $formattedBtcPrice BTC"
                            }
                        }
                    }
                }

                override fun onFailure(t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Unable to load users: " + t.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
        } else {
            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again")
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun addRowViewAt(index: Int, currency: String) {
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field, null)
        val spinner = rowView.findViewById<Spinner>(R.id.type_spinner)
        spinner.setSelection(availableCurrencies.indexOf(currency))
        val rateText = rowView.findViewById<TextView>(R.id.rate)
        val formattedBtcPrice = formatBtcPrice(ratesBasedInUSD[currency])
        rateText.text = "1 $currency = $formattedBtcPrice BTC"

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (view == null || parent == null) {
                    return
                }

                val i = currencyViews.indexOf(parent.parent as View)
                if (i == -1) {
                    return
                }
                val selectedCurrency = availableCurrencies.get(position)

                val rateTextToUpdate =
                    (parent.parent as View).findViewById<TextView>(R.id.rate)

                val selectedFormattedBtcPrice = formatBtcPrice(ratesBasedInUSD[selectedCurrency])
                rateTextToUpdate.text = "1 $selectedCurrency = $selectedFormattedBtcPrice BTC"

                currencies[i] = selectedCurrency
                saveCurrencies()
            }
        }

        parentLinearLayout?.addView(rowView, parentLinearLayout!!.childCount)

        currencies.add(index, currency)
        this.currencyViews.add(index, rowView)

    }

    fun onAdd(view: View) {
        val remainingCurrencies = availableCurrencies.subtract(currencies)
        if (remainingCurrencies.isEmpty()) {
            return
        }
        val currency = remainingCurrencies.first()

        addRowViewAt(currencies.size, currency)
        saveCurrencies()
    }

    fun onDelete(view: View) {
        val i = currencyViews.indexOf(view.parent as View)
        parentLinearLayout?.removeView(view.parent as View)

        currencies.removeAt(i)
        currencyViews.removeAt(i)

        saveCurrencies()
    }

    private fun saveCurrencies() {
        val _this = this

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            val data = _this.currencies.joinToString(",")
            Log.v(TAG, "saveCurrencies $data")

            putString(getString(R.string.currencies_key), data)
            apply()
            commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}