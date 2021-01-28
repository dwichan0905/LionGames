package id.dwichan.liongamesdfm.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.dwichan.liongames.core.ui.GamesAdapter
import id.dwichan.liongames.ui.details.DetailsActivity
import id.dwichan.liongamesdfm.favorite.databinding.FragmentFavoritesBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visibility = View.VISIBLE

        val adapter = GamesAdapter()
        adapter.onItemClick = { selectedData ->
            val i = Intent(activity, DetailsActivity::class.java)
            i.putExtra(DetailsActivity.EXTRA_GAMES, selectedData)
            startActivity(i)
        }

        favoritesViewModel.games.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            if (it.isEmpty()) {
                binding.viewEmpty.root.visibility = View.VISIBLE
                adapter.setData(ArrayList())
            } else {
                binding.viewEmpty.root.visibility = View.GONE
                adapter.setData(it)
            }
        })

        with(binding.rvGames) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        binding.rvGames.adapter = null
        super.onDestroyView()
        _binding = null
    }
}