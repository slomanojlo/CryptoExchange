package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.repo.Repo


class CryptoViewModel @ViewModelInject constructor(val repo: Repo) : ViewModel() {

    var cryptos: LiveData<PagingData<CryptoResponse.Data>> = repo.getCryptosPaging().cachedIn(viewModelScope)
    lateinit var compositeDisposable: CompositeDisposable
    lateinit var disposable: Disposable


}