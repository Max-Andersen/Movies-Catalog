package com.example.moviecatalog.mainScreen.movieData

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecatalog.repository.MovieRepository

class MovieSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, MoviePreView>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviePreView> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = movieRepository.loadPageOfMovies(nextPage)
            LoadResult.Page(
                data = movieListResponse,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                //nextKey = movieListResponse.page.plus(1)     //.page - поле в запросе к api
                nextKey = nextPage + 1    //заглушка

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MoviePreView>): Int? {
        TODO("Not yet implemented")   //   выбрасывать ошибку ¯\_(ツ)_/¯
    }
}

