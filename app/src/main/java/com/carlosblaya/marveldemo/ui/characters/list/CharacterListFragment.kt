package com.carlosblaya.marveldemo.ui.characters.list

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
import com.carlosblaya.marveldemo.util.fadeIn
import com.carlosblaya.marveldemo.util.fadeOut
import com.carlosblaya.marveldemo.util.hideKeyboard
import com.carlosblaya.marveldemo.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<CharacterListViewModel, FragmentCharacterListBinding>() {

    override fun getViewBinding(): FragmentCharacterListBinding = FragmentCharacterListBinding.inflate(layoutInflater)
    override val viewModel: CharacterListViewModel by viewModels()

    private val characterListAdapter by lazy {
        CharacterListAdapter {

        }
    }

    override fun init() {
        initView()
        setupClicks()
        initAdapter()
        bindEvents()
    }

    private fun initView() {
        (requireActivity() as MainActivity).setTitleHeadbar(resources.getString(R.string.search))

        with(fragmentBinding) {

            // initial recyclerView
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
                            if (!ivClearSearch.isVisible)
                                ivClearSearch.fadeIn()
                        } else if (s.length >= 3) {
                            lifecycleScope.launch {
                                viewModel.getCharacters(s.toString()).collect {
                                    characterListAdapter.submitData(it)
                                }
                            }
                        }
                    } else {
                        ivClearSearch.fadeOut()
                        requestBlankSearch()
                    }
                }
            })
        }

        requestBlankSearch() //First emoty request
    }

    private fun setupClicks() {
        fragmentBinding.ivClearSearch.setOnClickListener {
            fragmentBinding.etSearch.hideKeyboard()
            fragmentBinding.etSearch.setText("")
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
                requireActivity().showMessage(it.error.message.toString())
            }
        }
    }

    private fun bindEvents() {

    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            (requireActivity() as MainActivity).showProgress()
        } else {
            (requireActivity() as MainActivity).hideProgress()
        }
    }

    fun requestBlankSearch() {
        lifecycleScope.launch {
            viewModel.getCharacters(null).collect {
                characterListAdapter.submitData(it)
            }
        }
    }
}