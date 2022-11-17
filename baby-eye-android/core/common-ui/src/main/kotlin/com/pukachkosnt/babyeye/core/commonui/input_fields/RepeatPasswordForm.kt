package com.pukachkosnt.babyeye.core.commonui.input_fields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.RepeatPasswordShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.RepeatPasswordFormState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.rememberRepeatPasswordFormState
import com.pukachkosnt.babyeye.core.commonui.text_fields.ErrorLabel
import com.pukachkosnt.babyeye.core.commonui.utils.compose.next
import com.pukachkosnt.babyeye.core.commonui.utils.res.getStringOrEmpty
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatPasswordValidator

/**
 * Defines the form where the user inputs the password and repeats the password
 */
@Composable
fun RepeatPasswordInputForm(
    state: RepeatPasswordFormState,
    modifier: Modifier = Modifier,
    passwordModifier: Modifier = Modifier,
    repeatPasswordModifier: Modifier = Modifier
        .padding(top = dimensionResource(id = R.dimen.padding_extra_small)),
    keyboardActionsOnRepeatPassword: KeyboardActions = KeyboardActions.Default
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        PasswordInputField(
            modifier = passwordModifier,
            passwordState = state.passwordState,
            onValueChange = state::onPasswordValueChanged,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions.next {
                focusManager.moveFocus(FocusDirection.Down)
            }
        )

        HiddenTextInputField(
            modifier = repeatPasswordModifier,
            textInputFieldState = state.repeatPasswordState,
            label = stringResource(id = R.string.repeat_password),
            onValueChange = state::onRepeatPasswordValueChanged,
            onFocusChange = { state.validate() },
            keyboardActions = keyboardActionsOnRepeatPassword
        )

        val validModel = state.validModel.value
        if (validModel is ValidModel.Invalid && validModel.showError) {
            ErrorLabel(
                text = validModel.errorText.getStringOrEmpty(LocalContext.current),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.input_field_error_padding))
            )
        }
    }
}

@Preview
@Composable
private fun RepeatPasswordFormPreview() {
    val repeatPasswordState = rememberRepeatPasswordFormState(
        validationPipeline = ValidationPipeline.from(
            RepeatPasswordValidator() to RepeatPasswordShowErrorStrategy
        )
    )
    RepeatPasswordInputForm(state = repeatPasswordState)
}
