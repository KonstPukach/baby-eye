package com.pukachkosnt.babyeye.core.commonui.input_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.IValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.text_fields.ErrorLabel
import com.pukachkosnt.babyeye.core.commonui.utils.res.getStringOrEmpty
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel

/**
 * Based on [OutlinedTextField]
 */
@Composable
fun <T : Any> ValidatedTextInputField(
    modifier: Modifier = Modifier,
    textInputFieldState: IValidatedTextInputFieldState<T>,
    onValueChange: (String) -> Unit = { },
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    label: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = textInputFieldState.text,
            onValueChange = { newStringValue ->
                onValueChange(newStringValue)
                textInputFieldState.text = newStringValue
                textInputFieldState.validate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    textInputFieldState.isFocused = focusState.isFocused
                    if (!focusState.isFocused) {
                        textInputFieldState.validate()
                    }
                },
            textStyle = textStyle,
            label = label,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            singleLine = singleLine
        )

        val validModel = textInputFieldState.validModel.value
        if (validModel is ValidModel.Invalid && validModel.showError) {
            ErrorLabel(
                text = validModel.errorText.getStringOrEmpty(LocalContext.current),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.input_field_error_padding))
            )
        }
    }
}
