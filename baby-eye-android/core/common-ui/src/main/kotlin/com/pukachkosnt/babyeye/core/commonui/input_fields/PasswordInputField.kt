package com.pukachkosnt.babyeye.core.commonui.input_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.CommonTextInputFieldShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.ShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.IValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.utils.compose.Password
import com.pukachkosnt.babyeye.core.commonui.validators.EmptyInputFieldValidator
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.core.commonui.validators.password.PasswordInputFieldValidator

@Composable
fun <T : Any> PasswordInputField(
    modifier: Modifier = Modifier,
    passwordState: IValidatedTextInputFieldState<T>,
    onValueChange: (String) -> Unit = { },
    label: String = stringResource(id = R.string.password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    imeAction: ImeAction = ImeAction.Default
) {
    ValidatedTextInputField(
        modifier = modifier,
        textInputFieldState = passwordState,
        onValueChange = onValueChange,
        textStyle = textStyle,
        keyboardOptions = KeyboardOptions.Password.copy(imeAction = imeAction),
        keyboardActions = keyboardActions,
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
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

fun ValidatedTextInputFieldState.Companion.password() = ValidatedTextInputFieldState(
    ValidationPipeline.from(
        EmptyInputFieldValidator() to ShowErrorStrategy { it.isFocusedAtLeastOneTime },
        PasswordInputFieldValidator() to CommonTextInputFieldShowErrorStrategy,
    )
)

// ########################   PREVIEW   ########################

@Composable
@Preview
private fun PasswordPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PasswordInputField(
                passwordState = ValidatedTextInputFieldState.password()
            )
            PasswordInputField(
                passwordState = ValidatedTextInputFieldState.password()
            )
        }
    }
}
