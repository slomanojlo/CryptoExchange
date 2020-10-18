package rs.sloman.cryptoexchange.repo

import io.reactivex.Observable
import rs.sloman.cryptoexchange.model.PairResponse
import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/**Implementation of the Repository pattern.
 * Dagger-Hilt is in charge of providing this singleton object with its constructor.*/
class Repo @Inject constructor(private val cryptoApi: CryptoApi) {

        fun getCryptoPair(fromSymbol : String, toSymbol : String) : Observable<PairResponse>
                = cryptoApi.getCryptoPair(fromSymbol, toSymbol)

}