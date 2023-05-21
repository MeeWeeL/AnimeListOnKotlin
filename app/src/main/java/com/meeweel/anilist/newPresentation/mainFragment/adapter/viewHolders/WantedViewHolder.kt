package com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders

import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class WantedViewHolder(private val binding: WantedRecyclerItemBinding) :
    BaseViewHolder(binding.root) {

    override fun bind(anime: ShortAnime) {
        binding.apply {
            wantedFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.wantedFragmentRecyclerItemImageView.context)
                .load(anime.image)
                .error(R.drawable.anig)
                .placeholder(R.drawable.anig)
                .into(this.wantedFragmentRecyclerItemImageView)

            itemData.text = anime.data
        }
    }
}