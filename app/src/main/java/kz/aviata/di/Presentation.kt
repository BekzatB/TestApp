package kz.aviata.di

import kz.aviata.ui.details.DetailsViewModel
import kz.aviata.ui.everything.EverythingViewModel
import kz.aviata.ui.saved.SavedNewsViewModel
import kz.aviata.ui.top_headlines.TopHeadlinesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    viewModel {
        TopHeadlinesViewModel()
    }
    viewModel {
        DetailsViewModel()
    }
    viewModel {
        EverythingViewModel()
    }
    viewModel {
        SavedNewsViewModel()
    }
}