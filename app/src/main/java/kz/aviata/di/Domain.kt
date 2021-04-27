package kz.aviata.di

import kz.butik.domain.interactor.*
import kz.butik.domain.usecase.*
import org.koin.dsl.module

internal val domainModule = module {

    factory<GetTopHeadlinesNewsUseCase> {
        GetTopHeadlinesInteractor(get())
    }

    factory<SaveArticlesUseCase> {
        SaveArticlesIntercator(get())
    }

    factory<UpdateArticleUseCase> {
        UpdateArticleInteractor(get())
    }

    factory<GetArticleUseCase> {
        GetArticleInteractor(get())
    }

    factory<GetEverythingNewsUseCase> {
        GetEverythingInteractor(get())
    }

    factory<GetFavArticlesListUseCase> {
        GetFavArticlesListInteractor(get())
    }
}