package com.carlosblaya.marveldemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosblaya.marveldemo.base.BaseRepository
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.CharacterPagingSource
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import com.carlosblaya.marveldemo.util.Konsts
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterApiInterface: CharacterApiInterface,
    private val mapper: CharacterMapper,
    private val characterDao: CharacterDao
) : BaseRepository(), CharacterRepository {

    override suspend fun getCharacters(name: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Konsts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterApiInterface, mapper, name, characterDao)
            }
        ).flow
    }

    override suspend fun getCharacter(id: Long): Flow<Result<Character>> {
        return flow {
            emit(execute {
                mapper.toSingleCharacterList(characterApiInterface.getCharacterDetail(id))
            })
        }
    }

}