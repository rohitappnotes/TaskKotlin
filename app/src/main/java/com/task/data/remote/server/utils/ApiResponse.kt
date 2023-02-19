package com.task.data.remote.server.utils

sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T? = null)
        : ApiResponse<T>()

    data class Failure(val message: String)
        : ApiResponse<Nothing>()

    object NullBody
        : ApiResponse<Nothing>()

    object Loading
        : ApiResponse<Nothing>()
}