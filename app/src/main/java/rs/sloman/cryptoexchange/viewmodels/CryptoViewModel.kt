package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.repo.Repo


class CryptoViewModel @ViewModelInject constructor(val repo: Repo) : ViewModel() {

    var cryptos: MutableLiveData<CryptoResponse> = MutableLiveData()

    fun setCryptos(cryptoResponse: CryptoResponse) {
        cryptos.value = cryptoResponse
    }
}