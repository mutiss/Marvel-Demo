package com.carlosblaya.marveldemo.domain.repository

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(name:String?): Flow<PagingData<Character>>
    suspend fun getCharacter(id:Long): Flow<Result<Character>>
}
