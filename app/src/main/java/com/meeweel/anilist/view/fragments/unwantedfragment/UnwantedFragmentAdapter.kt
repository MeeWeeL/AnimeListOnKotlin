package com.meeweel.anilist.view.fragments.unwantedfragment

import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.UnwantedRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.ImageMaker
import java.io.File
import java.io.FileInputStream

class UnwantedFragmentAdapter :
    RecyclerView.Adapter<UnwantedFragmentAdapter.MainViewHolder>() {
    val imageMaker: ImageMaker = ImageMaker()
    private var animeData: MutableList<Anime> = mutableListOf()
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
                unwantedFragmentRecyclerItemTextView.text = anime.enTitle
                unwantedFragmentRecyclerItemImageView.setImageBitmap(BitmapFactory.decodeStream(
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
        animeData = data.toMutableList()
        notifyDataSetChanged()
    }

}