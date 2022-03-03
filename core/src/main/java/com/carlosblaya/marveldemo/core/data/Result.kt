package com.carlosblaya.marveldemo.core.data

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Failure<T>(val exception: RepositoryException) : Result<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> failure(exception: RepositoryException) = Failure<T>(exception)
    }
}