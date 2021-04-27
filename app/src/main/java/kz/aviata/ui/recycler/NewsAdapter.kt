package kz.aviata.ui.recycler

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import kz.aviata.base.BaseAdapter
import kz.aviata.ui.top_headlines.dvo.ArticleAdapterItem
import kz.aviata.ui.top_headlines.dvo.ArticleDvo

class NewsAdapter(
    private val onClick: (ArticleDvo, View) -> Unit
) : BaseAdapter<ArticleAdapterItem, ViewBinding>() {

    override fun setItems(items: List<ArticleAdapterItem>) {
        val diffCallback = NewsDiffUtil(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<ArticleAdapterItem, ViewBinding> {

        return when (viewType) {
            ArticleAdapterItem.HEADER -> DateViewHolder(parent)
            ArticleAdapterItem.CONTENT -> NewsViewHolder(parent, onClick)
            else -> throw Throwable("invalid view")
        } as ViewHolder<ArticleAdapterItem, ViewBinding>
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].typeArticleAdapterItem
    }

}