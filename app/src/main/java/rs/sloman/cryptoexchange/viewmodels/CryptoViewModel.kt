package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.repo.Repo
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class CryptoViewModel @ViewModelInject constructor(val repo: Repo) : ViewModel() {

    var cryptos: MutableLiveData<CryptoResponse> = MutableLiveData()
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var disposable: Disposable

    fun loadData() {
        disposable = Observable.interval(
                1, 5, TimeUnit.SECONDS
        )
                .flatMap { repo.getCryptos() }
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
                .doOnSubscribe { disposable ->
                    compositeDisposable = CompositeDisposable().apply {
                        add(disposable)
                    }
                }
                .doOnDispose { Timber.d("Disposed!") }
                .subscribe(
                        { d ->
                            Timber.d(d.message)
                            cryptos.postValue(d)
                        },
                        { t -> Timber.d(t.message) },
                        {}
                )
    }


    fun stopLoadingData() {
        compositeDisposable.clear()
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}