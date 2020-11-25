package com.miguel.stateofart2020.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.miguel.stateofart2020.Util.SharedPreferencesHelper
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import com.miguel.stateofart2020.model.Network.AnimalApiService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application){

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private var invalidApiKey = false

    //Get the links and clear when they finish;
    //if the viewModel get killed before the services end,
    //disposable is responsible for clear them all at the end
    //without memory leaks;
    private val disposable = CompositeDisposable()
    private val api = AnimalApiService()

    private val prefs = SharedPreferencesHelper(getApplication())

    fun refresh() {
        val key: String? = prefs.getApiKey()
        invalidApiKey = false
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRequest(){
        loading.value = true;
        getKey()
    }

    private fun getKey(){
        disposable.add(
            api.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {

                        loading.value = false
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                        } else {
                            loadError.value = false
                            prefs.saveAPIKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }
                })
        )
    }

    private fun getAnimals(key: String) {

        disposable.add(
            api.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loading.value = false
                        loadError.value = false

                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        if (!invalidApiKey) {
                            invalidApiKey = true
                        } else {
                            loading.value = false
                            loadError.value = true
                        }
                    }
                })
        )
        loadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}