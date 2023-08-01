package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.mainFragment.adapter.NewAnimeListAdapter.AdapterCallback

class WatchedViewHolder(
    private val parent: ViewGroup,
    private val callback: AdapterCallback,
    private val binding: WatchedRecyclerItemBinding =
        WatchedRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            watchedCardTitle.text =
                if (root.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle

            Glide.with(watchedCardImage.context)
                .load(anime.image)
                .error(progressDrawable)
                .placeholder(progressDrawable)
                .into(watchedCardImage)

            itemData.text = anime.data

            root.setOnClickListener {
                callback.onItemClick(anime.id)
            }
            root.setOnLongClickListener {
                callback.onLongItemClick(anime.id, it, ListState.WATCHED)
                return@setOnLongClickListener true
            }

            if (anime.ratingCheck == 0) {
                attention.visibility = View.VISIBLE
                callback.changeListInWatched()
            }
        }
    }
}