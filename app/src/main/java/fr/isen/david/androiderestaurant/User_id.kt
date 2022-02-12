package fr.isen.david.androiderestaurant

import java.io.Serializable

data class Connection(val data: Authentication, val code : String): Serializable

data class Authentication(var id : String, var firstname :String, var lastname : String) : Serializable


