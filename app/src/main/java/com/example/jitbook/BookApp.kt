package com.example.jitbook

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jitbook.book.theme.navigation.BookContentType
import com.example.jitbook.book.theme.navigation.BookNavigationType


@Composable
fun BookApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    var navigationType: BookNavigationType
    val contentType: BookContentType

    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = BookNavigationType.BOTTOM_NAVIGATION
            contentType = BookContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType =  BookNavigationType.NAVIGATION_RAIL
            contentType = BookContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = BookNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = BookContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = BookNavigationType.BOTTOM_NAVIGATION
            contentType = BookContentType.LIST_ONLY
        }

    }
}