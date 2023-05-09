package com.example.check24.overview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.check24.common.BaseResult
import com.example.check24.common.dispatcher.DispatcherProvider
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.overview.domain.usecase.GetProductOverviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductOverviewViewModel @Inject constructor(
    val useCase: GetProductOverviewUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _mutableUiState: MutableStateFlow<ProductOverviewUiState> =
        MutableStateFlow(ProductOverviewUiState.Init)
    val uiState get() = _mutableUiState.asStateFlow()

    var savedFilter = FilterCategory.ALL

    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
         // do your task
    }


    fun getProduceOverview(filterCategory: FilterCategory) {
        viewModelScope.launch(dispatcherProvider.io + handler) {
            useCase(filterCategory).onStart {
                setLoadingStatus(true)

            }.catch {
                setLoadingStatus(false)
                _mutableUiState.value = ProductOverviewUiState.Error(it.message ?: "")

            }.collect {
                setLoadingStatus(false)
                when (it) {
                    is BaseResult.Success -> _mutableUiState.value =
                        ProductOverviewUiState.Success(it.data)
                    is BaseResult.Error -> _mutableUiState.value =
                        ProductOverviewUiState.Error(it.error)
                }
            }
        }

    }

    private fun setLoadingStatus(isLoading: Boolean) {
        _mutableUiState.value = ProductOverviewUiState.Loading(isLoading)
    }
}


sealed class ProductOverviewUiState {
    object Init : ProductOverviewUiState()
    data class Loading(val isLoading: Boolean) : ProductOverviewUiState()
    data class Success(val list: List<ProductEntity>) : ProductOverviewUiState()
    data class Error(val error: String) : ProductOverviewUiState()
}