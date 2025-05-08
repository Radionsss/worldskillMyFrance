package coffee.company.lintu.di

import coffee.company.lintu.di.secondary.DatabaseModule
import workwork.company.worldskillstest.di.secondary.NetworkModule
import coffee.company.lintu.di.secondary.RepositoryModule
import coffee.company.lintu.di.secondary.SharedPreferencesManagerModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [DatabaseModule::class, RepositoryModule::class, NetworkModule::class, SharedPreferencesManagerModule::class,])
@InstallIn(SingletonComponent::class)
object AppModule
