package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.Constants.DEBOUNCE_DELAY
import rs.sloman.cryptoexchange.Constants.INITIAL_DELAY
import rs.sloman.cryptoexchange.Constants.PERIOD_DELAY
import rs.sloman.cryptoexchange.model.PairResponse
import rs.sloman.cryptoexchange.model.Status
import rs.sloman.cryptoexchange.repo.Repo
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


class DetailViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {


    val crypto: MutableLiveData<PairResponse> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val status: MutableLiveData<Status> = MutableLiveData()


    fun loadData(fromSymbol: String) {
        compositeDisposable.add(Observable
                .interval(INITIAL_DELAY, PERIOD_DELAY, TimeUnit.SECONDS)
                .flatMap { repo.getCryptoPair(fromSymbol, Constants.EUR) }
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
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { status.value = Status.LOADING }
                .doOnDispose { Timber.d("Disposed!") }
                .subscribe(
                        { d ->
                            Timber.d(d.message)
                            crypto.postValue(d)
                            status.postValue(Status.DONE)
                        },
                        { t -> Timber.d(t.message) },
                        { Timber.d("Complete") }
                ))
    }

    fun stopLoadingData() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}