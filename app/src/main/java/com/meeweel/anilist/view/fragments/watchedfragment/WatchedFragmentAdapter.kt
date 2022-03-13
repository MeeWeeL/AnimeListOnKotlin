package com.meeweel.anilist.view.fragments.watchedfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing

class WatchedFragmentAdapter :
    RecyclerView.Adapter<WatchedFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {

    private var animeData: MutableList<ShortAnime> = mutableListOf()
    private var onItemViewClickListener: WatchedFragment.OnItemViewClickListener? = null
    private var onLongItemViewClickListener: WatchedFragment.OnLongItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = WatchedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(animeData[position])
    }

    override fun getItemCount(): Int {
        return animeData.size
    }

    inner class MainViewHolder(private val binding: WatchedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun bind(anime: ShortAnime) {
            binding.apply {
                watchedFragmentRecyclerItemTextView.text = if (Changing.getContext()
                        .resources.getBoolean(R.bool.isRussian)
                ) anime.ruTitle else anime.enTitle

                itemData.text = anime.data
                Glide.with(this.watchedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.watchedFragmentRecyclerItemImageView)

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
            }
        }

        override fun onItemSelected() {
//            itemView.setBackgroundColor(0)
        }

        override fun onItemClear() {
//            itemView.setBackgroundColor(Changing.getContext().getColor(R.color.main_color))
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: WatchedFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun setOnLongItemViewClickListener(onLongItemViewClickListener: WatchedFragment.OnLongItemViewClickListener) {
        this.onLongItemViewClickListener = onLongItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
    }

    fun removeOnLongItemViewClickListener() {
        onLongItemViewClickListener = null
    }

    fun setAnime(data: List<ShortAnime>) {
        animeData = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        animeData.removeAt(fromPosition).apply {
            animeData.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun notifyRemove(anime: ShortAnime, position: Int) {
        animeData.remove(anime)
        notifyItemRemoved(position)
    }

    override fun onItemDismiss(position: Int, i: Int) {

    }

}