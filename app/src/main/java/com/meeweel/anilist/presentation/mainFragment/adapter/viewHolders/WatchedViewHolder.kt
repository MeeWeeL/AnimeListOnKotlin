package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WatchedRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class WatchedViewHolder(
    private val parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
    private val binding: WatchedRecyclerItemBinding =
        WatchedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) :
    BaseViewHolder(binding.root) {

    override fun bind(anime: ShortAnime) {
        binding.apply {

            val circularProgressDrawable = CircularProgressDrawable(this.watchedFragmentRecyclerItemImageView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()


            watchedFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.watchedFragmentRecyclerItemImageView.context)
                .load(anime.image)
                .error(circularProgressDrawable)
                .placeholder(circularProgressDrawable)
                .into(this.watchedFragmentRecyclerItemImageView)

            itemData.text = anime.data
            binding.root.setOnClickListener {
                onItemClick(anime.id)
            }
        }
    }
}