package com.carlosblaya.marveldemo.ui.comics

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.carlosblaya.marveldemo.base.BaseFragment
import com.carlosblaya.marveldemo.databinding.FragmentComicsListBinding
import com.carlosblaya.marveldemo.ui.comics.adapter.ComicListAdapter
import com.carlosblaya.marveldemo.util.ExtraConstants
import com.carlosblaya.marveldemo.util.extensions.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicListFragment : BaseFragment<ComicListViewModel, FragmentComicsListBinding>() {

    override fun getViewBinding(): FragmentComicsListBinding = FragmentComicsListBinding.inflate(layoutInflater)
    override val viewModel: ComicListViewModel by viewModels()

    private var idCharacter: Long = 0

    private val comicListAdapter by lazy {
        ComicListAdapter {
            showMessage(it?.name)
        }
    }

    override fun init() {
        initView()
        initAdapter()
    }

    private fun initView() {
        with(fragmentBinding) {
            rvComics.setHasFixedSize(true)
            rvComics.adapter = comicListAdapter
            arguments.let {
                idCharacter = requireArguments().getLong(ExtraConstants.ID_CHARACTER)
                viewModel.idCharacter = idCharacter // ViewModel idAlbum is always the same
                requestComicsCharacter()
            }
        }
    }

    fun requestComicsCharacter() {
        lifecycleScope.launch {
            viewModel.getComics().collect {
                comicListAdapter.submitData(it)
            }
        }
    }

    /**
     * LoadStateListener for paging
     */
    private fun initAdapter() {
        comicListAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && comicListAdapter.itemCount == 0

            // Only show the list if refresh succeeds.
            fragmentBinding.rvComics.isVisible = loadState.source.refresh is LoadState.NotLoading

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
}