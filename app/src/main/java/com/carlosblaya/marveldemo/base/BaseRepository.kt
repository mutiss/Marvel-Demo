package com.carlosblaya.marveldemo.base

import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.data.response.ApiErrorResponse
import com.carlosblaya.marveldemo.domain.repository.RepositoryException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> execute(
        apiCall: suspend () -> T
    ): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val error =
                            Gson().fromJson(throwable.response()?.errorBody()?.charStream(), ApiErrorResponse::class.java)
                        Result.Failure(RepositoryException(throwable.code(), parseApiError(error)))
                    }
                    else -> {
                        Result.Failure(RepositoryException())
                    }
                }
            }
        }
    }

    private fun parseApiError(error: ApiErrorResponse): String {
        return error.message.toString()
    }
}