package rs.sloman.cryptoexchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.cryptoexchange.MainActivity
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.databinding.FragmentCurrencyDetailBinding
import rs.sloman.cryptoexchange.viewmodels.DetailViewModel

@AndroidEntryPoint
class CurrencyDetailFragment : Fragment(R.layout.fragment_currency_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<CurrencyDetailFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setupActionBar()

        val binding = FragmentCurrencyDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root

    }

    private fun setupActionBar() {
        (activity as MainActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = args.crypto.coinInfo.name
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(args.crypto.coinInfo.name.toLowerCase())
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadingData()
    }

}