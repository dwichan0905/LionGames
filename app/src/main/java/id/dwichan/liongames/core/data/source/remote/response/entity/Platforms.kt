package id.dwichan.liongames.core.data.source.remote.response.entity

import com.google.gson.annotations.SerializedName

data class Platforms(
    @field:SerializedName("platform")
    val platform: Platform,

    @field:SerializedName("requirements_en")
    val requirements: Requirements?
)
