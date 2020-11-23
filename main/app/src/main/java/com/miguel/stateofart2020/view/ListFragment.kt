package com.miguel.stateofart2020.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.miguel.stateofart2020.R
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.view.Adapter.AnimalListAdapter
import com.miguel.stateofart2020.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter: AnimalListAdapter = AnimalListAdapter(ArrayList<Animal>())

    private val animalListDataObserver = Observer<List<Animal>>{ list : List<Animal> ->
        list.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading: Boolean ->
        loading.visibility = if(isLoading)  View.VISIBLE else View.GONE

        if(isLoading){
            textinput_error.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError: Boolean ->
        textinput_error.visibility = if(isError) View.VISIBLE else View.GONE
        if(isError) {
            loading.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
    }
}
