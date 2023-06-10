package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime

class NewMainViewHolder(
    private val parent: ViewGroup,
    private val onItemClickCallback: (Int) -> Unit,
    private val binding: MainRecyclerItemBinding = MainRecyclerItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ),
) : BaseViewHolder(binding.root) {
    override fun bind(anime: ShortAnime) {
        binding.apply {
            val circularProgressDrawable =
                CircularProgressDrawable(this.mainFragmentRecyclerItemImageView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            mainFragmentRecyclerItemTextView.text =
                if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
            Glide.with(this.mainFragmentRecyclerItemImageView.context).load(anime.image)
                .error(circularProgressDrawable).placeholder(circularProgressDrawable)
                .into(this.mainFragmentRecyclerItemImageView)

            itemData.text = anime.data

            binding.root.setOnClickListener {
                onItemClickCallback(anime.id)
            }
        }
    }
}