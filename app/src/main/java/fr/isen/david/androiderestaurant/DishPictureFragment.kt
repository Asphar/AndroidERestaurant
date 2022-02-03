package fr.isen.david.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.david.androiderestaurant.databinding.FragmentDishPictureBinding

/*
class DishPictureFragment : Fragment() {
    private lateinit var binding: FragmentDishPictureBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDishPictureBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_dish_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("picture_url")?.let {
            Picasso.get().load(it).placeholder(R.drawable.coyote).into(binding)
        }
    }

    companion object {
        fun newInstane(pictureUrl: String) =
            DishPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureUrl)
                }
        }
    }
}

 */