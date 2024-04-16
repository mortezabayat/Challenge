package co.meteto.cat.domain.repository

import androidx.paging.PagingData
import co.meteto.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    suspend fun getCats(): Flow<PagingData<Cat>>
}