package com.souzaemerson.mymangalist.view.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.souzaemerson.mymangalist.const.API_KEY
import com.souzaemerson.mymangalist.const.LANGUAGE
import com.souzaemerson.mymangalist.const.PAGE
import com.souzaemerson.mymangalist.core.state.status.Status
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.data.network.MovieService
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepositoryImpl
import com.souzaemerson.mymangalist.databinding.FragmentHomeBinding
import com.souzaemerson.mymangalist.view.fragment.home.adapter.MovieAdapter
import com.souzaemerson.mymangalist.view.fragment.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: MovieAdapter

    private lateinit var repository: MovieRepository
    private lateinit var viewModel: HomeViewModel

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
        repository = MovieRepositoryImpl(MovieService.service)
        viewModel = HomeViewModel.HomeViewModelProviderFactory(repository, Dispatchers.IO)
            .create(HomeViewModel::class.java)

        observeVMEvents()
    }

    override fun onResume() {
        super.onResume()
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies(API_KEY, LANGUAGE, PAGE)
    }

    private fun observeVMEvents() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        Toast.makeText(requireContext(), "Sucesso", Toast.LENGTH_SHORT).show()
                        setRecycler(response.results)
                    }
                }
                Status.LOADING -> {
                    Log.i("Loading", "observeVMEvents: ${it.loading.toString()} ")
                }
                Status.ERROR -> {
                    Log.e("Error", "observeVMEvents: ${it.error}")
                }
            }
        }
    }

    private fun setAdapter(animeList: List<Result>) {
        mAdapter = MovieAdapter(animeList)
    }

    private fun setRecycler(animeList: List<Result>) {
        setAdapter(animeList)
        binding.homeRecycler.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
}