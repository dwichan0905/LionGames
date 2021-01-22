package id.dwichan.liongames.di

import dagger.Binds
import dagger.Module
import id.dwichan.liongames.core.domain.usecase.GameInteractor
import id.dwichan.liongames.core.domain.usecase.GameUseCase

@Module
abstract class AppModule {

    @Binds
    abstract fun provideGameUseCase(gameInteractor: GameInteractor): GameUseCase

}