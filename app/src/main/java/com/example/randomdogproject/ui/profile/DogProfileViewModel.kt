package com.example.randomdogproject.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomdogproject.data.DogRepository
import com.example.randomdogproject.data.model.DogProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogProfileViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val dogProfileStream = MutableStateFlow<DogProfile?>(null)

    val uiState: StateFlow<DogProfileUiState> =
        dogProfileStream
            .filterNotNull()
            .map { DogProfileUiState.Profile(it.profileUrl) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(3_000),
                DogProfileUiState.Loading
            )

    fun refreshProfile() {
        viewModelScope.launch {
            dogProfileStream.value = repository.getRandomDogPhotoUrl()
        }
    }
}

sealed interface DogProfileUiState {

    data object Loading : DogProfileUiState

    data object LoadFailed : DogProfileUiState

    data class Profile(
        val photoUrl: String,
    ) : DogProfileUiState

    data object Empty : DogProfileUiState
}