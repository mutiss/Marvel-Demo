package com.carlosblaya.marveldemo.domain.usecases

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetComicsCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend fun getComics(id: Long): Flow<PagingData<Comic>> {
        return characterRepository.getComics(id)
    }
}