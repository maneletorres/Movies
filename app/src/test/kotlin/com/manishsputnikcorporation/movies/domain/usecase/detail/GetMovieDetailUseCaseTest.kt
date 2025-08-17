package com.manishsputnikcorporation.movies.domain.usecase.detail

import com.manishsputnikcorporation.movies.MainCoroutinesRule
import com.manishsputnikcorporation.movies.domain.error.FlowUseCaseDelegate
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorManager
import com.manishsputnikcorporation.movies.domain.error.GlobalErrorMapper
import com.manishsputnikcorporation.movies.domain.repository.MovieDetailRepository
import com.manishsputnikcorporation.movies.utils.eitherSuccess
import com.manishsputnikcorporation.movies.utils.extensions.isFailure
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import com.manishsputnikcorporation.movies.utils.fakeDomainError
import io.mockk.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetMovieDetailUseCaseTest {

    // region Setup
    @get:Rule val coroutineTestRule = MainCoroutinesRule()

    private lateinit var repository: MovieDetailRepository
    private lateinit var globalErrorMapper: GlobalErrorMapper
    private lateinit var globalErrorManager: GlobalErrorManager
    private lateinit var safeFlowUseCaseDelegate: FlowUseCaseDelegate
    private lateinit var useCaseToTest: GetMovieDetailUseCase

    private val fakeMovieDetailModelId = fakeMovieDetailModel.id

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        globalErrorMapper = mockk(relaxed = true)
        globalErrorManager = mockk(relaxed = true)
        safeFlowUseCaseDelegate = FlowUseCaseDelegate.Default(globalErrorManager)
        useCaseToTest =
            GetMovieDetailUseCase(
                movieDetailRepository = repository,
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
    fun `WHEN use case is called successfully THEN returns a success response with the corresponding movie detail`() = runTest {
        every { repository.getMovieDetail(fakeMovieDetailModelId) } returns eitherSuccess(data = fakeMovieDetailModel)

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = fakeMovieDetailModelId).collect { response ->
                assert(response.isSuccess)
                response.mapSuccess { movieDetailModel ->
                    assertNotNull(movieDetailModel)
                    assertEquals(fakeMovieDetailModel, movieDetailModel)
                }
            }
        }

        coVerify(exactly = 1) { repository.getMovieDetail(fakeMovieDetailModelId) }
        confirmVerified(repository)
    }

    @Test
    fun `WHEN use case is called successfully THEN returns a failure response`() = runTest {
        every { repository.getMovieDetail(fakeMovieDetailModelId) } returns fakeDomainError

        with(receiver = safeFlowUseCaseDelegate) {
            useCaseToTest.safePrepare(input = fakeMovieDetailModelId).collect { assert(it.isFailure) }
        }

        coVerify(exactly = 1) { repository.getMovieDetail(fakeMovieDetailModelId) }
        confirmVerified(repository)
    }
    // endregion
}
