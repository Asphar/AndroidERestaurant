package fr.isen.david.androiderestaurant


import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.isen.david.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONException
import org.json.JSONObject


class MenuActivity : AppCompatActivity() {
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

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.coyote, "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

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
            val params = HashMap<String,String>()
            params["id_shop"] = "1"

            val jsonObject = JSONObject(params as Map<*, *>?)

            // Volley post request with parameters
            val request = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                { response ->
                    // Process the json
                    try {
                        textView.text = "Response: $response"
                    }catch (e:Exception){
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

}


