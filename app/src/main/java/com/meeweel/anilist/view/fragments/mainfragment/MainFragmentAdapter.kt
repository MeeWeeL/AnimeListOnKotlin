package com.meeweel.anilist.view.fragments.mainfragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing.getContext
import com.meeweel.anilist.viewmodel.Changing.saveTo
import com.meeweel.anilist.viewmodel.ImageMaker

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {

    private var animeData: MutableList<Anime> = mutableListOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null
    val imageMaker: ImageMaker = ImageMaker()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun bind(anime: Anime) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text =
                    if (getContext().resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
                mainFragmentRecyclerItemImageView.setImageBitmap(
                    imageMaker.getPictureFromDirectory(
                        anime.image
                    )
                )
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                watchedBtn.setOnClickListener {
                    saveTo(anime, 2)
                    animeData.remove(anime)
                    notifyItemRemoved(layoutPosition)
                }
                notWatchedBtn.setOnClickListener {
                    saveTo(anime, 3)
                    animeData.remove(anime)
                    notifyItemRemoved(layoutPosition)
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

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener) {
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
        if (i == ItemTouchHelper.START) {
            saveTo(animeData[position], 3)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
        if (i == ItemTouchHelper.END) {
            saveTo(animeData[position], 2)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}