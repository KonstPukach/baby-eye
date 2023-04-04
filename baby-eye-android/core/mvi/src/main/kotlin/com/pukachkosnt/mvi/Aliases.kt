package com.pukachkosnt.mvi

import kotlinx.coroutines.channels.SendChannel

/**
 * According to the basic Redux pattern it is a stateless function, that returns a new state
 * based on the given intent and the previous state.
 */
typealias Reducer<State, Intent> = (state: State, intent: Intent) -> State

/**
 * Returns one-time events called **News**
 */
typealias NewsReducer<State, Intent, News> = (state: State, intent: Intent) -> List<News>

/**
 * Used for async tasks.
 * Example: basic network call.
 *
 * - state - previous state;
 * - query - in terms of actor model it is a message. Based on the query [Actor] decides
 * what kind of work to do;
 * - output - reducer's [SendChannel], that receives actor messages
 */
typealias Actor<State, Query, Intent> =
        suspend (state: State, query: Query, output: SendChannel<Intent>) -> Unit
