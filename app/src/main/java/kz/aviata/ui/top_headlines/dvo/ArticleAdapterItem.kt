package kz.aviata.ui.top_headlines.dvo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface ArticleAdapterItem {
    val typeArticleAdapterItem: Int

    companion object {
        const val HEADER = 1
        const val CONTENT = 2
    }
}
@Parcelize
data class ArticleDvo(
    val source: SourceDvo,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isFav: Boolean
) : ArticleAdapterItem, Parcelable {
    override val typeArticleAdapterItem: Int
        get() = ArticleAdapterItem.CONTENT
}
@Parcelize
data class SourceDvo(
    val id: String,
    val name: String
): Parcelable

data class DateDvo(
    val date: String
) : ArticleAdapterItem {
    override val typeArticleAdapterItem: Int
        get() = ArticleAdapterItem.HEADER
}

