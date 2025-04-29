package com.example.jitbook.book.theme.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.theme.JITBookTheme
import java.time.LocalDate
import java.util.Calendar


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
        label = { Text(label,
            color = MaterialTheme.colorScheme.onSecondary) },
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType), // Cấu hình bàn phím
        visualTransformation = visualTransformation, // Xử lý hiển thị văn bản

        // ✅ Cập nhật colors đúng cách
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi focus
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi không focus
            cursorColor = MaterialTheme.colorScheme.primary // Màu con trỏ nhập liệu
        ),

    )
}

@Composable
fun InputDisplay(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Username / Email",
    modifier: Modifier = Modifier

) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label phía trên TextField
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // TextField custom style
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Column {
                    innerTextField()
                    Spacer(modifier = Modifier.height(8.dp))
                    // Đường line màu cam bên dưới
                    Divider(
                        color = Color(0xFFFFA000), // màu cam như hình
                        thickness = 2.dp
                    )
                }
            }
        )
    }
}

@Composable
fun DateInputDisplay(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    label: String = "Select Date",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selected = LocalDate.of(year, month + 1, dayOfMonth)
                onDateSelected(selected)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = selectedDate?.format(dateFormatter) ?: "Chọn ngày",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Divider(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                color = Color(0xFFFFA000),
                thickness = 2.dp
            )
        }
    }
}

@Preview
@Composable
fun PrimaryTextFilePreview() {
    JITBookTheme(
        darkTheme = false, dynamicColor = false
    ) {
        DateInputDisplay(
            selectedDate = null,
            onDateSelected = {},
            label = "Select Date",
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
        )
    }

}



//@Preview
//@Composable
//fun EmailInputDisplayPreview() {
//    JITBookTheme(
//        darkTheme = isSystemInDarkTheme(), dynamicColor = false
//    ) {
//        InputDisplay(
//            value = "Enter your email",
//            onValueChange = {},
//            label = "Username / Email",
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(30.dp),
//        )
//
//    }
//
//}