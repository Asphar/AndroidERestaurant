package fr.isen.david.androiderestaurant

import java.io.Serializable

data class Model(val data: List<ImageSet>) : Serializable

data class ImageSet (val name_fr : String, val items: List<ItemsViewModel>) : Serializable

data class ItemsViewModel(val image: Int, val text: String) : Serializable
