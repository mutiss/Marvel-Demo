package com.carlosblaya.marveldemo.ui.characters.comics

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.carlosblaya.marveldemo.base.BaseViewModel
import com.carlosblaya.marveldemo.data.Result
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.domain.repository.RepositoryException
import com.carlosblaya.marveldemo.domain.usecases.GetComicsCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@HiltViewModel
class ComicListViewModel @Inject constructor(
    private val getComicsCharacterUseCase: GetComicsCharacterUseCase
) : BaseViewModel() {

    var idCharacter: Long = 0

    suspend fun getComics() = getComicsCharacterUseCase.getComics(idCharacter).cachedIn(viewModelScope)
}
