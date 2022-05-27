package com.meeweel.anilist.ui.fragments

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int, i: Int)
}