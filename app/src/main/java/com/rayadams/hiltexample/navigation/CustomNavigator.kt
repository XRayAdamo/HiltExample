package com.rayadams.hiltexample.navigation

import android.os.Parcelable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton


enum class NavigationCommand {
    /**
     * Navigate to destination
     */
    NAVIGATE,

    /**
     * Go back
     */
    GO_BACK,

    /**
     * Navigate back to specific destination
     */
    GO_BACK_TO,

    /**
     * Navigate back to specific destination, inclusive=true
     */
    GO_BACK_TO_INCLUSIVE,

    /**
     * Navigate to destination and remove current view from nav stack
     */
    NAVIGATE_AND_CLEAR_CURRENT,

    /**
     * Navigate to destination and remove all previous destinations making current one as a top
     */
    NAVIGATE_AND_CLEAR_TOP
}

data class NavigationAction(
    val navigationCommand: NavigationCommand,
    val destination: String = "",
    val parcelableArguments: Map<String, Parcelable> = emptyMap(),
    val navOptions: NavOptions = NavOptions.Builder().build()            // No NavOptions as default
)

@Suppress("unused", "MemberVisibilityCanBePrivate")
@Singleton
class CustomNavigator @Inject constructor() {
    private val _navActions =
        MutableSharedFlow<NavigationAction>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    val navActions: SharedFlow<NavigationAction> = _navActions

    fun navigate(navAction: NavigationAction) {
        _navActions.tryEmit(navAction)
    }

    fun navigate(destination: String) {
        navigate(NavigationAction(NavigationCommand.NAVIGATE, destination = destination))
    }

    fun navigateAndClear(destination: String) {
        navigate(NavigationAction(NavigationCommand.NAVIGATE_AND_CLEAR_TOP, destination = destination))
    }

    fun goBack() {
        navigate(NavigationAction(NavigationCommand.GO_BACK))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun runNavigationCommand(action: NavigationAction, navController: NavHostController) {
        when (action.navigationCommand) {
            NavigationCommand.GO_BACK -> navController.navigateUp()
            NavigationCommand.GO_BACK_TO -> navController.goBackTo(action.destination, inclusive = false)

            NavigationCommand.NAVIGATE -> navController.navigate(action.destination) {
                launchSingleTop = true
            }

            NavigationCommand.NAVIGATE_AND_CLEAR_CURRENT -> navController.navigate(action.destination) {
                navController.currentBackStackEntry?.destination?.route?.let {
                    popUpTo(it) { inclusive = true }
                }
            }

            NavigationCommand.NAVIGATE_AND_CLEAR_TOP -> navController.navigateAndReplaceStartRoute(action.destination)
            NavigationCommand.GO_BACK_TO_INCLUSIVE -> navController.goBackTo(action.destination, inclusive = true)
        }

        _navActions.resetReplayCache()
    }

    fun goBackTo(destination: String) {
        navigate(NavigationAction(NavigationCommand.GO_BACK_TO, destination = destination))
    }

    fun goBackTo(destination: String, inclusive: Boolean) {
        if (inclusive) {
            navigate(NavigationAction(NavigationCommand.GO_BACK_TO_INCLUSIVE, destination = destination))
        } else {
            navigate(NavigationAction(NavigationCommand.GO_BACK_TO, destination = destination))
        }
    }

    fun navigateAndClearCurrentScreen(destination: String) {
        navigate(NavigationAction(NavigationCommand.NAVIGATE_AND_CLEAR_CURRENT, destination = destination))
    }
}

fun NavHostController.navigateAndReplaceStartRoute(newHomeRoute: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(newHomeRoute)
    navigate(newHomeRoute)
}

fun NavHostController.goBackTo(routeName: String, inclusive: Boolean = false) {
    popBackStack(routeName, inclusive)
}

