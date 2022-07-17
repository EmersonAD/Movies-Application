package com.souzaemerson.mymangalist.core.state

import com.souzaemerson.mymangalist.core.state.status.Status

data class State<out T>(
    val status: Status,
    val data: T?,
    val error: Throwable?,
    val loading: Boolean?
) {
    companion object {
        fun <T> success(data: T?): State<T> =
            State(Status.SUCCESS, data, null, false)

        fun <T> loading(loading: Boolean): State<T> =
            State(Status.LOADING, null, null, loading)

        fun <T> error(error: Throwable): State<T> =
            State(Status.ERROR, null, error, false)
    }
}
