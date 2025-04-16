package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.navigation.BookContentType
import kotlinx.coroutines.launch


private data class OtpField(
    val text: String,
    val index: Int,
    val focusRequester: FocusRequester? = null,
    val isFocused: Boolean = false // 👈 thêm dòng này

)

@Composable
fun OtpInputField(
    contentType: BookContentType,
    otp: MutableState<String>,
    count: Int = 5,
    otpBoxModifier: Modifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.background,
        ),
    otpTextType: KeyboardType = KeyboardType.Number,
    textColor: Color = Color.Black,

    ) {
    val scope = rememberCoroutineScope()

    val otpFieldsValue = remember {
        (0 until count).mapIndexed { index, i ->
            mutableStateOf(
                OtpField(
                    text = otp.value.getOrNull(i)?.toString() ?: "",
                    index = index,
                    focusRequester = FocusRequester(),
                )

            )
        }
    }

    //Cập nhật giá trị của từng ô nhập OTP khi giá trị tổng thể của OTP thay đổi, và quản lý focus.
    LaunchedEffect(key1 = otp.value) {
        for (i in otpFieldsValue.indices) {
            otpFieldsValue[i].value = otpFieldsValue[i].value.copy(
                otp.value.getOrNull(i)?.toString() ?: "",
            )
        }

//        Yêu cầu focus vào ô đầu tiên nếu OTP đang trống (ví dụ: khi reset)
        if (otp.value.isBlank()) {
            try {
                otpFieldsValue[0].value.focusRequester?.requestFocus()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Create a row of OTP boxes.

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(count) { index ->
            OtpBox(
                contentType = contentType,
                modifier = otpBoxModifier,
                otpValue = otpFieldsValue[index].value,
                textType = otpTextType,
                textColor = textColor,
                isLastItem = index == count - 1, // Check if this box is the last in the sequence.
                totalBoxCount = count,
                onValueChange = { newValue ->
                    // Handling logic for input changes, including moving focus and updating OTP state.
                    scope.launch {
                        handleOtpInputChange(index, count, newValue, otpFieldsValue, otp)
                    }
                },
                onFocusSet = { focusRequester ->
                    // Save the focus requester for each box to manage focus programmatically.
                    otpFieldsValue[index].value =
                        otpFieldsValue[index].value.copy(focusRequester = focusRequester)
                },
                onNext = {
                    // Attempt to move focus to the next box when the "next" action is triggered.
                    focusNextBox(index, count, otpFieldsValue)
                },
            )
        }
    }
}

@Composable
private fun OtpBox(
    contentType: BookContentType,
    modifier: Modifier,
    otpValue: OtpField,
    textType: KeyboardType = KeyboardType.Number,
    textColor: Color = Color.Black,
    isLastItem: Boolean,
    totalBoxCount: Int,
    onValueChange: (String) -> Unit,
    onFocusSet: (FocusRequester) -> Unit,
    onNext: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequest = otpValue.focusRequester ?: FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.dpToPx().toInt()
    val paddingValue = 5
    val totalBoxSize = when (contentType) {
            BookContentType.LIST_ONLY -> {
            (screenWidth / totalBoxCount) - (paddingValue * totalBoxCount) - 30
        }

        else -> {
            (screenWidth / totalBoxCount) - (paddingValue * totalBoxCount) -250
        }
    }
    val isFocused = otpValue.text.isNotEmpty() // bạn có thể dùng `isFocused` khác nếu có

    // Tuỳ chỉnh màu viền và nền
    val borderColor = if (isFocused) Color(0xFFFF9800) else Color(0xFFE0E0E0)
    val backgroundColor = if (isFocused) Color(0xFFFFF8E1) else Color.White

    Box(
        modifier = modifier
            .size(totalBoxSize.pxToDp())
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .focusRequester(focusRequest)
            .onGloballyPositioned {
                onFocusSet(focusRequest)
            },
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = TextFieldValue(otpValue.text, TextRange(otpValue.text.length)),
            onValueChange = {
                if (it.text != otpValue.text) {
                    onValueChange(it.text)
                }
            },
            textStyle = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
                color = textColor
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = textType,
                imeAction = if (isLastItem) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { onNext() },
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            visualTransformation = getVisualTransformation(textType),
        )
    }
}

private fun focusNextBox(
    index: Int,
    count: Int,
    otpFieldsValues: List<MutableState<OtpField>>
) {
    if (index + 1 < count) {
        // Move focus to the next box if the current one is filled and it's not the last box.
        try {
            otpFieldsValues[index + 1].value.focusRequester?.requestFocus()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun handleOtpInputChange(
    index: Int,
    count: Int,
    newValue: String,
    otpFieldsValues: List<MutableState<OtpField>>,
    otp: MutableState<String>
) {
    // Handle input for the current box.
    if (newValue.length <= 1) {
        // Directly set the new value if it's a single character.
        otpFieldsValues[index].value = otpFieldsValues[index].value.copy(text = newValue)
    } else if (newValue.length == 2) {
        // If length of new value is 2, we can guess the user is typing focusing on current box
        // In this case set the unmatched character only
        otpFieldsValues[index].value =
            otpFieldsValues[index].value.copy(text = newValue.lastOrNull()?.toString() ?: "")
    } else if (newValue.isNotEmpty()) {
        // If pasting multiple characters, distribute them across the boxes starting from the current index.
        newValue.forEachIndexed { i, char ->
            if (index + i < count) {
                otpFieldsValues[index + i].value =
                    otpFieldsValues[index + i].value.copy(text = char.toString())
            }
        }
    }

    // Update the overall OTP state.
    var currentOtp = ""
    otpFieldsValues.forEach {
        currentOtp += it.value.text
    }

    try {
        // Logic to manage focus.
        if (newValue.isEmpty() && index > 0) {
            // If clearing a box and it's not the first box, move focus to the previous box.
            otpFieldsValues.getOrNull(index - 1)?.value?.focusRequester?.requestFocus()
        } else if (index < count - 1 && newValue.isNotEmpty()) {
            // If adding a character and not on the last box, move focus to the next box.
            otpFieldsValues.getOrNull(index + 1)?.value?.focusRequester?.requestFocus()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    otp.value = currentOtp
}


@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
private fun getVisualTransformation(textType: KeyboardType) =
    if (textType == KeyboardType.NumberPassword || textType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None


@Preview
@Composable
fun OtpView_Preivew() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OtpInputField(
                otp = remember { mutableStateOf("") },
                count = 4,
                otpBoxModifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    ),
                textColor = Color.Black,
                contentType = BookContentType.LIST_AND_DETAIL
            )
        }

    }
}
