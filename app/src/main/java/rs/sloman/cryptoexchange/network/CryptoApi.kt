package rs.sloman.cryptoexchange.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import rs.sloman.cryptoexchange.model.CryptoResponse


interface CryptoApi {

    @GET("data/top/totalvolfull")
    fun getCryptosRX(
            @Query("tsym") toSymbol: String,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): Single<CryptoResponse>

    @GET("data/top/totalvolfull")
    suspend fun getCryptos(
            @Query("tsym") toSymbol: String,
            @Query("page") page: Int,
            @Query("limit") limit: Int
    ): CryptoResponse
}