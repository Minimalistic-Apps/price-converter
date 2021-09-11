package com.example.minimalisticpriceconverter

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        arrayOf(R.id.source_code_link, R.id.mail_link, R.id.license).forEach {
            findViewById<TextView>(it).movementMethod = LinkMovementMethod.getInstance()
        }

        findViewById<TextView>(R.id.version).text =
            getString(R.string.version) + ": " + BuildConfig.VERSION_NAME
    }
}