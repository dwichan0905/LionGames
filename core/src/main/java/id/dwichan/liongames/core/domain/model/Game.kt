package id.dwichan.liongames.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val rating: Double,
    val supportedPlatform: String,
    val platformName: String,
    val platformRequirements: String,
    val playtime: Int,
    val genres: String,
    val isFavorite: Boolean
) : Parcelable
