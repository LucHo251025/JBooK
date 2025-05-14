package com.example.jitbook.book.theme.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.JITBookTheme


open class Item(val path: String, val title: String, val icon: ImageVector)


object NavTitle {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val LIST = "Favorite"
    const val PROFILE = "Profile"
}

sealed class NavItem {
    object Home :
        Item(path = Route.BookSubject.route, title = NavTitle.HOME, icon = Icons.Default.Home)

    object Search :
        Item(path = Route.BookList.route, title = NavTitle.SEARCH, icon = Icons.Default.Search)

    object List :
        Item(path = Route.BookFavorite.route, title = NavTitle.LIST, icon = Icons.Default.Favorite)

    object Profile :
        Item(
            path = Route.BookProfile.route, title = NavTitle.PROFILE, icon = Icons.Default.Person
        )
}

//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.List, NavItem.Profile)
//    var selectedItem by rememberSaveable { mutableStateOf(0) }
//
//    NavigationBar(
//        modifier = Modifier,
//        containerColor = MaterialTheme.colorScheme.background,
//        contentColor = MaterialTheme.colorScheme.primary
//    ) {
//        navItems.forEachIndexed { index, item ->
//            val scale by animateFloatAsState(
//                targetValue = if (selectedItem == index) 1.25f else 1f,
//                label = "nav_item_scale"
//            )
//            val labelAlpha by animateFloatAsState(
//                targetValue = if (selectedItem == index) 1f else 0f,
//                label = "nav_item_label_alpha"
//            )
//            NavigationBarItem(
//                alwaysShowLabel = false,
//                icon = { Icon(item.icon, contentDescription = item.title,modifier = Modifier.scale(scale)) },
//                label = { Text(
//                   text =item.title,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.alpha(labelAlpha)
//
//                ) },
//                selected = selectedItem == index,
//                onClick = {
//                    selectedItem = index
//                    navController.navigate(item.path) {
//                        navController.graph.startDestinationRoute?.let { route ->
//                            popUpTo(route) { saveState = true }
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = MaterialTheme.colorScheme.primary,
//                    selectedTextColor = MaterialTheme.colorScheme.primary,
//                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    indicatorColor = Color.Transparent
//
//                )
//            )
//        }
//    }
//
//}
//

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.List, NavItem.Profile)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        navItems.forEachIndexed { index, item ->
            val selected = currentRoute == item.path
            val scale by animateFloatAsState(
                targetValue = if (selected) 1.25f else 1f,
                label = "nav_item_scale"
            )
            val labelAlpha by animateFloatAsState(
                targetValue = if (selected) 1f else 0f,
                label = "nav_item_label_alpha"
            )

            NavigationBarItem(
                alwaysShowLabel = false,
                icon = { Icon(item.icon, contentDescription = item.title, modifier = Modifier.scale(scale)) },
                label = { Text(item.title, fontWeight = FontWeight.Bold, modifier = Modifier.alpha(labelAlpha)) },
                selected = selected,
                onClick = {
                    if (currentRoute != item.path) {
                        navController.navigate(item.path) {
                            popUpTo(navController.graph.startDestinationRoute ?: "") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    JITBookTheme(
        darkTheme =true,
        dynamicColor = false
    ) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            // Mock content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Preview Screen")
            }
        }
    }


}