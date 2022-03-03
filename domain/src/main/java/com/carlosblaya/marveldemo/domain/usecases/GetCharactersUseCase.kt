package com.carlosblaya.marveldemo.domain.usecases

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {
    suspend fun getCharacters(name: String?): Flow<PagingData<Character>> {
        return characterRepository.getCharacters(name)
    }
}