package com.souzaemerson.mymangalist.presentation.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.const.API_KEY
import com.souzaemerson.mymangalist.const.KEY_MOVIE
import com.souzaemerson.mymangalist.data.network.retrofit.RetrofitService
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepositoryImpl
import com.souzaemerson.mymangalist.data.repository.movie.SearchMovieRepositoryImpl
import com.souzaemerson.mymangalist.databinding.FragmentHomeBinding
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.repository.MovieRepository
import com.souzaemerson.mymangalist.domain.repository.SearchMovieRepository
import com.souzaemerson.mymangalist.domain.usecase.getmovie.GetMoviesContentUseCase
import com.souzaemerson.mymangalist.domain.usecase.getmovie.GetMoviesContentUseCaseImpl
import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCase
import com.souzaemerson.mymangalist.domain.usecase.search.SearchForMoviesUseCaseImpl
import com.souzaemerson.mymangalist.presentation.activity.HomeActivity
import com.souzaemerson.mymangalist.presentation.fragment.movie.adapter.MovieAdapter
import com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel.HomeViewModel
import com.souzaemerson.state.State
import com.souzaemerson.state.status.Status
import com.souzaemerson.ui.recyclerview.EndlessRecycler
import com.souzaemerson.ui.searchview.SearchViewQueryListener
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: MovieAdapter

    private lateinit var repository: MovieRepository
    private lateinit var repositorySearch: SearchMovieRepository
    private lateinit var useCaseGetMovies: GetMoviesContentUseCase
    private lateinit var useCaseSearchMovie: SearchForMoviesUseCase
    private lateinit var viewModel: HomeViewModel

    private val domainList = mutableListOf<ResultDomain>()
    private var isSearch = false
    private var actualPagination: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = MovieRepositoryImpl(RetrofitService.service)
        repositorySearch = SearchMovieRepositoryImpl(RetrofitService.service)
        useCaseGetMovies = GetMoviesContentUseCaseImpl(repository)
        useCaseSearchMovie = SearchForMoviesUseCaseImpl(repositorySearch)
        viewModel = HomeViewModel(useCaseGetMovies, useCaseSearchMovie, Dispatchers.IO)

        setupToolbar()
        getPopularMovies()
        setRecyclerView()
        observeVMEvents()
    }

    private fun getPopularMovies(page: Int = 1) {
        lifecycleScope.launchWhenResumed {
            viewModel.getPopularMovies(page, API_KEY)
        }
    }

    private fun searchMovies(movieName: String) {
        lifecycleScope.launchWhenResumed {
            viewModel.searchForMovie(movieName, API_KEY)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeVMEvents() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        if (isSearch) {
                            domainList.clear()
                        }
                        domainList.addAll(response)
                        mAdapter.notifyDataSetChanged()
                        isSearch = false
                    }
                }
                Status.LOADING -> {
                    setProgressBar(it)
                }
                Status.ERROR -> {}
            }
        }
        viewModel.search.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        isSearch = true

                        domainList.clear()
                        domainList.addAll(response)
                        mAdapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {}
            }
        }
    }

    private fun setupToolbar() {
        (activity as HomeActivity).setSupportActionBar(binding.toolbar.themoviedbToolbar)
        (activity as HomeActivity).title = null
        setMenu()
    }

    private fun setMenu() {
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                setSearchView(menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        })
    }

    private fun setSearchView(menu: Menu) {
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search for movies"

        searchView.setOnQueryTextListener(setupTextListener)
        onCloseSearchViewAction(searchView)
    }

    private val setupTextListener = SearchViewQueryListener(
        searchOnSubmit = {
            if (it.isNotEmpty()) {
                searchMovies(it)
            }
        }
    )

    private fun onCloseSearchViewAction(searchView: SearchView) {
        searchView.setOnCloseListener {
            if (isSearch) {
                getPopularMovies()
            }
            return@setOnCloseListener true
        }
    }

    private fun setProgressBar(it: State<List<ResultDomain>>) {
        if (it.loading == true) {
            binding.progressLoading.visibility = View.VISIBLE
        } else {
            binding.progressLoading.visibility = View.GONE
        }
    }

    private fun setRecyclerView() {
        binding.homeRecycler.run {
            setAdapter()
            setHasFixedSize(true)
            adapter = mAdapter
            addOnScrollListener(endlessListener)
        }
    }

    private fun setAdapter() {
        mAdapter = MovieAdapter(domainList) { movie ->
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                Bundle().apply {
                    putSerializable(KEY_MOVIE, movie)
                })
        }
    }

    private val endlessListener = EndlessRecycler {
        if (!isSearch) {
            actualPagination += 1
            getPopularMovies(actualPagination)
        }
    }
}