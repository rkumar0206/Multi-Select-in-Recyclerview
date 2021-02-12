package com.rohitthebest.multiselectinrecyclerview.adapter.itemsDetailsLookup

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.rohitthebest.multiselectinrecyclerview.adapter.CategoryAdapter

class CategoryItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<String>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<String>? {

        val view = recyclerView.findChildViewUnder(e.x, e.y)

        if (view != null) {

            return (recyclerView.getChildViewHolder(view) as CategoryAdapter.CategoryViewHolder).getItemDetails()
        }

        return null
    }
}