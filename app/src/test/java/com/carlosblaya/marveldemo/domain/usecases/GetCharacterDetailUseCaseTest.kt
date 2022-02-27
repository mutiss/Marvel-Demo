package com.carlosblaya.marveldemo.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

class GetCharacterDetailUseCaseTest : BaseCharacterUseCaseTest<GetCharacterDetailUseCase>(::GetCharacterDetailUseCase) {
    override lateinit var usecase: GetCharacterDetailUseCase
    val characterId = 1009282L
    @Test
    fun `verify call`() = runBlockingTest {
        usecase.getCharacter(characterId)
        verify(repositoryMock, times(1)).getCharacter(any())
    }

}