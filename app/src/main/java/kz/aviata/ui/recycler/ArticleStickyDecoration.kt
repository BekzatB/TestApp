package kz.aviata.ui.recycler

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import kz.aviata.R
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.DateDvo
import kz.aviata.utils.DimensionUtils

class ArticleStickyDecoration(
    context: Context,
    articles: List<ArticleAdapterItem>
) : RecyclerView.ItemDecoration() {

    private val textPaint: TextPaint
    private val headerRectHeight: Float
    private var backgroundColor: Int = 0
    private var startMargin: Int = 0
    private val screenWidth: Float = DimensionUtils.displayWidthPx().toFloat()

    private val filteredArticles = mutableListOf<ArticleAdapterItem>().apply {
        var lastDate = ""
        articles.forEach {
            if (it is DateDvo) {
                if(it.date != lastDate) {
                    this.add(it)
                }
                lastDate = it.date
            } else {
                this.add(it)
            }
        }
    }

    private val articleDateMap: Map<Int, ArticleAdapterItem> = filteredArticles
        .mapIndexed { index, article -> index to article }
        .filter { it.second is DateDvo }
        .toMap()

    init {
        backgroundColor = ContextCompat.getColor(context, R.color.blue)

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.LEFT
            textSize = context.resources.getDimensionPixelOffset(R.dimen.word_text_size).toFloat()
            typeface = Typeface.DEFAULT_BOLD
        }

        headerRectHeight = context.resources.getDimensionPixelOffset(R.dimen._50dp).toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childPosition = parent.getChildAdapterPosition(view)
        if (childPosition < 0) return

        if (articleDateMap.containsKey(childPosition)) {
            outRect.top = headerRectHeight.toInt()
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (articleDateMap.isEmpty() || parent.isEmpty()) return

        var lastFoundPosition: Int = NO_POSITION
        var prevHeaderTop: Float = Float.MAX_VALUE

        for (headerPosition in (parent.size - 1) downTo 0) {
            val view = parent.getChildAt(headerPosition)
            if (childOutsideParent(view, parent)) continue

            val childPosition = parent.getChildAdapterPosition(view)

            articleDateMap[childPosition]?.let {
                val bottomOfHeader = view.top.toFloat()
                    .coerceAtLeast(headerRectHeight)
                    .coerceAtMost(prevHeaderTop)
                // left - top - right - bottom
                drawHeaderRect(canvas, it, bottomOfHeader, parent.context)
                lastFoundPosition = childPosition
                prevHeaderTop = bottomOfHeader
            }
        }


        lastFoundPosition = if (lastFoundPosition != NO_POSITION) lastFoundPosition else
            parent.getChildAdapterPosition(parent[0]) + 1

        for (initialsPosition in articleDateMap.keys.reversed()) {
            if (initialsPosition < lastFoundPosition) {
                articleDateMap[initialsPosition]?.let {
                    val bottom = headerRectHeight
                        .coerceAtMost(prevHeaderTop - headerRectHeight)
                    drawHeaderRect(canvas, it, bottom, parent.context)
                }
                break
            }
        }
    }

    private fun drawHeaderRect(
        canvas: Canvas,
        date: Any,
        bottom: Float,
        context: Context
    ) {
        textPaint.color = backgroundColor
        canvas.drawRect(0f, (bottom - headerRectHeight), screenWidth, bottom, textPaint)
        textPaint.color = Color.WHITE
        val dateText = (date as DateDvo).date
        canvas.drawText(
            dateText,
            startMargin.toFloat(),
            bottom - headerRectHeight * .25f,
            textPaint
        )
    }

    private fun childOutsideParent(childView: View, parent: RecyclerView): Boolean {
        return childView.bottom < 0
                || (childView.top + childView.translationY.toInt() > parent.height)

    }


    companion object {
        private const val NO_POSITION = -1
    }

}

fun RecyclerView.clearDecorations() {
    if (itemDecorationCount > 0) {
        for (i in itemDecorationCount - 1 downTo 0) {
            removeItemDecorationAt(i)
        }
    }
}

fun  RecyclerView.makeArticleHeaderSticky(items: List<ArticleAdapterItem>, context: Context) {
    this.run {
        clearDecorations()
        val decoration = ArticleStickyDecoration(context, items)
        addItemDecoration(decoration)
    }
}