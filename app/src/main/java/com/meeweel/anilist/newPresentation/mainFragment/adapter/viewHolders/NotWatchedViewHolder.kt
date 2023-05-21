package com.meeweel.anilist.newPresentation.mainFragment.adapter.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class NotWatchedViewHolder(private val binding: NotWatchedRecyclerItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(anime: ShortAnime) {
        binding.apply {
            notWatchedFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.notWatchedFragmentRecyclerItemImageView.context)
                .load(anime.image)
                .error(R.drawable.anig)
                .placeholder(R.drawable.anig)
                .into(this.notWatchedFragmentRecyclerItemImageView)

            itemData.text = anime.data
        }
    }
}