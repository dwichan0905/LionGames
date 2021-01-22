package id.dwichan.liongames.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import id.dwichan.liongames.BuildConfig
import id.dwichan.liongames.core.data.source.remote.network.ApiResponse
import id.dwichan.liongames.core.data.source.remote.network.ApiService
import id.dwichan.liongames.core.data.source.remote.response.GameResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class RemoteDataSource private constructor(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }
    }

    @SuppressLint("CheckResult")
    fun getAllGames(): Flowable<ApiResponse<List<GameResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<GameResponse>>>()

        val client = apiService.getGamesList(BuildConfig.RAWG_KEY)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })
        /*val client = apiService.getGamesList(BuildConfig.RAWG_KEY)
        client.enqueue(object : Callback<ListGamesResponse> {
            override fun onResponse(
                call: Call<ListGamesResponse>,
                response: Response<ListGamesResponse>
            ) {
                val dataArray = response.body()?.results
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListGamesResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })*/

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    @SuppressLint("CheckResult")
    fun getSpecificGames(query: String): Flowable<ApiResponse<List<GameResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<GameResponse>>>()

        val client = apiService.getGamesList(BuildConfig.RAWG_KEY, query)
        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        /*val client = apiService.getGamesList(BuildConfig.RAWG_KEY, query)
        client.enqueue(object : Callback<ListGamesResponse> {
            override fun onResponse(
                call: Call<ListGamesResponse>,
                response: Response<ListGamesResponse>
            ) {
                val dataArray = response.body()?.results
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListGamesResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })*/

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}