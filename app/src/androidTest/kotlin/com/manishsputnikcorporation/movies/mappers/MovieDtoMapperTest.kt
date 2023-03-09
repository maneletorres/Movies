package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.di.annotations.TestMovieMapper
import com.manishsputnikcorporation.movies.network.model.MovieDtoMapper
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieListDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieDtoMapperTest {

  // region Setup
  @get:Rule val hiltRule = HiltAndroidRule(this)

  @Inject @TestMovieMapper lateinit var mapper: MovieDtoMapper

  @Before
  fun setup() {
    hiltRule.inject()
  }
  // endregion

  // region Testing
  @Test
  fun map_movie_dto_to_movie_model() {
    // Given:
    val fakeMovieDto = fakeMovieListDto.items[0] // TODO: replace by FakeApi retrofit call

    // When:
    val result = mapper.mapToDomainModel(fakeMovieDto)

    // Then:
    assert(fakeMovieDto.isContentEquals(result))
  }

  @Test
  fun map_movie_model_to_movie_dto() {
    // Given:
    val fakeMovieModel = fakeMovieModel // TODO: replace by FakeApi retrofit call

    // When:
    val result = mapper.mapFromDomainModel(fakeMovieModel)

    // Then:
    assert(result.isContentEquals(fakeMovieModel))
  }
  // endregion
}
