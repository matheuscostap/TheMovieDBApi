package com.costa.matheus.filmesapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestRule
import kotlinx.coroutines.test.*
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class CoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {

            //o main dispatcher vai utilizar o dispatcher de testes
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) = testCoroutineScope.runBlockingTest{ block() }
}