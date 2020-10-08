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

    private val _cryptos = MutableLiveData<CryptoResponse>()


    val cryptos: LiveData<CryptoResponse>
        get() = _cryptos

    init {
        getCryptos()
    }

    private fun getCryptos() {
        viewModelScope.launch {

            try {

                val cryptoResponse = repo.getCryptos()
                if (cryptoResponse.isSuccessful) {
                    _cryptos.value = cryptoResponse.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}