package id.dwichan.liongames.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.usecase.GameUseCase

class SearchViewModel(private val gameUseCase: GameUseCase) : ViewModel() {

    fun getGames(query: String): LiveData<Resource<List<Game>>> {
        return LiveDataReactiveStreams.fromPublisher(
            gameUseCase.getSpecificGames(query)
        )
    }

}