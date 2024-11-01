package id.dwichan.liongames.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.dwichan.liongames.core.data.source.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 2,
    exportSchema = false
)
abstract class GamesDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao

}