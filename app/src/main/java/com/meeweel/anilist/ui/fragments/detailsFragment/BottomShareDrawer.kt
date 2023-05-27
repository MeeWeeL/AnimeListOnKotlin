package com.meeweel.anilist.ui.fragments.detailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meeweel.anilist.R
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.databinding.BottomShareDrawerBinding
import com.meeweel.anilist.domain.enums.ListState

class BottomShareDrawer(private val repository: Repository) : BottomSheetDialogFragment() {

    private var _animeID: Int? = null
    private val animeID get() = _animeID!!
    lateinit var bind: BottomShareDrawerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = BottomShareDrawerBinding.inflate(inflater, container, false)
        _animeID = requireArguments().getInt("aniId")
        return bind.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bind.shareView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.to_watched -> repository.updateEntityLocal(animeID, ListState.WATCHED)
                R.id.to_not_watched -> repository.updateEntityLocal(animeID, ListState.NOT_WATCHED)
                R.id.to_wanted -> repository.updateEntityLocal(animeID, ListState.WANTED)
                R.id.to_unwanted -> repository.updateEntityLocal(animeID, ListState.UNWANTED)
                R.id.to_main -> repository.updateEntityLocal(animeID, ListState.MAIN)
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