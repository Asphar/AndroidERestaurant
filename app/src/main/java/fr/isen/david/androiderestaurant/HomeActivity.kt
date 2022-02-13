package fr.isen.david.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.david.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : HeaderActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your appetizer",Toast.LENGTH_SHORT).show()
            intent.putExtra("category_type", getString(R.string.entrees))
            startActivity(intent)

        }

        binding.button2.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your main course",Toast.LENGTH_SHORT).show()
            intent.putExtra("category_type", getString(R.string.plats))
            startActivity(intent)
        }

        binding.button3.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            Toast.makeText(this,"Choose your dessert",Toast.LENGTH_SHORT).show()
            intent.putExtra("category_type", getString(R.string.desserts))
            startActivity(intent)

        }

        binding.login.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            Toast.makeText(this, "Login Page", Toast.LENGTH_SHORT).show()
            intent.putExtra("category_type", getString(R.string.login))
            startActivity(intent)
        }

        binding.register.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            Toast.makeText(this, "Register Page", Toast.LENGTH_SHORT).show()
            intent.putExtra("category_type", getString(R.string.register))
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