package com.pukachkosnt.babyeye.features.login.ui.composables.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pukachkosnt.babyeye.core.commonui.input_fields.EmailInputField
import com.pukachkosnt.babyeye.core.commonui.input_fields.RepeatPasswordInputForm
import com.pukachkosnt.babyeye.core.commonui.text_fields.ErrorLabel
import com.pukachkosnt.babyeye.core.commonui.text_fields.Headline
import com.pukachkosnt.babyeye.core.commonui.utils.compose.next
import com.pukachkosnt.babyeye.core.commonui.utils.res.getStringOrEmpty
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.features.login.ui.R
import com.pukachkosnt.babyeye.features.login.ui.composables.LoginButton
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.models.UiSignUpModel
import com.pukachkosnt.babyeye.core.commonui.R as CR

@Composable
internal fun SignUpInputForm(
    onSignUpButtonClick: (signUpUiModel: UiSignUpModel) -> Unit,
    goToSignIn: () -> Unit,
    errorText: String? = null
) {
    val focusManager = LocalFocusManager.current
    val spaceBetweenElements = dimensionResource(id = CR.dimen.padding_extra_small)

    Card(
        modifier = Modifier.wrapContentSize(),
        elevation = dimensionResource(id = CR.dimen.middle_card_elevation),
        shape = RoundedCornerShape(dimensionResource(id = CR.dimen.middle_card_corner_radius)),
        border = BorderStroke(
            width = 1.dp,
            color = if (errorText == null) MaterialTheme.colors.background else MaterialTheme.colors.error
        )
    ) {
        val signUpState = rememberSignUpState()

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .wrapContentHeight()
                .padding(
                    horizontal = dimensionResource(id = CR.dimen.padding_small),
                    vertical = dimensionResource(id = CR.dimen.padding_medium)
                )
        ) {
            Headline(
                text = R.string.signup,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            EmailInputField(
                emailState = signUpState.emailState,
                modifier = Modifier.padding(top = spaceBetweenElements),
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions.next {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            RepeatPasswordInputForm(
                state = signUpState.repeatPasswordFormState,
                modifier = Modifier.padding(top = spaceBetweenElements),
                keyboardActionsOnRepeatPassword = KeyboardActions { focusManager.clearFocus() }
            )

            LoginButton(
                text = R.string.signup,
                onClick = {
                    if (signUpState.validate(forceShowError = true)) {
                        onSignUpButtonClick(signUpState.uiSignUpModel)
                    }
                }
            )

            val validModel = signUpState.validModel.value
            if (validModel is ValidModel.Invalid && validModel.showError) {
                ErrorLabel(text = validModel.errorText.getStringOrEmpty(LocalContext.current))
            }

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = goToSignIn
            ) {
                Text(text = stringResource(id = R.string.go_to_signin))
            }

            if (errorText != null) {
                ErrorLabel(text = errorText)
            }
        }
    }
}

@Preview
@Composable
private fun SignUpFormPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            SignUpInputForm(
                onSignUpButtonClick = { },
                goToSignIn = { }
            )
        }
    }
}
