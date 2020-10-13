package rs.sloman.cryptoexchange.repo

import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.network.CryptoApi
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val STARTING_PAGE_INDEX = 1

@Singleton
class CryptoDataSource @Inject constructor(
        private val cryptoApi: CryptoApi,
        private val compositeDisposable: CompositeDisposable
) :
        PageKeyedDataSource<Int, CryptoResponse.Data>() {

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, 0, params.requestedLoadSize)
                        .subscribe({
                            callback.onResult(it.data, null, params.requestedLoadSize + 1)

                        },
                                { Timber.d("Error") })
        )

    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, params.key, params.requestedLoadSize)
                        .subscribe({
                            callback.onResult(it.data, params.requestedLoadSize + params.key)

                        },
                                { Timber.d("Error") })
        )
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
    }

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoResponse.Data> {
//        val position = params.key ?: STARTING_PAGE_INDEX
//
//        return try {
//            val response = cryptoApi.getCryptos(EUR, position, params.loadSize)
//            val cryptos = response.data
//
//            LoadResult.Page(
//                    data = cryptos,
//                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
//                    nextKey = if (cryptos.isEmpty()) null else position + 1
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            LoadResult.Error(exception)
//        }
//
//
//    }


}