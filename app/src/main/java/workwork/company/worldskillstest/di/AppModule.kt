package workwork.company.worldskillstest.di

import workwork.company.worldskillstest.di.secondary.DatabaseModule
import workwork.company.worldskillstest.di.secondary.NetworkModule
import workwork.company.worldskillstest.di.secondary.RepositoryModule
import workwork.company.worldskillstest.di.secondary.SharedPreferencesManagerModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [DatabaseModule::class, RepositoryModule::class, NetworkModule::class, SharedPreferencesManagerModule::class,])
@InstallIn(SingletonComponent::class)
object AppModule
