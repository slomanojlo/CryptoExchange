package rs.sloman.cryptoexchange.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.Constants.DEBOUNCE_DELAY
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.Status
import rs.sloman.cryptoexchange.network.CryptoApi
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoDataSource @Inject constructor(
        private val cryptoApi: CryptoApi,
        private val compositeDisposable: CompositeDisposable
) :
        PageKeyedDataSource<Int, CryptoResponse.Data>() {

    val status: MutableLiveData<Status> = MutableLiveData()

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, 0, params.requestedLoadSize)
                        .retryWhen {
                            it.map { throwable ->
                                status.postValue(Status.ERROR)
                                if (throwable is UnknownHostException) {
                                    Timber.d("Error")
                                    throwable
                                } else {
                                    throw throwable
                                }
                            }.debounce(DEBOUNCE_DELAY, TimeUnit.SECONDS)
                        }
                        .subscribe({
                            callback.onResult(it.data, null, params.requestedLoadSize + 1)
                            status.postValue(Status.DONE)
                            Timber.d(it.message)
                        },
                                { Timber.d("Error")
                                    status.postValue(Status.ERROR)})
        )

    }

    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, params.key, params.requestedLoadSize)
                        .retryWhen {
                            it.map { throwable ->
                                status.postValue(Status.ERROR)
                                if (throwable is UnknownHostException) {
                                    Timber.d("Error")
                                    throwable
                                } else {
                                    throw throwable
                                }
                            }.debounce(DEBOUNCE_DELAY, TimeUnit.SECONDS)
                        }
                        .subscribe(
                                {
                                    callback.onResult(
                                            it.data,
                                            params.requestedLoadSize + params.key
                                    )
                                    status.postValue(Status.DONE)
                                    Timber.d(it.message)
                                },
                                {
                                    Timber.d("Error")
                                    status.postValue(Status.ERROR)
                                })
        )
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
    }


}