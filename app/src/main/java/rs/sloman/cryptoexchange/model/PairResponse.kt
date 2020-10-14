package rs.sloman.cryptoexchange.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PairResponse(
        @SerializedName("Message") val message: String,
        @SerializedName("Type") val type: Int,
        @SerializedName("Data") val data: Data
) : Parcelable {
    @Parcelize
    data class Data(
            @SerializedName("CoinInfo") val coinInfo: CoinInfo,
            @SerializedName("AggregatedData") val aggregatedData: AggregatedData
    ) : Parcelable {
        @Parcelize
        data class CoinInfo(
                @SerializedName("Id") val id: Double,
                @SerializedName("Name") val name: String,
                @SerializedName("FullName") val fullName: String,
                @SerializedName("ImageUrl") val imageUrl: String
        ) : Parcelable

        @Parcelize
        data class AggregatedData(
                @SerializedName("FROMSYMBOL") val fromSymbol: String,
                @SerializedName("TOSYMBOL") val toSymbol: String,
                @SerializedName("MEDIAN") val median: Double,
                @SerializedName("OPENDAY") val openDay: Double,
                @SerializedName("HIGHDAY") val highDay: Double,
                @SerializedName("LOWDAY") val lowDay: Double,
                @SerializedName("MKTCAP") val marketCap: Double,
                @SerializedName("CHANGEPCT24HOUR") val changePercent24h: Double
        ) : Parcelable
    }
}