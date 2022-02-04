package fr.isen.david.androiderestaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("picture_url")?.let { pictureUrl ->
            Picasso.get()
                .load(pictureUrl)
                .placeholder(R.drawable.androider)
                .into(R.drawable.androider)
                //.into(binding.dishPictureFrag)
        }
    }

    companion object {
        fun newInstance(pictureUrl: String) =
            DishPictureFragment().apply {
                arguments = Bundle().apply {
                    putString("picture_url", pictureUrl)
                }
        }
    }
}

 */

