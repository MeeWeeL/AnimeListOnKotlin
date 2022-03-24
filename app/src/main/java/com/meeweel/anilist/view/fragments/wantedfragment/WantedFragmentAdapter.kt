package com.meeweel.anilist.view.fragments.wantedfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.view.MainActivity.Companion.WATCHED
import com.meeweel.anilist.view.fragments.baselistfragment.BaseFragmentAdapter
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewHolder

class WantedFragmentAdapter(private val repository: LocalRepository) : BaseFragmentAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = WantedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: WantedRecyclerItemBinding) :
        BaseViewHolder(binding.root) {

        override fun bind(anime: ShortAnime) {
            binding.apply {
                wantedFragmentRecyclerItemTextView.text = if (App.ContextHolder.context
                        .resources.getBoolean(R.bool.isRussian)
                ) anime.ruTitle else anime.enTitle

                itemData.text = anime.data
                Glide.with(this.wantedFragmentRecyclerItemImageView.context)
                    .load(anime.image)
                    .error(R.drawable.anig)
                    .into(this.wantedFragmentRecyclerItemImageView)

                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                root.setOnLongClickListener {
                    onLongItemViewClickListener?.onLongItemViewClick(anime, root, layoutPosition)
                    true
                }
                watchedBtnOnWanted.setOnClickListener {
                    repository.updateLocalEntity(anime.id, WATCHED)
                    notifyRemove(anime, layoutPosition)
                }
            }
        }

        override fun onItemSelected() {
//            itemView.setBackgroundColor(0)
        }

        override fun onItemClear() {
//            itemView.setBackgroundColor(Changing.getContext().getColor(R.color.main_color))
        }
    }

    override fun onItemDismiss(position: Int, i: Int) {
        if (i == ItemTouchHelper.END) {
            repository.updateLocalEntity(animeData[position].id, WATCHED)
            animeData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

}