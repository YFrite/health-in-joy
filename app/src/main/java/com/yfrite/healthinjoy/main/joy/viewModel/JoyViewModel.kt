package com.yfrite.healthinjoy.main.joy.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yfrite.healthinjoy.data.messages.Message
import com.yfrite.healthinjoy.data.messages.MessagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoyViewModel @Inject constructor(private val repository: MessagesRepository): ViewModel(){

    val history: LiveData<List<Message>> = repository.getHistory()

    fun insert(data: Message) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(data)
    }

    fun clear() = viewModelScope.launch(Dispatchers.IO) {
        repository.clear()
    }
}