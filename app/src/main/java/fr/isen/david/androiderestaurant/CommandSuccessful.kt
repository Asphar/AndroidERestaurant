package fr.isen.david.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class CommandSuccessful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_successful)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                // This method will be executed once the timer is over
                val intent = Intent(this@CommandSuccessful, OrderTaken::class.java)
                startActivity(intent)
                finish()
            },
            6000 // value in milliseconds
        )
    }
}