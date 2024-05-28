package com.example.randomdogproject.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DogProfile(
    @SerialName("message")
    val profileUrl: String
)
