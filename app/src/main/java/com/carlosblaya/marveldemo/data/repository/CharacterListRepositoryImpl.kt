package com.carlosblaya.marveldemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosblaya.marveldemo.base.BaseRepository
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.CharacterPagingSource
import com.carlosblaya.marveldemo.data.response.mapper.CharacterListMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.CharacterListRepository
import com.carlosblaya.marveldemo.util.Konsts
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class CharacterListRepositoryImpl @Inject constructor(
    private val characterApiInterface: CharacterApiInterface,
    private val mapper: CharacterListMapper
) : BaseRepository(), CharacterListRepository {

    override suspend fun getCharacters(name: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Konsts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterApiInterface, mapper, name)
            }
        ).flow
    }

}