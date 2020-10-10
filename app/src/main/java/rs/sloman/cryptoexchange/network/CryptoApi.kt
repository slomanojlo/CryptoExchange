package rs.sloman.cryptoexchange.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import rs.sloman.cryptoexchange.model.CryptoResponse


interface CryptoApi {

    @GET("data/top/totalvolfull?limit=10&tsym=EUR")
    fun getCryptosRX(): Observable<CryptoResponse>
}