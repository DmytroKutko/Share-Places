package com.places.feature.utils

sealed interface StateUi<out T> {
    data object Loading : StateUi<Nothing>
    data object Idle : StateUi<Nothing>

    data class Error(val error: Throwable) : StateUi<Nothing>
    data class Success<out T>(val data: T) : StateUi<T>
}