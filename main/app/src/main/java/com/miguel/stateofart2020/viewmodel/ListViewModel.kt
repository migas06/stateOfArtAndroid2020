package com.miguel.stateofart2020.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.miguel.stateofart2020.di.AppModule
import com.miguel.stateofart2020.di.serviceapi.DaggerViewModelListComponent
import com.miguel.stateofart2020.di.shareddi.CONTEXT_APP
import com.miguel.stateofart2020.di.shareddi.TypeOfContext
import com.miguel.stateofart2020.util.SharedPreferencesHelper
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import com.miguel.stateofart2020.model.network.AnimalApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application){

    constructor(application: Application, test: Boolean = true) : this(application){
        injected = true
    }

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private var invalidApiKey = false
    private var injected = false

    //Get the links and clear when they finish;
    //if the viewModel get killed before the services end,
    //disposable is responsible for clear them all at the end
    //without memory leaks;
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var api: AnimalApiService
    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs : SharedPreferencesHelper

    fun inject() {
        if(!injected) {
            DaggerViewModelListComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    fun refresh() {
        inject()
        val key: String? = prefs.getApiKey()
        invalidApiKey = false
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRequest(){
        inject()
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
                            getKey()
                        } else {
                            loading.value = false
                            loadError.value = true
                            animals.value = null
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}