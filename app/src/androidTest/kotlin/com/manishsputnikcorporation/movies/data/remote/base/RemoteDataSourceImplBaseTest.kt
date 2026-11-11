package com.manishsputnikcorporation.movies.data.remote.base

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.manishsputnikcorporation.movies.extensions.fromJson
import com.manishsputnikcorporation.movies.network.NetworkManagerImpl
import com.manishsputnikcorporation.movies.network.service.RemoteApiService
import com.manishsputnikcorporation.movies.network.services.executor.RetrofitServiceExecutor
import com.manishsputnikcorporation.movies.network.services.executor.ServiceExecutor
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class RemoteDataSourceImplBaseTest {

    // region Setup
    lateinit var server: MockWebServer
    lateinit var serviceExecutor: ServiceExecutor
    lateinit var service: RemoteApiService
    lateinit var appContext: Context

    @Before
    open fun setUp() {
        server = MockWebServer()
        server.start(port = 8080)
        appContext = ApplicationProvider.getApplicationContext()
        service =
            Retrofit.Builder()
                .baseUrl(server.url(path = "/"))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RemoteApiService::class.java)
        serviceExecutor = RetrofitServiceExecutor(NetworkManagerImpl(context = appContext))
    }

    @After
    open fun tearDown() {
        server.shutdown()
    }

    // endregion

    // region Helper methods
    fun configureServerDispatcher(jsonFile: String) {
        server.dispatcher =
            object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    var mockResponse = MockResponse()
                    request.path?.let { mockResponse = mockResponse.fromJson(jsonFile = jsonFile) }
                    return mockResponse
                }
            }
    }
    // endregion
}
