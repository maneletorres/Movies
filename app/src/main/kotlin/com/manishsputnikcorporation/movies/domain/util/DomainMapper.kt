package com.manishsputnikcorporation.movies.domain.util

interface DomainMapper<T, DomainModel> {

    fun mapToDomainModel(dto: T): DomainModel

    fun mapFromDomainModel(domainModel: DomainModel): T
}
