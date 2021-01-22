package id.dwichan.liongames.core.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import id.dwichan.liongames.core.domain.repository.IGameRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class]
)
interface CoreComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CoreComponent
    }

    fun provideRepository(): IGameRepository

}