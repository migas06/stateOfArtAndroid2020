package com.miguel.stateofart2020.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.miguel.stateofart2020.R
import com.miguel.stateofart2020.Util.getProgressDrawable
import com.miguel.stateofart2020.Util.loadImage
import com.miguel.stateofart2020.model.Animal
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let{
            animalImage.loadImage(animal?.imageURL, getProgressDrawable(it))
        }

        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalDiet.text = animal?.diet
        animalLifespan.text = animal?.lifeSpan

    }
}
