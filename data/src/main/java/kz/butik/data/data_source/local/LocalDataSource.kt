package kz.butik.data.data_source.local

import kz.butik.data.local_models.ArticleLocalModel
import kz.butik.domain.entities.Article

class LocalDataSource(private val newsDao: NewsDao) : LocalDataSourceI {

    override suspend fun insertArticles(articles: List<ArticleLocalModel>) {
        newsDao.insertArticles(articles)
    }

    override suspend fun getArticles(): List<ArticleLocalModel> {
        return newsDao.getArticles()
    }

    override suspend fun getArticle(title: String): ArticleLocalModel {
        return newsDao.getArticle(title)
    }

    override suspend fun updateArticle(title: String, isFav: Int) {
        newsDao.updateArticle(title, isFav)
    }
}