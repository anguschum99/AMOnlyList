package com.example.mal

import android.app.Application
import com.example.mal.data.AppContainer
import com.example.mal.data.DefaultAppContainer

class MalApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}