package id.dwichan.liongames.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.usecase.GameUseCase

// agak bingung, karena udah dicoba pake beberapa metode
// datanya gak mau bertahan pas di rotasi layarnya :(
class SearchViewModel(gameUseCase: GameUseCase) : ViewModel() {

    val query = MutableLiveData<String>()

    fun setQuery(query: String) {
        this.query.value = query
    }

    var games: LiveData<Resource<List<Game>>> = Transformations.switchMap(query) {
        gameUseCase.getSpecificGames(it)
    }

}