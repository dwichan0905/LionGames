package id.dwichan.liongames.core.domain.repository

import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.domain.model.Game
import io.reactivex.Flowable

interface IGameRepository {

    fun getAllGames(): Flowable<Resource<List<Game>>>

    fun getSpecificGames(query: String): Flowable<Resource<List<Game>>>

    fun getFavoriteGames(): Flowable<List<Game>>

    fun setFavoriteGames(game: Game, state: Boolean)

}