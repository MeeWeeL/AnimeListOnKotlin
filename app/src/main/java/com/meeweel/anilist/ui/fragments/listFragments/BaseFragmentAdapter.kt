package com.meeweel.anilist.ui.fragments.listFragments

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.touchHelpers.ItemTouchHelperAdapter

abstract class BaseFragmentAdapter :
    ListAdapter<ShortAnime, BaseViewHolder>(DiffCallback), ItemTouchHelperAdapter {

    protected var onItemViewClickListener: BaseListFragment.OnItemViewClickListener? = null
    protected var onLongItemViewClickListener: BaseListFragment.OnLongItemViewClickListener? = null
    protected var onItemRemove: BaseListFragment.OnItemRemove? = null


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal fun setOnItemViewClickListener(onItemViewClickListener: BaseListFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    internal fun setOnLongItemViewClickListener(onLongItemViewClickListener: BaseListFragment.OnLongItemViewClickListener) {
        this.onLongItemViewClickListener = onLongItemViewClickListener
    }

    internal fun setOnItemRemove(onItemRemove: BaseListFragment.OnItemRemove) {
        this.onItemRemove = onItemRemove
    }

    internal fun removeClickListeners() {
        onItemViewClickListener = null
        onLongItemViewClickListener = null
        onItemRemove = null
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) = Unit
    override fun onItemDismiss(position: Int, i: Int) = Unit

    companion object DiffCallback : DiffUtil.ItemCallback<ShortAnime>() {

        override fun areItemsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShortAnime, newItem: ShortAnime): Boolean {
            return oldItem.id == newItem.id
        }
    }
}