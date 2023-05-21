package com.meeweel.anilist.newPresentation.mainFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.newPresentation.mainFragment.ListState
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.BaseViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.NewMainViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.NotWatchedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.UnwantedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.WantedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.WatchedViewHolder

class NewAnimeListAdapter : ListAdapter<ShortAnime, BaseViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ListState.MAIN.int - 1 -> NewMainViewHolder(
                MainRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ListState.UNWANTED.int - 1 -> UnwantedViewHolder(
                UnwantedRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ListState.WANTED.int - 1 -> WantedViewHolder(
                WantedRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ListState.NOT_WATCHED.int - 1 -> NotWatchedViewHolder(
                NotWatchedRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ListState.WATCHED.int - 1 -> WatchedViewHolder(
                WatchedRecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].list - 1
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