package kz.butik.domain.usecase

import kz.butik.domain.entities.Article

interface SaveArticlesUseCase {
    suspend fun execute(articles: List<Article>)
}