package id.dwichan.liongames.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "background_image")
    var backgroundImage: String,

    @ColumnInfo(name = "rating")
    var rating: Double,

    @ColumnInfo(name = "supported_platform")
    var supportedPlatform: String,

    @ColumnInfo(name = "platform_name")
    var platformName: String,

    @ColumnInfo(name = "platform_requirements")
    var platformRequirements: String,

    @ColumnInfo(name = "playtime")
    var playtime: Int,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean? = false
)
