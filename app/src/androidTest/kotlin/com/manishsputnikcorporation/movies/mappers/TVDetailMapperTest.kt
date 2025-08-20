package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.domain.model.TVDetailModel
import com.manishsputnikcorporation.movies.network.model.TVDetailDtoMapper
import com.manishsputnikcorporation.movies.network.response.tv.TVDetailDto
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDetailModel
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class TVDetailMapperTest {

    // region Setup
    @Inject lateinit var mapper: TVDetailDtoMapper

    @Before
    fun setup() {
        mapper = TVDetailDtoMapper()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN mapToDomainModel is called on a TVDetailDto THEN returns the corresponding TVDetailModel`() {
        val result: TVDetailModel = mapper.mapToDomainModel(dto = fakeTVDetailDto)

        assert(result.isContentEquals(tvDetailDto = fakeTVDetailDto))
    }

    @Test
    fun `WHEN mapFromDomainModel is called on a TVDetailModel THEN returns the corresponding TVDetailDto`() {
        val result: TVDetailDto = mapper.mapFromDomainModel(domainModel = fakeTVDetailModel)

        assert(result.isContentEquals(tvModel = fakeTVDetailModel))
    }
    // endregion
}
