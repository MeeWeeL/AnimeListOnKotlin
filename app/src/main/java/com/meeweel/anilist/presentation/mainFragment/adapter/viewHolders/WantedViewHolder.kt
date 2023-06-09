package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class WantedViewHolder(
    private val parent: ViewGroup,
    private val binding: WantedRecyclerItemBinding =
        WantedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
) :
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