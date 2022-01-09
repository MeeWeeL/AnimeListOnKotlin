package com.meeweel.anilist.view.fragments.unwantedfragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperAdapter
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.ImageMaker

class UnwantedFragmentAdapter :
    RecyclerView.Adapter<UnwantedFragmentAdapter.MainViewHolder>(), ItemTouchHelperAdapter {
//    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<ShortAnime> = mutableListOf()
    private var onItemViewClickListener: UnwantedFragment.OnItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = UnwantedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: UnwantedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun bind(anime: ShortAnime) {
            binding.apply {
                unwantedFragmentRecyclerItemTextView.text =
                    if (Changing.getContext().resources.getBoolean(
                            R.bool.isRussian
                        )
                    ) anime.ruTitle else anime.enTitle

                Glide.with(this.unwantedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.unwantedFragmentRecyclerItemImageView)

//                unwantedFragmentRecyclerItemImageView.setImageBitmap(
//                    imageMaker.getPictureFromDirectory(
//                        anime.image
//                    )
//                )

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

    fun setOnItemViewClickListener(onItemViewClickListener: UnwantedFragment.OnItemViewClickListener) {
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
    }

}