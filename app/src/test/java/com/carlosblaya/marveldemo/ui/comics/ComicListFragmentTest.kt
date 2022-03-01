package com.carlosblaya.marveldemo.ui.comics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.carlosblaya.marveldemo.ComicsMockResponse
import com.carlosblaya.marveldemo.CoroutinesTestRule
import com.carlosblaya.marveldemo.data.network.services.ComicApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.ComicPagingSource
import com.carlosblaya.marveldemo.data.response.ComicDataContainerResponse
import com.carlosblaya.marveldemo.data.response.ComicDataWrapperResponse
import com.carlosblaya.marveldemo.data.response.ComicResponse
import com.carlosblaya.marveldemo.data.response.Image
import com.carlosblaya.marveldemo.data.response.mapper.ComicMapper
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.util.Konsts
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(JUnit4::class)
class ComicListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getComicApiInterface: ComicApiInterface

    @Mock
    lateinit var getComicMapper: ComicMapper

    @Mock
    lateinit var comicPagingSource: ComicPagingSource

    var idCharacter:Long = 1009282

    companion object {
        var gson = Gson()
        var mComicDataWrapperResponse = gson?.fromJson(ComicsMockResponse.comicsListSampleResponse, ComicDataWrapperResponse::class.java)
    }

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        comicPagingSource = ComicPagingSource(getComicApiInterface,getComicMapper,idCharacter)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `comics paging source refresh - success`() = runBlockingTest {
        given(getComicApiInterface.getComicsCharacter(idCharacter,0,Konsts.PUBLIC_KEY, Konsts.timeStamp, Konsts.hash())).willReturn(mComicDataWrapperResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = mComicDataWrapperResponse.data.results,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, comicPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `comics paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(getComicApiInterface.getComicsCharacter(any(), any(), any(), any(), any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, Comic>(error)
        assertEquals(
            expectedResult, comicPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `comics paging source load - failure - received null`() = runBlockingTest {
        given(getComicApiInterface.getComicsCharacter(any(), any(), any(), any(), any())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Comic>(NullPointerException())
        assertEquals(
            expectedResult.toString(), comicPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

}