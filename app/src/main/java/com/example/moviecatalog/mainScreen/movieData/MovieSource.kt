package com.example.moviecatalog.mainScreen.movieData

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecatalog.repository.MovieRepository

class MovieSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movies>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {
        return try {
            val nextPage = params.key ?: 1

            val movieListResponse = movieRepository.loadPageOfMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.movies,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage >= movieListResponse.pageInfo.pageCount) null else movieListResponse.pageInfo.currentPage.plus(1)
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {
        TODO("Not yet implemented")   //   выбрасывать ошибку ¯\_(ツ)_/¯
    }
}

