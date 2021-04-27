package kz.butik.domain.interactor

import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.GetArticleUseCase

class GetArticleInteractor(private val repo: NewsRepo): GetArticleUseCase {
    override suspend fun execute(title: String): Article {
        return repo.getArticle(title)
    }
}