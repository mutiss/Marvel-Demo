package com.carlosblaya.marveldemo.domain.repository

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    suspend fun getCharacters(name:String?): Flow<PagingData<Character>>
}
