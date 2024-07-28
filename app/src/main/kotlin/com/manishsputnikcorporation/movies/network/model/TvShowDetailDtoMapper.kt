package com.manishsputnikcorporation.movies.network.model

import com.manishsputnikcorporation.movies.domain.model.TvShowDetailModel
import com.manishsputnikcorporation.movies.domain.util.DomainMapper
import com.manishsputnikcorporation.movies.network.response.tvshow.TVShowDetailDto

class TvShowDetailDtoMapper : DomainMapper<TVShowDetailDto, TvShowDetailModel> {

    override fun mapToDomainModel(model: TVShowDetailDto): TvShowDetailModel {
        with(model) {
            return TvShowDetailModel(
                id = id.toString(),
                title = title,
                originalTitle = originalTitle,
                fullTitle = title,
                plot = overview,
                image = image,
                releaseDate = releaseDate,
                runtimeStr = runtime.map { it.toString() })
        }
    }

    override fun mapFromDomainModel(domainModel: TvShowDetailModel): TVShowDetailDto {
        with(domainModel) {
            return TVShowDetailDto(
                id = id.toInt(),
                title = title,
                originalTitle = originalTitle,
                image = image,
                releaseDate = releaseDate,
                runtime = runtimeStr.map { timeToInt(it) },
                overview = plot)
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
