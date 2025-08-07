package com.example.valora.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * An interface for observing network connectivity status.
 */
interface ConnectivityObserver {
    /**
     * A flow that emits the network status whenever it changes.
     */
    fun observe(): Flow<Status>

    /**
     * Gets the current network status at the time of the call.
     */
    val currentStatus: Status

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}

/**
 * Implementation of ConnectivityObserver using Android's ConnectivityManager.
 */
class NetworkConnectivityObserver @Inject constructor(
    @param:ApplicationContext private val context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val currentStatus: ConnectivityObserver.Status
        get() {
            return if (connectivityManager.activeNetwork != null) {
                ConnectivityObserver.Status.Available
            } else {
                ConnectivityObserver.Status.Unavailable
            }
        }

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(ConnectivityObserver.Status.Available)
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    trySend(ConnectivityObserver.Status.Losing)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(ConnectivityObserver.Status.Lost)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(ConnectivityObserver.Status.Unavailable)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged() // Only emit when the status actually changes
    }
}
