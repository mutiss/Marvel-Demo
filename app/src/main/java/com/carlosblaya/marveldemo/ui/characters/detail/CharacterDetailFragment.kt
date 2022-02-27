package com.carlosblaya.marveldemo.ui.characters.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.carlosblaya.marveldemo.base.BaseFragment
import com.carlosblaya.marveldemo.databinding.FragmentCharacterDetailBinding
import com.carlosblaya.marveldemo.domain.model.Character
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
            getDetailCharacterFromDB(idCharacter) //From DB First
            requestDetailCharacter(idCharacter)
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

    fun requestDetailCharacter(idCharacter: Long) {
        lifecycleScope.launch {
            viewModel.getCharacter(idCharacter)
        }
    }

    fun getDetailCharacterFromDB(idCharacter: Long) {
        lifecycleScope.launch(Dispatchers.Main) {
            setupCharacter(viewModel.getCharacterDB(idCharacter))
        }
    }
}