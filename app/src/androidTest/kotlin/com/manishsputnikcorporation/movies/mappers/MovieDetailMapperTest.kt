package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.domain.model.MovieDetailModel
import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.network.response.movie.MovieDetailDto
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MovieDetailMapperTest {

    // region Setup
    @Inject lateinit var mapper: MovieDetailDtoMapper

    @Before
    fun setup() {
        mapper = MovieDetailDtoMapper()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN mapToDomainModel is called on a MovieDetailDto THEN returns the corresponding MovieDetailModel`() {
        val result: MovieDetailModel = mapper.mapToDomainModel(dto = fakeMovieDetailDto)

        assert(result.isContentEquals(movieDetailDto = fakeMovieDetailDto))
    }

    @Test
    fun `WHEN mapToDomainModel is called on a MovieModel THEN returns the corresponding MovieDetailDto`() {
        val result: MovieDetailDto = mapper.mapFromDomainModel(domainModel = fakeMovieDetailModel)

        assert(result.isContentEquals(movieModel = fakeMovieDetailModel))
    }
    // endregion
}
