package rs.sloman.cryptoexchange.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import rs.sloman.cryptoexchange.model.CryptoResponse


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
}

@BindingAdapter("bindListData")
fun bindListData(recyclerView: RecyclerView, cryptos: PagedList<CryptoResponse.Data>?) {
    val adapter = recyclerView.adapter as CryptoAdapter

    adapter.submitList(cryptos)
}