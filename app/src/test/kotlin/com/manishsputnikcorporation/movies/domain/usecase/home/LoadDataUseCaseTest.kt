package com.manishsputnikcorporation.movies.domain.usecase.home

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MoviesRepository
import com.manishsputnikcorporation.movies.domain.repository.TVsRepository
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.enums.Type.*
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.fakeDomainError
import io.mockk.*
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class LoadDataUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var moviesRepository: MoviesRepository
    private lateinit var tvsRepository: TVsRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: LoadDataUseCase

    @Before
    fun setUp() {
        moviesRepository = mockk(relaxed = true)
        tvsRepository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            LoadDataUseCase(
                moviesRepository = moviesRepository,
                tVsRepository = tvsRepository,
                globalErrorMapper = globalErrorMapper,
                dispatcherProvider = coroutineTestRule.testDispatcherProvider,
            )
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN use case is successfully called with a MOVIE type as input THEN returns a success response`() = runTest {
        every { moviesRepository.refreshPopularMovies() } returns eitherSuccess(data = Unit)

        with(receiver = safeFlowUseCaseDelegate) { useCaseToTest.safePrepare(input = MOVIE).collect { assertTrue(it.isSuccess) } }

        coVerify(exactly = 1) { moviesRepository.refreshPopularMovies() }
        coVerify(exactly = 0) { tvsRepository.refreshPopularTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }

    @Test
    fun `WHEN use case is unsuccessfully called with a MOVIE type as input THEN returns a failure response`() = runTest {
        every { moviesRepository.refreshPopularMovies() } returns fakeDomainError

        with(receiver = safeFlowUseCaseDelegate) { useCaseToTest.safePrepare(input = MOVIE).collect { assertTrue(it.isFailure) } }

        coVerify(exactly = 1) { moviesRepository.refreshPopularMovies() }
        coVerify(exactly = 0) { tvsRepository.refreshPopularTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }

    @Test
    fun `WHEN use case is successfully called with a TV type as input THEN returns a success response`() = runTest {
        every { tvsRepository.refreshPopularTVs() } returns eitherSuccess(Unit)

        with(receiver = safeFlowUseCaseDelegate) { useCaseToTest.safePrepare(input = TV).collect { assertTrue(it.isSuccess) } }

        coVerify(exactly = 0) { moviesRepository.refreshPopularMovies() }
        coVerify(exactly = 1) { tvsRepository.refreshPopularTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }

    @Test
    fun `WHEN use case is unsuccessfully called with a TV type as input THEN returns a failure response`() = runTest {
        every { tvsRepository.refreshPopularTVs() } returns fakeDomainError

        with(receiver = safeFlowUseCaseDelegate) { useCaseToTest.safePrepare(input = TV).collect { assertTrue(it.isFailure) } }

        coVerify(exactly = 0) { moviesRepository.refreshPopularMovies() }
        coVerify(exactly = 1) { tvsRepository.refreshPopularTVs() }
        confirmVerified(moviesRepository, tvsRepository)
    }
    // endregion
}
