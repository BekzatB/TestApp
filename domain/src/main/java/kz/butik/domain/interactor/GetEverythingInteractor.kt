package kz.butik.domain.interactor

import kz.butik.domain.RemoteUseCaseResult
import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.GetEverythingNewsUseCase

class GetEverythingInteractor(private val repo: NewsRepo): GetEverythingNewsUseCase {
    override suspend fun execute(page: Int, newsPageSize: Int): RemoteUseCaseResult<List<Article>> {
        return repo.getEverythingNews(page, newsPageSize)
    }
}