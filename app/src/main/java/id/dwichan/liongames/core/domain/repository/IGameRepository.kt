package id.dwichan.liongames.core.domain.repository

import androidx.lifecycle.LiveData
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game

interface IGameRepository {

    fun getAllGames(): LiveData<Resource<List<Game>>>

    fun getSpecificGames(query: String): LiveData<Resource<List<Game>>>

    fun getFavoriteGames(): LiveData<List<Game>>

    fun getSpecificFavoriteGames(query: String): LiveData<List<Game>>

    fun setFavoriteGames(game: Game, state: Boolean)

}