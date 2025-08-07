package com.example.valora.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * The Application class for the Valora app.
 *
 * The @HiltAndroidApp annotation triggers Hilt's code generation, including a base class
 * for your application that serves as the application-level dependency container.
 * This generated Hilt component is attached to the Application object's lifecycle and
 * provides dependencies to it. Additionally, it is the parent component of the app,
 * which means that other components can access the dependencies that it provides.
 */
@HiltAndroidApp
class ValoraApp : Application() {
    // We can add any application-wide initialization here if needed in the future.
}