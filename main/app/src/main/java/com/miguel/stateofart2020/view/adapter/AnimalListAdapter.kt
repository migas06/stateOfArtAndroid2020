package com.miguel.stateofart2020.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.miguel.stateofart2020.R
import com.miguel.stateofart2020.databinding.ItemAnimalBinding
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.view.ListFragmentDirections

interface ClickListenerInterface{
    fun onClick(v: View)
}

class AnimalListAdapter(private var animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(),
    ClickListenerInterface {

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int = animalList.size

    class AnimalViewHolder(var view: ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    override fun onClick(v: View) {
        for(animal in animalList){
            if(animal.name == v.tag){
                val action: NavDirections = ListFragmentDirections.actionGoToDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }
}