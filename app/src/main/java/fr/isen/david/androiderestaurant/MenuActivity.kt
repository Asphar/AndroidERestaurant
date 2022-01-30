package fr.isen.david.androiderestaurant

import android.content.Intent.getIntent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val ss:String = intent.getStringExtra("Button Text").toString()
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText(ss).toString()
        print(textView)
        val textViewValue = textView.text

    }

}


