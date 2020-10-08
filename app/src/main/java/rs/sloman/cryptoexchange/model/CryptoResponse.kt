package rs.sloman.cryptoexchange.model

import com.google.gson.annotations.SerializedName


data class CryptoResponse(
        @SerializedName("Message") val message: String,
        @SerializedName("Type") val type: Int,
        @SerializedName("Data") val data: List<Data>
) {
    data class Data(
            @SerializedName("CoinInfo") val coinInfo: CoinInfo
    ) {
        data class CoinInfo(
                @SerializedName("Id") val id: Double,
                @SerializedName("Name") val name: String,
                @SerializedName("FullName") val fullName: String
        )
    }
}