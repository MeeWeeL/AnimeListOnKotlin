package com.meeweel.anilist.ui.fragments.baselistfragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.ItemTouchHelperViewHolder

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(anime: ShortAnime)
}