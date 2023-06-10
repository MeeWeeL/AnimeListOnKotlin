package com.meeweel.anilist.utils

/*
import android.content.ClipData
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.ProfileLayoutBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.presentation.NewMainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object DialogsUtils {

    fun showProfileDialog(activity: FragmentActivity, allAnime: Single<List<ShortAnime>>) {
        val isRu = activity.resources.getBoolean(R.bool.isRussian)
        val parentActivity = activity as NewMainActivity
        val dialog = BottomSheetDialog(activity)
        val profileBinding = ProfileLayoutBinding.inflate(activity.layoutInflater)
        dialog.setContentView(profileBinding.root)

        fun String.copyText() {
            val clipboard =
                activity.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("TAG", this)
            clipboard.setPrimaryClip(clip)
            activity.toast(activity.resources.getString(R.string.copied))
        }

        fun List<ShortAnime>.copy(listInt: Int) {
            val copyList = StringBuilder()
            var count = 0
            this.sortedBy { item -> if (isRu) item.ruTitle else item.enTitle }.forEach {
                if (it.list == listInt)
                    copyList.append("${++count}. ${if (isRu) it.ruTitle else it.enTitle} (${it.data})\n")
            }
            copyList.toString().copyText()
        }

        fun profileData(list: List<ShortAnime>) {
            var main = 0
            var watched = 0
            var wanted = 0
            var notWatched = 0
            var unwanted = 0
            list.forEach { item ->
                when (item.list) {
                    NewMainActivity.MAIN -> main++
                    NewMainActivity.WATCHED -> watched++
                    NewMainActivity.NOT_WATCHED -> notWatched++
                    NewMainActivity.WANTED -> wanted++
                    NewMainActivity.UNWANTED -> unwanted++
                }
            }
            with(profileBinding) {
                mainCounter.text = main.toString()
                watchedCounter.text = watched.toString()
                notWatchedCounter.text = notWatched.toString()
                wantedCounter.text = wanted.toString()
                unwantedCounter.text = unwanted.toString()
                mainCopy.setOnClickListener { list.copy(NewMainActivity.MAIN) }
                watchedCopy.setOnClickListener { list.copy(NewMainActivity.WATCHED) }
                notWatchedCopy.setOnClickListener { list.copy(NewMainActivity.NOT_WATCHED) }
                wantedCopy.setOnClickListener { list.copy(NewMainActivity.WANTED) }
                unwantedCopy.setOnClickListener { list.copy(NewMainActivity.UNWANTED) }
            }
        }

        with(profileBinding) {
            nightModeCheckbox.isChecked = parentActivity.getCurrentTheme()
            nightModeCheckbox.setOnCheckedChangeListener { _, _ ->
                if (nightModeCheckbox.isChecked) parentActivity.setNightMode(true)
                else parentActivity.setNightMode(false)
            }
        }
        allAnime
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                profileData(it)
            }, { })
        dialog.show()
    }
}
*/
