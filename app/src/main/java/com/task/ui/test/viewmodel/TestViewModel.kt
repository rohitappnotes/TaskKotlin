package com.task.ui.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.data.repository.LocalRepository
import com.task.data.repository.RemoteRepository
import com.task.ui.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.task.data.model.Data
import com.task.data.remote.server.model.BaseResponseObjectFormat
import com.task.data.remote.server.utils.ApiResponse
import kotlinx.coroutines.launch

@HiltViewModel
class TestViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : BaseViewModel() {

    private val mTag: String = TestViewModel::class.java.simpleName

    private val employee1Success = MutableLiveData<ApiResponse<BaseResponseObjectFormat<Data>>>()
    val dataSuccess: LiveData<ApiResponse<BaseResponseObjectFormat<Data>>> get() = employee1Success

    fun employee1() = viewModelScope.launch {
        employee1Success.value = ApiResponse.Loading
        employee1Success.value = remoteRepository.employee1()
    }
}