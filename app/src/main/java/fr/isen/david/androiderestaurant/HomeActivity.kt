package fr.isen.david.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import fr.isen.david.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your appetizer",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.button2.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your main course",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        binding.button3.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your dessert",Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        
    }
}