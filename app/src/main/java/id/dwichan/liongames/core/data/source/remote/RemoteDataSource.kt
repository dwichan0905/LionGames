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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

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
                resultData.onNext(if (!dataArray.isNullOrEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

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
                resultData.onNext(if (!dataArray.isNullOrEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}