package co.meteto.cat.data.datasource.remot

import co.meteto.cat.core.generic.dto.ResponseDto
import co.meteto.cat.data.model.remote.dto.response.CatResponseDto


interface CatRemoteDataSource {

    suspend fun getCats(
        //apiKey: String,
        pageNumber: Int,
    ): ResponseDto<List<CatResponseDto>>

}