package com.carlosblaya.marveldemo.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosblaya.marveldemo.core.Konsts
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.model.Character

class CharacterPagingSource(
    private val service: CharacterApiInterface,
    private val mapper: CharacterMapper,
    private val name: String?,
    private val characterDao: CharacterDao,
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: Konsts.INITIAL_LOAD_SIZE
        val offset = if (params.key != null) (position * Konsts.NETWORK_PAGE_SIZE) else Konsts.INITIAL_LOAD_SIZE
        return try {
            val jsonResponse = service.getCharacters(name = name, offset = offset).dataResponse
            val response = mapper.toCharacterList(jsonResponse.results)
            characterDao.insert(mapper.toCharacterListDB(jsonResponse.results)) // Insert list into DB
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }
}