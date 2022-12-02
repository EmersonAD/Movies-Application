package com.souzaemerson.ui.searchview

import androidx.appcompat.widget.SearchView

class SearchViewQueryListener(
    private val searchOnSubmit: (query: String) -> Unit = {},
    private val searchOnChange: (movieName: String?) -> Unit = {}
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchOnSubmit(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchOnChange(newText)
        return false
    }
}