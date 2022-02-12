package fr.isen.david.androiderestaurant


import android.content.Context
import android.content.Intent

class SharedPreferences private constructor(context: Context) {

    //this method will checker whether user is already logged in or not
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences?.getString(KEY_FIRSTNAME, null) != null
        }

    //this method will give the logged in user
    val user: User
        get() {
            val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences!!.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
            )
        }

    init {
        ctx = context
    }

    //this method will store the user data in shared preferences
    fun userLogin(user: User) {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(KEY_ID, user.id)
        editor?.putString(KEY_FIRSTNAME, user.firstname)
        editor?.putString(KEY_LASTNAME, user.lastname)
        // editor?.putString(KEY_GENDER, user.gender)
        editor?.apply()
    }

    //this method will logout the user
    fun logout() {
        val sharedPreferences = ctx?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
        ctx?.startActivity(Intent(ctx, LoginActivity::class.java))
    }

    companion object {

        private val SHARED_PREF_NAME = "volleyregisterlogin"
        private val KEY_FIRSTNAME = "keyusername"
        private val KEY_LASTNAME = "keyemail"
        // private val KEY_GENDER = "keygender"
        private val KEY_ID = "keyid"
        private var mInstance: SharedPreferences? = null
        private var ctx: Context? = null
        @Synchronized
        fun getInstance(context: Context): SharedPreferences {
            if (mInstance == null) {
                mInstance = SharedPreferences(context)
            }
            return mInstance as SharedPreferences
        }
    }
}