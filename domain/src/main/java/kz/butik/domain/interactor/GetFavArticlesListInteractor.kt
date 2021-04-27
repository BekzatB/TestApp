package kz.butik.domain.interactor

import kz.butik.domain.entities.Article
import kz.butik.domain.repo.NewsRepo
import kz.butik.domain.usecase.GetFavArticlesListUseCase

class GetFavArticlesListInteractor(private val repo: NewsRepo) : GetFavArticlesListUseCase {
    override suspend fun execute(isFav: Boolean): List<Article> {
        return repo.getFavArticles(isFav)
    }
}