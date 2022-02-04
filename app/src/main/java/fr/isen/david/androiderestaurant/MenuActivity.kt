package fr.isen.david.androiderestaurant


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.david.androiderestaurant.databinding.ActivityMenuBinding
import org.json.JSONObject


class MenuActivity : AppCompatActivity(), CellClickListener {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)




        var category: String? = ""
        if (intent.hasExtra("category_type")) {
            category = intent.getStringExtra("category_type")
        }

        val textViewCategory = binding.category
        textViewCategory.setText(category)


        val btn = findViewById<Button>(R.id.button4)
        btn.setOnClickListener {
            //http request to the API
            val queue = Volley.newRequestQueue(this)
            val url = "http://test.api.catering.bluecodegames.com/menu"
            val jsonObject = JSONObject()
            jsonObject.put("id_shop", "1")

            // Request a string response from the provided URL.
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->

                    var gson = Gson()
                    var dishresult = gson.fromJson(response.toString(), DishResult::class.java)
                    displayDishes(
                        dishresult.data.firstOrNull { it.name_fr == category }?.items ?: listOf()
                    )
                    Log.d("", "$response")
                }, {
                    // Error in request
                    Log.i("", "Volley error: $it")
                })

            // Volley request policy, only one time request to avoid duplicate transaction
            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            // Add the volley post request to the request queue
            queue.add(request)
        }
    }

    private fun displayDishes (dishresult: List<DishModel>){
        // Getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // This creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(dishresult, this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }


    override fun onCellClickListener(data: DishModel) {
        val monIntent : Intent =  Intent(this, Activity_detail::class.java)
        monIntent.putExtra("itemDish", data)
        startActivity(monIntent)
    }
}

