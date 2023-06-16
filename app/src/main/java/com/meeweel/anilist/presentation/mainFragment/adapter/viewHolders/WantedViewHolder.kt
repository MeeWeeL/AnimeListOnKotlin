package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime

class WantedViewHolder(
    private val parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
    private val onItemStateChange: (id: Int, State: ListState) -> Unit,
    private val binding: WantedRecyclerItemBinding =
        WantedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
) :
    BaseViewHolder(binding.root) {

    override fun bind(anime: ShortAnime) {
        binding.apply {

            val circularProgressDrawable = CircularProgressDrawable(this.wantedFragmentRecyclerItemImageView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()


            wantedFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.wantedFragmentRecyclerItemImageView.context)
                .load(anime.image)
                .error(circularProgressDrawable)
                .placeholder(circularProgressDrawable)
                .into(this.wantedFragmentRecyclerItemImageView)

            itemData.text = anime.data
            binding.root.setOnClickListener {
                onItemClick(anime.id)
            }
            watchedBtnOnWanted.setOnClickListener {
                onItemStateChange(anime.id, ListState.WATCHED)
            }
        }
    }
}