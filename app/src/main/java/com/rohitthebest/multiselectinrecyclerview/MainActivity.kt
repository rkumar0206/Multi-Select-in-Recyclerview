package com.rohitthebest.multiselectinrecyclerview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitthebest.multiselectinrecyclerview.adapter.CategoryAdapter
import com.rohitthebest.multiselectinrecyclerview.adapter.itemsDetailsLookup.CategoryItemDetailsLookup
import com.rohitthebest.multiselectinrecyclerview.adapter.keyProvider.CategoryItemKeyProvider
import com.rohitthebest.multiselectinrecyclerview.data.Category
import com.rohitthebest.multiselectinrecyclerview.databinding.ActivityMainBinding
import com.rohitthebest.multiselectinrecyclerview.util.Functions.Companion.generateKey
import kotlinx.coroutines.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CategoryAdapter.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mAdapter: CategoryAdapter

    private lateinit var categoryList: ArrayList<Category>

    private var tracker: SelectionTracker<String>? = null

    private var selectedItems: Selection<String>? = null

    var mActionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryList = ArrayList()
        mAdapter = CategoryAdapter()

        populateList()

        GlobalScope.launch {

            delay(200)

            withContext(Dispatchers.Main) {

                setUpRecyclerView()

                setUpTracker()

                addObserverToTracker()
            }
        }

    }

    private fun setUpRecyclerView() {

        mAdapter.submitList(categoryList)

        binding.categoryRV.apply {

            adapter = mAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mAdapter.setOnClickListener(this)
    }

    override fun onItemClick(model: Category) {

        Toast.makeText(this, "${model.categoryName}", Toast.LENGTH_SHORT).show()
    }


    private fun setUpTracker() {

        tracker = SelectionTracker.Builder(
            "category_selection_id",
            binding.categoryRV,
            CategoryItemKeyProvider(mAdapter),
            CategoryItemDetailsLookup(binding.categoryRV),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        mAdapter.tracker = tracker

    }

    private fun addObserverToTracker() {

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {

                @SuppressLint("SetTextI18n")
                override fun onSelectionChanged() {
                    super.onSelectionChanged()

                    val items = tracker?.selection?.size()

                    selectedItems = tracker?.selection

                    if (items != null) {

                        if (items > 0) {

                            if (mActionMode != null) {

                                return
                            }

                            mActionMode =
                                (this@MainActivity as (AppCompatActivity)).startSupportActionMode(
                                    mActionModeCallback
                                )
                        } else {
                            if (mActionMode != null) {

                                mActionMode?.finish()
                            }

                            mActionMode = null
                        }
                    }

                    //mActionMode?.title = "Selected $items"
                }
            }
        )

    }

    private val mActionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {

            mode?.menuInflater?.inflate(R.menu.action_menu, menu)
            mode?.title = "Choose option"
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {

            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

            return when (item?.itemId) {

                R.id.menu_delete -> {

                    deleteSelectedItem()

                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {

            mActionMode = null
            tracker?.clearSelection()
        }
    }

    private fun deleteSelectedItem() {

        selectedItems?.let {

            if (it.size() != 0) {

                it.forEach { s ->

                    Log.d(TAG, "onClick: $s")

                    val category = categoryList.find { c ->
                        c.keyValue == s
                    }

                    //val index = categoryList.indexOf(category)

                    val isRemoved = categoryList.remove(category)

                    if (isRemoved) {

                        Log.d(TAG, "onClick: $s deleted")
                    } else {
                        Log.d(TAG, "onClick: $s Item not removed")
                    }
                }

                mAdapter.notifyDataSetChanged()

            }
        }

    }

    private fun populateList() {

        for (i in 0..50) {

            val category = Category(
                System.currentTimeMillis() + i.toLong(),
                "category ${i + 1}",
                generateKey()
            )

            categoryList.add(category)
        }
    }

}