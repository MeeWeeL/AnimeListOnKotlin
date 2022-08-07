package com.meeweel.anilist.ui.fragments.listFragments

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.touchHelpers.ItemTouchHelperViewHolder

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(anime: ShortAnime)
}