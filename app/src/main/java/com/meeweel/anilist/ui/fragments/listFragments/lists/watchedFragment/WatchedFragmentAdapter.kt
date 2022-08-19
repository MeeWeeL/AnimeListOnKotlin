package com.meeweel.anilist.ui.fragments.listFragments.lists.watchedFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseFragmentAdapter
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewHolder

class WatchedFragmentAdapter : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = WatchedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: WatchedRecyclerItemBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(anime: ShortAnime) {
            binding.apply {
                attention.visibility = if (anime.ratingCheck == 0) View.VISIBLE else View.GONE
                watchedFragmentRecyclerItemTextView.text = if (App.ContextHolder.context
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
}