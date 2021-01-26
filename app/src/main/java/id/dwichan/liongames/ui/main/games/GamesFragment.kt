package id.dwichan.liongames.ui.main.games

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.dwichan.liongames.R
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.ui.GamesAdapter
import id.dwichan.liongames.databinding.FragmentGamesBinding
import id.dwichan.liongames.ui.details.DetailsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class GamesFragment : Fragment() {

    private val gamesViewModel: GamesViewModel by viewModel()
    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = GamesAdapter()
        adapter.onItemClick = { selectedData ->
            val i = Intent(activity, DetailsActivity::class.java)
            i.putExtra(DetailsActivity.EXTRA_GAMES, selectedData)
            startActivity(i)
        }

        gamesViewModel.games.observe(viewLifecycleOwner, { games ->
            if (games != null) {
                when (games) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.setData(games.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            games.message ?: getString(R.string.error)
                    }
                }
            }
        })

        with(binding.rvGames) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}