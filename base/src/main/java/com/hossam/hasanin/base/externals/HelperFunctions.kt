package com.hossam.hasanin.base.externals

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.onEndReached(block: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleItems = recyclerView.layoutManager!!.childCount
                val totalCount = recyclerView.layoutManager!!.itemCount
                val pastVisibleItems =
                    (recyclerView.layoutManager!! as LinearLayoutManager).findFirstVisibleItemPosition()
                if (visibleItems + pastVisibleItems == totalCount ) {
                    block()
                }
            }
        }
    })
}
