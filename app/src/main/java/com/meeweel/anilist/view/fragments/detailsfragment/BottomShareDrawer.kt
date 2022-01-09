package com.meeweel.anilist.view.fragments.detailsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.BottomShareDrawerBinding
import com.meeweel.anilist.viewmodel.Changing.MAIN
import com.meeweel.anilist.viewmodel.Changing.NOT_WATCHED
import com.meeweel.anilist.viewmodel.Changing.UNWANTED
import com.meeweel.anilist.viewmodel.Changing.WANTED
import com.meeweel.anilist.viewmodel.Changing.WATCHED
import com.meeweel.anilist.viewmodel.Changing.saveTo

class BottomShareDrawer  : BottomSheetDialogFragment() {

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
                    saveTo(aniId!!, WATCHED)
                }
                R.id.to_not_watched -> {
                    saveTo(aniId!!, NOT_WATCHED)
                }
                R.id.to_wanted -> {
                    saveTo(aniId!!, WANTED)
                }
                R.id.to_unwanted -> {
                    saveTo(aniId!!, UNWANTED)
                }
                R.id.to_main -> {
                    saveTo(aniId!!, MAIN)
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