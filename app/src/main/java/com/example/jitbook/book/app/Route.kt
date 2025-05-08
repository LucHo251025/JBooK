package com.example.jitbook.book.app

import kotlinx.serialization.Serializable

sealed class Route(val route: String) {
    object BookSignUp: Route("book_sign_up")
    object BookLogin : Route("book_login")
    object BookGraph : Route("book_graph")
    object BookSubject: Route("book_subject")
    object BookList : Route("book_list")
    object BookSplash : Route("book_splash")
    object BookProfile : Route("book_profile")
    object BookFavorite : Route("book_favorite")

    object BookDetail : Route("book_detail/{bookId}") {
        fun createRoute(bookId: String) = "book_detail/$bookId"
    }


}
