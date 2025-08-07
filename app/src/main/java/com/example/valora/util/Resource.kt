package com.example.valora.util

/**
 * A generic class that holds a value with its loading status.
 * This is used to communicate the state of a data request (e.g., from network)
 * to the UI layer.
 *
 * @param T The type of the data being held.
 */
sealed class Resource<T>(val data:T? , val message: String?) {
    /**
     * Represents a successful data fetch.
     * @param data The successfully retrieved data.
     */
    class Success<T>(data: T) : Resource<T>(data, null)

    /**
     * Represents a failed data fetch.
     * @param message A message describing the error.
     */
    class Error<T>(message: String) : Resource<T>(null, message)
}