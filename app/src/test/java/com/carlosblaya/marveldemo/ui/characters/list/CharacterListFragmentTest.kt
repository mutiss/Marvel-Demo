package com.carlosblaya.marveldemo.ui.characters.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.carlosblaya.marveldemo.CoroutinesTestRule
import com.carlosblaya.marveldemo.core.Konsts
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.network.services.CharacterApiInterface
import com.carlosblaya.marveldemo.data.pagingsources.CharacterPagingSource
import com.carlosblaya.marveldemo.data.response.CharacterDataContainerResponse
import com.carlosblaya.marveldemo.data.response.CharacterResponse
import com.carlosblaya.marveldemo.data.response.CharacterResultResponse
import com.carlosblaya.marveldemo.data.response.ThumbnailResponse
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.model.Character
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
class CharacterListFragmentTest {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getCharacterApiInterface: CharacterApiInterface

    @Mock
    lateinit var getCharacterDao: CharacterDao

    @Mock
    lateinit var getCharacterMapper: CharacterMapper

    val characterName = "Doctor Strange"

    @Mock
    lateinit var characterPagingSource: CharacterPagingSource

    companion object {
        val characterResponse = CharacterResultResponse(
            code = 200,
            status = "Ok",
            copyright = "© 2022 MARVEL",
            attributionText = "Data provided by Marvel. © 2022 MARVEL",
            dataResponse = CharacterDataContainerResponse(
                offset = 0,
                limit = 20,
                total = 2,
                count = 2,
                results = listOf(
                    CharacterResponse(1009282, "Doctor Strange", "", "", "", ThumbnailResponse("", "")),
                    CharacterResponse(1011358, "Doctor Strange (Ultimate)", "", "", "", ThumbnailResponse("", ""))
                )
            )
        )
    }

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        characterPagingSource = CharacterPagingSource(getCharacterApiInterface,getCharacterMapper,characterName,getCharacterDao)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `characters paging source refresh - success`() = runBlockingTest {
        given(getCharacterApiInterface.getCharacters(Konsts.PUBLIC_KEY, Konsts.timeStamp, Konsts.hash(), 0, characterName)).willReturn(characterResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = characterResponse.dataResponse.results,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `characters paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(getCharacterApiInterface.getCharacters(any(), any(), any(), any(), any())).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, Character>(error)
        assertEquals(
            expectedResult, characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `characters paging source load - failure - received null`() = runBlockingTest {
        given(getCharacterApiInterface.getCharacters(any(), any(), any(), any(), any())).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Character>(NullPointerException())
        assertEquals(
            expectedResult.toString(), characterPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

}