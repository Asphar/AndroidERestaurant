package fr.isen.david.androiderestaurant


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONObject.NULL


class MenuActivity : AppCompatActivity(), CellClickListener  {
    // private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        // binding = ActivityHomeBinding.inflate(layoutInflater)
        // setContentView(binding.root)

        val ss:String = intent.getStringExtra("Button Text").toString()
        val textView = findViewById<TextView>(R.id.textView)
        textView.setText(ss).toString()
        print(textView)
        val textViewValue = textView.text

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


        /*
        // TEST onClickRecycler
        recyclerview.addOnItemTouchListener(
            RecyclerItemClickListener(context, recyclerview, object :
                RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {

                }

                override fun onLongItemClick(view: View?, position: Int) {

                }
            })
        )
        */



        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..5) {
            data.add(ItemsViewModel(R.drawable.coyote, "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data, this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // Request API request
        val btn = findViewById<Button>(R.id.button4)
        btn.setOnClickListener {
            // val url = "https://postman-echo.com/post"
            val url = "http://test.api.catering.bluecodegames.com/menu"

            textView.text = ""

            // Post parameters
            // Form fields and values
            val params = HashMap<String, String>()
            params["id_shop"] = "1"

            val jsonObject = JSONObject(params as Map<*, *>?)

            // Volley post request with parameters
            val request = JsonObjectRequest(Request.Method.POST, url, jsonObject,
                { response ->
                    // Process the json
                    try {
                        var gson = Gson()
                        // var dishresult = gson.fromJson(response.toString(), DishResult::class.java)
                        // displayDishes(dishresult.data.firstOrNull { it.name_fr == ss }?.items ?: listOf())

                        textView.text = "Response: $response"

                        // println("> From JSON String:\n" + menu_result)

                        // textView.text = "Response : $menu_result"

                    } catch (e: Exception) {
                        textView.text = "Exception: $e"
                    }

                }, {
                    // Error in request
                    textView.text = "Volley error: $it"
                })


            // Volley request policy, only one time request to avoid duplicate transaction
            request.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                // 0 means no retry
                0, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES = 2
                1f // DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

            // Add the volley post request to the request queue
            VolleySingleton.getInstance(this).addToRequestQueue(request)
        }

    }

    private fun displayDishes (dishresult: List<ItemsViewModel>){
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(dishresult,this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }


    override fun onCellClickListener(data: ItemsViewModel) {
        val monIntent =  Intent(this, activity_detail::class.java)
        monIntent.putExtra("itemDish", "test")

        startActivity(monIntent)
    }


}