package id.dwichan.liongames.ui.main.favorites

import androidx.lifecycle.ViewModel
import id.dwichan.liongames.core.domain.usecase.GameUseCase

class FavoritesViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val games = gameUseCase.getFavoriteGames()
}