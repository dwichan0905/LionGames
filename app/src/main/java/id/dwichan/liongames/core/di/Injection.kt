package id.dwichan.liongames.core.di

import android.content.Context
import id.dwichan.liongames.core.data.GamesRepository
import id.dwichan.liongames.core.data.source.local.LocalDataSource
import id.dwichan.liongames.core.data.source.local.room.GamesDatabase
import id.dwichan.liongames.core.data.source.remote.RemoteDataSource
import id.dwichan.liongames.core.data.source.remote.network.ApiConfig
import id.dwichan.liongames.core.domain.repository.IGameRepository
import id.dwichan.liongames.core.domain.usecase.GameInteractor
import id.dwichan.liongames.core.domain.usecase.GameUseCase
import id.dwichan.liongames.core.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context): IGameRepository {
        val database = GamesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService(context))
        val localDataSource = LocalDataSource.getInstance(database.gamesDao())
        val appExecutors = AppExecutors()

        return GamesRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideGameUseCase(context: Context): GameUseCase {
        val repository = provideRepository(context)
        return GameInteractor(repository)
    }

}