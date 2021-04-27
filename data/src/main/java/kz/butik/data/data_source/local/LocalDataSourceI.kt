package kz.butik.data.data_source.local

import kz.butik.data.local_models.ArticleLocalModel
import kz.butik.domain.entities.Article

interface LocalDataSourceI {
    suspend fun insertArticles(articles: List<ArticleLocalModel>)
    suspend fun getArticles(): List<ArticleLocalModel>
    suspend fun getArticle(title: String): ArticleLocalModel
    suspend fun updateArticle(title: String, isFav: Int)
}