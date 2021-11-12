package com.meeweel.anilist.model.room

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import com.meeweel.anilist.R
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.entityes.Entity
import com.meeweel.anilist.viewmodel.ImageMaker

val isRussian: Boolean = R.bool.isRussian.toString().toBoolean()
@SuppressLint("StaticFieldLeak")
val imageMaker = ImageMaker()
fun convertEntityToAnilist(entityList: List<Entity>): List<Anime> {
    return entityList.map {
        Anime(it.id, it.ruTitle, it.enTitle, it.originalTitle, if (isRussian) { it.ruDescription } else { it.enDescription },
            it.image, it.data, if (isRussian) { it.ruGenre } else { it.enGenre }, it.author, it.ageRating, it.rating, it.seriesQuantity, it.ratingCheck, it.list)
    }
}