package com.yfrite.healthinjoy.main.train.physical.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.yfrite.healthinjoy.data.trainings.Training
import com.yfrite.healthinjoy.data.trainings.TrainingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysicalTrainingViewModel @Inject constructor(private val repository: TrainingsRepository): ViewModel(){

    val trainings = MediatorLiveData<List<Training>>()
    val currentTrainingNumber: MutableLiveData<Int> by lazy {
        MutableLiveData(1)
    }

    fun trainingsByTypeAndDifficulty(type: String, difficulty: Int) = MainScope().launch {
        val data = repository.getByTypeAndDifficulty(type, difficulty)

        trainings.addSource(data) {
            trainings.removeSource(data)
            trainings.value = it
        }
    }

    fun isLast(): Boolean{
        Log.e("et", "${currentTrainingNumber.value!!} ${trainings.value!!.size}")
        return currentTrainingNumber.value!! == trainings.value!!.size
    }

    fun next(){
        Log.e("time", trainings.value!!.size.toString())
        currentTrainingNumber.value = currentTrainingNumber.value!! + 1
    }
}