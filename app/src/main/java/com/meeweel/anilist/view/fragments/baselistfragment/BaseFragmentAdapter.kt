package com.meeweel.anilist.view.fragments.baselistfragment

import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter

abstract class BaseFragmentAdapter :
    RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    protected var animeData: MutableList<ShortAnime> = mutableListOf()
    protected var onItemViewClickListener: BaseListFragment.OnItemViewClickListener? = null
    protected var onLongItemViewClickListener: BaseListFragment.OnLongItemViewClickListener? = null

    override fun getItemCount(): Int {
        return animeData.size
    }

    internal fun setOnItemViewClickListener(onItemViewClickListener: BaseListFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    internal fun setOnLongItemViewClickListener(onLongItemViewClickListener: BaseListFragment.OnLongItemViewClickListener) {
        this.onLongItemViewClickListener = onLongItemViewClickListener
    }

    internal fun removeClickListeners() {
        onItemViewClickListener = null
        onLongItemViewClickListener = null
    }

    internal fun setAnime(data: List<ShortAnime>) {
        animeData = data.toMutableList()
        notifyDataSetChanged()
    }

    internal fun notifyRemove(anime: ShortAnime, position: Int) {
        animeData.remove(anime)
        notifyItemRemoved(position)
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