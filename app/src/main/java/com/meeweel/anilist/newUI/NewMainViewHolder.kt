package com.meeweel.anilist.newUI

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewHolder
import com.meeweel.anilist.ui.fragments.listFragments.touchHelpers.ItemTouchHelperViewHolder

class NewMainViewHolder(private val binding: MainRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(anime: ShortAnime) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text =
                    if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
                Glide.with(this.mainFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .placeholder(R.drawable.anig)
                    .into(this.mainFragmentRecyclerItemImageView)

                itemData.text = anime.data
            }
        }
}