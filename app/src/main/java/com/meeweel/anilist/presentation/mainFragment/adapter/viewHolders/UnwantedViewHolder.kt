package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.mainFragment.adapter.NewAnimeListAdapter.AdapterCallback

class UnwantedViewHolder(
    private val parent: ViewGroup,
    private val callback: AdapterCallback,
    private val binding: UnwantedRecyclerItemBinding =
        UnwantedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            unwantedCardTitle.text =
                if (root.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle

            Glide.with(unwantedCardImage.context)
                .load(anime.image)
                .error(progressDrawable)
                .placeholder(progressDrawable)
                .into(unwantedCardImage)

            itemData.text = anime.data

            binding.root.setOnClickListener {
                callback.onItemClick(anime.id)
            }
            binding.root.setOnLongClickListener {
                callback.onLongItemClick(anime.id, it, ListState.UNWANTED)
                return@setOnLongClickListener true
            }
        }
    }
}