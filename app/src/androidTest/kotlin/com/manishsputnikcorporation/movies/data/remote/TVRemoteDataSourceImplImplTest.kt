package com.manishsputnikcorporation.movies.data.remote

import com.manishsputnikcorporation.movies.data.remote.base.RemoteDataSourceImplBaseTest
import com.manishsputnikcorporation.movies.utils.extensions.isSuccess
import com.manishsputnikcorporation.movies.utils.extensions.mapSuccess
import com.manishsputnikcorporation.movies.utils.fake.fakeDatabaseTV
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TVRemoteDataSourceImplImplTest : RemoteDataSourceImplBaseTest() {

    // region Setup
    private lateinit var remoteDataSource: TVRemoteDataSource

    @Before
    override fun setUp() {
        super.setUp()
        remoteDataSource = TVRemoteDataSourceImpl(serviceExecutor = serviceExecutor, service = service, context = appContext)
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN getPopularTVs is called successfully THEN returns a TVsDto`() = runTest {
        configureServerDispatcher(jsonFile = "popular_tvs.json")

        val response = remoteDataSource.getPopularTVs()

        assertTrue(response.isSuccess)
        response.mapSuccess { tvsDto -> assertNotNull(tvsDto) }
    }

    @Test
    fun `WHEN getTV is called successfully THEN returns a TVDetailDto`() = runTest {
        configureServerDispatcher(jsonFile = "movie_detail.json")

        val response = remoteDataSource.getTV(id = fakeDatabaseTV.id)

        assertTrue(response.isSuccess)
        response.mapSuccess { tvDetailDto -> assertNotNull(tvDetailDto) }
    }
    // endregion
}
