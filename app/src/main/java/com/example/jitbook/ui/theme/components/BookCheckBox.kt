package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.ui.theme.JITBookTheme

@Composable
fun BookCheckBox(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit, // ✅ thêm callback để thay đổi trạng thái
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange, // ✅ gọi callback khi nhấn
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = Color.White
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Preview
@Composable
fun BookCheckBoxPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ){
       BookCheckBox(
            label = "Remember me",
            isChecked = true,
            onCheckedChange = {}
       )
    }

}