package com.souzaemerson.mymangalist.presentation.fragment.upcoming

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.souzaemerson.const.API_KEY
import com.souzaemerson.mymangalist.databinding.FragmentUpcomingBinding
import com.souzaemerson.mymangalist.domain.mapper.UpcomingDomain
import com.souzaemerson.mymangalist.presentation.adapter.UpcomingAdapter
import com.souzaemerson.mymangalist.presentation.fragment.upcoming.viewmodel.UpcomingViewModel
import com.souzaemerson.state.status.Status
import com.souzaemerson.ui.recyclerview.EndlessLinearRecycler
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding
    private val upcomingViewModel by viewModel<UpcomingViewModel>()
    private val upcomingList = mutableListOf<UpcomingDomain>()
    private lateinit var mAdapter: UpcomingAdapter
    private var pagination: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecycler()
        setUpcoming()
        observeVMEvents()
    }

    private fun setUpcoming() {
        if (pagination == 1) {
            getUpcomingMovies(pagination)
        }
    }

    private fun getUpcomingMovies(page: Int) {
        upcomingViewModel.getUpcomingMovies(API_KEY, page = page)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeVMEvents() {
        upcomingViewModel.upcoming.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { upcomingDomains ->
                        upcomingList.addAll(upcomingDomains)
                        mAdapter.notifyDataSetChanged()
                    }
                }
                Status.LOADING -> {
                    with(binding) {
                        if (it.loading == true) {
                            shimmerUpcomingPlaceholder.startShimmer()
                            rvUpcoming.visibility = View.GONE
                            shimmerUpcomingPlaceholder.visibility = View.VISIBLE
                        } else {
                            shimmerUpcomingPlaceholder.visibility = View.GONE
                            rvUpcoming.visibility = View.VISIBLE
                            shimmerUpcomingPlaceholder.stopShimmer()
                        }
                    }
                }
                Status.ERROR -> {}
            }
        }
    }

    private fun setRecycler() {
        setAdapter()
        with(binding.rvUpcoming) {
            adapter = mAdapter
            setHasFixedSize(true)
            addOnScrollListener(endlessRecycler)
        }
    }

    private fun setAdapter() {
        mAdapter = UpcomingAdapter(upcomingList)
    }

    private val endlessRecycler = EndlessLinearRecycler {
        pagination++
        getUpcomingMovies(pagination)
    }
}