package com.pukachkosnt.domain

/**
 * Maker class. Extend this class if you want to process some specific error
 */
open class Error(errorText: String?) : Exception(errorText) {
    constructor() : this(errorText = null)
}

/**
 * Occurs when a device doesn't have any connection to the network
 */
class NetworkConnectionError(errorText: String? = null) : Error(errorText)

/**
 * Network timeout error. Occurs when request waiting time runs out of the limit
 */
class NetworkTimeoutError(errorText: String? = null) : Error(errorText)

/**
 * Server error - 500 status code
 */
class ServerError(errorText: String? = null) : Error(errorText)

/**
 * Unknown error. Usually it is used in cases of unexpected local or network errors
 */
class UnknownError(errorText: String? = null) : Error(errorText)

/**
 * Token store error
 */
class TokenStoreError : Error()
