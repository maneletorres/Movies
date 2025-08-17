package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.TvDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tvshow.TVDetailDto

class TvShowDetailDtoMapper : DomainMapper<TVDetailDto, TvDetailModel> {

    override fun mapToDomainModel(model: TVDetailDto): TvDetailModel {
        with(receiver = model) {
            return TvDetailModel(
                id = id.toString(),
                title = title.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                fullTitle = title.orEmpty(),
                plot = overview.orEmpty(),
                image = image.orEmpty(),
                releaseDate = releaseDate.orEmpty(),
                runtimeStr = runtime?.map { it.toString() }.orEmpty(),
            )
        }
    }

    override fun mapFromDomainModel(domainModel: TvDetailModel): TVDetailDto {
        with(receiver = domainModel) {
            return TVDetailDto(
                id = id.toInt(),
                title = title,
                originalTitle = originalTitle,
                image = image,
                releaseDate = releaseDate,
                runtime = runtimeStr.map { timeToInt(it) },
                overview = plot,
            )
        }
    }

    // region Helper methods
    private fun intToTime(time: Int): String {
        val hours = time / 60
        val minutes = time % 60
        return String.format("%02d %02d", hours, minutes)
    }

    private fun timeToInt(time: String): Int {
        val hours = time.substring(0, 2).toInt()
        val minutes = time.substring(3, 5).toInt()
        return hours * 60 + minutes
    }
    // endregion
}
