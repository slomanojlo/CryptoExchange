package rs.sloman.cryptoexchange.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.network.CryptoApi
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    /**Function that returns a singleton CryptoApi*/
    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoApi =
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .baseUrl(Constants.BASE_URL)
                    .build()
                    .create(CryptoApi::class.java)

}
