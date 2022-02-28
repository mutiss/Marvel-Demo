package com.carlosblaya.marveldemo.ui.characters.detail

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.carlosblaya.marveldemo.R
import com.carlosblaya.marveldemo.base.BaseFragment
import com.carlosblaya.marveldemo.databinding.FragmentCharacterDetailBinding
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.ui.comics.ComicListFragment
import com.carlosblaya.marveldemo.ui.main.MainActivity
import com.carlosblaya.marveldemo.util.ExtraConstants
import com.carlosblaya.marveldemo.util.extensions.fromUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment<CharacterDetailViewModel, FragmentCharacterDetailBinding>() {

    override fun getViewBinding(): FragmentCharacterDetailBinding = FragmentCharacterDetailBinding.inflate(layoutInflater)
    override val viewModel: CharacterDetailViewModel by viewModels()

    private var idCharacter: Long = 0

    override fun init() {
        setupCollector()
        initView()
    }

    private fun initView() {
        arguments.let {
            idCharacter = requireArguments().getLong(ExtraConstants.ID_CHARACTER)
            viewModel.idCharacter = idCharacter // ViewModel idAlbum is always the same
            requestComicsCharacter(idCharacter)
            getDetailCharacterFromDB() //From DB First
            requestDetailCharacter()
        }
    }

    fun setupCollector() {
        lifecycleScope.launchWhenCreated {
            viewModel.characterDetailState.collect { uiState ->
                when (uiState) {
                    is CharacterDetailState.Loading -> showProgress()
                    is CharacterDetailState.Success -> {
                        hideProgress()
                        setupCharacter(uiState.character)
                    }
                    is CharacterDetailState.Error -> {
                        hideProgress()
                        showMessage(uiState.repositoryException.message)
                    }
                }

            }
        }
    }

    fun setupCharacter(character: Character) {
        with(fragmentBinding) {
            (requireActivity() as MainActivity).setTitleHeadbar(character.name)
            ivSuperhero.fromUrl(character.thumbnail)
            tvDescription.text = character.description
        }
    }

    fun requestDetailCharacter() {
        lifecycleScope.launch {
            viewModel.getCharacter()
        }
    }

    fun getDetailCharacterFromDB() {
        lifecycleScope.launch(Dispatchers.Main) {
            setupCharacter(viewModel.getCharacterDB())
        }
    }

    fun requestComicsCharacter(idCharacter: Long) {
        val bundle = Bundle()
        bundle.putSerializable(ExtraConstants.ID_CHARACTER, idCharacter)
        val mComicListFragment = ComicListFragment()
        mComicListFragment.arguments = bundle
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container_comics, mComicListFragment)
        transaction.commit()
    }
}