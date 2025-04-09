package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.ui.theme.JITBookTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextFile(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType), // Cấu hình bàn phím
        visualTransformation = visualTransformation, // Xử lý hiển thị văn bản

        // ✅ Cập nhật colors đúng cách
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi focus
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi không focus
            cursorColor = MaterialTheme.colorScheme.primary // Màu con trỏ nhập liệu
        ),

    )
}

@Preview
@Composable
fun PrimaryTextFilePreview() {
    JITBookTheme(
        darkTheme = isSystemInDarkTheme(), dynamicColor = false
    ) {
        PrimaryTextFile(
            label = "Enter your name",
            value = "Enter your name",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
        )
    }

}