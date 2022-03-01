package com.carlosblaya.marveldemo.domain.repository

import androidx.paging.PagingData
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.model.Comic
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(name:String?): Flow<PagingData<Character>>
    suspend fun getCharacter(id:Long): Flow<Result<Character>>
    suspend fun getComics(id:Long): Flow<PagingData<Comic>>
}
