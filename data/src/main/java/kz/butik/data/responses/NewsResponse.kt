package kz.butik.data.responses

import kz.butik.data.api.JsonResponse
import kz.butik.domain.entities.Article
import kz.butik.domain.entities.Source


data class NewsResponse(
    val status: String,
    val totalResult: Int,
    val articles: List<ArticleResponse>
) : JsonResponse<List<Article>> {
    override fun toModel(): List<Article> {
        return articles.map {
            Article(
                it.source.toModel(),
                it.author ?: "",
                it.title ?: "",
                it.description ?: "",
                it.url ?: "",
                it.urlToImage ?: "",
                it.publishedAt ?: "",
                it.content ?: "",
                isFavorite = false
            )
        }
    }
}

data class ArticleResponse(
    val source: SourceResponse,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceResponse(
    val id: String?,
    val name: String?
) : JsonResponse<Source> {
    override fun toModel(): Source {
        return Source(
            id = id ?: "",
            name = name ?: ""
        )
    }
}