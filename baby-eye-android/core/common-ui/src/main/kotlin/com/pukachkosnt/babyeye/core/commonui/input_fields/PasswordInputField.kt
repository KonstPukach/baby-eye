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
import com.pukachkosnt.babyeye.core.commonui.utils.compose.Password

@Composable
fun PasswordInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    errorText: String? = null,
    onValueChange: (String) -> Unit = { },
    onFocusChanged: (Boolean) -> Unit = { },
    label: String = stringResource(id = R.string.password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    imeAction: ImeAction = ImeAction.Default
) {
    ValidatedTextInputField(
        modifier             = modifier,
        value                = value,
        errorText            = errorText,
        onValueChange        = onValueChange,
        onFocusChanged       = onFocusChanged,
        textStyle            = textStyle,
        keyboardOptions      = KeyboardOptions.Password.copy(imeAction = imeAction),
        keyboardActions      = keyboardActions,
        visualTransformation = PasswordVisualTransformation(),
        singleLine           = true,
        label                = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text  = label,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    )
}

// ########################   PREVIEW   ########################

@Composable
@Preview
private fun PasswordPreview() {
    MaterialTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PasswordInputField()
        }
    }
}
