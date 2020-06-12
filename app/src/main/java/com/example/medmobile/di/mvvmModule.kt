package com.example.medmobile.di

import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.repositories.LogInRepository
import com.example.medmobile.mvvm.viewModels.LogInViewModel
import com.example.medmobile.mvvm.repositories.LogInRepositoryImpl
import com.example.medmobile.mvvm.repositories.UserRepository
import com.example.medmobile.mvvm.viewModels.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { PageHelper() }

    single<LogInRepository> { LogInRepositoryImpl(get()) }
    viewModel { LogInViewModel(get()) }

    single { UserRepository(get(), get()) }
    viewModel { UserViewModel(get()) }
}