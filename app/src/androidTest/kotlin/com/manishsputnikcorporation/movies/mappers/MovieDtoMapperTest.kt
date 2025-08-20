package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.domain.model.MovieModel
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.network.response.movie.MovieDto
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModel
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class MovieDtoMapperTest {

    // region Setup
    @Inject lateinit var mapper: MovieDtoMapper

    @Before
    fun setup() {
        mapper = MovieDtoMapper()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN mapToDomainModel is called on a MovieDto THEN returns the corresponding MovieModel`() {
        val result: MovieModel = mapper.mapToDomainModel(dto = fakeMovieDto)

        assert(result.isContentEquals(movieDto = fakeMovieDto))
    }

    @Test
    fun `WHEN mapToDomainModel is called on a MovieModel THEN returns the corresponding MovieDto`() {
        val result: MovieDto = mapper.mapFromDomainModel(domainModel = fakeMovieModel)

        assert(result.isContentEquals(movieModel = fakeMovieModel))
    }
    // endregion
}
