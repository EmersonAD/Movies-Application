package com.souzaemerson.mymangalist.presentation.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.souzaemerson.mymangalist.const.KEY_MOVIE
import com.souzaemerson.mymangalist.data.model.movie.Result
import com.souzaemerson.mymangalist.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var movie: Result

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
        movie = arguments?.getSerializable(KEY_MOVIE) as Result
        binding.run {
            detailsMovieImage.load("https://image.tmdb.org/t/p/original/${movie.poster_path}")
            detailsMovieTitle.text = movie.title
            detailsMovieReleaseDate.text = movie.release_date
            if (movie.adult) {
                detailsMovieAdult.visibility = View.VISIBLE
            }
            detailsMovieDescription.text = movie.overview
            detailsMovieOrignalTitle.text = movie.original_title
            detailsMovieLanguage.text = "Original language: ${movie.original_language}"
        }
    }
}