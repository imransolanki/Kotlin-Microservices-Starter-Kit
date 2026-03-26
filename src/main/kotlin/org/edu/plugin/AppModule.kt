package org.edu.plugin

import org.edu.repository.PetRepository
import org.edu.repository.PetRepositoryImpl
import org.edu.service.PetService
import org.koin.dsl.module

val appModules =
    module {
        single<PetRepository> { PetRepositoryImpl() }
        single { PetService(get()) }
    }
