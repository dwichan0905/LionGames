package id.dwichan.liongames.di

import dagger.Component
import id.dwichan.liongames.core.di.CoreComponent
import id.dwichan.liongames.ui.details.DetailsActivity
import id.dwichan.liongames.ui.main.favorites.FavoritesFragment
import id.dwichan.liongames.ui.main.games.GamesFragment
import id.dwichan.liongames.ui.search.SearchActivity

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: GamesFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(activity: DetailsActivity)
    fun inject(activity: SearchActivity)

}