package com.hyper.medapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyper.medapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _responseMessage = MutableStateFlow("Нажмите кнопку, чтобы загрузить данные")
    val responseMessage: StateFlow<String> = _responseMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getExampleData()
                if (response.success) {
                    _responseMessage.value = "Сообщение от сервера: ${response.message}"
                } else {
                    _responseMessage.value = "Ошибка: ${response.message}"
                }
            } catch (e: Exception) {
                _responseMessage.value = "Ошибка подключения: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}