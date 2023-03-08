package com.manishsputnikcorporation.movies

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class) interface HiltTestApplication
