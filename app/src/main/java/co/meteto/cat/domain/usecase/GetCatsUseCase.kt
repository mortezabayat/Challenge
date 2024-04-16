package co.meteto.cat.domain.usecase

import androidx.paging.PagingData
import co.meteto.cat.core.generic.usecase.BaseUseCase
import co.meteto.cat.domain.model.Cat
import co.meteto.cat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(
    private val repository: CatRepository
) : BaseUseCase<Unit, Flow<PagingData<Cat>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<Cat>> {
        return repository.getCats()
    }
}