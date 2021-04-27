package kz.butik.data.local_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.butik.data.api.LocalModel
import kz.butik.domain.entities.Article
import kz.butik.domain.entities.Source

@Entity(tableName = "article_table")
data class ArticleLocalModel(
    val source: SourceLocalModel,
    val author: String,
    @PrimaryKey
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
    val isFavorite: Int
) : LocalModel<Article> {
    override fun toModel(): Article {
        return Article(
            source = source.toModel(),
            author = author,
            title = title,
            description = description,
            url, urlToImage, publishedAt, content,
            isFavorite = isFavorite > 0
        )
    }
}

data class SourceLocalModel(
    val id: String,
    val name: String
) : LocalModel<Source> {
    override fun toModel(): Source {
        return Source(
            id, name
        )
    }
}