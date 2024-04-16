package co.meteto.cat.data.datasource.remot

import android.util.Log
import co.meteto.cat.core.generic.dto.ResponseDto
import co.meteto.cat.core.network.CatApi
import co.meteto.cat.data.model.remote.dto.response.CatResponseDto
import javax.inject.Inject

class CatRemoteDataSourceImpl @Inject constructor(
    private val api: CatApi
) : CatRemoteDataSource {

    override suspend fun getCats(
        pageNumber: Int,
    ): ResponseDto<List<CatResponseDto>> {
        val result =  api.getCats(page = pageNumber )
        Log.e("Request Result" , result.message())

       val response =  ResponseDto<List<CatResponseDto>>().apply {
           this.results = result.body()
           this.page = pageNumber
       }
        return response
    }

}