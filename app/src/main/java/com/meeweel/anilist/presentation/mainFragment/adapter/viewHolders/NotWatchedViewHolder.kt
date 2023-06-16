package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.mainFragment.adapter.NewAnimeListAdapter.AdapterCallback

class NotWatchedViewHolder(
    private val parent: ViewGroup,
    private val callback: AdapterCallback,
    private val binding: NotWatchedRecyclerItemBinding =
        NotWatchedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            notWatchedCardTitle.text =
                if (root.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle

            Glide.with(notWatchedCardImage.context)
                .load(anime.image)
                .error(progressDrawable)
                .placeholder(progressDrawable)
                .into(notWatchedCardImage)

            itemData.text = anime.data

            binding.root.setOnClickListener {
                callback.onItemClick(anime.id)
            }
            unwantedBtn.setOnClickListener {
                callback.onItemStateChange(anime.id, ListState.UNWANTED)
            }
            wantedBtn.setOnClickListener {
                callback.onItemStateChange(anime.id, ListState.WANTED)
            }
            binding.root.setOnLongClickListener {
                callback.onLongItemClick(anime.id, it, ListState.NOT_WATCHED)
                return@setOnLongClickListener true
            }
        }
    }
}