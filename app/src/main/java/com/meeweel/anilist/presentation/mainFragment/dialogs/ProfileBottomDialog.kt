package com.meeweel.anilist.presentation.mainFragment.dialogs

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.data.room.isRussian
import com.meeweel.anilist.databinding.ProfileLayoutBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime

class ProfileBottomDialog(
    context: Context,
    val clipboardCopy: (String) -> (Unit),
) : BottomSheetDialog(context) {
    private val binding = ProfileLayoutBinding.inflate(layoutInflater)

    init {
        with(binding) {
            setContentView(root)
            val theme =
                context.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
            nightModeCheckbox.isChecked =
                theme == android.content.res.Configuration.UI_MODE_NIGHT_YES
            nightModeCheckbox.setOnCheckedChangeListener { _, _ ->
                AppCompatDelegate.setDefaultNightMode(
                    if (nightModeCheckbox.isChecked) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
            }

        }
    }

    fun pasteData(animeData: Map<ListState, MutableList<ShortAnime>>) {
        with(binding) {
            mainCounter.text = animeData[ListState.MAIN]?.size.toString()
            watchedCounter.text = animeData[ListState.WATCHED]?.size.toString()
            notWatchedCounter.text = animeData[ListState.NOT_WATCHED]?.size.toString()
            wantedCounter.text = animeData[ListState.WANTED]?.size.toString()
            unwantedCounter.text = animeData[ListState.UNWANTED]?.size.toString()
            buttonEnabled()
            mainCopy.setOnClickListener { animeData.copy(ListState.MAIN) }
            watchedCopy.setOnClickListener { animeData.copy(ListState.WATCHED) }
            notWatchedCopy.setOnClickListener { animeData.copy(ListState.NOT_WATCHED) }
            wantedCopy.setOnClickListener { animeData.copy(ListState.WANTED) }
            unwantedCopy.setOnClickListener { animeData.copy(ListState.UNWANTED) }
        }
    }

    private fun buttonEnabled() {
        with(binding) {
            mainCopy.isEnabled = true
            watchedCopy.isEnabled = true
            notWatchedCopy.isEnabled = true
            wantedCopy.isEnabled = true
            unwantedCopy.isEnabled = true
        }
    }

    private fun Map<ListState, MutableList<ShortAnime>>.copy(state: ListState) {
        val copyList = StringBuilder()
        this[state]?.sortBy { if (isRussian) it.ruTitle else it.enTitle }
        this[state]?.forEachIndexed { index, item ->
            val title = if (isRussian) item.ruTitle else item.enTitle
            copyList.append("$index) $title (${item.data})\n")
        }
        clipboardCopy(copyList.toString())
    }
}


