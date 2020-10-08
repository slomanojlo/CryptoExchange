package rs.sloman.cryptoexchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.databinding.FragmentCurrencyListBinding
import rs.sloman.cryptoexchange.viewmodels.CryptoViewModel

@AndroidEntryPoint
class CurrencyListFragment : Fragment(R.layout.fragment_currency_list) {

    private val viewModel: CryptoViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCurrencyListBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        return binding.root
    }
}