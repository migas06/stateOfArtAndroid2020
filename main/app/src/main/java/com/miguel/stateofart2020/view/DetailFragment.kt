package com.miguel.stateofart2020.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.miguel.stateofart2020.R
import com.miguel.stateofart2020.databinding.FragmentDetailBinding
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.AnimalPalette

class DetailFragment : Fragment() {

    var animal: Animal? = null
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        dataBinding.animal = animal

        animal?.imageURL?.let {
            setupBackgroundColor(it)
        }
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate()
                        { palette ->
                            var intColor = palette?.vibrantSwatch?.rgb ?: 0
                            if(intColor == 0){
                                intColor = palette?.lightVibrantSwatch?.rgb ?: 0
                            }
                            dataBinding.animalPalette = AnimalPalette(intColor)

                        }
                }
                override fun onLoadCleared(placeholder: Drawable?) {    }
            })
    }
}
