package com.example.simpleunitconverterapp

import android.app.Application
import com.example.simpleunitconverterapp.di.AppContainer

class SimpleUnitConverterApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}