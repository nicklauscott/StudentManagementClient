package org.example.project.di

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.project.data.remote.repository.RepositoryImpl
import org.example.project.domain.repository.Repository
import org.example.project.presentation.screens.home.HomeScreenManager
import org.koin.dsl.module

val module = module {

    single {
        HttpClient(Js) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    factory<Repository> { RepositoryImpl(get()) }

    single { HomeScreenManager(get()) }

}