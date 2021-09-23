package com.meeweel.anilist.view.fragments.unwantedfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.view.fragments.mainfragment.MainFragment

class UnwantedFragmentAdapter :
    RecyclerView.Adapter<UnwantedFragmentAdapter.MainViewHolder>() {

    private var animeData: List<Anime> = listOf()
    private var onItemViewClickListener: UnwantedFragment.OnItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = UnwantedRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(animeData[position])
    }

    override fun getItemCount(): Int {
        return animeData.size
    }

    inner class MainViewHolder(private val binding: UnwantedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            binding.apply {
                unwantedFragmentRecyclerItemTextView.text = anime.title
                unwantedFragmentRecyclerItemImageView.setImageResource(anime.image)
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
            }
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: UnwantedFragment.OnItemViewClickListener){
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun removeOnItemViewClickListener(){
        onItemViewClickListener = null
    }

    fun setAnime(data: List<Anime>) {
        animeData = data
        notifyDataSetChanged()
    }

}