package id.dwichan.liongames.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.dwichan.liongames.R
import id.dwichan.liongames.core.data.Resource
import id.dwichan.liongames.core.ui.GamesAdapter
import id.dwichan.liongames.core.ui.ViewModelFactory
import id.dwichan.liongames.databinding.ActivitySearchBinding
import id.dwichan.liongames.ui.details.DetailsActivity

class SearchActivity : AppCompatActivity() {

    private var binding: ActivitySearchBinding? = null
    private lateinit var adapter: GamesAdapter
    private lateinit var searchViewModel: SearchViewModel

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) super.onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = GamesAdapter()
        adapter.onItemClick = { selectedData ->
            val i = Intent(this, DetailsActivity::class.java)
            i.putExtra(DetailsActivity.EXTRA_GAMES, selectedData)
            startActivity(i)
        }
        binding?.rvGames?.adapter = adapter
        binding?.rvGames?.layoutManager = LinearLayoutManager(this)

        val factory = ViewModelFactory.getInstance(this)
        searchViewModel = ViewModelProvider(this, factory)[
                SearchViewModel::class.java
        ]
        binding?.search?.query?.toString()?.let { getSearchResult(it) }

        binding?.search?.setIconifiedByDefault(false)
        binding?.search?.queryHint = getString(R.string.search)
        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getSearchResult(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun getSearchResult(query: String) {
        searchViewModel.setQuery(query)
        searchViewModel.games.observe(this, { games ->
            if (games != null) {
                when (games) {
                    is Resource.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                        setSearchResultVisibility(false)
                    }
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        if (games.data?.size != 0) {
                            setSearchResultVisibility(isVisible = true, isZeroResult = false)
                            adapter.setData(games.data)
                        } else {
                            setSearchResultVisibility(isVisible = true, isZeroResult = true)
                            adapter.setData(ArrayList())
                        }
                        val resultCount = resources.getQuantityString(
                            R.plurals.total_search,
                            games.data?.size ?: 0,
                            games.data?.size ?: 0
                        )
                        binding?.tvTotalSearch?.text = resultCount
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        setSearchResultVisibility(false)
                        binding?.viewError?.root?.visibility = View.VISIBLE
                        binding?.viewError?.tvError?.text =
                            games.message ?: getString(R.string.error)
                    }
                }
            }
        })
    }

    private fun setSearchResultVisibility(isVisible: Boolean, isZeroResult: Boolean = false) {
        if (!isVisible) {
            binding?.tvTotalSearch?.visibility = View.GONE
            binding?.rvGames?.visibility = View.GONE
            binding?.tvFinalSearch?.visibility = View.GONE
        } else {
            binding?.tvTotalSearch?.visibility = View.VISIBLE
            binding?.rvGames?.visibility = View.VISIBLE
            if (!isZeroResult) binding?.tvFinalSearch?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}