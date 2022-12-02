package com.souzaemerson.ui.searchview

import androidx.appcompat.widget.SearchView

class SearchViewQueryListener(
    private val onEmptyField: () -> Unit = {},
    private val onSearching: (movieName: String?) -> Unit = {}
) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        when (newText) {
            "" -> onEmptyField()
            else -> onSearching(newText)
        }
        return false
    }
}