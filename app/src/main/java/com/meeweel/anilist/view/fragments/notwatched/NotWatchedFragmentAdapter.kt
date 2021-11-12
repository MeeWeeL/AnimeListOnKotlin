package com.meeweel.anilist.view.fragments.notwatched

import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.NotWatchedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.ImageMaker
import java.io.File
import java.io.FileInputStream

class NotWatchedFragmentAdapter :
    RecyclerView.Adapter<NotWatchedFragmentAdapter.MainViewHolder>() {
    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<Anime> = mutableListOf()
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
                notWatchedFragmentRecyclerItemTextView.text = anime.enTitle
                notWatchedFragmentRecyclerItemImageView.setImageBitmap(BitmapFactory.decodeStream(
                    FileInputStream(
                        File(
                            ContextWrapper(
                                Changing.getContext()
                            ).getDir("imageDir", Context.MODE_PRIVATE).absolutePath, "${anime.image}.jpeg")
                    )
                ))
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                wantedBtn.setOnClickListener {
                    Changing.saveTo(anime,4)
                    animeData.remove(anime)
                    notifyDataSetChanged()
                }
                unwantedBtn.setOnClickListener {
                    Changing.saveTo(anime,5)
                    animeData.remove(anime)
                    notifyDataSetChanged()
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
        animeData = data.toMutableList()
        notifyDataSetChanged()
    }

}