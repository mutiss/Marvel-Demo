package com.carlosblaya.marveldemo.ui.characters.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carlosblaya.marveldemo.CoroutinesTestRule
import com.carlosblaya.marveldemo.core.data.Result
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.usecases.GetCharacterDetailUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
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
class CharacterDetailModelTest {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase

    @Mock
    lateinit var getCharacterDao: CharacterDao

    @Mock
    lateinit var getCharacterMapper: CharacterMapper

    @Mock
    lateinit var flow: Flow<Result<Character>>

    @Mock
    lateinit var mock: Character

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    val characterId = 1009282L

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCharacter verify call`() {
        runBlockingTest {
            val vm = CharacterDetailViewModel(getCharacterDetailUseCase, getCharacterDao, getCharacterMapper)
            whenever(getCharacterDetailUseCase.getCharacter(any())).thenReturn(flow)

            vm.idCharacter = characterId
            vm.getCharacter()
            verify(getCharacterDetailUseCase, times(1)).getCharacter(any())
            assert(vm.characterDetailState.value as CharacterDetailState.Loading == CharacterDetailState.Loading)
        }
    }

    @Test
    fun `getCharacter success call`() {
        runBlockingTest {
            val vm = CharacterDetailViewModel(getCharacterDetailUseCase, getCharacterDao, getCharacterMapper)
            whenever(flow.collect(any())).then {
                launch {
                    (it.arguments[0] as FlowCollector<Result<Character>>).emit(Result.success(mock))
                }
            }

            whenever(getCharacterDetailUseCase.getCharacter(any())).thenReturn(flow)
            vm.idCharacter = characterId
            vm.getCharacter()
            assert((vm.characterDetailState.value as CharacterDetailState.Success).character == mock)
        }
    }

}