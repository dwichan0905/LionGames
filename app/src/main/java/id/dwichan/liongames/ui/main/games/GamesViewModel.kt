package id.dwichan.liongames.ui.main.games

import androidx.lifecycle.ViewModel
import id.dwichan.liongames.core.domain.usecase.GameUseCase

class GamesViewModel(gameUseCase: GameUseCase) : ViewModel() {

    val games = gameUseCase.getAllGames()

}