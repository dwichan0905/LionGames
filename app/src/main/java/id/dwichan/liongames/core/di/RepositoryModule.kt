package id.dwichan.liongames.core.di

import dagger.Binds
import dagger.Module
import id.dwichan.liongames.core.data.GamesRepository
import id.dwichan.liongames.core.domain.repository.IGameRepository

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gamesRepository: GamesRepository): IGameRepository

}