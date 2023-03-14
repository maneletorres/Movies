package com.manishsputnikcorporation.movies.domain.usecase.base.flow

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

internal class UseCaseExtensionsTest {

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `prepare a FlowUseCase`() = runTest {
    val fakeString = "fake-String"
    val flow: FlowUseCase<Unit, String> =
        object : FlowUseCase<Unit, String>(mockk { every { io() } returns Dispatchers.IO }) {

          override fun prepareFlow(input: Unit): Flow<String> = flow { emit(fakeString) }
        }

    val prepared = flow.prepare()
    assertNotNull(prepared)
    assertEquals(prepared.first(), fakeString)
  }
}
