package rs.sloman.cryptoexchange.repo

import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Inject


class Repo @Inject constructor(private val cryptoApi: CryptoApi){

    suspend fun getCryptos() = cryptoApi.getCryptos()

}