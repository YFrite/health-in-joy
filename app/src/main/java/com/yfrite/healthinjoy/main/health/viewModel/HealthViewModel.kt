package com.yfrite.healthinjoy.main.health.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val notifications: LiveData<List<Notification>> = repositoryNotification.getAll()
    val twoNotifications = MutableLiveData<List<Notification>>()

    fun updateEatenData(data: Eaten) = viewModelScope.launch(Dispatchers.IO) {
        repositoryEaten.update(data)
    }

//    fun insertEatenData(data: Eaten) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(data)
//    }

    fun twoNotifications(){
        viewModelScope.launch(Dispatchers.IO) {
            twoNotifications.postValue(repositoryNotification.getTwoNotifications().value)
        }
    }

    fun newNotification(data: Notification) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotification.insert(data)
    }

    fun removeNotification(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotification.delete(id)
    }
}