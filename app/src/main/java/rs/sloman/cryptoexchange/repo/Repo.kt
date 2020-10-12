package rs.sloman.cryptoexchange.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val cryptoApi: CryptoApi) {

//    fun getCryptos() = cryptoApi.getCryptosRX(EUR,  LIMIT)

    fun getCryptosPaging() =
            Pager(
                    config = PagingConfig(
                            pageSize = 20,
//                            maxSize = 100,
                            enablePlaceholders = false
                    ),
                    pagingSourceFactory = { CryptoPagingSource(cryptoApi) }).liveData

}