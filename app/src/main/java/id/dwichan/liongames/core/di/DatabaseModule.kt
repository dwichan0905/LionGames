package id.dwichan.liongames.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.dwichan.liongames.core.data.source.local.room.GamesDao
import id.dwichan.liongames.core.data.source.local.room.GamesDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): GamesDatabase = Room.databaseBuilder(
        context,
        GamesDatabase::class.java, "GamesDatabase.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGamesDao(database: GamesDatabase): GamesDao = database.gamesDao()
}