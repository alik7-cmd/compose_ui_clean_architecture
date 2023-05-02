package com.example.check24

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FirstViewModel : ViewModel() {

    private val _uiState : MutableStateFlow<UiState> = MutableStateFlow(UiState.Init)
    val uiState get() = _uiState
}


sealed class UiState{
    object Init : UiState()

}