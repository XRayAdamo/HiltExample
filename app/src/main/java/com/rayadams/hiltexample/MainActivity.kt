package com.rayadams.hiltexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rayadams.hiltexample.navigation.CustomNavigator
import com.rayadams.hiltexample.navigation.NavigationParams
import com.rayadams.hiltexample.navigation.NavigationPath
import com.rayadams.hiltexample.ui.theme.HiltExampleTheme
import com.rayadams.hiltexample.views.AddContactView
import com.rayadams.hiltexample.views.ContactsView
import com.rayadams.hiltexample.views.EditContactView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navHelper: CustomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HiltExampleTheme {
                val navController: NavHostController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            navHelper.navActions.collect { navigatorState ->
                                navigatorState.parcelableArguments.forEach { arg ->
                                    navController.currentBackStackEntry?.arguments?.putParcelable(
                                        arg.key,
                                        arg.value
                                    )
                                }
                                navHelper.runNavigationCommand(navigatorState, navController)

                            }
                        }
                    }
                }
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationView(navController)
                }
            }
        }
    }

    @Composable
    fun NavigationView(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavigationPath.CONTACTS_VIEW) {
            composable(NavigationPath.CONTACTS_VIEW) {
                ContactsView()
            }
            composable(NavigationPath.ADD_CONTACTS_VIEW) {
                AddContactView()
            }
            composable(NavigationPath.EDIT_CONTACT_VIEW_ID,
                arguments = listOf(navArgument(NavigationParams.CONTACT_ID) { type = NavType.StringType })
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    NavigationParams.CONTACT_ID,
                    navController.currentBackStackEntry?.arguments?.getString(NavigationParams.CONTACT_ID)
                )
                EditContactView()
            }
        }
    }

}

