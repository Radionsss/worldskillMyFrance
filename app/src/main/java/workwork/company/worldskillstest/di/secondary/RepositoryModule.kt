package coffee.company.lintu.di.secondary

import workwork.company.worldskillstest.data.MainRepositoryImpl
import workwork.company.worldskillstest.domain.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Suppress("FunctionName")
    @Binds
    fun bindClassRepositoryImpl_to_ClassRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}