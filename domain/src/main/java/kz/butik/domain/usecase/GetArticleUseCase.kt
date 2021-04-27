package kz.butik.domain.usecase

import kz.butik.domain.entities.Article

interface GetArticleUseCase {
    suspend fun execute(title: String): Article
}