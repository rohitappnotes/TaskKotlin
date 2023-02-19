package com.task.data.model

import com.google.gson.annotations.SerializedName
import com.task.data.remote.server.model.BaseModel

data class Data (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("job") val job: String,
    @SerializedName("salary") val salary: String,
    @SerializedName("profile_picture") val profilePicture: String
): BaseModel()