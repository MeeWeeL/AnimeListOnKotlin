package com.meeweel.anilist.newPresentation.mainFragment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.BaseViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.NewMainViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.NotWatchedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.UnwantedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.WantedViewHolder
import com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders.WatchedViewHolder

class NewAnimeListAdapter(private val stateCallBack: (id: Int, State: ListState) -> Unit) :
    ListAdapter<ShortAnime, BaseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ListState.MAIN.int -> NewMainViewHolder(parent, stateCallBack = stateCallBack)

            ListState.UNWANTED.int -> UnwantedViewHolder(parent)

            ListState.WANTED.int -> WantedViewHolder(parent, stateCallBack = stateCallBack)

            ListState.NOT_WATCHED.int -> NotWatchedViewHolder(parent, stateCallBack = stateCallBack)

            ListState.WATCHED.int -> WatchedViewHolder(parent)

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].list
    }

    fun onItemSwipe(viewHolderPosition: Int, i: Int) {
        val position = getItem(viewHolderPosition).id
        val viewType = getItemViewType(viewHolderPosition)
        if (i == ItemTouchHelper.START) {
            when (viewType) {
                ListState.MAIN.int -> stateCallBack(position, ListState.NOT_WATCHED)

                ListState.NOT_WATCHED.int -> stateCallBack(position, ListState.UNWANTED)

                ListState.WANTED.int -> stateCallBack(position, ListState.WATCHED)

                else -> notifyItemChanged(viewHolderPosition)
            }
        }
        if (i == ItemTouchHelper.END) {
            when (viewType) {
                ListState.MAIN.int -> stateCallBack(position, ListState.WATCHED)

                ListState.NOT_WATCHED.int -> stateCallBack(position, ListState.WANTED)

                ListState.WANTED.int -> stateCallBack(position, ListState.WATCHED)

                else -> notifyItemChanged(viewHolderPosition)
            }
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