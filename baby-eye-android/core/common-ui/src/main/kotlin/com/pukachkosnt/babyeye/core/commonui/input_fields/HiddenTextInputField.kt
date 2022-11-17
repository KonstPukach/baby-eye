package com.pukachkosnt.babyeye.core.commonui.input_fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.TextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.utils.compose.Password

@Composable
fun HiddenTextInputField(
    modifier: Modifier = Modifier,
    textInputFieldState: TextInputFieldState,
    onValueChange: (String) -> Unit = { },
    onFocusChange: (Boolean) -> Unit = { },
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = true,
    label: String
) {
    OutlinedTextField(
        value = textInputFieldState.text,
        onValueChange = { newStringValue ->
            onValueChange(newStringValue)
            textInputFieldState.text = newStringValue
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textInputFieldState.isFocused = focusState.isFocused
                onFocusChange(focusState.isFocused)
            },
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions.Password.copy(imeAction = imeAction),
        keyboardActions = keyboardActions,
        visualTransformation = PasswordVisualTransformation(),
        singleLine = singleLine,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    )
}

@Preview
@Composable
private fun HiddenTextInputFieldPreview() {
    HiddenTextInputField(textInputFieldState = TextInputFieldState(), label = "Password")
}
