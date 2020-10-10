package rs.sloman.cryptoexchange.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import timber.log.Timber


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
    Timber.d("Slobodan $string")
}
