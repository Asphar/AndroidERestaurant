package fr.isen.david.androiderestaurant

import java.io.Serializable

data class OrderResult (val data : List<Orders>, val code: String) : Serializable
data class Orders (val id_sender: String, val message : String) : Serializable

