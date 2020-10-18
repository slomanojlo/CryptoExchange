package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import rs.sloman.cryptoexchange.di.AppModule
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.Status
import rs.sloman.cryptoexchange.repo.CryptoDataSource
import rs.sloman.cryptoexchange.repo.CryptoDataSourceFactory

/**Core of the app, where all the magic happens!*/
class CryptoViewModel @ViewModelInject constructor(private val cryptoDataSourceFactory: CryptoDataSourceFactory) :
        ViewModel() {

    var cryptos: LiveData<PagedList<CryptoResponse.Data>> = MutableLiveData()
    private val config: PagedList.Config = AppModule.provideConfig()

    /**Init block in charge of fetching data and populating the list of cryptocurrencies.*/
    init {
        cryptos = LivePagedListBuilder(cryptoDataSourceFactory, config).build()
    }

    /**LiveData to check the Status of API calls.*/
    fun getStatus(): LiveData<Status> = Transformations.switchMap(
            cryptoDataSourceFactory.cryptoDataSourceLiveData,
            CryptoDataSource::status
    )

    fun retry() {
        cryptoDataSourceFactory.cryptoDataSourceLiveData.value?.retry()
    }

}