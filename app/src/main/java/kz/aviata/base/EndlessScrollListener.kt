package kz.aviata.base

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class EndlessScrollListener(
    var currentPage: Int = 1,
    private val onLoadMore: ((page: Int) -> Unit)?
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 5
    private var previousTotalItemCount = 0

    private var loading = true

    private val startingPageIndex = 1
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    constructor(
        layoutManager: LinearLayoutManager,
        currentPage: Int,
        onLoadMore: ((page: Int) -> Unit)?
    ) : this(currentPage, onLoadMore) {
        this.mLayoutManager = layoutManager
    }

    constructor(
        layoutManager: GridLayoutManager,
        currentPage: Int = 1,
        onLoadMore: ((page: Int) -> Unit)?
    ) : this(currentPage, onLoadMore) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    constructor(
        layoutManager: StaggeredGridLayoutManager,
        currentPage: Int = 1,
        onLoadMore: ((page: Int) -> Unit)?
    ) : this(currentPage, onLoadMore) {
        this.mLayoutManager = layoutManager
        visibleThreshold *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)

                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore?.invoke(currentPage)
            loading = true
        }
    }

fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }
}