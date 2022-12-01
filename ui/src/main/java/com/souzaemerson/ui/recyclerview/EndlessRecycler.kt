package com.souzaemerson.ui.recyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessRecycler(private val loadMore: () -> Unit) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            recyclerView.layoutManager?.let { layoutManager ->
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = (layoutManager as GridLayoutManager).itemCount
                val pastVisibleItems =
                    (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    loadMore()
                }
            }
        }
    }
}