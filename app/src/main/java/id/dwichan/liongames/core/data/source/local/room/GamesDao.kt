package id.dwichan.liongames.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.dwichan.liongames.core.data.source.local.entity.GameEntity

@Dao
interface GamesDao {

    @Query("SELECT * FROM games")
    fun getAllGames(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM games WHERE name LIKE ('%' || :query || '%')")
    fun getSpecificGames(query: String): LiveData<List<GameEntity>>

    @Query("SELECT * FROM games WHERE is_favorite = 1")
    fun getFavoriteGames(): LiveData<List<GameEntity>>

    @Query("SELECT * FROM games WHERE is_favorite = 1 AND name LIKE ('%' || :query || '%')")
    fun getSpecificFavoriteGames(query: String): LiveData<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    fun insertGames(games: List<GameEntity>)

    @Update
    fun updateFavoriteGame(game: GameEntity)
}