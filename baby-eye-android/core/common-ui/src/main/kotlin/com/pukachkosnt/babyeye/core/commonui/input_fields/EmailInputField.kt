package com.pukachkosnt.babyeye.core.commonui.input_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.CommonTextInputFieldShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.ShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.utils.compose.Email
import com.pukachkosnt.babyeye.core.commonui.validators.EmailInputFieldValidator
import com.pukachkosnt.babyeye.core.commonui.validators.EmptyInputFieldValidator
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline

@Composable
fun EmailInputField(
    emailState: ValidatedTextInputFieldState,
    modifier: Modifier = Modifier,
    labelText: String = stringResource(id = R.string.email),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (String) -> Unit = { },
) {
    ValidatedTextInputField(
        textInputFieldState = emailState,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = MaterialTheme.typography.body2,
        keyboardOptions = KeyboardOptions.Email.copy(imeAction = imeAction),
        keyboardActions = keyboardActions,
        singleLine = true,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text  = labelText,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    )
}

fun ValidatedTextInputFieldState.Companion.email()
    = ValidatedTextInputFieldState(ValidationPipeline.from(
    EmptyInputFieldValidator() to ShowErrorStrategy { it.isFocusedAtLeastOneTime },
    EmailInputFieldValidator() to CommonTextInputFieldShowErrorStrategy
))

// ########################   PREVIEW   ########################

@Preview
@Composable
private fun EmailPreview() {
    MaterialTheme {
        Column {
            EmailInputField(
                emailState = ValidatedTextInputFieldState.email()
                    .apply {
                        text = "example@mail.com"
                    },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            EmailInputField(
                emailState = ValidatedTextInputFieldState.email(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}
