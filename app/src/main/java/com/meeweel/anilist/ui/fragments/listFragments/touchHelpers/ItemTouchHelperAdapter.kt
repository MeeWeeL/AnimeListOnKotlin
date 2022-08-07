package com.meeweel.anilist.ui.fragments.listFragments.touchHelpers

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int, i: Int)
}