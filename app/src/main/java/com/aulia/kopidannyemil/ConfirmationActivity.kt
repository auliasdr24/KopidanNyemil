package com.aulia.kopidannyemil

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val username = intent.getStringExtra("USERNAME") ?: "Aulia"
        val total = intent.getStringExtra("TOTAL") ?: "Rp 0"
        val orderDetail = intent.getStringExtra("ORDER_DETAIL") ?: ""

        // === Generate Nomor Pesanan ===
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val dateStr = dateFormat.format(Date())
        val randomNum = Random().nextInt(900) + 100  // 100-999
        val orderNumber = "ORD-$dateStr-$randomNum"

        // === Tampilkan nomor pesanan ===
        val tvOrderNumber = findViewById<TextView>(R.id.tvOrderNumber)
        tvOrderNumber.text = "No. Pesanan: $orderNumber"

        // === Tampilkan total ===
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        tvTotal.text = total

        // === Tampilkan detail pesanan ===
        val llOrderItems = findViewById<LinearLayout>(R.id.llOrderItems)
        val poppinsRegular: Typeface? = try {
            ResourcesCompat.getFont(this, R.font.poppins_regular)
        } catch (e: Exception) {
            null
        }

        if (orderDetail.isNotEmpty()) {
            orderDetail.split("\n").forEach { line ->
                val tvItem = TextView(this).apply {
                    text = "• $line"
                    textSize = 14f
                    setTextColor(resources.getColor(R.color.brown_dark, theme))
                    poppinsRegular?.let { typeface = it }
                    setPadding(0, 4, 0, 4)
                }
                llOrderItems.addView(tvItem)
            }
        } else {
            val tvItem = TextView(this).apply {
                text = "Tidak ada item"
                textSize = 14f
                setTextColor(resources.getColor(R.color.brown_light, theme))
                poppinsRegular?.let { typeface = it }
            }
            llOrderItems.addView(tvItem)
        }

        // === Kosongkan cart setelah order berhasil ===
        Cart.clear()

        // === Tombol Pesan Lagi → balik ke Home ===
        val btnPesanLagi = findViewById<Button>(R.id.btnPesanLagi)
        btnPesanLagi.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", username)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // === Tombol Selesai → balik ke Welcome ===
        val btnSelesai = findViewById<Button>(R.id.btnSelesai)
        btnSelesai.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}