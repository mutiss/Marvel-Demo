package com.carlosblaya.marveldemo.data.response

import com.google.gson.annotations.SerializedName

class ApiErrorResponse {
    @SerializedName(value = "code")
    var code: String? = null

    @SerializedName(value = "status")
    var message: String? = null
}