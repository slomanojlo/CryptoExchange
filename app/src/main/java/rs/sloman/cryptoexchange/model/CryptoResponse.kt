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
            @SerializedName("CoinInfo") val coinInfo: CoinInfo,
            @SerializedName("DISPLAY") val display: Display
    ) : Parcelable {
        @Parcelize
        data class CoinInfo(
                @SerializedName("Id") val id: Double,
                @SerializedName("Name") val name: String,
                @SerializedName("FullName") val fullName: String,
                @SerializedName("ImageUrl") val imageUrl: String
        ) : Parcelable

        @Parcelize
        data class Display(
                @SerializedName("EUR") val eur: Eur
        ) : Parcelable{

            @Parcelize
            data class Eur(
                    @SerializedName("PRICE") val price: String,
                    @SerializedName("FROMSYMBOL") val symbol: String
            ) : Parcelable
        }
    }
}