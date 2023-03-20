package com.manishsputnikcorporation.movies

import com.manishsputnikcorporation.movies.domain.usecase.base.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutinesRule : TestRule {

  private val testCoroutineDispatcher = StandardTestDispatcher()

  val testDispatcherProvider =
      object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testCoroutineDispatcher
        override fun io(): CoroutineDispatcher = testCoroutineDispatcher
        override fun main(): CoroutineDispatcher = testCoroutineDispatcher
        override fun unconfined(): CoroutineDispatcher = testCoroutineDispatcher
      }

  override fun apply(base: Statement, description: Description): Statement =
      object : Statement() {
        override fun evaluate() {
          Dispatchers.setMain(testCoroutineDispatcher)
          base.evaluate()
          Dispatchers.resetMain()
        }
      }

  fun runTest(block: suspend TestScope.() -> Unit) = TestScope().runTest(testBody = block)
}
