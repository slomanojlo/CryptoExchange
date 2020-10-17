package rs.sloman.cryptoexchange.adapters

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import rs.sloman.cryptoexchange.Constants
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.model.CryptoResponse
import rs.sloman.cryptoexchange.model.Status


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
}

@BindingAdapter("bindMedian")
fun bindMedian(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "Median: € $double" else ""
}

@BindingAdapter("bindOpenDay")
fun bindOpenDay(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "Open day: € $double" else ""
}

@BindingAdapter("bindHighDay")
fun bindHighDay(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "High Day: € $double" else ""
}

@BindingAdapter("bindLowDay")
fun bindLowDay(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "Low day: € $double" else ""
}

@BindingAdapter("bindMktCap")
fun bindMktCap(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "Market cap: € $double" else ""
}

@BindingAdapter("bindPercent")
fun bindPercent(textView: TextView, double: Double) {
    textView.text = if(double != 0.0) "Change in 24h: $double%" else ""
}

@BindingAdapter("bindListData")
fun bindListData(recyclerView: RecyclerView, cryptos: PagedList<CryptoResponse.Data>?) {
    val adapter = recyclerView.adapter as CryptoAdapter

    adapter.submitList(cryptos)
}

@BindingAdapter("bindImage")
fun bindImage(imageView: ImageView, photoUri: String?) {
    if (photoUri == null) return

    if (photoUri.isNotEmpty()) {
        imageView.visibility = View.VISIBLE
        Glide.with(imageView)
                .load(Constants.IMAGES_FOLDER + photoUri)
                .circleCrop()
                .placeholder(R.drawable.loading_img)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.ic_round_broken_image)
                .into(imageView.apply {
                    colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
                        setSaturation(0F)
                    })
                })

    } else {
        imageView.visibility = View.GONE
    }
}

@BindingAdapter("bindError")
fun bindError(iw: ImageView, status: Status?) {
    iw.visibility = if(status == Status.ERROR) View.VISIBLE else View.GONE
}

@BindingAdapter("bindTextView", "bindMessage")
fun bindHeader(tw: TextView, fulllName: String?, message: String?) {

    message?.let{
    tw.text = if (message.startsWith("Total")) fulllName else message
    }

}



