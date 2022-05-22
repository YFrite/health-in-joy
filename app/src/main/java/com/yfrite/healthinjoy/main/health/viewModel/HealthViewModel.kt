package com.yfrite.healthinjoy.main.health.viewModel

import androidx.lifecycle.*
import com.yfrite.healthinjoy.data.eaten.Eaten
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.data.notifications.Notification
import com.yfrite.healthinjoy.data.notifications.NotificationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(private val repositoryEaten: EatenRepository,
                                          private val repositoryNotification: NotificationsRepository): ViewModel() {

    val eatenHistory: LiveData<List<Eaten>> = repositoryEaten.getHistory()
    val notifications: LiveData<List<Notification>> = repositoryNotification.getAll()
    val twoNotifications = MediatorLiveData<List<Notification>>()

    fun updateEatenData(data: Eaten) = viewModelScope.launch(Dispatchers.IO) {
        repositoryEaten.update(data)
    }

    fun twoNotifications() = MainScope().launch {
        val data = repositoryNotification.getTwoNotifications()

        twoNotifications.addSource(data) {
            twoNotifications.removeSource(data)
            twoNotifications.value = it
        }

    }

    fun newNotification(data: Notification) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotification.insert(data)
        twoNotifications()
    }

    fun removeNotification(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        repositoryNotification.delete(id)
        twoNotifications()
    }
}