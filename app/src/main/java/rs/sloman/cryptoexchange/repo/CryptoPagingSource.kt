package rs.sloman.cryptoexchange.repo

import androidx.paging.PagingSource
import retrofit2.HttpException
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.Constants.EUR
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.network.CryptoApi
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1


class CryptoPagingSource(private val cryptoApi: CryptoApi) :
        PagingSource<Int, CryptoResponse.Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoResponse.Data> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = cryptoApi.getCryptos(EUR, position, params.loadSize)
            val cryptos = response.data

            LoadResult.Page(
                    data = cryptos,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (cryptos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }


    }
}