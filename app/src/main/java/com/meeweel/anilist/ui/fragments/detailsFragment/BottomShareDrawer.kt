package com.meeweel.anilist.ui.fragments.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meeweel.anilist.R
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.databinding.BottomShareDrawerBinding
import com.meeweel.anilist.ui.MainActivity.Companion.MAIN
import com.meeweel.anilist.ui.MainActivity.Companion.NOT_WATCHED
import com.meeweel.anilist.ui.MainActivity.Companion.UNWANTED
import com.meeweel.anilist.ui.MainActivity.Companion.WANTED
import com.meeweel.anilist.ui.MainActivity.Companion.WATCHED

class BottomShareDrawer(private val repository: LocalRepository)  : BottomSheetDialogFragment() {

    private var aniId: Int? = null
    lateinit var bind: BottomShareDrawerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = BottomShareDrawerBinding.inflate(inflater, container, false)
        aniId = requireArguments().getInt("aniId")
        return bind.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bind.shareView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.to_watched -> {
                    repository.updateLocalEntity(aniId!!, WATCHED)
                }
                R.id.to_not_watched -> {
                    repository.updateLocalEntity(aniId!!, NOT_WATCHED)
                }
                R.id.to_wanted -> {
                    repository.updateLocalEntity(aniId!!, WANTED)
                }
                R.id.to_unwanted -> {
                    repository.updateLocalEntity(aniId!!, UNWANTED)
                }
                R.id.to_main -> {
                    repository.updateLocalEntity(aniId!!, MAIN)
                }
            }
            toast("Moved")
            dismiss() // Закрывает диалоговое окно
            true
        }
    }
    private fun toast(text: String) {
        Toast.makeText(requireActivity().applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}