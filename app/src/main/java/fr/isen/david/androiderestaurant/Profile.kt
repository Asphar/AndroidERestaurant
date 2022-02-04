package fr.isen.david.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView

class Profile : AppCompatActivity(), View.OnClickListener {

    private lateinit var id: TextView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var gender: TextView
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        if (SharedPreferences.getInstance(this).isLoggedIn) {
            id = findViewById(R.id.textViewId)
            userName = findViewById(R.id.textViewUsername)
            userEmail = findViewById(R.id.textViewEmail)
            gender = findViewById(R.id.textViewGender)
            btnLogout = findViewById(R.id.buttonLogout)

            val user = SharedPreferences.getInstance(this).user

            id.text = user.id.toString()
            userEmail.text = user.email
            gender.text = user.gender
            userName.text = user.name

            btnLogout.setOnClickListener(this)

        } else {
            val intent = Intent(this@Profile, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onClick(view: View) {
        if (view == btnLogout) {
            SharedPreferences.getInstance(applicationContext).logout()
        }
    }
}