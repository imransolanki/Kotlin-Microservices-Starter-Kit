package org.edu.plugin

import org.edu.service.PetService
import org.koin.dsl.module

val appModules = module {
    single { PetService() }
}
