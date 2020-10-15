package rs.sloman.cryptoexchange.repo

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoDataSourceFactory @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        private val cryptoApi: CryptoApi
) : DataSource.Factory<Int, CryptoResponse.Data>() {

    val cryptoDataSourceLiveData = MutableLiveData<CryptoDataSource>()


    override fun create(): DataSource<Int, CryptoResponse.Data> {
        val cryptoDataSource = CryptoDataSource(cryptoApi, compositeDisposable)

        cryptoDataSourceLiveData.postValue(cryptoDataSource)
        return cryptoDataSource
    }

}