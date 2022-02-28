package com.carlosblaya.marveldemo.ui.comics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlosblaya.marveldemo.databinding.ListItemComicBinding
import com.carlosblaya.marveldemo.domain.model.Comic
import com.carlosblaya.marveldemo.util.extensions.fromUrl
import javax.inject.Inject

class ComicListAdapter  @Inject constructor(private val comicClickListener: (comicItem: Comic?) -> Unit) :
    PagingDataAdapter<Comic, ComicListAdapter.ComicListViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicListViewHolder {
        val binding = ListItemComicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComicListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind() }
    }

    inner class ComicListViewHolder(val binding: ListItemComicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val comicItem = getItem(absoluteAdapterPosition)
            comicItem?.let {
                binding.ivComic.fromUrl(comicItem.thumbnail)
                binding.root.setOnClickListener {
                    comicClickListener(comicItem)
                }
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(
            oldItem: Comic,
            newItem: Comic
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Comic,
            newItem: Comic
        ): Boolean {
            return oldItem == newItem
        }
    }
}