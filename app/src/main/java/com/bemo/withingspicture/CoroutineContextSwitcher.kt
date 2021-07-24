package com.bemo.withingspicture

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CoroutineContextSwitcher private constructor(
    private val scope: CoroutineScope,
    private val mainContext: CoroutineContext,
    private val backgroundContext: CoroutineContext
) : ThreadManager {

    companion object {
        fun newInstance(): CoroutineContextSwitcher {
            val job = Job()
            val mainContext = Dispatchers.Main + job
            val backgroundContext = Dispatchers.IO + job

            return CoroutineContextSwitcher(
                scope = CoroutineScope(mainContext),
                mainContext = mainContext,
                backgroundContext = backgroundContext
            )
        }
    }

    override fun onMainThread(command: suspend CoroutineScope.() -> Unit) {
        scope.launch(mainContext) {
            command()
        }
    }

    override fun onBackgroundThread(command: suspend CoroutineScope.() -> Unit) {
        scope.launch(backgroundContext) {
            command()
        }
    }
}

interface ThreadManager {
    fun onMainThread(command: suspend CoroutineScope.() -> Unit)
    fun onBackgroundThread(command: suspend CoroutineScope.() -> Unit)
}