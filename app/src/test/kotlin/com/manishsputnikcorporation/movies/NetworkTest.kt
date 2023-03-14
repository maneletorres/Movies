package com.manishsputnikcorporation.movies

import android.content.Context
import com.manishsputnikcorporation.movies.network.MoviesService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(MockitoJUnitRunner::class)
class NetworkTest : BaseTest() {

  @Mock private lateinit var mockContext: Context

  private lateinit var service: MoviesService

  @Before
  fun setup() {
    val url = mockWebServer.url("/")
    service =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    // TODO: add dependency for ->
                    // Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    ))
            .build()
            .create(MoviesService::class.java)
  }

  @Test
  fun api_service() {
    // TODO: to complete this test.
    enqueue("popular_movies.json")
    runTest {
      // Given:
      // mockContext = mock()
      // val networkManager = NetworkManagerImpl(mockContext)
      // val customService = CustomServicesExecutor.Default(networkManager)

      // When:
      // val call = service.getMostPopularMovies(BuildConfig.API_KEY)
      // val response = customService.execute(call)

      // Then:
      // assertNotNull(response)
      // response.mapSuccess {
      //    val movies = it.data.items
      //    assertTrue("The list was empty", movies.isNotEmpty())
      //    assertEquals("The IDs did not match","424905", movies[0].id)
      // }
    }
  }
}
