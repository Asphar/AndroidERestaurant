package fr.isen.david.androiderestaurant

import java.io.Serializable


data class DishResult(val data: List<CategoryModel>): Serializable

data class CategoryModel (val name_fr : String, val items: List<DishModel>) : Serializable

data class DishModel(val name_fr: Int, val images: String, val prices: List<PriceModel>, val ingredients: List<IngredientModel>) : Serializable

data class PriceModel (val price : String) : Serializable

data class IngredientModel(val name_fr: String) : Serializable
