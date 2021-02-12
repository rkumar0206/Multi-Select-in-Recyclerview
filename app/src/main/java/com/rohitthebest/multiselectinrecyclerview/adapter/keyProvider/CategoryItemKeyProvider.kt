package com.rohitthebest.multiselectinrecyclerview.adapter.keyProvider

import androidx.recyclerview.selection.ItemKeyProvider
import com.rohitthebest.multiselectinrecyclerview.adapter.CategoryAdapter

class CategoryItemKeyProvider(private val adapter: CategoryAdapter) : ItemKeyProvider<String>(
    SCOPE_CACHED
) {

    override fun getKey(position: Int): String? = adapter.currentList[position].keyValue

    override fun getPosition(key: String): Int =
        adapter.currentList.indexOfFirst { it.keyValue == key }
}