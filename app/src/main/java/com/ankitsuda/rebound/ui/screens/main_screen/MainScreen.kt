package com.ankitsuda.rebound.ui.screens.main_screen

import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ankitsuda.rebound.ui.MainScreenNavigationConfigurations
import com.ankitsuda.rebound.ui.MainScreenScaffold
import com.ankitsuda.rebound.ui.Route
import com.ankitsuda.rebound.ui.components.PanelTopCollapsed
import com.ankitsuda.rebound.ui.components.PanelTopDragHandle
import com.ankitsuda.rebound.ui.components.PanelTopExpanded
import com.ankitsuda.rebound.ui.components.WorkoutPanel
import com.ankitsuda.rebound.ui.screens.exercise.ExerciseDetailScreen
import com.ankitsuda.rebound.ui.screens.exercises.ExercisesScreen
import com.ankitsuda.rebound.ui.screens.history.HistoryScreen
import com.ankitsuda.rebound.ui.screens.home.HomeScreen
import com.ankitsuda.rebound.ui.screens.more.MoreScreen
import com.ankitsuda.rebound.ui.screens.workout.WorkoutScreen
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch

/**
 * Root screen of the app
 * Currently testing sliding panel, so using colors instead of other content.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()


    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.History,
        BottomNavigationScreens.Workout,
        BottomNavigationScreens.Exercises,
        BottomNavigationScreens.More
    )

    val swipeableState = rememberSwipeableState(0)
    val coroutine = rememberCoroutineScope()

    BackHandler(swipeableState.currentValue != 0) {
        coroutine.launch {
            swipeableState.animateTo(0)
        }
    }
    MainScreenScaffold(
        modifier = Modifier,
        swipeableState = swipeableState,
        bottomBar = {
            BottomNavigation(
                contentColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp,
                modifier = Modifier
                    .navigationBarsHeight(additional = 56.dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavigationItems.forEach { screen ->

                    BottomNavigationItem(
                        icon = { Icon(screen.icon, screen.title) },
                        label = { Text(screen.title) },
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = Color.Black.copy(0.4f),
                        alwaysShowLabel = false, // This hides the title for the unselected items
                        modifier = Modifier.navigationBarsPadding(),
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
//                        selected = currentRoute == screen.route,
                        onClick = {
                            // This if check gives us a "singleTop" behavior where we do not create a
                            // second instance of the composable if we are already on that destination
//                               if (currentRoute != screen.route) {
//                                   navController.navigate(screen.route)
//                               }
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }

                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }

                        },
                    )
                }
            }
        },
        panel = {
            WorkoutPanel()
        },
        panelTopCommon = {
            PanelTopDragHandle()
        },
        panelTopCollapsed = {
            PanelTopCollapsed()
        },
        panelTopExpanded = {
            PanelTopExpanded(
                onCollapseBtnClicked = {
                    coroutine.launch {
                        swipeableState.animateTo(0)
                    }
                },
                onTimerBtnClicked = { },
                onFinishBtnClicked = {})
        }) {
        MainScreenNavigationConfigurations(navController = navController)
    }
}


sealed class BottomNavigationScreens(val route: String, val title: String, val icon: ImageVector) {
    object Home :
        BottomNavigationScreens(Route.HomeTab.route, "Home", Icons.Outlined.Home)

    object History :
        BottomNavigationScreens(Route.HistoryTab.route, "History", Icons.Outlined.AccessTime)

    object Workout :
        BottomNavigationScreens(Route.WorkoutTab.route, "Workout", Icons.Outlined.Add)

    object Exercises :
        BottomNavigationScreens(Route.ExercisesTab.route, "Exercises", Icons.Outlined.FitnessCenter)

    object More :
        BottomNavigationScreens(Route.MoreTab.route, "More", Icons.Outlined.Menu)
}
