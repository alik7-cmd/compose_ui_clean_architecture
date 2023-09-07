package com.example.check24.overview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.check24.common.BaseResult
import com.example.check24.common.dispatcher.DispatcherProvider
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.overview.domain.usecase.GetProductOverviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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


    fun getProduceOverview(filterCategory: FilterCategory, isRefreshing : Boolean = false) {
        viewModelScope.launch(dispatcherProvider.io) {
            useCase(filterCategory, isRefreshing).onStart {
                setLoadingStatus()

            }.catch {
                _mutableUiState.value = ProductOverviewUiState.Error(it.message ?: "")

            }.collect {
                when (it) {
                    is BaseResult.Loading -> setLoadingStatus()
                    is BaseResult.Success -> _mutableUiState.value =
                        ProductOverviewUiState.Success(it.data)
                    is BaseResult.Error -> _mutableUiState.value =
                        ProductOverviewUiState.Error(it.error)
                }
            }
        }

    }

    private fun setLoadingStatus() {
        _mutableUiState.value = ProductOverviewUiState.Loading
    }
}


sealed class ProductOverviewUiState {
    object Init : ProductOverviewUiState()
    object Loading : ProductOverviewUiState()
    data class Success(val list: List<ProductEntity>) : ProductOverviewUiState()
    data class Error(val error: String) : ProductOverviewUiState()
}