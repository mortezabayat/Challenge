package co.meteto.cat.data.repository

import android.provider.SyncStateContract
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import co.meteto.cat.core.app.Constants
import co.meteto.cat.data.datasource.remot.CatRemoteDataSource
import co.meteto.cat.data.repository.paging.CatPagingSource
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource
) : CatRepository {

    override suspend fun getCats(): Flow<PagingData<Cat>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                CatPagingSource(remoteDataSource)
            }
        ).flow
    }
}