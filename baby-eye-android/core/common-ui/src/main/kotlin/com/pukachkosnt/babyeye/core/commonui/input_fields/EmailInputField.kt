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
import com.pukachkosnt.babyeye.core.commonui.utils.compose.Email

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = { },
    onFocusChanged: (Boolean) -> Unit = { },
    errorText: String? = null,
    labelText: String = stringResource(id = R.string.email),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Default
) {
    ValidatedTextInputField(
        modifier        = modifier,
        value           = value,
        errorText       = errorText,
        onValueChange   = onValueChange,
        onFocusChanged  = onFocusChanged,
        textStyle       = MaterialTheme.typography.body2,
        keyboardOptions = KeyboardOptions.Email.copy(imeAction = imeAction),
        keyboardActions = keyboardActions,
        singleLine      = true,
        label           = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text  = labelText,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    )
}

// ########################   PREVIEW   ########################

@Preview
@Composable
private fun EmailPreview() {
    MaterialTheme {
        Column {
            EmailInputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}
