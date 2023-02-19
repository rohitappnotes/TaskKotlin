package com.task.data.remote.server

import com.task.data.model.Data
import com.task.data.remote.server.model.BaseResponseArrayFormat
import com.task.data.remote.server.model.BaseResponseObjectFormat
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(ApiConfiguration.EMPLOYEE)
    suspend fun employee(): BaseResponseObjectFormat<Data>

    @GET(ApiConfiguration.EMPLOYEE_LIST)
    suspend fun employeeList(): BaseResponseArrayFormat<Data>

    @GET(ApiConfiguration.EMPLOYEE)
    suspend fun employee1(): Response<BaseResponseObjectFormat<Data>>

    @GET(ApiConfiguration.EMPLOYEE_LIST)
    suspend fun employeeList2(): Response<BaseResponseArrayFormat<Data>>
}