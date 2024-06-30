package com.example.randomdogproject.data.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

sealed interface ApiResponse<out T> {
    data class ApiSuccess<out T>(val value: T) : ApiResponse<T>

    data class ApiError(val code: Int, val message: String) : ApiResponse<Nothing>

    data class ApiException(val throwable: Throwable) : ApiResponse<Nothing>
}


fun <T> safeFlow(apiFunc: suspend () -> T): Flow<ApiResponse<T>> = flow {
    try {
        emit(ApiResponse.ApiSuccess(apiFunc.invoke()))
    } catch (e: HttpException) {
        emit(ApiResponse.ApiError(code = e.code(), message = e.message()))
    } catch (e: Exception) {
        emit(ApiResponse.ApiException(throwable = e))
    }
}