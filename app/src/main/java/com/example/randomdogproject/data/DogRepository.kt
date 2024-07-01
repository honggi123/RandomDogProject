package com.example.randomdogproject.data

import com.example.randomdogproject.data.model.ApiResponse
import com.example.randomdogproject.data.model.DogProfile
import com.example.randomdogproject.data.model.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DogRepository {

    fun getRandomProfile(): Flow<ApiResponse<DogProfile>>
}
