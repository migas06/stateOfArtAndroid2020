package com.miguel.stateofart2020.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguel.stateofart2020.R
import com.miguel.stateofart2020.model.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private var animalList: ArrayList<Animal>) : RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
    }

    override fun getItemCount(): Int = animalList.size

    class AnimalViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}