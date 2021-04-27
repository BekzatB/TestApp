package kz.butik.domain.interactor

import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.UpdateArticleUseCase

class UpdateArticleInteractor(private val repo: NewsRepo): UpdateArticleUseCase{
    override suspend fun execute(title: String,isFav: Boolean) {
        return repo.updateArticle(title, isFav)
    }
}