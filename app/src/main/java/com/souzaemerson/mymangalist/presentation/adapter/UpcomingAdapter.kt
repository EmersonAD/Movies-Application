package com.souzaemerson.mymangalist.presentation.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.souzaemerson.mymangalist.R
import com.souzaemerson.mymangalist.databinding.UpcomingItemBinding
import com.souzaemerson.mymangalist.domain.mapper.UpcomingDomain

class UpcomingAdapter(private val upcomingList: List<UpcomingDomain>) :
    RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UpcomingItemBinding.inflate(inflater, parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val upcoming = upcomingList[position]
        holder.bindView(upcoming)
    }

    override fun getItemCount(): Int = upcomingList.count()

    inner class UpcomingViewHolder(private val binding: UpcomingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(upcoming: UpcomingDomain) {
            with(binding) {
                    ivBannerUpcoming.load(initPath.plus(upcoming.image))
                    setAdultFlagVisibility(upcoming)
                    setOverviewContent(upcoming)
                    tvReleaseDateUpcoming.text = upcoming.releaseDate
            }
        }
    }

    private fun UpcomingItemBinding.setOverviewContent(upcoming: UpcomingDomain) {
        if (upcoming.overview.isNotEmpty()) {
            tvOverviewUpcoming.text = upcoming.overview
        } else {
            tvOverviewUpcoming.gravity = Gravity.CENTER
            tvOverviewUpcoming.setText(R.string.no_overview_upcoming)
        }
    }

    private fun UpcomingItemBinding.setAdultFlagVisibility(
        upcoming: UpcomingDomain
    ) {
        if (upcoming.adult) {
            iv18RatingUpcoming.visibility = View.VISIBLE
        } else {
            iv18RatingUpcoming.visibility = View.GONE
        }
    }

    companion object {
        private const val initPath = "https://image.tmdb.org/t/p/w500"
    }
}