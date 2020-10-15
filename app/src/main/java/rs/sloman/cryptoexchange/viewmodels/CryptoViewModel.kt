package rs.sloman.cryptoexchange.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import rs.sloman.cryptoexchange.di.AppModule
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.Status
import rs.sloman.cryptoexchange.repo.CryptoDataSource
import rs.sloman.cryptoexchange.repo.CryptoDataSourceFactory


class CryptoViewModel @ViewModelInject constructor(private val cryptoDataSourceFactory: CryptoDataSourceFactory) :
        ViewModel() {


    val cryptos: LiveData<PagedList<CryptoResponse.Data>>
    private val config: PagedList.Config = AppModule.provideConfig()

    init {
        cryptos = LivePagedListBuilder(cryptoDataSourceFactory, config).build()
    }

    fun getStatus(): LiveData<Status> = Transformations.switchMap(
            cryptoDataSourceFactory.cryptoDataSourceLiveData,
            CryptoDataSource::status
    )

}