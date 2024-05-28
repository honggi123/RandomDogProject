package com.example.randomdogproject.data

import com.example.randomdogproject.data.model.DogProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val dogService: DogService
) {

    suspend fun getRandomDogPhotoUrl(): DogProfile{
        return dogService.getDogProfile()
    }
}