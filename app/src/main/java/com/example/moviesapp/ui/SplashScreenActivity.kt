package com.example.moviesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.moviesapp.MainActivity
import com.example.moviesapp.R

class SplashScreenActivity : AppCompatActivity() {

    // buat waktu delay
    private val delay : Long = 2000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // panggil delay handler
            Handler().postDelayed({

                // buat perpindahan activity yang akan dipanggil ketika delai selesai
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))

                finish()

            }, delay)


    }
}
