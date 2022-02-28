package com.carlosblaya.marveldemo.domain.usecases

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.domain.repository.ComicRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetComicsCharacterUseCase @Inject constructor(private val comicRepository: ComicRepository) {
    suspend fun getComics(id: Long): Flow<PagingData<Comic>> {
        return comicRepository.getComics(id)
    }
}