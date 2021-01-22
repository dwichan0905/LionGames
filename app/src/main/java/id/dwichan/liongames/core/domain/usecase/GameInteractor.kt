package id.dwichan.liongames.core.domain.usecase

import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {

    override fun getAllGames() = gameRepository.getAllGames()

    override fun getSpecificGames(query: String) = gameRepository.getSpecificGames(query)

    override fun getFavoriteGames() = gameRepository.getFavoriteGames()

    override fun setFavoriteGames(game: Game, state: Boolean) =
        gameRepository.setFavoriteGames(game, state)

}