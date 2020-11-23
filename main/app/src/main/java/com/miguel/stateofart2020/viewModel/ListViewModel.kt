package com.miguel.stateofart2020.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.miguel.stateofart2020.model.Animal

class ListViewModel(application: Application) : AndroidViewModel(application){

    val animals by lazy { MutableLiveData<ArrayList<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh(){
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal("urso")
        val a2 = Animal("cão")
        val a3 = Animal("tigre")
        val a4 = Animal("gato")
        val a5 = Animal("raposa")

        val tempAnimals : ArrayList<Animal> = arrayListOf(a1, a2, a3, a4, a5)

        animals.value = tempAnimals
        loadError.value = false
        loading.value = false
    }
}