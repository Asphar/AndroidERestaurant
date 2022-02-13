package fr.isen.david.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.david.androiderestaurant.databinding.ActivityDetailBinding
import java.io.File


class ActivityDetail : HeaderActivity() {
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_detail)

        // var item: String? = ""

        val itemDish = intent.getSerializableExtra("itemDish") as DishModel

        // Course title
        val detail_title = findViewById<TextView>(R.id.detailtitle)
        detail_title.setText(itemDish.name_fr)

        // Dish title
        val detail_text = findViewById<TextView>(R.id.detailtext)

        // Image viewer
        val detail_image = findViewById<ImageView>(R.id.detailImage)

        if (itemDish.images[0]!="") {
            Picasso.get()
                .load(itemDish.images[0])
                .placeholder(R.drawable.coyote)
                .error(R.drawable.coyote)
                .fit()
                .into(detail_image)
        }

        else {
            detail_image.setImageResource(R.drawable.coyote)
        }

        val detail_price = findViewById<TextView>(R.id.detailprice)
        detail_price.setText("Total : " + itemDish.prices[0].price + "€")


        for (index in itemDish.ingredients)
            detail_text.append(index.name_fr + "\n")


        // Set the number of basket count
        var basketCount = 1
        val quantity = findViewById<TextView>(R.id.quantity)
        quantity.text = "$basketCount"


        // Plus button
        val plus = findViewById<FloatingActionButton>(R.id.plus)

        plus.setOnClickListener {
            basketCount++

            val totalprice = (itemDish.prices[0].price.toFloat()) * basketCount.toFloat()
            detail_price.text = "Total   " + "$totalprice" + "€"
            quantity.text = "$basketCount"


        }

        // Minus button
        val moins = findViewById<FloatingActionButton>(R.id.moins)
        moins.setOnClickListener() {
            if (basketCount > 1) basketCount--
            else basketCount = 1

            val totalprice: Float = (itemDish.prices[0].price.toFloat()) * basketCount
            detail_price.text = "Total   " + "$totalprice" + "€"
            quantity.text = "$basketCount"

        }

        // Set an onClickListener on the price
        detail_price.setOnClickListener() {
            if (SharedPreferences.getInstance(this).isLoggedIn) {
                val itemtoadd = DishBasket(itemDish, basketCount)
                val filename = "/basket.json"
                val file = File(cacheDir.absolutePath + filename)
                var quantityalreadyinbasket: Int = 0
                var namealeradyinbasket: String = ""
                var notinbasket: Boolean = false
                Snackbar.make(view, "Ajouté au panier", Snackbar.LENGTH_LONG).show()

                if (file.exists()) {
                    var dishbasket = Gson().fromJson(file.readText(), SavedDishInBasket::class.java)


                    for (item in dishbasket.list) {
                        if (item.itemdish.name_fr == itemtoadd.itemdish.name_fr) {
                            quantityalreadyinbasket = itemtoadd.quantity + item.quantity
                            namealeradyinbasket = item.itemdish.name_fr
                            notinbasket = false
                        } else {
                            notinbasket = true
                        }
                    }

                    if (dishbasket.list.size == 0) notinbasket = true


                    if (notinbasket == true) dishbasket.list.add(itemtoadd)


                    dishbasket.list.forEach {
                        if (it.itemdish.name_fr == namealeradyinbasket) it.quantity =
                            quantityalreadyinbasket
                    }

                    file.writeText(Gson().toJson(SavedDishInBasket(dishbasket.list)))
                } else {
                    file.writeText(Gson().toJson(SavedDishInBasket(arrayListOf(itemtoadd))))
                }

            } else {
                val intent = Intent(this@ActivityDetail, LoginActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
    }
}
