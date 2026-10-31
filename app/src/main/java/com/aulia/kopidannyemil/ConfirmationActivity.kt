package com.aulia.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        // Ambil username dari intent
        val username = intent.getStringExtra("USERNAME") ?: "Aulia"

        // Tombol Oke → balik ke Home
        val btnOk = findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", username)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}