package kz.butik.data.data_source.remote

import kz.butik.data.NewsApi
import kz.butik.data.api.toRemoteUseCaseResult
import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article

class RemoteDataSource(private val api: NewsApi) : RemoteDataSourceI {
    override suspend fun getTopHeadlinesNews(
        page: Int,
        newsPageSize: Int
    ): RemoteUseCaseResult<List<Article>> {
        return api.getTopHeadlinesNewsInUS(page = page, pageSize = newsPageSize)
            .toRemoteUseCaseResult()
    }

    override suspend fun getEverythingNews(
        page: Int,
        newsPageSize: Int
    ): RemoteUseCaseResult<List<Article>> {
        return api.getAllAppleNews(page = page, pageSize = newsPageSize)
            .toRemoteUseCaseResult()
    }
}