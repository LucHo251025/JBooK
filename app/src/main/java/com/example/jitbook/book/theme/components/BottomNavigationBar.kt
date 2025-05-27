package com.example.jitbook.book.theme.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.JITBookTheme


open class Item(val path: String, val title: String, val icon: ImageVector,val color: Color)


object NavTitle {
    const val HOME = "Home"
    const val SEARCH = "Search"
    const val LIST = "Favorite"
    const val PROFILE = "Profile"
}

sealed class NavItem {
    object Home :
        Item(path = Route.BookSubject.route, title = NavTitle.HOME, icon = Icons.Default.Home, color = Color(0xFF5B37B7))

    object Search :
        Item(path = Route.BookList.route, title = NavTitle.SEARCH, icon = Icons.Default.Search, color = Color(0xFFE6A919))

    object List :
        Item(path = Route.BookFavorite.route, title = NavTitle.LIST, icon = Icons.Default.Favorite,color = Color(0xFFC9379D))

    object Profile :
        Item(
            path = Route.BookProfile.route, title = NavTitle.PROFILE, icon = Icons.Default.Person, color = Color(0xFF1194AA)
        )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navItems = listOf(NavItem.Home, NavItem.Search, NavItem.List, NavItem.Profile)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedIndex = navItems.indexOfFirst { it.path == currentRoute }.coerceAtLeast(0)

    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), // hoặc chọn màu tùy ý
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )

            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEachIndexed { index, item ->
                val selected = index == selectedIndex

                if (selected) {
                    // Mục đang chọn: bo tròn nền tím nhạt + icon và chữ
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(item.color.copy(alpha = 0.15f))
                            .clickable {
                                navController.navigate(item.path) {
                                    popUpTo(
                                        navController.graph.startDestinationRoute ?: ""
                                    ) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(horizontal = 20.dp, vertical = 13.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = item.color,
                            modifier = Modifier
                                .size(27.dp)
                                .graphicsLayer(rotationZ = rotation)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = item.color
                        )
                    }
                } else {
                    // Mục chưa chọn: chỉ hiển thị icon
                    IconButton(
                        onClick = {
                            navController.navigate(item.path) {
                                popUpTo(navController.graph.startDestinationRoute ?: "") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    JITBookTheme(
        darkTheme =false,
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