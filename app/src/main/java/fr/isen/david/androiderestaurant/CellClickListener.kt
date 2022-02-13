package fr.isen.david.androiderestaurant


interface CellClickListener {
    fun onCellClickListener( data: DishModel)
    fun onCellClickListenerBasket(data : DishBasket)

}