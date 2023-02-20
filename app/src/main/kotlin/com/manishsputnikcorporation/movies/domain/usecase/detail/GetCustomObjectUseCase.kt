package com.manishsputnikcorporation.movies.domain.usecase.detail

// class GetCustomObjectUseCase @Inject constructor(
//    private val movieRepository: MovieRepository,
//    override val globalErrorMapper: GlobalErrorMapper,
//    dispatcherProvider: DispatcherProvider
// ) : FlowUseCase<Int, Either<MovieModel, DomainError>>(dispatcherProvider),
// FlowUseCaseExceptionDelegate {
//
//    override fun prepareFlow(input: Int): Flow<Either<MovieModel, DomainError>> = flow {
//        movieRepository.getMovie(input)
//            .onSuccess { emit(eitherSuccess(it)) }
//            .onFailure { emit(eitherFailure(DomainError("", ""))) }
//    }.spreadUnauthorized { eitherFailure(DomainError("", "")) }
// }
