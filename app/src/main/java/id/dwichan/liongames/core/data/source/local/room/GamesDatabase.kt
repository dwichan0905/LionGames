package id.dwichan.liongames.core.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.dwichan.liongames.core.data.source.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GamesDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao

    companion object {
        @Volatile
        private var instance: GamesDatabase? = null

        fun getInstance(context: Context): GamesDatabase =
            instance ?: synchronized(this) {
                val roomInstance = Room.databaseBuilder(
                    context.applicationContext,
                    GamesDatabase::class.java,
                    "GamesDatabase.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                instance = roomInstance
                roomInstance
            }
    }
}