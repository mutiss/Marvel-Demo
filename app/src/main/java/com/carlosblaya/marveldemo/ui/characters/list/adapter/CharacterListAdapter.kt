package com.carlosblaya.marveldemo.ui.characters.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlosblaya.marveldemo.databinding.ItemCharacterBinding
import com.carlosblaya.marveldemo.domain.model.Character
import com.carlosblaya.marveldemo.util.fromUrl
import com.carlosblaya.marveldemo.util.hideKeyboard
import javax.inject.Inject

class CharacterListAdapter @Inject constructor(private val artistClickListener: (characterItem: Character) -> Unit) :
    PagingDataAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(DiffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class CharacterListViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {

            val characterItem = getItem(absoluteAdapterPosition)
            characterItem?.let {
                binding.ivSuperhero.fromUrl(character.thumbnail)
                binding.tvName.text = character.name
                artistClickListener(it)
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem == newItem
        }
    }
}