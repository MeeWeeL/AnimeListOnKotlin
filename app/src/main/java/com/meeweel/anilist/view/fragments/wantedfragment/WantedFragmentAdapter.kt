package com.meeweel.anilist.view.fragments.wantedfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.Changing.WATCHED

class WantedFragmentAdapter :
    RecyclerView.Adapter<WantedFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {

    private var animeData: MutableList<ShortAnime> = mutableListOf()
    private var onItemViewClickListener: WantedFragment.OnItemViewClickListener? = null
    private var onLongItemViewClickListener: WantedFragment.OnLongItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = WantedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: WantedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun bind(anime: ShortAnime) {
            binding.apply {
                wantedFragmentRecyclerItemTextView.text = if (Changing.getContext()
                        .resources.getBoolean(R.bool.isRussian)
                ) anime.ruTitle else anime.enTitle

                itemData.text = anime.data
                Glide.with(this.wantedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.wantedFragmentRecyclerItemImageView)

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
                watchedBtnOnWanted.setOnClickListener {
                    Changing.saveTo(anime.id, WATCHED)
                    notifyRemove(anime, layoutPosition)
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

    fun setOnItemViewClickListener(onItemViewClickListener: WantedFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun setOnLongItemViewClickListener(onLongItemViewClickListener: WantedFragment.OnLongItemViewClickListener) {
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
        if (i == ItemTouchHelper.END) {
            Changing.saveTo(animeData[position].id, WATCHED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}