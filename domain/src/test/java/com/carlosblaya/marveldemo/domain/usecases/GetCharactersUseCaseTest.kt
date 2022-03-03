package com.carlosblaya.marveldemo.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class GetCharacterlUseCaseTest : BaseCharacterUseCaseTest<GetCharactersUseCase>(::GetCharactersUseCase) {
    override lateinit var usecase: GetCharactersUseCase
    val characterName = "Doctor Strange"
    @Test
    fun `verify call`() = runBlockingTest {
        usecase.getCharacters(characterName)
        Mockito.verify(repositoryMock, times(1)).getCharacters(any())
    }
}