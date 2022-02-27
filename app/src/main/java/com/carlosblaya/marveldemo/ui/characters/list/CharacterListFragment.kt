package com.carlosblaya.marveldemo.ui.characters.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.carlosblaya.marveldemo.R
import com.carlosblaya.marveldemo.base.BaseFragment
import com.carlosblaya.marveldemo.databinding.FragmentCharacterListBinding
import com.carlosblaya.marveldemo.ui.characters.list.adapter.CharacterListAdapter
import com.carlosblaya.marveldemo.ui.main.MainActivity
import com.carlosblaya.marveldemo.util.ExtraConstants
import com.carlosblaya.marveldemo.util.FragmentUtil
import com.carlosblaya.marveldemo.util.extensions.fadeIn
import com.carlosblaya.marveldemo.util.extensions.fadeOut
import com.carlosblaya.marveldemo.util.extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<CharacterListViewModel, FragmentCharacterListBinding>() {

    override fun getViewBinding(): FragmentCharacterListBinding = FragmentCharacterListBinding.inflate(layoutInflater)
    override val viewModel: CharacterListViewModel by viewModels()

    private val characterListAdapter by lazy {
        CharacterListAdapter {
            val bundle = Bundle()
            bundle.putSerializable(ExtraConstants.ID_CHARACTER, it?.id)
            FragmentUtil.changeMainFragment(
                requireActivity() as MainActivity,
                FragmentUtil.TAG_DETAIL, bundle
            )
        }
    }

    override fun init() {
        initView()
        setupClicks()
        initAdapter()
    }

    private fun initView() {
        (requireActivity() as MainActivity).setTitleHeadbar(resources.getString(R.string.search))
        with(fragmentBinding) {
            rvSuperheroes.setHasFixedSize(true)
            rvSuperheroes.adapter = characterListAdapter
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        if (s.length == 1) {
                            if (!ivClearSearch.isVisible) {
                                ivClearSearch.fadeIn()
                                ivDoSearch.fadeIn()
                            }
                        }
                    } else {
                        ivClearSearch.fadeOut()
                        ivDoSearch.fadeOut()
                        requestBlankSearch()
                    }
                }
            })
        }

        requestBlankSearch() //First emoty request
    }

    private fun setupClicks() {
        with(fragmentBinding) {
            ivClearSearch.setOnClickListener {
                fragmentBinding.etSearch.hideKeyboard()
                etSearch.setText("")
            }
            ivDoSearch.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.getCharacters(etSearch.text.toString()).collect {
                        characterListAdapter.submitData(it)
                    }
                }
            }
        }
    }

    /**
     * LoadStateListener for paging
     */
    private fun initAdapter() {
        characterListAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && characterListAdapter.itemCount == 0
            fragmentBinding.tvNoResults.isVisible = isListEmpty

            // Only show the list if refresh succeeds.
            fragmentBinding.rvSuperheroes.isVisible = loadState.source.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh.
            handleLoading(loadState.source.refresh is LoadState.Loading)

            /**
             * loadState.refresh - represents the load state for loading the PagingData for the first time.
             * loadState.prepend - represents the load state for loading data at the start of the list.
             * loadState.append - represents the load state for loading data at the end of the list.
             * */
            // If we have an error, show a toast
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                showMessage(it.error.message.toString())
            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) showProgress() else hideProgress()
    }

    fun requestBlankSearch() {
        lifecycleScope.launch {
            viewModel.getCharacters(null).collect {
                characterListAdapter.submitData(it)
            }
        }
    }
}