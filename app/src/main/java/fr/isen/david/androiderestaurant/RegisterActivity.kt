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
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
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
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        progressBar = findViewById(R.id.progressBar)

        // If the user is already logged in we will directly start the profile activity
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


    private fun registerUser() {
        // val username = editTextUsername.text.toString().trim { it <= ' ' }
        val firstname = editTextFirstname.text.toString().trim { it <= ' ' }
        val lastname = editTextLastname.text.toString().trim { it <= ' ' }
        val address = editTextAddress.text.toString().trim { it <= ' ' }
        val email = editTextEmail.text.toString().trim { it <= ' ' }
        val password = editTextPassword.text.toString().trim { it <= ' ' }

        val gender = (findViewById<View>(radioGroupGender.checkedRadioButtonId) as RadioButton).text.toString()


        // Test entries
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

        // Volley request
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/register"
        val jsonObject = JSONObject()

        jsonObject.put("id_shop", "1")
        jsonObject.put("firstname", firstname)
        jsonObject.put("lastname", lastname)
        jsonObject.put("address", address)
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            { response ->
                val httpCode = Gson().fromJson(response.toString(), Connection::class.java)

                if ( httpCode.code == "200" )  {

                    // Creating a new user object
                    val user = User(
                        httpCode.data.id,
                        httpCode.data.firstname,
                        httpCode.data.lastname
                    )

                    // Storing the user in shared preferences
                    SharedPreferences.getInstance(applicationContext).userLogin(user)

                    startActivity(Intent(applicationContext, LoginActivity::class.java))

                } else {
                    Log.e("HTTP ERROR CODE", (httpCode.code))
                }
                Log.d("", "$response")
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