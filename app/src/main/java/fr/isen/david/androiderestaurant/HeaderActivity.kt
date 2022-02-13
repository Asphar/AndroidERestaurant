package fr.isen.david.androiderestaurant

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


open class HeaderActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.menuButtonBasket -> {
                val monIntent : Intent =  Intent(this, BasketActivity::class.java)
                startActivity(monIntent)
                true
            }
            R.id.menuButtonUser -> {
                val monIntent : Intent =  Intent(this, Profile::class.java)
                startActivity(monIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}