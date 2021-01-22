package id.dwichan.liongames.di

import id.dwichan.liongames.core.domain.usecase.GameInteractor
import id.dwichan.liongames.core.domain.usecase.GameUseCase
import id.dwichan.liongames.ui.details.DetailsViewModel
import id.dwichan.liongames.ui.main.favorites.FavoritesViewModel
import id.dwichan.liongames.ui.main.games.GamesViewModel
import id.dwichan.liongames.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { GamesViewModel(get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}