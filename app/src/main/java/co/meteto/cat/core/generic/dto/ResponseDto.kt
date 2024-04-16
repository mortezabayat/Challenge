package co.meteto.cat.core.generic.dto

import com.google.gson.annotations.SerializedName


class ResponseDto<T : Any?> {
    @SerializedName("results")
    var results: T? = null

    @SerializedName("page")
    var page: Int = 1

    @SerializedName("total_pages")
    val totalPages: Int = 10

    @SerializedName("total_results")
    val totalResults: Int? = null
}