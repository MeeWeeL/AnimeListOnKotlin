package com.meeweel.anilist.presentation.mainFragment.adapter.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.domain.models.ShortAnime

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(anime: ShortAnime)
}
