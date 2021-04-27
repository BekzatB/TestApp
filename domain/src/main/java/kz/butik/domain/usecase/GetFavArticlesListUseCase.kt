package kz.butik.domain.usecase

import kz.butik.domain.entities.Article

interface GetFavArticlesListUseCase {
    suspend fun execute(isFav: Boolean): List<Article>
}