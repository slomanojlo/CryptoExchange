package rs.sloman.cryptoexchange

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
/**Activity class in charge of only setting up its UI layout.*/
class MainActivity : AppCompatActivity() {

    /** Main entrance point to the app.*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**Handling Suport action bar UP button.*/
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}