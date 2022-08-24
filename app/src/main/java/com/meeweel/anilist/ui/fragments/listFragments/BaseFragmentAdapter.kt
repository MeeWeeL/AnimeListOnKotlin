package com.meeweel.anilist.ui.fragments.listFragments

import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.touchHelpers.ItemTouchHelperAdapter

abstract class BaseFragmentAdapter :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    protected var animeData: MutableList<ShortAnime> = mutableListOf()
    protected var onItemViewClickListener: BaseListFragment.OnItemViewClickListener? = null
    protected var onLongItemViewClickListener: BaseListFragment.OnLongItemViewClickListener? = null
    protected var onItemRemove: BaseListFragment.OnItemRemove? = null

    override fun getItemCount(): Int {
        return animeData.size
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

    internal fun setAnime(data: List<ShortAnime>) {
        animeData = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        animeData.removeAt(fromPosition).apply {
            animeData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int, i: Int) {

    }
}