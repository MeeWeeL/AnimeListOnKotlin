package com.meeweel.anilist.newPresentation.mainFragment.adapter

import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
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

class NewAnimeListAdapter(private val itemClickListener: (Int) -> Unit) :
    ListAdapter<ShortAnime, BaseViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ListState.MAIN.int -> NewMainViewHolder(
                parent,
                { animeId -> itemClickListener(animeId) })

            ListState.UNWANTED.int -> UnwantedViewHolder(parent)

            ListState.WANTED.int -> WantedViewHolder(parent)

            ListState.NOT_WATCHED.int -> NotWatchedViewHolder(parent)

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