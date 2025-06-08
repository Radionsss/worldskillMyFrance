package workwork.company.worldskillstest.di.secondary

import android.content.Context
import workwork.company.worldskillstest.data.database.MainDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTableDatabase(
        @ApplicationContext context: Context
    ): MainDatabase {
        return MainDatabase(context)
    }
}