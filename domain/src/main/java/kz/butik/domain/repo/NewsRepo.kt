package kz.butik.domain.repo

import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article

interface NewsRepo {
    suspend fun getTopHeadLinesNewsInUs(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>>
    suspend fun saveArticles(articles: List<Article>)
    suspend fun getArticle(title: String): Article
    suspend fun updateArticle(title: String, isFav: Boolean)
    suspend fun getEverythingNews(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>>
    suspend fun getFavArticles(isFav: Boolean): List<Article>
}