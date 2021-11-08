package com.example.prueba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplahsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splahs)

        //ocultando la toolbart
        supportActionBar?.hide();
        //codificando la transiccion entre la pantalla de inicio splash y la siguiente pantalla
        Handler().postDelayed({
            val intent= Intent(this.applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)


    }
}