package kz.butik.domain.usecase

import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article

interface GetTopHeadlinesNewsUseCase {
    suspend fun execute(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>>
}