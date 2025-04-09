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
import kotlinx.coroutines.launch


private data class OtpField(
    val text: String,
    val index: Int,
    val focusRequester: FocusRequester? = null,
)

@Composable
fun OtpInputField(
    otp: MutableState<String>,
    count: Int = 5,
    otpBoxModifier: Modifier = Modifier
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        )
        .background(
            color = MaterialTheme.colorScheme.primaryContainer,
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
        repeat(count) {
            index ->
            OtpBox(
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

@Composable
private fun OtpBox(
    modifier: Modifier,
    otpValue: OtpField, // Giá trị hiện tại của ô nhập OTP này.
    textType: KeyboardType = KeyboardType.Number,
    textColor: Color = Color.Black,
    isLastItem: Boolean, // Có phải ô này là ô cuối cùng trong dãy OTP hay không
    totalBoxCount: Int, // Tổng số ô OTP (để tính toán giao diện).
    onValueChange: (String) -> Unit, // Hàm gọi lại khi giá trị ô nhập thay đổi.
    onFocusSet: (FocusRequester) -> Unit, // Hàm gọi lại để thiết lập FocusRequester.
    onNext: () -> Unit, // Hàm gọi lại khi cần chuyển focus sang ô kế tiếp.
) {
    val focusManager = LocalFocusManager.current
    val focusRequest = otpValue.focusRequester ?: FocusRequester()
    val keyboardController = LocalSoftwareKeyboardController.current

//    Tính toán kích thước của ô dựa trên chiều rộng màn hình và tổng số ô.
//    Nếu bạn đang sử dụng mã này trong Kotlin Multiplatform Mobile,
//    bạn có thể sử dụng LocalWindowInfo.current.containerSize.width để lấy chiều rộng màn hình.
//    Nếu bạn đang phát triển ứng dụng Android, bạn có thể lấy chiều rộng màn hình thông qua
//    LocalConfiguration.current.screenWidthDp.

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp.dpToPx().toInt()
    val paddingValue = 5
    val totalBoxSize = (screenWidth / totalBoxCount) - paddingValue * totalBoxCount

    Box(
        modifier = modifier
            .size(totalBoxSize.pxToDp()),
        contentAlignment = Alignment.Center,
        ) {
        BasicTextField(
            value = TextFieldValue(otpValue.text, TextRange(maxOf(0, otpValue.text.length))),
            onValueChange = {

                if(!it.text.equals(otpValue)){
                    onValueChange(it.text)
                }
            },

            modifier = Modifier
                .testTag("otpBox${otpValue.index}")
                .focusRequester(focusRequest)
                .onGloballyPositioned {
                    onFocusSet(focusRequest)
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
                onNext = {
                    onNext()
                },
                onDone = {
                    // Hide keyboard and clear focus when done.
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            visualTransformation = getVisualTransformation(textType),
        )
    }
}

@Preview
@Composable
fun OtpView_Preivew() {
    MaterialTheme {
        val otpValue = remember {
            mutableStateOf("124")
        }
        Column(
            modifier = Modifier.padding(40.pxToDp()),
            verticalArrangement = Arrangement.spacedBy(20.pxToDp())
        ) {
            OtpInputField(
                otp = otpValue,
                count = 4,
                otpBoxModifier = Modifier
                    .border(1.pxToDp(), Color.Black)
                    .background(Color.White),
                otpTextType = KeyboardType.Number
            )

            OtpInputField(
                otp = otpValue,
                count = 4,
                otpTextType = KeyboardType.NumberPassword,
                otpBoxModifier = Modifier
                    .border(3.pxToDp(), Color.Gray)
                    .background(Color.White)
            )

            OtpInputField(
                otp = otpValue,
                count = 5,
                textColor = MaterialTheme.colorScheme.onBackground,
                otpBoxModifier = Modifier
                    .border(7.pxToDp(), Color(0xFF277F51), shape = RoundedCornerShape(12.pxToDp()))
            )

            OtpInputField(
                otp = otpValue,
                count = 5,
                otpBoxModifier = Modifier
                    .border(
                        border = BorderStroke(6.pxToDp(), Color.DarkGray),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}