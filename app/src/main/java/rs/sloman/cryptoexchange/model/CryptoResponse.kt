package rs.sloman.cryptoexchange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoResponse(
        @SerializedName("Message") val message: String,
        @SerializedName("Type") val type: Int,
        @SerializedName("Data") val data: List<Data>
) : Parcelable {
    @Parcelize
    data class Data(
            @SerializedName("CoinInfo") val coinInfo: CoinInfo
    ) : Parcelable {
        @Parcelize
        data class CoinInfo(
                @SerializedName("Id") val id: Double,
                @SerializedName("Name") val name: String,
                @SerializedName("FullName") val fullName: String
        ) : Parcelable
    }
}