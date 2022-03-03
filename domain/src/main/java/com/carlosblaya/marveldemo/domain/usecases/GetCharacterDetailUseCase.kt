package com.carlosblaya.marveldemo.domain.usecases

import com.carlosblaya.marveldemo.core.data.Result
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharacterDetailUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend fun getCharacter(id: Long): Flow<Result<Character>> {
        return characterRepository.getCharacter(id)
    }
}