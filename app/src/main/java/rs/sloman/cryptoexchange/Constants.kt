package rs.sloman.cryptoexchange

object Constants {

    /**Currency to compare cryptocurrencies against.*/
    const val EUR = "eur"

    /**Base URL of Crypto Compare API*/
    const val BASE_URL = "https://min-api.cryptocompare.com/"

    /**Location where Crypto Compare API stores its images.*/
    const val IMAGES_FOLDER = "https://www.cryptocompare.com/"

    /**Initial delay of RxJava Observable triggers.*/
    const val INITIAL_DELAY : Long = 0

    /**Delay between two RxJava Observable triggers.*/
    const val PERIOD_DELAY : Long = 120

    /**Debounce delay between two RxJava Observable triggers when error occurs.*/
    const val DEBOUNCE_DELAY : Long = 2

    /**Page size in CryptoCurrencies RecyclerView.*/
    const val PAGE_SIZE = 20

}
