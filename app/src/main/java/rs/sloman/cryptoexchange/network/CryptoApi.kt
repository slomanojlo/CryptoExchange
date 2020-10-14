package rs.sloman.cryptoexchange.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.PairResponse


interface CryptoApi {

    @GET("data/top/totalvolfull")
    fun getCryptosRX(
            @Query("tsym") toSymbol: String,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): Observable<CryptoResponse>

    //https://min-api.cryptocompare.com/data/top/exchanges/full?fsym=BTC&tsym=eur
    @GET("data/top/exchanges/full")
    fun getCryptoPair(
            @Query("fsym") fromSymbol: String,
            @Query("tsym") toSymbol: String
    ): Observable<PairResponse>
}