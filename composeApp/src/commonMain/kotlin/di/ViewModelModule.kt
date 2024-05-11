package di

import org.koin.dsl.module
import screens.home.HomeViewModel

val provideviewModelModule = module {
    single {
        HomeViewModel(get())
    }
}