package fr.isen.david.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPassword: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (SharedPreferences.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, Profile::class.java))
        }

        progressBar = findViewById(R.id.progressBar)
        etName = findViewById(R.id.etUserName)
        etPassword = findViewById(R.id.etUserPassword)

        // Login Button
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Register text
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        //calling the method userLogin() for login the user
        btnLogin.setOnClickListener(View.OnClickListener {
            userLogin()
        })

        //if user presses on textview it call the activity RegisterActivity
        tvRegister.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        })
    }

    private fun userLogin() {
        // First getting the values
        val email = etName.text.toString()
        val password = etPassword.text.toString()

        // Validating inputs
        if (TextUtils.isEmpty(email)) {
            etName.error = "Please enter your email"
            // etName.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Please enter your password"
            // etPassword.requestFocus()
            return
        }

        /*
        // If everything is fine
        val stringRequest = object : StringRequest(Request.Method.POST, URLs.URL_LOGIN,
            Response.Listener { response ->
                progressBar.visibility = View.GONE

                try {
                    //converting response to json object
                    val obj = JSONObject(response)

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        //getting the user from the response
                        val userJson = obj.getJSONObject("user")

                        //creating a new user object

                        val user = User(
                            userJson.getInt("id"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getString("gender")
                        )

                        /*
                        val user = User(
                            userJson.getInt("id_shop"),
                            userJson.getString("firstname"),
                            userJson.getString("lastname"),
                            userJson.getString("address"),
                            userJson.getString("email"),
                            userJson.getString("gender")
                        )

                        */

                        //storing the user in shared preferences
                        SharedPreferences.getInstance(applicationContext).userLogin(user)
                        //starting the MainActivity
                        finish()
                        startActivity(Intent(applicationContext, Profile::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                /*
                params["username"] = username
                params["password"] = password
                */
                params["id_shop"] = "1"
                params["email"] = email
                params["password"] = password
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        */

        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/login"
        val jsonObject = JSONObject()

        jsonObject.put("id_shop", "1")
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->

                val httpCode = Gson().fromJson(response.toString(), Connection::class.java)

                //save user id into shared preferences
                // (activity as ConnectionActivity)?.saveId(httpanswer.data.id,httpanswer.data.firstname,httpanswer.data.lastname)

                if( httpCode.code == "200" )  {
                    // Creating a new user object
                    val user = User(
                        httpCode.data.id,
                        httpCode.data.firstname,
                        httpCode.data.lastname
                    )

                    // Storing the user in shared preferences
                    SharedPreferences.getInstance(applicationContext).userLogin(user)

                    startActivity(Intent(applicationContext, Profile::class.java))

                } else {
                    Toast.makeText(this,"Did you forget your password or email ?",Toast.LENGTH_SHORT).show()
                }



            }, {
                // Error in request
                Log.i("","Volley error: $it")
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