package id.dwichan.liongames.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.dwichan.liongames.core.domain.usecase.GameUseCase
import id.dwichan.liongames.ui.details.DetailsViewModel
import id.dwichan.liongames.ui.main.favorites.FavoritesViewModel
import id.dwichan.liongames.ui.main.games.GamesViewModel
import id.dwichan.liongames.ui.search.SearchViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val gameUseCase: GameUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(GamesViewModel::class.java) -> {
                GamesViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(gameUseCase) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(gameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: " + modelClass.name)
        }

}