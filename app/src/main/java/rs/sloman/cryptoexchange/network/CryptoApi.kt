package rs.sloman.cryptoexchange.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.PairResponse

/**Typical Retrofit interface in charge of querying the server.
 * Queries return RxJava Observables handled further down the line.*/
interface CryptoApi {

    @GET("data/top/totalvolfull")
    fun getCryptosRX(
            @Query("tsym") toSymbol: String,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): Observable<CryptoResponse>

    @GET("data/top/exchanges/full")
    fun getCryptoPair(
            @Query("fsym") fromSymbol: String,
            @Query("tsym") toSymbol: String
    ): Observable<PairResponse>
}