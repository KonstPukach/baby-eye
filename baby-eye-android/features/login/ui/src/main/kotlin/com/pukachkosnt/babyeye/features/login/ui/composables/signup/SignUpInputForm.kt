package com.pukachkosnt.babyeye.features.login.ui.composables.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pukachkosnt.babyeye.core.commonui.input_fields.EmailInputField
import com.pukachkosnt.babyeye.core.commonui.input_fields.PasswordInputField
import com.pukachkosnt.babyeye.core.commonui.text_fields.ErrorLabel
import com.pukachkosnt.babyeye.core.commonui.text_fields.Headline
import com.pukachkosnt.babyeye.core.commonui.utils.compose.next
import com.pukachkosnt.babyeye.core.commonui.utils.compose.string
import com.pukachkosnt.babyeye.features.login.ui.R
import com.pukachkosnt.babyeye.features.login.ui.composables.LoginButton
import com.pukachkosnt.babyeye.features.login.ui.mvi.textIfToast
import com.pukachkosnt.babyeye.features.login.ui.mvi.textUnlessToast
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel
import com.pukachkosnt.babyeye.core.commonui.R as CR

@Composable
internal fun SignUpInputForm(
    goToSignIn: () -> Unit,
    signUpViewModel: SignUpViewModel,
    showLoadingScreen: (Boolean) -> Unit
) {
    val state by signUpViewModel.state.collectAsState()
    val showToastMsg by rememberUpdatedState(::showToast)

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val spaceBetweenElements = dimensionResource(id = CR.dimen.padding_extra_small)

    showLoadingScreen(state.showLoadingScreen)

    if (state.showLoadingScreen) {
        focusManager.clearFocus(force = true)
    }

    state.responseError?.textIfToast?.string()?.let { globalErrorText ->
        LaunchedEffect(state.responseError) {
            showToastMsg(context, globalErrorText)
        }
    }

    Card(
        modifier = Modifier.wrapContentSize(),
        elevation = dimensionResource(id = CR.dimen.middle_card_elevation),
        shape = RoundedCornerShape(dimensionResource(id = CR.dimen.middle_card_corner_radius)),
        border = BorderStroke(
            width = 1.dp,
            color = if (state.responseError == null) MaterialTheme.colors.background
                    else MaterialTheme.colors.error
        )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .wrapContentHeight()
                .padding(
                    horizontal = dimensionResource(id = CR.dimen.padding_small),
                    vertical = dimensionResource(id = CR.dimen.padding_medium)
                )
        ) {
            var emailFieldUnfocusCounter by remember { mutableStateOf(0) }
            var passwordFieldUnfocusCounter by remember { mutableStateOf(0) }
            var repPasswordFieldUnfocusCounter by remember { mutableStateOf(0) }

            Headline(
                text = R.string.signup,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            EmailInputField(
                modifier = Modifier.padding(top = spaceBetweenElements),
                value = state.email.value,
                onValueChange = signUpViewModel::onEmailFieldChanged,
                onFocusChanged = { isFocused ->
                    if (!isFocused && ++emailFieldUnfocusCounter > 1) {
                        signUpViewModel.onEmailFieldVisited()
                    }
                },
                errorText = state.email.validationResult.errorText?.string()?.takeIf {
                    state.email.showError
                },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions.next {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            PasswordInputField(
                value = state.password.value,
                onValueChange = signUpViewModel::onPasswordFieldChanged,
                onFocusChanged = { isFocused ->
                    if (!isFocused && ++passwordFieldUnfocusCounter > 1) {
                        signUpViewModel.onPasswordFieldVisited()
                    }
                },
                errorText = state.password.validationResult.errorText?.string()?.takeIf {
                    state.password.showError
                },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions.next {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            PasswordInputField(
                value = state.repeatPassword.value,
                onValueChange = signUpViewModel::onRepeatPasswordFieldChanged,
                onFocusChanged = { isFocused ->
                    if (!isFocused && ++repPasswordFieldUnfocusCounter > 1) {
                        signUpViewModel.onRepeatPasswordFieldVisited()
                    }
                },
                errorText = state.repeatPassword.validationResult.errorText?.string()?.takeIf {
                    state.repeatPassword.showError
                },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions.next {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )

            LoginButton(
                text = R.string.signup,
                onClick = signUpViewModel::signUp
            )

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = goToSignIn
            ) {
                Text(text = stringResource(id = R.string.go_to_signin))
            }

            state.responseError?.textUnlessToast?.let { errorText ->
                ErrorLabel(text = errorText.string())
            }
        }
    }
}

private fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
