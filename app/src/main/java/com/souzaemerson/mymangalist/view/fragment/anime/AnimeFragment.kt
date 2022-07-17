package com.souzaemerson.mymangalist.view.fragment.anime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.souzaemerson.mymangalist.core.state.status.Status
import com.souzaemerson.mymangalist.data.model.anime.Data
import com.souzaemerson.mymangalist.data.network.retrofit.AnimeService
import com.souzaemerson.mymangalist.data.repository.anime.AnimeRepository
import com.souzaemerson.mymangalist.data.repository.anime.AnimeRepositoryImpl
import com.souzaemerson.mymangalist.databinding.FragmentAnimesBinding
import com.souzaemerson.mymangalist.view.fragment.anime.adapter.AnimeAdapter
import com.souzaemerson.mymangalist.view.fragment.anime.viewmodel.AnimeViewModel
import kotlinx.coroutines.Dispatchers

class AnimeFragment : Fragment() {
    private lateinit var binding: FragmentAnimesBinding
    private lateinit var mAdapter: AnimeAdapter

    private lateinit var repository: AnimeRepository
    private lateinit var viewModel: AnimeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = AnimeRepositoryImpl(AnimeService.service)
        viewModel = AnimeViewModel.AnimeViewModelProvideFactory(repository, Dispatchers.IO)
            .create(AnimeViewModel::class.java)

        observeVMEvent()

    }

    override fun onResume() {
        super.onResume()
        getAnimeList()
    }

    private fun getAnimeList() {
        viewModel.getTopAnimeList()
    }

    private fun observeVMEvent() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@observe

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { animeResponse ->
                        Log.i("Success", "observeVMEvent: ${it.data}")
                        setRecycler(animeResponse.data)
                    }
                }
                Status.ERROR -> {
                    Log.e("Error", "observeVMEvent: ${it.error}")
                }
                Status.LOADING -> {
                    Log.i("Loading", "observeVMEvent: ${it.loading.toString()}")
                }
            }
        }
    }

    private fun setAdapter(animeList: List<Data>) {
        mAdapter = AnimeAdapter(animeList)
    }

    private fun setRecycler(animeList: List<Data>) {
        setAdapter(animeList)
        binding.animeRecycler.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }
}