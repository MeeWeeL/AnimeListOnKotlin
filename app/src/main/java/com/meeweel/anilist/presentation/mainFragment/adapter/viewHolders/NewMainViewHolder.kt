package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime

class NewMainViewHolder(
    private val parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
    private val onItemStateChange: (id: Int, State: ListState) -> Unit,
    private val binding: MainRecyclerItemBinding =
        MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) : BaseViewHolder(binding.root) {
        override fun bind(anime: ShortAnime) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text =
                    if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
                Glide.with(this.mainFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .placeholder(R.drawable.anig)
                    .into(this.mainFragmentRecyclerItemImageView)

                itemData.text = anime.data

                binding.root.setOnClickListener {
                    onItemClick(anime.id)
                }
                notWatchedBtn.setOnClickListener {
                    onItemStateChange(anime.id, ListState.NOT_WATCHED)
                }
                watchedBtn.setOnClickListener {
                    onItemStateChange(anime.id, ListState.WATCHED)
                }
            }
    }
}