package id.dwichan.liongames.core.data.source.remote.response.entity

import com.google.gson.annotations.SerializedName

data class Requirements(
    @field:SerializedName("minimum")
    val minimum: String?
)
