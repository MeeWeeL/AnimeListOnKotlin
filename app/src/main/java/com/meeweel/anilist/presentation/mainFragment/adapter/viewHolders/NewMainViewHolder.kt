package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.mainFragment.adapter.NewAnimeListAdapter.AdapterCallback

class NewMainViewHolder(
    private val parent: ViewGroup,
    private val callback: AdapterCallback,
    private val binding: MainRecyclerItemBinding =
        MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            mainCardTitle.text =
                if (root.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle

            Glide.with(mainCardImage.context)
                .load(anime.image)
                .error(progressDrawable)
                .placeholder(progressDrawable)
                .into(mainCardImage)

            itemData.text = anime.data

            binding.root.setOnClickListener {
                callback.onItemClick(anime.id)
            }
            binding.root.setOnLongClickListener {
                callback.onLongItemClick(anime.id, it, ListState.MAIN)
                return@setOnLongClickListener true
            }
            notWatchedBtn.setOnClickListener {
                callback.onItemStateChange(anime.id, ListState.NOT_WATCHED)
            }
            watchedBtn.setOnClickListener {
                callback.onItemStateChange(anime.id, ListState.WATCHED)
            }
        }
    }
}