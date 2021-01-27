package id.dwichan.liongames.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.dwichan.liongames.R
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.databinding.ActivityDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private val detailsViewModel: DetailsViewModel by viewModel()

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(320, 240)
        .error(R.drawable.ic_error)
        .placeholder(R.drawable.ic_loading)

    companion object {
        const val EXTRA_GAMES = "extra_games"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gameDetail = intent.getParcelableExtra<Game>(EXTRA_GAMES)
        showDetails(gameDetail)
    }

    private fun showDetails(game: Game?) {
        game?.let {
            binding.tvName.text = it.name
            binding.tvGenre.text = it.genres

            Glide.with(this)
                .asBitmap()
                .load(it.backgroundImage)
                .apply(requestOptions)
                .into(binding.imgBackground)

            var statusFavorite = it.isFavorite
            setFavoriteStatus(statusFavorite)

            with(binding.contentDetails) {
                Glide.with(this@DetailsActivity)
                    .asBitmap()
                    .load(it.backgroundImage)
                    .apply(requestOptions)
                    .into(this.imgGame)

                tvGameRating.text = it.rating.toString()
                tvGameReleaseDate.text = it.released
                tvGameGenre.text = it.genres
                tvGamePlatforms.text = it.supportedPlatform
                val playtime = resources.getQuantityString(
                    R.plurals.total_playtime,
                    it.playtime,
                    it.playtime
                )
                tvGamePlaytime.text = playtime
                tvSystemRequirementsLabel.text = getString(
                    R.string.system_requirements, it.platformName
                )
                val systemRequirement = HtmlCompat.fromHtml(
                    it.platformRequirements,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
                tvSystemRequirements.text = systemRequirement

                btnAddToFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailsViewModel.setFavoriteGames(game, statusFavorite)
                    setFavoriteStatus(statusFavorite)
                }
            }
        }
    }

    private fun setFavoriteStatus(status: Boolean) {
        with(binding.contentDetails.btnAddToFavorite) {
            if (status) {
                this.icon = ContextCompat.getDrawable(
                    this@DetailsActivity,
                    R.drawable.ic_baseline_favorite_24
                )
                this.text = resources.getString(R.string.remove_from_favorite)
            } else {
                this.icon = ContextCompat.getDrawable(
                    this@DetailsActivity,
                    R.drawable.ic_baseline_favorite_border_24
                )
                this.text = resources.getString(R.string.add_to_favorite)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) super.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}