package rs.sloman.cryptoexchange.repo

import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val cryptoApi: CryptoApi) {

}