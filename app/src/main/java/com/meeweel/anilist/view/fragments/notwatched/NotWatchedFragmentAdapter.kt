package com.meeweel.anilist.view.fragments.notwatched

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.notWatchedToUnwanted
import com.meeweel.anilist.model.data.notWatchedToWanted
import com.meeweel.anilist.view.fragments.mainfragment.MainFragment
import com.meeweel.anilist.view.fragments.mainfragment.MainFragmentAdapter

class NotWatchedFragmentAdapter :
    RecyclerView.Adapter<NotWatchedFragmentAdapter.MainViewHolder>() {

    private var animeData: List<Anime> = listOf()
    private var onItemViewClickListener: NotWatchedFragment.OnItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = NotWatchedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: NotWatchedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            binding.apply {
                notWatchedFragmentRecyclerItemTextView.text = anime.title
                notWatchedFragmentRecyclerItemImageView.setImageResource(anime.image)
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                wantedBtn.setOnClickListener {
                    notWatchedToWanted(anime)
                    wantedBtn.visibility = View.GONE
                    unwantedBtn.visibility = View.GONE
                }
                unwantedBtn.setOnClickListener {
                    notWatchedToUnwanted(anime)
                    wantedBtn.visibility = View.GONE
                    unwantedBtn.visibility = View.GONE
                }
            }
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: NotWatchedFragment.OnItemViewClickListener){
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