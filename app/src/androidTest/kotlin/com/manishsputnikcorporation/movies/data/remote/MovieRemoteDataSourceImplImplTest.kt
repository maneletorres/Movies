package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.data.remote.base.RemoteDataSourceImplBaseTest
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseMovie
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class MovieRemoteDataSourceImplImplTest : RemoteDataSourceImplBaseTest() {

    // region Setup
    private lateinit var remoteDataSource: MovieRemoteDataSource

    @Before
    override fun setUp() {
        super.setUp()
        remoteDataSource = MovieRemoteDataSourceImpl(serviceExecutor = serviceExecutor, service = service)
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getPopularMovies is called successfully THEN returns a MoviesDto`() = runTest {
        configureServerDispatcher(jsonFile = "popular_movies.json")

        val response = remoteDataSource.getPopularMovies()

        assertTrue(response.isSuccess)
        response.mapSuccess { moviesDto -> assertNotNull(moviesDto) }
    }

    @Test
    fun `WHEN getMovie is called successfully THEN returns a MovieDetailDto`() = runTest {
        configureServerDispatcher(jsonFile = "movie_detail.json")

        val response = remoteDataSource.getMovie(id = fakeDatabaseMovie.id)

        assertTrue(response.isSuccess)
        response.mapSuccess { movieDetailDto -> assertNotNull(movieDetailDto) }
    }
    // endregion
}
