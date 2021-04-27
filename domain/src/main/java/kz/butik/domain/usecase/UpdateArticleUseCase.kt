package kz.butik.domain.usecase

interface UpdateArticleUseCase {
    suspend fun execute(title: String, isFav: Boolean)
}