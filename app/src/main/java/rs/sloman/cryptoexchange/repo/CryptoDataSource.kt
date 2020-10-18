package rs.sloman.cryptoexchange.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.Status
import rs.sloman.cryptoexchange.network.CryptoApi
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/**Class in charge of smoothly fetching data from the server. Returns a [PageKeyedDataSource]*/
class CryptoDataSource @Inject constructor(
        private val cryptoApi: CryptoApi,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, CryptoResponse.Data>() {

    val status: MutableLiveData<Status> = MutableLiveData()
    private var retryCompletable: Completable? = null

    /**Callback that loads the initial chunk of data.*/
    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, 0, params.requestedLoadSize)
                        .subscribe({
                            callback.onResult(it.data, null, params.requestedLoadSize + 1)
                            status.postValue(Status.DONE)
                            Timber.d(it.message)
                        },
                                {
                                    Timber.d("Error")
                                    status.postValue(Status.ERROR)
                                    setRetry(Action { loadInitial(params, callback) })
                                })
        )

    }

    /**Callback that loads every next chunk of data when the user reaches a certain position
     * in the RecyclerView.*/
    override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
        compositeDisposable.add(
                cryptoApi.getCryptosRX(Constants.EUR, params.key, params.requestedLoadSize)
                        .subscribe(
                                {
                                    status.postValue(Status.DONE)
                                    callback.onResult(
                                            it.data,
                                            params.requestedLoadSize + params.key
                                    )
                                    Timber.d(it.message)
                                },
                                {
                                    Timber.d("Error")
                                    status.postValue(Status.ERROR)
                                    setRetry(Action { loadAfter(params, callback) })
                                })
        )
    }

    /**Load before should have been used if the loading started somewhere from the middle of the list.
     *In our case we're loading from the begining, therefore it's empty. */
    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, CryptoResponse.Data>
    ) {
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }


    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe())
        }
    }

}