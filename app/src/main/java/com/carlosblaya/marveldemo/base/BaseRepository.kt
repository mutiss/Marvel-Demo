package com.carlosblaya.marveldemo.base

import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.repository.RepositoryException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun  <T> execute(
        apiCall: suspend () -> T
    ): Result<T>{
        return withContext(Dispatchers.IO){
            try {
                Result.Success(apiCall.invoke())
            } catch (throwable: Throwable){
                when (throwable){
                    is HttpException -> {
                        Result.Failure(RepositoryException(throwable.code(),throwable.response()?.errorBody().toString()))
                    }
                    else ->{
                        Result.Failure(RepositoryException())
                    }
                }
            }
        }
    }
}