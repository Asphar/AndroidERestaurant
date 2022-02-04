package fr.isen.david.androiderestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class RegisterActivity : AppCompatActivity() {

    // private lateinit var editTextUsername: EditText
    private lateinit var editTextFirstname: EditText
    private lateinit var editTextLastname: EditText
    private lateinit var editTextAddress: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var radioGroupGender: RadioGroup
    internal lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        progressBar = findViewById(R.id.progressBar)

        //if the user is already logged in we will directly start the MainActivity (profile) activity
        if (SharedPreferences.getInstance(this).isLoggedIn) {
            finish()
            startActivity(Intent(this, Profile::class.java))
            return
        }

        // editTextUsername = findViewById(R.id.editTextUsername)
        editTextFirstname = findViewById(R.id.editTextFirstname)
        editTextLastname = findViewById(R.id.editTextLastname)
        editTextAddress = findViewById(R.id.editTextAddress)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        radioGroupGender = findViewById(R.id.radioGender)

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        val textViewLogin = findViewById<TextView>(R.id.textViewLogin)

        /*
        val test = findViewById<Button>(R.id.test)
        val textView = findViewById<TextView>(R.id.test1)


        // Run volley
        test.setOnClickListener {
            val url = "http://test.api.catering.bluecodegames.com/user/register\n"
            textView.text = ""

            // Post parameters
            // Form fields and values
            val params = HashMap<String, String>()
            params["id_shop"] = "1"
            params["firstname"] = "isenaeaegaeg"
            params["lastname"] = "yncreagagega"
            params["address"] = "12 Pl. des Abattoirs, 13015 Marseille"
            params["email"] = "somethingalongtheline@gmail.com"
            params["password"] = "something123456789#"


            val jsonObject = JSONObject(params as Map<*, *>?)

            // Volley post request with parameters
            val request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject,
                { response ->
                    // Process the json
                    try {
                        textView.text = "Response: $response"
                    } catch (e: Exception) {
                        textView.text = "Exception: $e"
                    }

                    Log.d("", "$response")
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
            VolleySingleton.getInstance(this).addToRequestQueue(request)

        }
        */
        buttonRegister.setOnClickListener {
            //if user pressed on button register
            //here we will register the user to server
            registerUser()
            Log.d("TEST ERROR", "Breakpoint Reached")
        }

        textViewLogin.setOnClickListener {
            finish()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun registerUser() {
        // val username = editTextUsername.text.toString().trim { it <= ' ' }
        val firstname = editTextFirstname.text.toString().trim { it <= ' ' }
        val lastname = editTextLastname.text.toString().trim { it <= ' ' }
        val address = editTextAddress.text.toString().trim { it <= ' ' }
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password = editTextPassword.text.toString().trim { it <= ' ' }

        val gender = (findViewById<View>(radioGroupGender.checkedRadioButtonId) as RadioButton).text.toString()

        //first we will do the validations
        /*
        if (TextUtils.isEmpty(username)) {
            editTextUsername.error = "Please enter username"
            editTextUsername.requestFocus()
            return
        }
        */

        if (TextUtils.isEmpty(firstname)) {
            editTextFirstname.error = "Please enter your first name"
            editTextFirstname.requestFocus()
            return
        }

        if (TextUtils.isEmpty(lastname)) {
            editTextLastname.error = "Please enter your last name"
            editTextLastname.requestFocus()
            return
        }

        if (TextUtils.isEmpty(address)) {
            editTextAddress.error = "Please enter your address"
            editTextAddress.requestFocus()
            return
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.error = "Please enter your email"
            editTextEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.error = "Enter a valid email"
            editTextEmail.requestFocus()
            return
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.error = "Enter a password"
            editTextPassword.requestFocus()
            return
        }


        val stringRequest = object : StringRequest(Method.POST, URLs.URL_REGISTER,
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
                            userJson.getString("password")
                        )

                        */

                        // Storing the user in shared preferences
                        SharedPreferences.getInstance(applicationContext).userLogin(user)

                        //starting the MainActivity activity
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
                params["id_shop"] = "1"
                params["firstname"] = firstname
                params["lastname"] = lastname
                params["address"] = address
                params["email"] = email
                params["password"] = password

                // params["username"] = username
                // params["email"] = email
                // params["password"] = password
                // params["gender"] = gender
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}