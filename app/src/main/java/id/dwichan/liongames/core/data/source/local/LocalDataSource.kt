package id.dwichan.liongames.core.data.source.local

import id.dwichan.liongames.core.data.source.local.entity.GameEntity
import id.dwichan.liongames.core.data.source.local.room.GamesDao
import io.reactivex.Flowable

class LocalDataSource private constructor(private val gamesDao: GamesDao) {
    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(gamesDao: GamesDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(gamesDao)
            }
    }

    fun getAllGames(): Flowable<List<GameEntity>> = gamesDao.getAllGames()

    fun getSpecificGames(query: String): Flowable<List<GameEntity>> =
        gamesDao.getSpecificGames(query)

    fun getFavoriteGames(): Flowable<List<GameEntity>> = gamesDao.getFavoriteGames()

    fun insertGames(games: List<GameEntity>) = gamesDao.insertGames(games)

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gamesDao.updateFavoriteGame(game)
    }
}