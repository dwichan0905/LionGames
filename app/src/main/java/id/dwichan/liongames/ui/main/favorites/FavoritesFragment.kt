package id.dwichan.liongames.ui.main.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.dwichan.liongames.core.ui.GamesAdapter
import id.dwichan.liongames.databinding.FragmentFavoritesBinding
import id.dwichan.liongames.ui.details.DetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private var binding: FragmentFavoritesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            binding?.progressBar?.visibility = View.VISIBLE

            val adapter = GamesAdapter()
            adapter.onItemClick = { selectedData ->
                val i = Intent(activity, DetailsActivity::class.java)
                i.putExtra(DetailsActivity.EXTRA_GAMES, selectedData)
                startActivity(i)
            }

            favoritesViewModel.games.observe(viewLifecycleOwner, {
                binding?.progressBar?.visibility = View.GONE
                if (it.isEmpty()) {
                    binding?.viewEmpty?.root?.visibility = View.VISIBLE
                    adapter.setData(ArrayList())
                } else {
                    binding?.viewEmpty?.root?.visibility = View.GONE
                    adapter.setData(it)
                }
            })

            with(binding?.rvGames) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}