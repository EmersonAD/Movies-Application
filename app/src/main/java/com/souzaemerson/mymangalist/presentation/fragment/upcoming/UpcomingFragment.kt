package com.souzaemerson.mymangalist.presentation.fragment.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.souzaemerson.mymangalist.databinding.FragmentUpcomingBinding
import com.souzaemerson.mymangalist.presentation.adapter.MovieAdapter
import com.souzaemerson.mymangalist.presentation.fragment.upcoming.viewmodel.UpcomingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingBinding
    private val upcomingViewModel by viewModel<UpcomingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}