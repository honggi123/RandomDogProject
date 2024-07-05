package com.example.randomdogproject.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomdogproject.data.DogRepository
import com.example.randomdogproject.data.model.ApiResponse
import com.example.randomdogproject.data.model.DogProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogProfileViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogProfileUiState>(DogProfileUiState.Loading)
    val uiState: StateFlow<DogProfileUiState> = _uiState

    fun onRefreshClick() {
        viewModelScope.launch {
            repository.getRandomProfile().collectLatest { result ->
                _uiState.value = when (result) {
                    is ApiResponse.ApiSuccess -> {
                        DogProfileUiState.Profile(result.value)
                    }

                    is ApiResponse.ApiError, is ApiResponse.ApiException -> {
                        DogProfileUiState.LoadFailed
                    }
                }
            }
        }
    }
}

sealed interface DogProfileUiState {

    data object Loading : DogProfileUiState

    data object LoadFailed : DogProfileUiState

    data class Profile(
        val profile: DogProfile,
    ) : DogProfileUiState
}