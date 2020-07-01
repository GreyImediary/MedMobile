package com.example.medmobile.di

import com.example.medmobile.mvvm.model.PageHelper
import com.example.medmobile.mvvm.repositories.*
import com.example.medmobile.mvvm.viewModels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { PageHelper() }

    single<LogInRepository> { LogInRepositoryImpl(get()) }
    viewModel { LogInViewModel(get()) }

    single { UserRepository(get(), get()) }
    viewModel { UserViewModel(get()) }

    single { ManufacturerRepository(get(), get()) }
    viewModel { ManufacturerViewModel(get()) }

    single { MedicineRepository(get(), get()) }
    viewModel { MedicineViewModel(get()) }

    single { PharmacyRepository(get(), get()) }
    viewModel { PharmacyViewModel(get()) }

    single { MedicineRequestRepository(get(), get()) }
    viewModel { MedicineRequestViewModel(get()) }

    single { SupplyRepository(get(), get()) }
    viewModel { SupplyViewModel(get()) }

    single { SellsRepository(get(), get()) }
    viewModel { SellsViewModel(get()) }
}