package kz.butik.domain.interactor

import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.GetTopHeadlinesNewsUseCase

class GetTopHeadlinesInteractor(
    private val repo: NewsRepo
) : GetTopHeadlinesNewsUseCase {
    override suspend fun execute(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>> {
        return repo.getTopHeadLinesNewsInUs(page, newsPageSize)
    }
}