package com.carlosblaya.marveldemo.data.pagingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.util.Konsts

class ComicPagingSource(
    private val service: CharacterApiInterface,
    private val mapper: ComicMapper,
    private val idCharacter: Long,
) : PagingSource<Int, Comic>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comic> {
        val position = params.key ?: Konsts.INITIAL_LOAD_SIZE
        val offset = if (params.key != null) (position * Konsts.NETWORK_PAGE_SIZE) else Konsts.INITIAL_LOAD_SIZE
        return try {
            val jsonResponse = service.getComicsCharacter(id = idCharacter,offset = offset).data.results
            val response = mapper.toComicList(jsonResponse)
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

    override fun getRefreshKey(state: PagingState<Int, Comic>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return null
    }
}