package rs.sloman.cryptoexchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.cryptoexchange.CryptoCurrencyActivity
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.adapters.CryptoAdapter
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

        setupActionBar()

        val binding = FragmentCurrencyListBinding.inflate(layoutInflater)

        /** Giving the binding access to the OverviewViewModel. */
        binding.viewModel = viewModel

        /** Allows Data Binding to Observe LiveData with the lifecycle of this Fragment. */
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rwCryptos.apply {
            addItemDecoration(
                    DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                    )
            )
            setHasFixedSize(true)
            /**Setting up the ReviewListAdapter with its [CryptoAdapter.OnClickListenerCrypto]*/
            adapter = CryptoAdapter(CryptoAdapter.OnClickListenerCrypto {
                val action =
                        CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment(
                                it
                        )
                findNavController().navigate(action)
            })
        }

        binding.iwError.setOnClickListener {
            viewModel.retry()
        }

        return binding.root
    }

    private fun setupActionBar() {
        (activity as CryptoCurrencyActivity?)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
            title = getString(R.string.app_name)
        }
    }


}