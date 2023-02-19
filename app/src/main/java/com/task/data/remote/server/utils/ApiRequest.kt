package com.task.data.remote.server.utils

import com.task.data.remote.server.exception.ExceptionHelper
import retrofit2.Response

abstract class ApiRequest {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiResponse.Success(body)
                } else {
                    ApiResponse.NullBody
                }
            } else {
                ApiResponse.Failure(response.message())
            }
        } catch (throwable: Throwable) {
            ApiResponse.Failure(ExceptionHelper.getInstance().handleException(throwable).displayMessage)
        }
    }
}