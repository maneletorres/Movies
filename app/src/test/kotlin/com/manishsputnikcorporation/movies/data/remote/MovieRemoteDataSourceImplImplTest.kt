// package com.manishsputnikcorporation.movies.data.remote
//
// import android.content.Context
// import com.manishsputnikcorporation.movies.data.remote.base.MovieRemoteDataSourceImplBaseTest
// import com.manishsputnikcorporation.movies.network.NetworkManagerImpl
// import com.manishsputnikcorporation.movies.network.service.RemoteApiService
// import io.mockk.mockk
// import kotlinx.coroutines.test.runTest
// import org.junit.Before
// import org.junit.Test
// import org.junit.runner.RunWith
// import org.mockito.Mock
// import org.mockito.junit.MockitoJUnitRunner
// import retrofit2.Retrofit
// import retrofit2.converter.moshi.MoshiConverterFactory
// import kotlin.test.DefaultAsserter.assertNotNull
// import kotlin.test.DefaultAsserter.assertTrue
//
// @RunWith(MockitoJUnitRunner::class)
// class MovieRemoteDataSourceImplImplTest : MovieRemoteDataSourceImplBaseTest() {
//
//  @Mock private lateinit var mockContext: Context
//
//  private lateinit var service: RemoteApiService
//
//  @Before
//  fun setup() {
//    val url = mockWebServer.url("/")
//    service =
//        Retrofit.Builder()
//            .baseUrl(url)
//            .addConverterFactory(
//                MoshiConverterFactory.create(
//                    // TODO: add dependency for ->
//                    // Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//                    ))
//            .build()
//            .create(MoviesService::class.java)
//  }
//
//  @Test
//  fun api_service() {
//    // TODO: to complete this test.
//    enqueue("popular_movies.json")
//    runTest {
//       mockContext = mockk()
//       val networkManager = NetworkManagerImpl(mockContext)
//       val customService = CustomServicesExecutor.Default(networkManager)
//
//       val call = service.getMostPopularMovies(BuildConfig.API_KEY)
//       val response = customService.execute(call)
//
//       assertNotNull(response)
//       response.mapSuccess {
//          val movies = it.data.items
//          assertTrue("The list was empty", movies.isNotEmpty())
//          assertEquals("The IDs did not match","424905", movies[0].id)
//       }
//    }
//  }
// }
