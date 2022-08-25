package com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity.Companion.NOT_WATCHED
import com.meeweel.anilist.ui.MainActivity.Companion.WATCHED
import com.meeweel.anilist.ui.fragments.listFragments.BaseFragmentAdapter
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewHolder

class MainFragmentAdapter(private val repository: LocalRepository) : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(animeData[position])
    }

    override fun getItemCount(): Int {
        return animeData.size
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
                    repository.updateLocalEntity(anime.id, WATCHED)
                    onItemRemove?.removeItem(anime)
                }
                notWatchedBtn.setOnClickListener {
                    repository.updateLocalEntity(anime.id, NOT_WATCHED)
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
            repository.updateLocalEntity(animeData[position].id, NOT_WATCHED)
            onItemRemove?.removeItem(animeData[position])
        }
        if (i == ItemTouchHelper.END) {
            repository.updateLocalEntity(animeData[position].id, WATCHED)
            onItemRemove?.removeItem(animeData[position])
        }
    }
}