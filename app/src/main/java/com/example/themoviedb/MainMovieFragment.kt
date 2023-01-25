package com.example.themoviedb

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.databinding.FragmentMainMovieBinding
import kotlinx.coroutines.launch

private const val TAG = "MainMovieFragment"
class MainMovieFragment : Fragment(), MenuProvider {

    private var searchView: SearchView? = null

    private var _binding: FragmentMainMovieBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "cannot access binding because it is null. is the view visible?"
        }

    private val vm: MainMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMovieBinding.inflate(inflater, container, false)
        binding.rvMovieGrid.layoutManager = GridLayoutManager(context, 3)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(this)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    binding.rvMovieGrid.adapter = MovieListAdapter(
                        state.movies,
                    ) { moviePageUri ->
                        findNavController().navigate(
                            MainMovieFragmentDirections.navShowMovie(
                                moviePageUri
                            )
                        )
                    }
                    searchView?.setQuery(state.query, false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.removeMenuProvider(this)
        searchView = null
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_movie_gallery, menu)
        val searchItem: MenuItem = menu.findItem(R.id.miSearch)
        searchView = searchItem.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vm.setQuery(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "QueryTextChange: $newText")
                return false
            }
        })

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.miSearch -> {
                true
            }
            R.id.miClear -> {
                vm.setQuery("")

                val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputManager?.hideSoftInputFromWindow(view?.windowToken, 0)

                true
            }
            else -> false
        }
    }
}

fun SearchView.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}
