package fr.isen.david.androiderestaurant

import java.io.Serializable

data class SavedDishInBasket (val list : ArrayList<DishBasket>) :Serializable
data class DishBasket(val itemdish : DishModel, var quantity : Int): Serializable

