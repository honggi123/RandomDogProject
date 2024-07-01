package com.example.randomdogproject.data.impl

import com.example.randomdogproject.data.DogRepository
import com.example.randomdogproject.data.DogService
import com.example.randomdogproject.data.model.ApiResponse
import com.example.randomdogproject.data.model.DogProfile
import com.example.randomdogproject.data.model.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(private val dogService: DogService) : DogRepository {

    override fun getRandomProfile(): Flow<ApiResponse<DogProfile>> =
        safeFlow { dogService.getDogProfile() }
}