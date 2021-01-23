package id.dwichan.liongamesdfm.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoritesViewModel(get()) }
}