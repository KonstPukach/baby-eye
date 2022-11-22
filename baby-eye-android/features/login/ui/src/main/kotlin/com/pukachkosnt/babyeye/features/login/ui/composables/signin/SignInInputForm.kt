package com.pukachkosnt.babyeye.features.login.ui.composables.signin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pukachkosnt.babyeye.core.commonui.input_fields.EmailInputField
import com.pukachkosnt.babyeye.core.commonui.input_fields.PasswordInputField
import com.pukachkosnt.babyeye.core.commonui.text_fields.ErrorLabel
import com.pukachkosnt.babyeye.core.commonui.text_fields.Headline
import com.pukachkosnt.babyeye.core.commonui.utils.compose.next
import com.pukachkosnt.babyeye.features.login.ui.R
import com.pukachkosnt.babyeye.features.login.ui.composables.LoginButton
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.models.UiSignInModel
import com.pukachkosnt.babyeye.core.commonui.R as CR

@Composable
internal fun SignInInputForm(
    onSignInButtonClick: (signInUiModel: UiSignInModel) -> Unit,
    goToSignUp: () -> Unit,
    errorText: String? = null
) {
    val focusManager = LocalFocusManager.current
    val spaceBetweenElements = dimensionResource(id = CR.dimen.padding_extra_small)

    Card(
        elevation = dimensionResource(id = CR.dimen.middle_card_elevation),
        shape = RoundedCornerShape(dimensionResource(id = CR.dimen.middle_card_corner_radius)),
        border = BorderStroke(width = 1.dp, color = if (errorText == null) Color.White else Color.Red)
    ) {
        val signInState = rememberSignInState()

        Column(
            modifier = Modifier
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
                emailState = signInState.emailState,
                modifier = Modifier.padding(top = spaceBetweenElements),
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions.next {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            PasswordInputField(
                passwordState = signInState.passwordState,
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }
            )

            LoginButton(
                text = R.string.signin,
                onClick = {
                    if (signInState.validate(forceShowError = true)) {
                        onSignInButtonClick(signInState.uiSignInModel)
                    }
                }
            )

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = goToSignUp
            ) {
                Text(text = stringResource(id = R.string.go_to_signup))
            }

            if (errorText != null) {
                ErrorLabel(text = errorText)
            }
        }
    }
}

@Preview
@Composable
private fun SignInFormPreview() {
    SignInInputForm(
        onSignInButtonClick = { },
        goToSignUp = { }
    )
}
