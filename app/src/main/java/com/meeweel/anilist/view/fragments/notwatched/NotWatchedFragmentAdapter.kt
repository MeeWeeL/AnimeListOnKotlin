package com.meeweel.anilist.view.fragments.notwatched

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseFragmentAdapter
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewHolder
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.Changing.UNWANTED
import com.meeweel.anilist.viewmodel.Changing.WANTED

class NotWatchedFragmentAdapter : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = NotWatchedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(animeData[position])
    }

    override fun getItemCount(): Int {
        return animeData.size
    }

    inner class MainViewHolder(private val binding: NotWatchedRecyclerItemBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(anime: ShortAnime) {
            binding.apply {
                notWatchedFragmentRecyclerItemTextView.text =
                    if (Changing.getContext().resources.getBoolean(
                            R.bool.isRussian
                        )
                    ) anime.ruTitle else anime.enTitle

                Glide.with(this.notWatchedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.notWatchedFragmentRecyclerItemImageView)

                itemData.text = anime.data

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
                wantedBtn.setOnClickListener {
                    Changing.saveTo(anime.id, WANTED)
                    notifyRemove(anime, layoutPosition)
                }
                unwantedBtn.setOnClickListener {
                    Changing.saveTo(anime.id, UNWANTED)
                    notifyRemove(anime, layoutPosition)
                }
            }
        }

        override fun onItemSelected() {
//            itemView.setBackgroundColor(0)
        }

        override fun onItemClear() {
//            itemView.setBackgroundColor(getContext().getColor(R.color.main_color))
        }
    }

    override fun onItemDismiss(position: Int, i: Int) {
        if (i == ItemTouchHelper.START) {
            Changing.saveTo(animeData[position].id, UNWANTED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
        if (i == ItemTouchHelper.END) {
            Changing.saveTo(animeData[position].id, WANTED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}