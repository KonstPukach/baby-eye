package com.pukachkosnt.mvi

import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Central part of MVI implementation.
 *
 * @param state describes the UI state in current time. **Note:** must be immutable!;
 * @param intents is an event which [Reducer] receives;
 * @param queries is an event which [Actor] receives;
 */
class Store<State : Any, News : Any, Intent : Any, Query : Any> internal constructor(
    val state: StateFlow<State>,
    val news: SharedFlow<News>,
    val intents: SendChannel<Intent>,
    val queries: SendChannel<Query>
) {
    fun close() {
        intents.close()
        queries.close()
    }
}
