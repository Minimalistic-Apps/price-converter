package com.example.minimalisticpriceconverter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private var parentLinearLayout: LinearLayout? = null

    private var currencies: List<String> = listOf()

    override fun onStart() {
        super.onStart()
        val currencies = this.getPreferences(Context.MODE_PRIVATE)
            .getString(getString(R.string.currencies_key), "")

        if (currencies != null) {
            this.currencies = currencies.split(",")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        parentLinearLayout = findViewById(R.id.parent_linear_layout)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view -> this.onAdd(view) }
    }

    fun onAdd(view: View) {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.field, null)
        parentLinearLayout?.addView(rowView, parentLinearLayout!!.childCount - 1)
    }

    fun onDelete(view: View) {
        parentLinearLayout?.removeView(view.parent as View)
    }

    fun saveCurrencies() {
        val _this = this

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.currencies_key), _this.currencies.joinToString(","))
            apply()
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