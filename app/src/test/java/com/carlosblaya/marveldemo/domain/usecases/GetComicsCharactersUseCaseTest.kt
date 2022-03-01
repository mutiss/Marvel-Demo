package com.carlosblaya.marveldemo.domain.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class GetComicsCharactersUseCaseTest : BaseCharacterUseCaseTest<GetComicsCharacterUseCase>(::GetComicsCharacterUseCase) {
    override lateinit var usecase: GetComicsCharacterUseCase
    val idCharacter:Long = 1200405
    @Test
    fun `verify call`() = runBlockingTest {
        usecase.getComics(idCharacter)
        Mockito.verify(repositoryMock, times(1)).getCharacters(any())
    }
}