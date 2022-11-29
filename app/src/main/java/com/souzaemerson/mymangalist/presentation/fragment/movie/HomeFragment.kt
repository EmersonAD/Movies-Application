package com.souzaemerson.mymangalist.presentation.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.const.KEY_MOVIE
import com.souzaemerson.mymangalist.data.network.retrofit.RetrofitService
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepository
import com.souzaemerson.mymangalist.data.repository.movie.MovieRepositoryImpl
import com.souzaemerson.mymangalist.databinding.FragmentHomeBinding
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain
import com.souzaemerson.mymangalist.domain.usecase.GetMoviesContentUseCase
import com.souzaemerson.mymangalist.domain.usecase.GetMoviesContentUseCaseImpl
import com.souzaemerson.mymangalist.presentation.fragment.movie.adapter.MovieAdapter
import com.souzaemerson.mymangalist.presentation.fragment.movie.viewmodel.HomeViewModel
import com.souzaemerson.state.status.Status
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: MovieAdapter

    private lateinit var repository: MovieRepository
    private lateinit var useCase: GetMoviesContentUseCase
    private lateinit var viewModel: HomeViewModel

    private val domainList = mutableListOf<ResultDomain>()
    private var actualPagination: Int = 1

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
        useCase = GetMoviesContentUseCaseImpl(repository)
        viewModel = HomeViewModel(useCase, Dispatchers.IO)

        getPopularMovies()
        setRecyclerView()
        observeVMEvents()
    }

    private fun getPopularMovies(page: Int = 1) {
        lifecycleScope.launchWhenResumed {
            viewModel.getPopularMovies(page)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeVMEvents() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { response ->
                        domainList.addAll(response)
                        mAdapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {}
                Status.ERROR -> {}
            }
        }
    }

    private fun setRecyclerView() {
        binding.homeRecycler.run {

            setAdapter()
            setHasFixedSize(true)
            adapter = mAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        recyclerView.layoutManager?.let { layoutManager ->
                            val visibleItemCount = layoutManager.childCount
                            val totalItemCount = (layoutManager as GridLayoutManager).itemCount
                            val pastVisibleItems =
                                (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                actualPagination += 1
                                getPopularMovies(actualPagination)
                            }
                        }
                    }
                }
            })
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
}