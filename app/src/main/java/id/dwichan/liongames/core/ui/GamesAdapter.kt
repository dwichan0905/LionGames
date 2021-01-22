package id.dwichan.liongames.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.dwichan.liongames.R
import id.dwichan.liongames.core.domain.model.Game
import id.dwichan.liongames.databinding.ItemGamesBinding

class GamesAdapter : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    private var listGames = ArrayList<Game>()
    var onItemClick: ((Game) -> Unit)? = null

    fun setData(games: List<Game>?) {
        if (games == null) return
        listGames.clear()
        listGames.addAll(games)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesAdapter.GamesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent, false)
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapter.GamesViewHolder, position: Int) {
        holder.bind(listGames[position])
    }

    override fun getItemCount(): Int = listGames.size

    inner class GamesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemGamesBinding.bind(itemView)

        fun bind(game: Game) {
            with(binding) {
                val requestOptions = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(320, 240)

                Glide.with(itemView.context)
                    .asBitmap()
                    .load(game.backgroundImage)
                    .apply(requestOptions)
                    .into(imgBackground)

                tvGameName.text = game.name
                tvGameGenre.text = game.genres
                tvGameRating.text = game.rating.toString()

                if (game.isFavorite) {
                    imgFavoriteIndicator.visibility = View.VISIBLE
                } else {
                    imgFavoriteIndicator.visibility = View.GONE
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listGames[adapterPosition])
            }
        }
    }
}