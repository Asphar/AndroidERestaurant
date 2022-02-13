package fr.isen.david.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.david.androiderestaurant.databinding.ActivityBasketBinding
import java.io.File


class BasketActivity : HeaderActivity(), CellClickListener {

    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        val filename = "/basket.json"
        val file = File(cacheDir.absolutePath + filename)


        if (file.exists())
        {
            val lu = Gson().fromJson(file.readText(), SavedDishInBasket::class.java)
            displayBasket(lu.list)
        }

        binding.order.setOnClickListener {
            val monIntent: Intent = Intent(this, CommandSuccessful::class.java)
            startActivity(monIntent)

        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun displayBasket(dishBasket: ArrayList<DishBasket>) {

        val recyclerview = binding.listinbasket

        recyclerview.layoutManager = LinearLayoutManager(this)

        val adapter = CustomAdapterForBasket(dishBasket,this)

        recyclerview.adapter = adapter


    }

    override fun onCellClickListener(data: DishModel) {

    }



    override fun onCellClickListenerBasket(data : DishBasket) {
        val filename = "/basket.json"
        val file = File(cacheDir.absolutePath + filename)
        val lu = Gson().fromJson(file.readText(), SavedDishInBasket::class.java)
        lu.list.remove(
            DishBasket(data.itemdish,data.quantity)
        )

        file.writeText(Gson().toJson(SavedDishInBasket(lu.list)))
        displayBasket(lu.list)



    }
}