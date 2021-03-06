package com.carlosblaya.marveldemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosblaya.marveldemo.core.data.BaseRepository
import com.carlosblaya.marveldemo.core.Konsts
import com.carlosblaya.marveldemo.core.data.Result
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.CharacterPagingSource
import com.carlosblaya.marveldemo.data.pagingsources.ComicPagingSource
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterApiInterface: CharacterApiInterface,
    private val mapperCharacter: CharacterMapper,
    private val mapperComic: ComicMapper,
    private val characterDao: CharacterDao
) : BaseRepository(), CharacterRepository {

    override suspend fun getCharacters(name: String?): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = Konsts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterApiInterface, mapperCharacter, name, characterDao)
            }
        ).flow
    }

    override suspend fun getCharacter(id: Long): Flow<Result<Character>> {
        return flow {
            emit(execute {
                mapperCharacter.toSingleCharacterList(characterApiInterface.getCharacterDetail(id))
            })
        }
    }

    override suspend fun getComics(idCharacter: Long): Flow<PagingData<Comic>> {
        return Pager(
            config = PagingConfig(
                pageSize = Konsts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ComicPagingSource(characterApiInterface, mapperComic, idCharacter)
            }
        ).flow
    }

}