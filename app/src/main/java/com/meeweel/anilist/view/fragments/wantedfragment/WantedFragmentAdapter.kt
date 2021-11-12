package com.meeweel.anilist.view.fragments.wantedfragment

import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.WantedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.ImageMaker
import java.io.File
import java.io.FileInputStream

class WantedFragmentAdapter :
    RecyclerView.Adapter<WantedFragmentAdapter.MainViewHolder>() {
    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<Anime> = mutableListOf()
    private var onItemViewClickListener: WantedFragment.OnItemViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = WantedRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: WantedRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            binding.apply {
                wantedFragmentRecyclerItemTextView.text = anime.enTitle
                wantedFragmentRecyclerItemImageView.setImageBitmap(BitmapFactory.decodeStream(
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
                watchedBtn.setOnClickListener {
                    Changing.saveTo(anime,2)
                    animeData.remove(anime)
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: WantedFragment.OnItemViewClickListener){
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