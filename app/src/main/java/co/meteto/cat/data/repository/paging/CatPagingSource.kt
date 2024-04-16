package co.meteto.cat.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.meteto.cat.data.datasource.remot.CatRemoteDataSource
import co.meteto.cat.data.model.remote.maper.mapFromListModel
import co.meteto.cat.domain.model.Cat
import retrofit2.HttpException
import java.io.IOException

class CatPagingSource (
    private val remoteDataSource: CatRemoteDataSource,
) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        return try {
            val currentPage = params.key ?: 1
            val cats = remoteDataSource.getCats(
                pageNumber = currentPage
            )
            LoadResult.Page(
                data = cats.results!!.mapFromListModel(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (cats.results.isNullOrEmpty()) null else cats.page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return state.anchorPosition
    }

}