package com.carlosblaya.marveldemo.ui.characters.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.carlosblaya.marveldemo.base.BaseViewModel
import com.carlosblaya.marveldemo.domain.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) : BaseViewModel() {
    suspend fun getCharacters(name: String?) = getCharactersUseCase.getCharacters(name).cachedIn(viewModelScope)
}