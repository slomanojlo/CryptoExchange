package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.repo.Repo
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class CryptoViewModel @ViewModelInject constructor(val repo: Repo) : ViewModel() {

    var cryptos: MutableLiveData<CryptoResponse> = MutableLiveData()
    private var compositeDisposable = CompositeDisposable()


    init {
        loadData()
    }

    fun setCryptos(cryptoResponse: CryptoResponse) {
        cryptos.value = cryptoResponse
    }

    fun loadData() {
        val disposable = Observable.interval(
                1, 20, TimeUnit.SECONDS
        )
                .flatMap { repo.getCryptosRx() }
                .retryWhen {
                    it.map { throwable ->
                        if (throwable is UnknownHostException) {
                            Timber.d("Error")
                            throwable
                        } else {
                            throw throwable
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t ->
                            Timber.d(t.message)
                            setCryptos(t)
                        },
                        { t -> Timber.d(t.message) },
                        {}
                )
        compositeDisposable.add(disposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}