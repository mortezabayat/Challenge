package co.meteto.cat.core.di

import co.meteto.cat.core.network.CatApi
import co.meteto.cat.data.datasource.remot.CatRemoteDataSource
import co.meteto.cat.data.datasource.remot.CatRemoteDataSourceImpl
import co.meteto.cat.data.repository.CatRepositoryImpl
import co.meteto.cat.domain.repository.CatRepository
import co.meteto.cat.domain.usecase.GetCatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesMovieRemoteDataSource(
        api: CatApi
    ): CatRemoteDataSource {
        return CatRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesMovieRepository(
        movieRemoteDataSource: CatRemoteDataSource
    ): CatRepository {
        return CatRepositoryImpl(movieRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesGetMoviesUseCase(
        movieRepository: CatRepository
    ): GetCatsUseCase {
        return GetCatsUseCase(movieRepository)
    }
}