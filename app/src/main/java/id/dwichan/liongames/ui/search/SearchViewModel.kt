package id.dwichan.liongames.ui.search

import androidx.lifecycle.*
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.usecase.GameUseCase

class SearchViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val query: LiveData<String> get() = _query

    val data: LiveData<Resource<List<Game>>> = query.switchMap {
            LiveDataReactiveStreams.fromPublisher(
                gameUseCase.getSpecificGames(it)
            )
    }

    fun setQuery(query: String) {
        _query.value = query
    }

}