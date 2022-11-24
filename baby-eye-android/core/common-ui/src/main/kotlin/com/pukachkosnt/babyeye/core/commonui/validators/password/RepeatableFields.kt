package com.pukachkosnt.babyeye.core.commonui.validators.password

/**
 * Marks models that contain repeatable fields that need to be validated
 */
interface RepeatableFields {
    val repeatableFields: List<RepeatableField>
}

data class SimpleRepeatable(
    override val repeatableFields: List<RepeatableField>
) : RepeatableFields {
    constructor(repeatableField: RepeatableField) : this(listOf(repeatableField))
}
