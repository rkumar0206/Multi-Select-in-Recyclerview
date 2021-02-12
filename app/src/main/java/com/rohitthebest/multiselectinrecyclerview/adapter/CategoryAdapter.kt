package com.rohitthebest.multiselectinrecyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rohitthebest.multiselectinrecyclerview.data.Category
import com.rohitthebest.multiselectinrecyclerview.databinding.ItemCategoryBinding

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffUtilCallback()) {

    private var mListener: OnClickListener? = null
    var tracker: SelectionTracker<String>? = null

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

            binding.root.setOnClickListener {

                mListener!!.onItemClick(getItem(absoluteAdapterPosition))
            }
        }

        fun bind(category: Category?, isSelected: Boolean) = with(binding) {

            category?.let {

                categoryNameTV.text = it.categoryName
                categoryTimeTV.text = it.timestamp.toString()
                textView3.text = it.keyValue
                categorySelectedIcon.isVisible = isSelected
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {

                override fun getPosition(): Int = absoluteAdapterPosition

                override fun getSelectionKey(): String? = getItem(absoluteAdapterPosition).keyValue
            }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    class DiffUtilCallback : DiffUtil.ItemCallback<Category>() {

        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.keyValue == newItem.keyValue

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        tracker?.let {

            holder.bind(getItem(position), it.isSelected(getItem(position).keyValue))
        }
    }

    interface OnClickListener {

        fun onItemClick(model: Category)
    }

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }
}
