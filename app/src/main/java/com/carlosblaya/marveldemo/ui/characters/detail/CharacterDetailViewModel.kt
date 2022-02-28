package com.carlosblaya.marveldemo.ui.characters.detail

import com.carlosblaya.marveldemo.base.BaseViewModel
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.database.entities.CharacterDB
import com.carlosblaya.marveldemo.data.response.mapper.CharacterMapper
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.domain.repository.RepositoryException
import com.carlosblaya.marveldemo.domain.usecases.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val characterDao: CharacterDao,
    private val characterMapper: CharacterMapper
) : BaseViewModel() {

    var idCharacter: Long = 0

    private val _characterDetailState = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    var characterDetailState: StateFlow<CharacterDetailState> = _characterDetailState

    suspend fun getCharacter() = getCharacterDetailUseCase.getCharacter(idCharacter).collect { result ->
        _characterDetailState.value = CharacterDetailState.Loading
        when (result) {
            is Result.Success -> {
                _characterDetailState.value = CharacterDetailState.Success(result.data)
            }
            is Result.Failure -> {
                _characterDetailState.value = CharacterDetailState.Error(RepositoryException(result.exception.code, result.exception.message))
            }
        }
    }

    fun getCharacterDB():Character {
        return characterMapper.toCharacterFromDB(characterDao.getCharacter(idCharacter))
    }
}

sealed class CharacterDetailState {
    data class Success(val character: Character) : CharacterDetailState()
    data class Error(val repositoryException: RepositoryException) : CharacterDetailState()
    object Loading : CharacterDetailState()
}