package com.example.randomdogproject.data

import com.example.randomdogproject.data.model.DogProfile
import retrofit2.http.GET


interface DogService {

    @GET("/api/breeds/image/random")
    suspend fun getDogProfile(): DogProfile
}
