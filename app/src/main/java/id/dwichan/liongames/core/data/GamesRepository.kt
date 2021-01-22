package id.dwichan.liongames.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import id.dwichan.liongames.core.data.source.local.LocalDataSource
import id.dwichan.liongames.core.data.source.remote.RemoteDataSource
import id.dwichan.liongames.core.data.source.remote.network.ApiResponse
import id.dwichan.liongames.core.data.source.remote.response.GameResponse
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.core.domain.repository.IGameRepository
import id.dwichan.liongames.core.utils.AppExecutors
import id.dwichan.liongames.core.utils.DataMapper

class GamesRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGameRepository {

    companion object {
        @Volatile
        private var instance: GamesRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): GamesRepository =
            instance ?: synchronized(this) {
                instance ?: GamesRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun getAllGames(): LiveData<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, List<GameResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Game>> {
                return Transformations.map(localDataSource.getAllGames()) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<GameResponse>>> {
                return remoteDataSource.getAllGames()
            }

            override fun saveCallResult(data: List<GameResponse>) {
                val gamesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gamesList)
            }
        }.asLiveData()
    }

    override fun getSpecificGames(query: String): LiveData<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, List<GameResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Game>> {
                return Transformations.map(localDataSource.getSpecificGames(query)) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<GameResponse>>> {
                return remoteDataSource.getSpecificGames(query)
            }

            override fun saveCallResult(data: List<GameResponse>) {
                val gamesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertGames(gamesList)
            }
        }.asLiveData()
    }

    override fun getFavoriteGames(): LiveData<List<Game>> {
        return Transformations.map(localDataSource.getFavoriteGames()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getSpecificFavoriteGames(query: String): LiveData<List<Game>> {
        return Transformations.map(localDataSource.getSpecificFavoriteGames(query)) {
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