package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.meeweel.anilist.domain.models.ShortAnime

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val progressDrawable = CircularProgressDrawable(itemView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    abstract fun bind(anime: ShortAnime)
}
