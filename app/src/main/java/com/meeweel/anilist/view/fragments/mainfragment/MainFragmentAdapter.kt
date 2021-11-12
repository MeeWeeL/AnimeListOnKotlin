package com.meeweel.anilist.view.fragments.mainfragment

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meeweel.anilist.databinding.MainRecyclerItemBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.viewmodel.Changing
import com.meeweel.anilist.viewmodel.Changing.saveTo
import com.meeweel.anilist.viewmodel.ImageMaker
import java.io.File
import java.io.FileInputStream

class MainFragmentAdapter :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var animeData: MutableList<Anime> = mutableListOf()
    private var onItemViewClickListener: MainFragment.OnItemViewClickListener? = null
    val imageMaker: ImageMaker = ImageMaker()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = MainRecyclerItemBinding.inflate(
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

    inner class MainViewHolder(private val binding: MainRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime) {
            val b: Bitmap = BitmapFactory.decodeStream(
                FileInputStream(
                    File(
                        ContextWrapper(
                            Changing.getContext()
                        ).getDir("imageDir", Context.MODE_PRIVATE).absolutePath, "${anime.image}.jpeg")
                )
            )
            binding.apply {
                mainFragmentRecyclerItemTextView.text = anime.enTitle
                mainFragmentRecyclerItemImageView.setImageBitmap(Bitmap.createScaledBitmap(b, b.width/20, b.height/20, false))
                root.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(anime)
                }
                watchedBtn.setOnClickListener {
                    saveTo(anime,2)
                    animeData.remove(anime)
                    notifyItemRemoved(layoutPosition)
                }
                notWatchedBtn.setOnClickListener {
                    saveTo(anime,3)
                    animeData.remove(anime)
                    notifyItemRemoved(layoutPosition)
                }
            }
        }
    }

    fun setOnItemViewClickListener(onItemViewClickListener: MainFragment.OnItemViewClickListener){
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