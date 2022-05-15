package com.yfrite.healthinjoy.main.health.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yfrite.healthinjoy.data.eaten.Eaten
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.data.notifications.Notification
import com.yfrite.healthinjoy.data.notifications.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(private val repositoryEaten: EatenRepository,
                                          private val repositoryNotification: NotificationsRepository): ViewModel() {

    val eatenHistory: LiveData<List<Eaten>> = repositoryEaten.getHistory()

    fun updateEatenData(data: Eaten) = viewModelScope.launch(Dispatchers.IO) {
        repositoryEaten.update(data)
    }

//    fun insertEatenData(data: Eaten) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(data)
//    }

    fun newNotification(data: Notification) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotification.insert(data)
    }
}