package com.miguel.stateofart2020

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.miguel.stateofart2020.di.AppModule
import com.miguel.stateofart2020.di.serviceapi.DaggerViewModelListComponent
import com.miguel.stateofart2020.model.Animal
import com.miguel.stateofart2020.model.ApiKey
import com.miguel.stateofart2020.model.network.AnimalApiService
import com.miguel.stateofart2020.util.SharedPreferencesHelper
import com.miguel.stateofart2020.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalService: AnimalApiService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewModel(application, true)

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        DaggerViewModelListComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalService))
            .sharedModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Before
    fun setupRxSchedulers(){
        val immediate = object: Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    private val key = "test_key"

    @Test
    fun getAnimalsSuccess(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val animal = Animal("cow", null, null, null, null, null, null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalsFailure(){
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)

        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle =  Single.just(ApiKey("OK", key))

        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animals.value?.size)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeySucces(){

        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle =  Single.just(ApiKey("OK", key))

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        val animal = Animal("cow", null, null, null, null, null, null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)
        Mockito.`when`(animalService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeyFailure(){

        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle =  Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animals.value?.size)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

}