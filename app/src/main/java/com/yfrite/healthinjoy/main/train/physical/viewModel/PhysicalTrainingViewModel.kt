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
    val currentTrainingNumber: LiveData<Int> by lazy {
        MutableLiveData(1)
    }

    fun trainingsByTypeAndDifficulty(type: String, difficulty: Int) = MainScope().launch {
        val data = repository.getByTypeAndDifficulty(type, difficulty)

        trainings.addSource(data) {
            trainings.removeSource(data)
            trainings.value = it
        }
    }
}