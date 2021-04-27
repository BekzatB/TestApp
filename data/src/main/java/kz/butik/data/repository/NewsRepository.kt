package kz.butik.data.repository

import kz.butik.data.data_source.local.LocalDataSourceI
import kz.butik.data.data_source.remote.RemoteDataSourceI
import kz.butik.data.local_models.LocalMapper
import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo

class NewsRepository(
    private val remoteDataSource: RemoteDataSourceI,
    private val localDataSource: LocalDataSourceI,
    private val mapper: LocalMapper = LocalMapper()
) : NewsRepo {
    override suspend fun getTopHeadLinesNewsInUs(
        page: Int,
        newsPageSize: Int
    ): RemoteUseCaseResult<List<Article>> {
        return remoteDataSource.getTopHeadlinesNews(page, newsPageSize)
    }

    override suspend fun saveArticles(articles: List<Article>) {
        localDataSource.insertArticles(mapper.mapToArticleLocalModel(articles))
    }

    override suspend fun getArticle(title: String): Article {
        return mapper.mapToArticle(localDataSource.getArticle(title))
    }

    override suspend fun updateArticle(title: String, isFav: Boolean) {
        val isFavorite = if (isFav) 1 else 0
        return localDataSource.updateArticle(title, isFavorite)
    }

    override suspend fun getEverythingNews(
        page: Int,
        newsPageSize: Int
    ): RemoteUseCaseResult<List<Article>> {
        return remoteDataSource.getEverythingNews(page, newsPageSize)
    }

    override suspend fun getFavArticles(isFav: Boolean): List<Article> {
        val isFavorite = if (isFav) 1 else 0
        return mapper.mapToArticleList(
            localDataSource.getArticles().filter { it.isFavorite == isFavorite })
    }
}