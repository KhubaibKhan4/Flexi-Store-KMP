package org.flexi.app.presentation.viewmodels

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.flexi.app.domain.repository.Repository
import org.flexi.app.domain.usecase.ResultState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val repository: Repository,
) : ViewModel() {

    private val _login = MutableStateFlow<ResultState<String>>(ResultState.Loading)
    val login: StateFlow<ResultState<String>> = _login.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                _login.value = ResultState.Success(response)
            } catch (e: Exception) {
                _login.value = ResultState.Error(e)
            }
        }
    }

}