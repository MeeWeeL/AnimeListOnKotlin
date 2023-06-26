package com.meeweel.anilist.presentation.mainFragment.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.BaseViewHolder
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.NewMainViewHolder
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.NotWatchedViewHolder
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.UnwantedViewHolder
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.WantedViewHolder
import com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders.WatchedViewHolder

class NewAnimeListAdapter(
    private val callback: AdapterCallback,
) : ListAdapter<ShortAnime, BaseViewHolder>(DiffCallback) {
    private var originalList: List<ShortAnime>? = null
    val filter: AnimeListFilter = AnimeListFilter()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ListState.MAIN.int -> NewMainViewHolder(parent, callback)
            ListState.UNWANTED.int -> UnwantedViewHolder(parent, callback)
            ListState.WANTED.int -> WantedViewHolder(parent, callback)
            ListState.NOT_WATCHED.int -> NotWatchedViewHolder(parent, callback)
            ListState.WATCHED.int -> WatchedViewHolder(parent, callback)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].list
    }

    override fun submitList(list: List<ShortAnime>?) {
        originalList = list
        super.submitList(filter.filter(originalList!!))
    }

    override fun submitList(list: List<ShortAnime>?, commitCallback: Runnable?) {
        originalList = list
        super.submitList(filter.filter(originalList!!), commitCallback)
    }

    fun setSearchText(text: String, commitCallback: Runnable?) {
        filter.setTitleText(text)
        submitList(originalList, commitCallback)
    }

    fun submitFilter(commitCallback: Runnable?) =
        submitList(originalList, commitCallback)


    fun onItemSwipe(viewHolderPosition: Int, i: Int) {
        val id = getItem(viewHolderPosition).id

        val viewType = getItemViewType(viewHolderPosition)
        if (i == ItemTouchHelper.START) {
            when (viewType) {
                ListState.MAIN.int -> callback.onItemStateChange(id, ListState.NOT_WATCHED)
                ListState.NOT_WATCHED.int -> callback.onItemStateChange(id, ListState.UNWANTED)
                else -> notifyItemChanged(viewHolderPosition)
            }
        }
        if (i == ItemTouchHelper.END) {
            when (viewType) {
                ListState.MAIN.int -> callback.onItemStateChange(id, ListState.WATCHED)
                ListState.NOT_WATCHED.int -> callback.onItemStateChange(id, ListState.WANTED)
                ListState.WANTED.int -> callback.onItemStateChange(id, ListState.WATCHED)
                else -> notifyItemChanged(viewHolderPosition)
            }
        }
    }

    interface AdapterCallback {
        fun onItemClick(id: Int)
        fun onItemStateChange(id: Int, state: ListState)
        fun onLongItemClick(id: Int, view: View, listState: ListState)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<ShortAnime>() {

        override fun areItemsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem.id == newItem.id
        }
    }
}