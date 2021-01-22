package id.dwichan.liongames.core.data.source.remote.network

import id.dwichan.liongames.core.data.source.remote.response.ListGamesResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    fun getGamesList(
        @Query("key") key: String,
        @Query("search") search: String = ""
    ): Flowable<ListGamesResponse>
}