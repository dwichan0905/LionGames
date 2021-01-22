package id.dwichan.liongames.core.utils

import id.dwichan.liongames.core.data.source.local.entity.GameEntity
import id.dwichan.liongames.core.data.source.remote.response.GameResponse
import id.dwichan.liongames.core.domain.model.Game

object DataMapper {

    fun mapResponsesToEntities(input: List<GameResponse>): List<GameEntity> {
        val gameList = ArrayList<GameEntity>()

        input.map {
            val platform = it.supportedPlatform
            var supportedPlatform = ""
            for (j in platform.indices) {
                supportedPlatform += if (j == (platform.size - 1))
                    platform[j].platform.name
                else
                    platform[j].platform.name + ", "
            }

            val genres = it.genres
            var genre = ""
            for (k in genres.indices) {
                genre += if (k == genres.size - 1)
                    genres[k].name
                else
                    genres[k].name + ", "
            }

            val platformName = platform[0].platform.name
            val platformRequirements = platform[0].requirements?.minimum ?: ""

            val game = GameEntity(
                id = it.id,
                name = it.name,
                released = it.released,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                supportedPlatform = supportedPlatform,
                platformName = platformName,
                platformRequirements = platformRequirements,
                playtime = it.playtime,
                genres = genre
            )
            gameList.add(game)
        }

        return gameList
    }

    fun mapEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                id = it.id,
                name = it.name,
                released = it.released,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                supportedPlatform = it.supportedPlatform,
                platformName = it.platformName,
                platformRequirements = it.platformRequirements,
                playtime = it.playtime,
                genres = it.genres,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Game) = GameEntity(
        id = input.id,
        name = input.name,
        released = input.released,
        backgroundImage = input.backgroundImage,
        rating = input.rating,
        supportedPlatform = input.supportedPlatform,
        platformName = input.platformName,
        platformRequirements = input.platformRequirements,
        playtime = input.playtime,
        genres = input.genres,
        isFavorite = input.isFavorite
    )
}