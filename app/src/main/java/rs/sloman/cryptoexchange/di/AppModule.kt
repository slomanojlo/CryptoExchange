package rs.sloman.cryptoexchange.di

import androidx.paging.PagedList
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.Constants.PAGE_SIZE
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


    @Singleton
    @Provides
    fun provideCompDisposable(): CompositeDisposable = CompositeDisposable()

    @Singleton
    @Provides
    fun provideConfig() : PagedList.Config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
}
