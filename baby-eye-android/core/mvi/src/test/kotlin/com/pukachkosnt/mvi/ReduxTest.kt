package com.pukachkosnt.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ReduxTest {
    data class State(
        val data: Int,
        val errMsg: String? = null
    )

    sealed class Intent {
        object IncrementData : Intent()
        object Loading : Intent()
        object Error : Intent()
        class Success(val data: Int) : Intent()
    }

    sealed class Query {
        object NetworkRequest : Query()
    }

    sealed class News {
        data class ShowToast(val msg: String) : News()
        data class PlayNotificationSound(val soundType: String) : News()
    }

    private val reducer: Reducer<State, Intent> = { state, intent ->
        when (intent) {
            is Intent.IncrementData -> state.copy(data = state.data + 10, errMsg = null)
            is Intent.Loading       -> state.copy(data = 0, errMsg = null)
            is Intent.Error         -> state.copy(data = 0, errMsg = "Error")
            is Intent.Success       -> state.copy(data = intent.data, errMsg = null)
        }
    }

    private val newsReducer: NewsReducer<State, Intent, News> = { _, intent ->
        when (intent) {
            is Intent.Error -> listOf(News.ShowToast("Error"))
            is Intent.Success -> listOf(News.PlayNotificationSound("Success"))
            else -> emptyList()
        }
    }

    private val actor: Actor<State, Query, Intent> = { state, query, output ->
        when (query) {
            is Query.NetworkRequest -> {
                output.send(Intent.Loading)
                delay(1000) // Network call
                if (state.data > 0) output.send(Intent.Success(1000))
                else output.send(Intent.Error)
            }
        }
    }

    @Test
    fun `redux states and news test`() = runTest {
        val store = launchActorBasedRedux(
            initialState = State(data = -5), reducer, actor, newsReducer, scope = this)

        val actualStates = mutableListOf<State>()
        val actualNews = mutableListOf<News>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            store.news.take(count = 2).toList(actualNews)
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            store.state.take(count = 6).toList(actualStates)
        }

        store.queries.send(Query.NetworkRequest)
        delay(2000)
        store.intents.send(Intent.IncrementData)
        store.queries.send(Query.NetworkRequest)
        delay(2000)

        val expectedStates = listOf(
            State(-5, null),
            State(0, null),
            State(0, "Error"),
            State(10, null),
            State(0, null),
            State(1000, null)
        )

        val expectedNews = listOf(
            News.ShowToast("Error"),
            News.PlayNotificationSound("Success")
        )

        assert(expectedStates == actualStates)
        assert(expectedNews == actualNews)

        store.close()
    }
}