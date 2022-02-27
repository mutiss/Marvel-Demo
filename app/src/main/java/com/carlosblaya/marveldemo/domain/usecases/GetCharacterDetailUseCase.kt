package com.carlosblaya.marveldemo.domain.usecases

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharacterDetailUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend fun getCharacter(id: Long): Flow<Result<Character>> {
        return characterRepository.getCharacter(id)
    }
}