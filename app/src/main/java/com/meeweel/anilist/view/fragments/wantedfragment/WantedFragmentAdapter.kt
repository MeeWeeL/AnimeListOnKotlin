package com.meeweel.anilist.view.fragments.wantedfragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.Changing.WATCHED
import com.meeweel.anilist.viewmodel.ImageMaker

class WantedFragmentAdapter :
    RecyclerView.Adapter<WantedFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {
//    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<ShortAnime> = mutableListOf()
    private var onItemViewClickListener: WantedFragment.OnItemViewClickListener? = null

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

                Glide.with(this.wantedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.wantedFragmentRecyclerItemImageView)

//                wantedFragmentRecyclerItemImageView.setImageBitmap(
//                    imageMaker.getPictureFromDirectory(
//                        anime.image
//                    )
//                )

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                watchedBtn.setOnClickListener {
                    Changing.saveTo(anime.id, WATCHED)
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

    fun setOnItemViewClickListener(onItemViewClickListener: WantedFragment.OnItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener() {
        onItemViewClickListener = null
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

    override fun onItemDismiss(position: Int, i: Int) {
        if (i == ItemTouchHelper.END) {
            Changing.saveTo(animeData[position].id, WATCHED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}