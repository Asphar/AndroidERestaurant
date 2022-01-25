package fr.isen.david.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import fr.isen.david.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener(){
            Toast.makeText(this,"Choose your appetizer",Toast.LENGTH_SHORT).show()
        }

        binding.button2.setOnClickListener(){
            Toast.makeText(this,"Choose your main course",Toast.LENGTH_SHORT).show()
        }

        binding.button3.setOnClickListener(){
            Toast.makeText(this,"Choose your dessert",Toast.LENGTH_SHORT).show()
        }


    }
}