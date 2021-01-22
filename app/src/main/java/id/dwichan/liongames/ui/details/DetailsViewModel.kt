package id.dwichan.liongames.ui.details

import androidx.lifecycle.ViewModel
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.usecase.GameUseCase

class DetailsViewModel(private val gameUseCase: GameUseCase) : ViewModel() {
    fun setFavoriteGames(game: Game, newState: Boolean) =
        gameUseCase.setFavoriteGames(game, newState)
}