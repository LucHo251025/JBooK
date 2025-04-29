package com.example.jitbook.book.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.theme.JITBookTheme

@Composable
fun BookRating(
//    onRatingChanged: (Int) -> Unit,
//    onDescriptionChanged: (String) -> Unit,
    onSubmitClicked: (rating: Int, description: String) -> Unit,
) {

    var rating by remember { mutableStateOf(0) }
    var description by remember { mutableStateOf("") }

    var isEditing by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()

    ) {
        Text(
            text = "Rate this Ebook",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary
        )
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = if (i <= rating) "Rated $i stars" else "Rate $i stars",
                    tint = if (i <= rating) androidx.compose.material3.MaterialTheme.colorScheme.primary else androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .size(37.dp)
                        .clickable {
                            rating = i
                            println(
                                rating
                            )
                        }
                )
            }
        }
        InputDisplay(
            value = description,
            onValueChange = {
                description = it
            },
            label = "Comment",
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    isEditing = focusState.isFocused
                }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        if (isEditing) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.width(220.dp)
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus() // Chỉ ẩn bàn phím, giữ nguyên description
                        isEditing = false
                    },
                    modifier = Modifier.weight(1.3f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCC80)
                    )
                ) {
                    Text(
                        "Cancel",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        onSubmitClicked(rating, description)
                        description = ""
                        isEditing = false
                        focusManager.clearFocus() // Ẩn bàn phím
                    },
                    modifier = Modifier.weight(1.3f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Submit",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary
                    )
                }


            }


        }
    }
}

@Preview
@Composable
fun BookRatingPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        BookRating(
//            onRatingChanged = {},
//            onDescriptionChanged = {},
            onSubmitClicked = { rating, description ->
                println("Rating: $rating, Description: $description")
            }
        )
    }

}