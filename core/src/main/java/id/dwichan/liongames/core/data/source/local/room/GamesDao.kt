package id.dwichan.liongames.core.data.source.local.room

import androidx.room.*
import id.dwichan.liongames.core.data.source.local.entity.GameEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getAllGames(): Flowable<List<GameEntity>>

    @Query("SELECT * FROM games WHERE name LIKE ('%' || :query || '%')")
    fun getSpecificGames(query: String): Flowable<List<GameEntity>>

    @Query("SELECT * FROM games WHERE is_favorite = 1")
    fun getFavoriteGames(): Flowable<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertGames(games: List<GameEntity>): Completable

    @Update
    fun updateFavoriteGame(game: GameEntity)
}