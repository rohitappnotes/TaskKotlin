package com.task.data.repository

import com.task.data.model.Data
import com.task.data.remote.server.ApiService
import com.task.data.remote.server.model.BaseResponseArrayFormat
import com.task.data.remote.server.model.BaseResponseObjectFormat
import com.task.data.remote.server.utils.ApiRequest
import javax.inject.Inject

public class RemoteRepository @Inject constructor(private val apiService: ApiService) : ApiRequest() {

    suspend fun employee(): BaseResponseObjectFormat<Data> = apiService.employee()

    suspend fun employeeList(): BaseResponseArrayFormat<Data> = apiService.employeeList()

    suspend fun employee1() = safeApiCall {
        apiService.employee1()
    }
}
