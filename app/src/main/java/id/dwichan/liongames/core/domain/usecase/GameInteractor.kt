package id.dwichan.liongames.core.domain.usecase

import androidx.lifecycle.LiveData
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository) : GameUseCase {
    override fun getAllGames(): LiveData<Resource<List<Game>>> =
        gameRepository.getAllGames()

    override fun getSpecificGames(query: String): LiveData<Resource<List<Game>>> =
        gameRepository.getSpecificGames(query)

    override fun getFavoriteGames(): LiveData<List<Game>> =
        gameRepository.getFavoriteGames()

    override fun getSpecificFavoriteGames(query: String): LiveData<List<Game>> =
        gameRepository.getSpecificFavoriteGames(query)

    override fun setFavoriteGames(game: Game, state: Boolean) =
        gameRepository.setFavoriteGames(game, state)

}