package com.example.jitbook.book.theme.components

import com.google.firebase.Timestamp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.book.data.model.Rating
import com.example.jitbook.book.theme.JITBookTheme
import java.util.Calendar

@Composable
fun BookListRating(
    currentUser: String,
    ratings: List<Rating>,
    onEditClicked: (Rating) -> Unit,
    onDeleteClicked: (Rating) -> Unit,
) {

    ratings.forEach { rating ->
        BookRating(
            ratings = rating,
            isShowMore = if(rating.userId == currentUser ) true else false,
            onEditClicked = { onEditClicked(rating) },
            onDeleteClicked = { onDeleteClicked(rating) }
        )
    }
}

@Preview
@Composable
fun BookListRatingPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        val calendar = Calendar.getInstance().apply {
            set(2023, Calendar.OCTOBER, 1)
        }
        val specificTimestamp = Timestamp(calendar.time)

        BookListRating(
            ratings = listOf(
                Rating(
                    userId = "1",
                    userName = "John Doe",
                    imageUrl = null,
                    rating = 5,
                    comment = "Great book!",
                    timestamp = java.sql.Timestamp(Timestamp.now().seconds * 1000)
                ),
                Rating(
                    userId = "2",
                    userName = "Jane Smith",
                    imageUrl = null,
                    rating = 4,
                    comment = "Very informative.",
                    timestamp = java.sql.Timestamp(Timestamp.now().seconds * 1000)
                )
            ),
            currentUser = "1",
            onEditClicked = {
            },
            onDeleteClicked = {
            }
        )
    }

}