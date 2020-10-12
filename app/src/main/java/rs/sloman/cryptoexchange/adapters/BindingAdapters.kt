package rs.sloman.cryptoexchange.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rs.sloman.cryptoexchange.model.CryptoResponse


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
}

@BindingAdapter("bindListData")
fun bindListData(recyclerView: RecyclerView, cryptos: PagingData<CryptoResponse.Data>?) {
    val adapter = recyclerView.adapter as CryptoAdapter

    if (cryptos != null) {

        GlobalScope.launch(Dispatchers.Main) {
            adapter.submitData(cryptos)
        }
    }
}