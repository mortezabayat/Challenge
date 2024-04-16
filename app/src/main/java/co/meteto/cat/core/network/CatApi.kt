package co.meteto.cat.core.network

import co.meteto.cat.core.app.Constants
import co.meteto.cat.core.generic.dto.ResponseDto
import co.meteto.cat.data.model.remote.dto.response.CatResponseDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CatApi {

    companion object {
        const val SERVER_URL = "https://api.thecatapi.com"
        const val API_URL = "$SERVER_URL/v1/images/"
    }

    @GET("search")
    suspend fun getCats(
        @Header("x-api-key") apiKey: String = Constants.CAT_API_KEY,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Response<List<CatResponseDto>>

}