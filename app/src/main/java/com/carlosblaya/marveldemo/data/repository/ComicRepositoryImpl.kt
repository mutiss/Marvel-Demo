package com.carlosblaya.marveldemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.carlosblaya.marveldemo.base.BaseRepository
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.data.network.services.ComicApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.CharacterPagingSource
import com.carlosblaya.marveldemo.data.pagingsources.ComicPagingSource
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.domain.repository.ComicRepository
import com.carlosblaya.marveldemo.util.Konsts
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class ComicRepositoryImpl @Inject constructor(
    private val comicApiInterface: ComicApiInterface,
    private val mapper: ComicMapper,
) : BaseRepository(), ComicRepository {

    override suspend fun getComics(idCharacter: Long): Flow<PagingData<Comic>> {
        return Pager(
            config = PagingConfig(
                pageSize = Konsts.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ComicPagingSource(comicApiInterface, mapper, idCharacter)
            }
        ).flow
    }

}