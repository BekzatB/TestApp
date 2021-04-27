package kz.butik.data.local_models

import kz.butik.domain.entities.Article
import kz.butik.domain.entities.Source

class LocalMapper {
    fun mapToArticleLocalModel(articles: List<Article>): List<ArticleLocalModel> {
        val articleLocalModels = mutableListOf<ArticleLocalModel>()
        articles.map { article ->
            articleLocalModels.add(
                ArticleLocalModel(
                    source = mapToSourceLocalModel(article.source),
                    author = article.author,
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    publishedAt = article.publishedAt,
                    content = article.content,
                    isFavorite = when (article.isFavorite) {
                        true -> 1
                        false -> 0
                    }
                )
            )
        }
        return articleLocalModels
    }

    private fun mapToSourceLocalModel(source: Source): SourceLocalModel {
        return SourceLocalModel(
            id = source.id,
            name = source.name
        )
    }

    fun mapToArticle(articleLocalModel: ArticleLocalModel): Article {
        return Article(
            source = mapToSource(articleLocalModel.source),
            author = articleLocalModel.author,
            title = articleLocalModel.title,
            description = articleLocalModel.description,
            url = articleLocalModel.url,
            urlToImage = articleLocalModel.urlToImage,
            publishedAt = articleLocalModel.publishedAt,
            content = articleLocalModel.content,
            isFavorite = articleLocalModel.isFavorite > 0
        )
    }

    private fun mapToSource(sourceLocalModel: SourceLocalModel): Source {
        return Source(
            id = sourceLocalModel.id,
            name = sourceLocalModel.name
        )
    }

    fun mapToArticleList(articlesListLocalModel: List<ArticleLocalModel>): List<Article> {
        val article = mutableListOf<Article>()

        articlesListLocalModel.forEach { articleLocalModel ->
            article.add(
                Article(
                    source = mapToSource(articleLocalModel.source),
                    author = articleLocalModel.author,
                    title = articleLocalModel.title,
                    description = articleLocalModel.description,
                    url = articleLocalModel.url,
                    urlToImage = articleLocalModel.urlToImage,
                    publishedAt = articleLocalModel.publishedAt,
                    content = articleLocalModel.content,
                    isFavorite = articleLocalModel.isFavorite > 0
                )
            )
        }
        return article
    }
}