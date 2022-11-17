package com.pukachkosnt.babyeye.core.commonui.input_fields.states

import androidx.compose.runtime.State
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline

/**
 * State that need to be validated
 */
interface ValidatedState<T : Any> {
    val validationPipeline: ValidationPipeline<T, *>
    val validModel: State<ValidModel>
    fun validate(forceShowError: Boolean = false): Boolean
}
