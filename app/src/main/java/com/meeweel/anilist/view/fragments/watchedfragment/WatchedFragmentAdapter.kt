package com.meeweel.anilist.view.fragments.watchedfragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.ImageMaker

class WatchedFragmentAdapter :
    RecyclerView.Adapter<WatchedFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {
    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<Anime> = mutableListOf()
    private var onItemViewClickListener: WatchedFragment.OnItemViewClickListener? = null

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

        fun bind(anime: Anime) {
            binding.apply {
                watchedFragmentRecyclerItemTextView.text = if (Changing.getContext()
                        .resources.getBoolean(R.bool.isRussian)
                ) anime.ruTitle else anime.enTitle
                watchedFragmentRecyclerItemImageView.setImageBitmap(
                    imageMaker.getPictureFromDirectory(
                        anime.image
                    )
                )
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(0)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: WatchedFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
    }

    fun setAnime(data: List<Anime>) {
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