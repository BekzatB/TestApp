package kz.aviata.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.ArticleDvo
import kz.aviata.ui.top_headlines.dvo.DateDvo

class NewsDiffUtil(
    private val oldList: List<ArticleAdapterItem>,
    private val newList: List<ArticleAdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        if (!areTheSameType(oldItemPosition, newItemPosition)) {
            return false
        }

        return when (getType(oldItemPosition)) {
            1 ->  (oldList[oldItemPosition] as DateDvo).date == (newList[newItemPosition] as DateDvo).date
            2 ->  (oldList[oldItemPosition] as ArticleDvo).title == (newList[newItemPosition] as ArticleDvo).title
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        if (!areTheSameType(oldItemPosition, newItemPosition)) {
            return false
        }

        return when(getType(oldItemPosition)) {
            1 -> return (oldList[oldItemPosition] as DateDvo).date == (newList[newItemPosition] as DateDvo).date
            2 -> {
                val oldAuthor = (oldList[oldItemPosition] as ArticleDvo).author
                val newAuthor = (newList[newItemPosition] as ArticleDvo).author
                val oldUrl = (oldList[oldItemPosition] as ArticleDvo).url
                val newUrl = (newList[newItemPosition] as ArticleDvo).url
                return oldAuthor == newAuthor && oldUrl == newUrl
            }
            else -> false
        }
    }

    private fun areTheSameType(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if ((oldList[oldItemPosition] is DateDvo && newList[newItemPosition] !is DateDvo)
            || (oldList[oldItemPosition] !is DateDvo && newList[newItemPosition] is DateDvo)
        ) {
            return false
        }
        return true
    }

    private fun getType(oldItemPosition: Int): Int {
        return when (oldList[oldItemPosition]) {
            is ArticleDvo -> 2
            else -> 1
        }
    }
}