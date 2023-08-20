package com.souzaemerson.mymangalist.presentation.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.const.KEY_MOVIE
import com.souzaemerson.mymangalist.databinding.FragmentDetailsBinding
import com.souzaemerson.mymangalist.domain.mapper.ResultDomain

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var movie: ResultDomain

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Remover e passar por navigation
        movie = arguments?.getSerializable(KEY_MOVIE) as ResultDomain

        binding.run {
            tvTitle.text = movie.title
            tvOriginalTitle.text = getString(R.string.original_title, movie.original_title)
            tvReleaseDate.text =
                getString(R.string.release_date, movie.release_date.replace("-", "/"))
            ivDetailsMovieBanner.load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
            tvPopularityQuantity.text = movie.popularity.toInt().toString()
            setRatingWarning()
            setMovieDescription()
        }
    }

    private fun FragmentDetailsBinding.setRatingWarning() {
        if (movie.adult) iv18RatingDetails.visibility = View.VISIBLE
    }

    private fun FragmentDetailsBinding.setMovieDescription() {
        if (movie.overview.isNotBlank()) {
            tvDetailsOverview.text = movie.overview
        } else {
            tvDetailsOverview.setText(R.string.no_overview)
        }
    }
}