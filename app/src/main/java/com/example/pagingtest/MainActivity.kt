package com.example.pagingtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingtest.data.Cheese
import com.example.pagingtest.di.Injection
import com.example.pagingtest.vm.CheeseViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var cheeseViewModel: CheeseViewModel
    lateinit var adapter: CheeseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = CheeseAdapter()
        cheeseList.adapter = adapter
        initViewModel()
        initObserver()
        initSwipeToDelete()
    }

    private fun initViewModel() {
        cheeseViewModel = Injection.provideCheeseViewModel(this)
        cheeseViewModel.getCheese()
    }

    private fun initObserver() {
        cheeseViewModel.pagedListCheese.observe(this, Observer { t: PagedList<Cheese> ->
            adapter.submitList(t)
        })
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as CheeseViewHolder).cheese?.let {
                    cheeseViewModel.remove(it)
                }
            }
        }).attachToRecyclerView(cheeseList)
    }
}
