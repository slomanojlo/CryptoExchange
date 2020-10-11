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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import rs.sloman.cryptoexchange.R
import rs.sloman.cryptoexchange.adapters.CryptoAdapter
import rs.sloman.cryptoexchange.databinding.FragmentCurrencyListBinding
import rs.sloman.cryptoexchange.repo.Repo
import rs.sloman.cryptoexchange.viewmodels.CryptoViewModel
import timber.log.Timber
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


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
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rwCryptos.apply {
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            binding.rwCryptos.setHasFixedSize(true)
            binding.rwCryptos.adapter = CryptoAdapter(CryptoAdapter.OnClickListenerCrypto {
                val action = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyDetailFragment(it)
                findNavController().navigate(action)
            })
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadingData()
    }

}