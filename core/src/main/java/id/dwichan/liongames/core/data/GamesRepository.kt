package id.dwichan.liongames.core.data

import android.annotation.SuppressLint
import id.dwichan.liongames.core.data.source.local.LocalDataSource
import id.dwichan.liongames.core.data.source.remote.RemoteDataSource
import id.dwichan.liongames.core.data.source.remote.network.ApiResponse
import id.dwichan.liongames.core.data.source.remote.response.GameResponse
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.repository.IGameRepository
import id.dwichan.liongames.core.utils.AppExecutors
import id.dwichan.liongames.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GamesRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {

    override fun getAllGames(): Flowable<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, List<GameResponse>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Game>> {
                return localDataSource.getAllGames().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): Flowable<ApiResponse<List<GameResponse>>> {
                return remoteDataSource.getAllGames()
            }

            override fun saveCallResult(data: List<GameResponse>) {
                val gamesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gamesList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }.asFlowable()
    }

    override fun getSpecificGames(query: String): Flowable<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, List<GameResponse>>(appExecutors) {
            override fun loadFromDB(): Flowable<List<Game>> {
                return localDataSource.getSpecificGames(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): Flowable<ApiResponse<List<GameResponse>>> {
                return remoteDataSource.getSpecificGames(query)
            }

            @SuppressLint("CheckResult")
            override fun saveCallResult(data: List<GameResponse>) {
                val gamesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gamesList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        /* no-op */
                    }, {
                        /* no-op */
                    })
            }
        }.asFlowable()
    }

    override fun getFavoriteGames(): Flowable<List<Game>> {
        return localDataSource.getFavoriteGames().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteGames(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapDomainToEntity(game)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteGame(gameEntity, state)
        }
    }
}