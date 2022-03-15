package com.meeweel.anilist.view.fragments.mainfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseFragmentAdapter
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewHolder
import com.meeweel.anilist.viewmodel.Changing.NOT_WATCHED
import com.meeweel.anilist.viewmodel.Changing.WATCHED
import com.meeweel.anilist.viewmodel.Changing.getContext
import com.meeweel.anilist.viewmodel.Changing.saveTo

class MainFragmentAdapter : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: MainRecyclerItemBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(anime: ShortAnime) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text =
                    if (getContext().resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
                Glide.with(this.mainFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .placeholder(R.drawable.anig)
                    .into(this.mainFragmentRecyclerItemImageView)

                itemData.text = anime.data

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
                watchedBtn.setOnClickListener {
                    saveTo(anime.id, WATCHED)
                    notifyRemove(anime, layoutPosition)
                }
                notWatchedBtn.setOnClickListener {
                    saveTo(anime.id, NOT_WATCHED)
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
            saveTo(animeData[position].id, NOT_WATCHED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
        if (i == ItemTouchHelper.END) {
            saveTo(animeData[position].id, WATCHED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}