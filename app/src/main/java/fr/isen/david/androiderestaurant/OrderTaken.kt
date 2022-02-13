package fr.isen.david.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class OrderTaken : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_taken)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                // This method will be executed once the timer is over
                val intent = Intent(this@OrderTaken, HomeActivity::class.java)
                startActivity(intent)
                finish()
            },
            3000 // value in milliseconds
        )
    }
}