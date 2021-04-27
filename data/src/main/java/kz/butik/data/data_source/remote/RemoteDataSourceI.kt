package kz.butik.data.data_source.remote

import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article

interface RemoteDataSourceI {
    suspend fun getTopHeadlinesNews(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>>
    suspend fun getEverythingNews(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>>
}