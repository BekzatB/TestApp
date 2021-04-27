package kz.butik.domain.interactor

import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.SaveArticlesUseCase

class SaveArticlesIntercator(private val repo: NewsRepo): SaveArticlesUseCase {
    override suspend fun execute(articles: List<Article>) {
        return repo.saveArticles(articles)
    }
}