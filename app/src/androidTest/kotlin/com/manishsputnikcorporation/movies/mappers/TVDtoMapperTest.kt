package com.manishsputnikcorporation.movies.mappers

import com.manishsputnikcorporation.movies.domain.model.TVModel
import com.manishsputnikcorporation.movies.network.model.TVDtoMapper
import com.manishsputnikcorporation.movies.network.response.tv.TVDto
import com.manishsputnikcorporation.movies.utils.extensions.isContentEquals
import com.manishsputnikcorporation.movies.utils.fake.fakeTVDto
import com.manishsputnikcorporation.movies.utils.fake.fakeTVModel
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class TVDtoMapperTest {

    // region Setup
    @Inject lateinit var mapper: TVDtoMapper

    @Before
    fun setup() {
        mapper = TVDtoMapper()
    }

    // endregion

    // region Testing
    @Test
    fun `WHEN mapToDomainModel is called on a TVDto THEN returns the corresponding TVModel`() {
        val result: TVModel = mapper.mapToDomainModel(dto = fakeTVDto)

        assert(result.isContentEquals(tvDto = fakeTVDto))
    }

    @Test
    fun `WHEN mapFromDomainModel is called on a TVModel THEN returns the corresponding TVDto`() {
        val result: TVDto = mapper.mapFromDomainModel(domainModel = fakeTVModel)

        assert(result.isContentEquals(tvModel = fakeTVModel))
    }
    // endregion
}
