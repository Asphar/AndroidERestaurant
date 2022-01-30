package fr.isen.david.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            intent.putExtra("Button Text", "Appetizer menu")
            startActivity(intent)

        }

        binding.button2.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your main course",Toast.LENGTH_SHORT).show()
            intent.putExtra("Button Text", "Main course menu")
            startActivity(intent)
        }

        binding.button3.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your dessert",Toast.LENGTH_SHORT).show()
            intent.putExtra("Button Text", "Dessert menu")
            startActivity(intent)
        }

    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeActivity", "HomeActivity has been stopped");
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeActivity", "HomeActivity has been destroyed");
    }


}