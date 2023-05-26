package com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.ui.fragments.listFragments.BaseFragmentAdapter
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewHolder

class MainFragmentAdapter(private val repository: Repository) : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    inner class MainViewHolder(private val binding: MainRecyclerItemBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(anime: ShortAnime) {
            binding.apply {
                mainFragmentRecyclerItemTextView.text =
                    if (App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)) anime.ruTitle else anime.enTitle
                Glide.with(this.mainFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .placeholder(R.drawable.anig)
                    .into(this.mainFragmentRecyclerItemImageView)

                itemData.text = anime.data

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
                watchedBtn.setOnClickListener {
                    repository.updateEntityLocal(anime.id, ListState.WATCHED)
                    onItemRemove?.removeItem(anime)
                }
                notWatchedBtn.setOnClickListener {
                    repository.updateEntityLocal(anime.id, ListState.NOT_WATCHED)
                    onItemRemove?.removeItem(anime)
                }
            }
        }


        override fun onItemSelected() {
//            itemView.setBackgroundColor(0)
        }

        override fun onItemClear() {
//            itemView.setBackgroundColor(getContext().getColor(R.color.main_color))
        }
    }

    override fun onItemDismiss(position: Int, i: Int) {
        if (i == ItemTouchHelper.START) {
            repository.updateEntityLocal(getItem(position).id, ListState.NOT_WATCHED)
            onItemRemove?.removeItem(getItem(position))
        }
        if (i == ItemTouchHelper.END) {
            repository.updateEntityLocal(getItem(position).id, ListState.WATCHED)
            onItemRemove?.removeItem(getItem(position))
        }
    }
}