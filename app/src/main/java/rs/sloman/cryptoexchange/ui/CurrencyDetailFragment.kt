package rs.sloman.cryptoexchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.cryptoexchange.CryptoCurrencyActivity
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.databinding.FragmentCurrencyDetailBinding
import rs.sloman.cryptoexchange.viewmodels.DetailViewModel

@AndroidEntryPoint
class CurrencyDetailFragment : Fragment(R.layout.fragment_currency_detail) {

    /**Instantiating or retrieving the DetailViewModel.*/
    private val viewModel: DetailViewModel by viewModels()
    /**Type-safe arguments provided by SafeArgs and navigation.*/
    private val args by navArgs<CurrencyDetailFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        setupActionBar()


        val binding = FragmentCurrencyDetailBinding.inflate(layoutInflater)

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment */
        binding.lifecycleOwner = this

        /** Giving the binding access to the OverviewViewModel */
        binding.viewModel = viewModel

        return binding.root

    }

    /**Setting up the title and back icon in Support action bar.*/
    private fun setupActionBar() {
        (activity as CryptoCurrencyActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = args.crypto.coinInfo.name
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(args.crypto.coinInfo.name)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadingData()
    }

}