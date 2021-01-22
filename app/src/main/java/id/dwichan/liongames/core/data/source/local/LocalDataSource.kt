package id.dwichan.liongames.core.data.source.local

import androidx.lifecycle.LiveData
import id.dwichan.liongames.core.data.source.local.entity.GameEntity
import id.dwichan.liongames.core.data.source.local.room.GamesDao

class LocalDataSource private constructor(private val gamesDao: GamesDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(gamesDao: GamesDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(gamesDao)
            }
    }

    fun getAllGames(): LiveData<List<GameEntity>> = gamesDao.getAllGames()

    fun getSpecificGames(query: String): LiveData<List<GameEntity>> = gamesDao.getSpecificGames(query)

    fun getFavoriteGames(): LiveData<List<GameEntity>> = gamesDao.getFavoriteGames()

    fun getSpecificFavoriteGames(query: String): LiveData<List<GameEntity>> = gamesDao.getSpecificFavoriteGames(query)

    fun insertGames(games: List<GameEntity>) = gamesDao.insertGames(games)

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gamesDao.updateFavoriteGame(game)
    }
}