package rs.sloman.cryptoexchange.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.sloman.cryptoexchange.model.CryptoResponse


interface CryptoApi {

    @GET("data/top/totalvolfull")
    fun getCryptosRX(@Query("tsym") toSymbol: String,
            @Query("limit") limit: Int): Observable<CryptoResponse>
}