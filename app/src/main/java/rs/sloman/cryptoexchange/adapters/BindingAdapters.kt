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


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
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
        Glide.with(imageView)
                .load(Constants.IMAGES_FOLDER + photoUri)
                .circleCrop()
                .placeholder(R.drawable.loading_img)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView.apply {
                    colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
                        setSaturation(0F)
                    })
                })
        imageView.visibility = View.VISIBLE

    } else {
        imageView.visibility = View.GONE
    }
}

