package com.carlosblaya.marveldemo.domain.usecases

import com.carlosblaya.marveldemo.domain.repository.CharacterRepository
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class BaseCharacterUseCaseTest<T>(private val factory: (CharacterRepository) -> T) {

    abstract var usecase: T

    @Mock
    lateinit var repositoryMock: CharacterRepository

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        usecase = factory.invoke(repositoryMock)
    }
}