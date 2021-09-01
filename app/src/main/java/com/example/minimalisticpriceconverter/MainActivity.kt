package com.example.minimalisticpriceconverter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var availableCurrencies = arrayOf<String>()

    private var parentLinearLayout: LinearLayout? = null

    private var currencies: MutableList<String> = mutableListOf()
    private var currencyViews: MutableList<View> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        availableCurrencies = resources.getStringArray(R.array.currencies)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val currencies = this.getPreferences(Context.MODE_PRIVATE)
            .getString(getString(R.string.currencies_key), "")

        parentLinearLayout = findViewById(R.id.parent_linear_layout)

        if (currencies != null && currencies != "") {
            val currenciesSplited = currencies.split(",")

            for (i in currenciesSplited.indices) {
                val currency = currenciesSplited[i]
                addRowViewAt(i, currency)
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view -> this.onAdd(view) }
    }

    private fun addRowViewAt(index: Int, currency: String) {
        val inflater =
            this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field, null)
        val spinner = rowView.findViewById<Spinner>(R.id.type_spinner)
        spinner.setSelection(availableCurrencies.indexOf(currency))

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