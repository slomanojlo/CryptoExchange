package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.model.PairResponse
import rs.sloman.cryptoexchange.repo.Repo
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class DetailViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {


    var crypto: MutableLiveData<PairResponse> = MutableLiveData()
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var disposable: Disposable

    fun loadData(fromSymbol: String) {
        disposable = Observable.interval(1, 5, TimeUnit.SECONDS)
                .flatMap { repo.getCryptoPair(fromSymbol, Constants.EUR) }
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
                            crypto.postValue(d)
                        },
                        { t -> Timber.d(t.message) },
                        { Timber.d("Complete") }
                )
    }

    fun stopLoadingData() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}