package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.network.model.MovieDetailDtoMapper
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeMovieDetailModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import javax.inject.Named
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieDetailMapperTest {

  // region Setup
  @get:Rule val hiltRule = HiltAndroidRule(this)

  @Inject @Named("test_movie_detail_mapper") lateinit var mapper: MovieDetailDtoMapper

  @Before
  fun setup() {
    hiltRule.inject()
  }
  // endregion

  // region Testing
  @Test
  fun map_movie_detail_dto_to_movie_detail_model() {
    // Given:
    val fakeMovieDetailDto = fakeMovieDetailDto // TODO: replace by FakeApi retrofit call

    // When:
    val result = mapper.mapToDomainModel(fakeMovieDetailDto)

    // Then:
    assert(fakeMovieDetailDto.isContentEquals(result))
  }

  @Test
  fun map_from_movie_detail_model_to_movie_detail_dto() {
    // Given:
    val fakeMovieDetailModel = fakeMovieDetailModel // TODO: replace by FakeApi retrofit call

    // When:
    val result = mapper.mapFromDomainModel(fakeMovieDetailModel)

    // Then:
    assert(result.isContentEquals(fakeMovieDetailModel))
  }
  // endregion
}
