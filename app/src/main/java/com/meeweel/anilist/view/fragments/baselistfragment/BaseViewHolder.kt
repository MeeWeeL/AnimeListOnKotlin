package com.meeweel.anilist.view.fragments.baselistfragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.ItemTouchHelperViewHolder

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperViewHolder {
    abstract fun bind(anime: ShortAnime)
}