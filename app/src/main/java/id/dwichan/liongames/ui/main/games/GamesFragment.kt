package id.dwichan.liongames.ui.main.games

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dwichan.liongames.R
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.ui.GamesAdapter
import id.dwichan.liongames.core.ui.ViewModelFactory
import id.dwichan.liongames.databinding.FragmentGamesBinding
import id.dwichan.liongames.ui.details.DetailsActivity

class GamesFragment : Fragment() {

    private lateinit var gamesViewModel: GamesViewModel
    private var binding: FragmentGamesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val adapter = GamesAdapter()
            adapter.onItemClick = { selectedData ->
                val i = Intent(activity, DetailsActivity::class.java)
                i.putExtra(DetailsActivity.EXTRA_GAMES, selectedData)
                startActivity(i)
            }

            val factory = ViewModelFactory.getInstance(requireContext())
            gamesViewModel = ViewModelProvider(this, factory)[
                    GamesViewModel::class.java
            ]
            gamesViewModel.games.observe(viewLifecycleOwner, { games ->
                if (games != null) {
                    when (games) {
                        is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            adapter.setData(games.data)
                        }
                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            binding?.viewError?.root?.visibility = View.VISIBLE
                            binding?.viewError?.tvError?.text =
                                games.message ?: getString(R.string.error)
                        }
                    }
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