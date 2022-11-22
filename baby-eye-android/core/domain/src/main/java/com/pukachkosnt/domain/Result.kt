package com.pukachkosnt.domain

sealed class Result<T : Any, E : Error> {
    val isFailure: Boolean get() = this is Failure
    val isSuccess: Boolean get() = this is Success

    inline fun onSuccess(action: (data: T) -> Unit): Result<T, E> {
        (this as? Success)?.let { success -> action(success.data) }
        return this
    }

    inline fun onFailure(action: (error: E) -> Unit): Result<T, E> {
        (this as? Failure)?.let { failure -> action(failure.error) }
        return this
    }

    inline fun <NewT : Any> mapSuccess(mapper: (T) -> Result<NewT, E>): Result<NewT, E> {
        return if (this is Success) {
            mapper(data)
        } else {
            Failure((this as Failure).error)
        }
    }
}

class Success<T : Any, E : Error>(val data: T) : Result<T, E>()
class Failure<T : Any, E : Error>(val error: E) : Result<T, E>()
