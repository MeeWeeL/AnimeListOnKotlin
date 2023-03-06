package com.meeweel.anilist.newUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewHolder

class NewAnimeListAdapter: ListAdapter<ShortAnime, NewMainViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewMainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewMainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onItemSwipe(viewHolderPosition: Int, i: Int) {
        if (i == ItemTouchHelper.START) {
            // TODO
        }
        if (i == ItemTouchHelper.END) {
            // TODO
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ShortAnime>() {

        override fun areItemsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem.id == newItem.id
        }
    }
}