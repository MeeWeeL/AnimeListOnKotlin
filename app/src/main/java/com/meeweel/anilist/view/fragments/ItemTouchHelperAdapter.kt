package com.meeweel.anilist.view.fragments

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int, i: Int)
}