package com.carlosblaya.marveldemo.data

import com.carlosblaya.marveldemo.domain.repository.RepositoryException

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Render<T>(val data: T) : State<T>()
    data class Error(val exception: RepositoryException) : State<Nothing>()
}
