package com.souzaemerson.mymangalist.ui.fragment.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.const.API_KEY
import com.souzaemerson.mymangalist.const.KEY_MOVIE
import com.souzaemerson.mymangalist.const.LANGUAGE
import com.souzaemerson.mymangalist.core.state.status.Status
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.data.network.retrofit.RetrofitService
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepositoryImpl
import com.souzaemerson.mymangalist.databinding.FragmentHomeBinding
import com.souzaemerson.mymangalist.ui.fragment.movie.adapter.MovieAdapter
import com.souzaemerson.mymangalist.ui.fragment.movie.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: MovieAdapter

    private lateinit var repository: MovieRepository
    private lateinit var viewModel: HomeViewModel

    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = MovieRepositoryImpl(RetrofitService.service)
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO)
            .create(HomeViewModel::class.java)

        getPopularMovies()
        observeVMEvents()
    }

    private fun getPopularMovies(page: Int = 1) {
        lifecycleScope.launchWhenResumed {
            viewModel.getPopularMovies(API_KEY, LANGUAGE, page)
        }
    }

    private fun observeVMEvents() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response -> setRecycler(response.results) }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
    }

    private fun setAdapter(animeList: List<Result>) {
        mAdapter = MovieAdapter(animeList) { movie ->
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                Bundle().apply {
                    putSerializable(KEY_MOVIE, movie)
                })
        }
    }

    private fun setRecycler(animeList: List<Result>) {
        setAdapter(animeList)
        binding.homeRecycler.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
}