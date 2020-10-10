package rs.sloman.cryptoexchange.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rs.sloman.cryptoexchange.databinding.CryptoItemBinding
import rs.sloman.cryptoexchange.model.CryptoResponse


class CryptoAdapter(private val onClickListener: OnClickListenerCrypto) :
        ListAdapter<CryptoResponse.Data, CryptoAdapter.CryptoViewHolder>(DiffCallback) {


    class CryptoViewHolder(private var binding: CryptoItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(crypto: CryptoResponse.Data) {
            binding.crypto = crypto
            binding.executePendingBindings()
        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<CryptoResponse.Data>() {
            override fun areItemsTheSame(
                    oldItem: CryptoResponse.Data,
                    newItem: CryptoResponse.Data
            ): Boolean = oldItem.coinInfo.id == newItem.coinInfo.id


            override fun areContentsTheSame(
                    oldItem: CryptoResponse.Data,
                    newItem: CryptoResponse.Data
            ): Boolean = oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(CryptoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(crypto)
        }
        holder.bind(crypto)
    }

    class OnClickListenerCrypto (val clickListnener: (crypto: CryptoResponse.Data)-> Unit) {
        fun onClick(crypto: CryptoResponse.Data) = clickListnener(crypto)

    }
}
