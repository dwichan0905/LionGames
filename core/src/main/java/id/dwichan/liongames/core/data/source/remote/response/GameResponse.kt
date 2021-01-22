package id.dwichan.liongames.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import id.dwichan.liongames.core.data.source.remote.response.entity.Genres
import id.dwichan.liongames.core.data.source.remote.response.entity.Platforms

data class GameResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("background_image")
    val backgroundImage: String,

    @field:SerializedName("rating")
    val rating: Double,

    @field:SerializedName("platforms")
    val supportedPlatform: List<Platforms>,

    @field:SerializedName("playtime")
    val playtime: Int,

    @field:SerializedName("genres")
    val genres: List<Genres>
)
