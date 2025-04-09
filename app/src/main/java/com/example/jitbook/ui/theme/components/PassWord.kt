package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.TextUnit
import com.example.jitbook.ui.theme.JITBookTheme
import androidx.compose.ui.unit.sp

class CustomPasswordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = PasswordVisualTransformation().filter(text)
        val transformed = AnnotatedString("●".repeat(text.length)) // Thay đổi dấu chấm thành "●" (dấu chấm lớn hơn)
        return TransformedText(transformed, original.offsetMapping)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassWord(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Password,
    visualTransformation: VisualTransformation = CustomPasswordVisualTransformation()
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                color = MaterialTheme.colorScheme.onPrimary,
            )
                },
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType), // Cấu hình bàn phím
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else visualTransformation, // Sử dụng VisualTransformation tùy chỉnh


        // ✅ Cập nhật colors đúng cách
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi focus
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary, // Màu viền dưới khi không focus
            cursorColor = MaterialTheme.colorScheme.primary // Màu con trỏ nhập liệu

        ),
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Ẩn mật khẩu" else "Hiển thị mật khẩu",
                    tint = MaterialTheme.colorScheme.primary // Màu icon
                )
            }
        }
        )
}
@Preview
@Composable
fun PassWordPreview(
) {
    JITBookTheme(
        darkTheme = isSystemInDarkTheme(), dynamicColor = false
    ) {
        PassWord(
            label = "Enter your password",
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            keyboardType = KeyboardType.Password,
            visualTransformation = VisualTransformation.None
        )
    }

}

