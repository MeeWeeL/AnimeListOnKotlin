package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class UnwantedViewHolder(
    private val parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
    private val onLongItemClick: (id: Int, view: View) -> Unit,
    private val binding: UnwantedRecyclerItemBinding =
        UnwantedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            unwantedFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.unwantedFragmentRecyclerItemImageView.context)
                .load(anime.image)
                .error(R.drawable.anig)
                .placeholder(R.drawable.anig)
                .into(this.unwantedFragmentRecyclerItemImageView)

            itemData.text = anime.data
            binding.root.setOnClickListener {
                onItemClick(anime.id)
            }
            binding.root.setOnLongClickListener{
                onLongItemClick(anime.id, it)
                return@setOnLongClickListener true
            }
        }
    }
}