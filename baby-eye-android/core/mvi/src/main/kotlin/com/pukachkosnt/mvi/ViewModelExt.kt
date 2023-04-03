package com.pukachkosnt.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

// In most cases you will have to use Redux inside a ViewModel

fun <State : Any, News : Any, Intent : Any, Query : Any> ViewModel.launchActorBasedRedux(
    initialState: State,
    reducer: Reducer<State, Intent>,
    actor: Actor<State, Query, Intent>,
    newsReducer: NewsReducer<State, Intent, News>? = null,
    savedState: SavedStateHandle? = null
): Store<State, News, Intent, Query> = launchActorBasedRedux(
    initialState,
    reducer,
    actor,
    newsReducer,
    if (savedState != null) savedState to StoreKey else null,
    viewModelScope
)

fun <State : Any, News : Any, Intent : Any, Query : Any> launchActorBasedRedux(
    initialState: State,
    reducer: Reducer<State, Intent>,
    actor: Actor<State, Query, Intent>,
    newsReducer: NewsReducer<State, Intent, News>? = null,
    scope: CoroutineScope
) : Store<State, News, Intent, Query> = launchActorBasedRedux(
    initialState = initialState,
    reducer = reducer,
    actor = actor,
    newsReducer = newsReducer,
    savedStateToKey = null,
    scope = scope
)

internal fun <State : Any, News : Any, Intent : Any, Query : Any> launchActorBasedRedux(
    initialState: State,
    reducer: Reducer<State, Intent>,
    actor: Actor<State, Query, Intent>,
    newsReducer: NewsReducer<State, Intent, News>? = null,
    savedStateToKey: Pair<SavedStateHandle, String>? = null,
    scope: CoroutineScope
) : Store<State, News, Intent, Query> {
    val (savedState, stateKey) = savedStateToKey ?: (null to null)

    val restoredState =
        if (savedState != null && stateKey != null) savedState[stateKey] ?: initialState
        else initialState

    val stateFlow     = MutableStateFlow(restoredState)
    val newsFlow      = MutableSharedFlow<News>()
    val intentChannel = Channel<Intent>(Channel.UNLIMITED)
    val queryChannel  = Channel<Query>(Channel.UNLIMITED)

    scope.launch {
        for (query in queryChannel) {
            actor(stateFlow.value, query, intentChannel)
        }
    }

    scope.launch {
        for (intent in intentChannel) {
            val newState = reducer(stateFlow.value, intent)
            val news = newsReducer?.invoke(stateFlow.value, intent)

            if (savedState != null && stateKey != null) {
                savedState[stateKey] = newState
            }

            stateFlow.value = newState

            if (!news.isNullOrEmpty()) {
                newsFlow.emitAll(news.asFlow())
            }
        }
    }

    return Store(stateFlow, newsFlow, intentChannel, queryChannel)
}

private val ViewModel.StoreKey: String get() = "${this::class.java}-state"
