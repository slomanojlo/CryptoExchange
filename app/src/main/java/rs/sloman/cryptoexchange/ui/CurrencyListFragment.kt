package rs.sloman.cryptoexchange.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    @Inject lateinit var repo: Repo
    lateinit var disposable: Disposable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCurrencyListBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.rwCryptos.addItemDecoration(
                DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                )
        )
        binding.rwCryptos.setHasFixedSize(true)
        binding.rwCryptos.adapter = CryptoAdapter(CryptoAdapter.OnClickListenerCrypto {})

        disposable = Observable.interval(
                1, 20, TimeUnit.SECONDS
        )
                .flatMap { repo.getCryptosRx() }
                .retryWhen {
                    it.map { throwable ->
                        if (throwable is UnknownHostException) {
                            Timber.d("Error")
                            throwable
                        } else {
                            throw throwable
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t ->
                            Timber.d(t.message)
                            viewModel.setCryptos(t)
                        },
                        { t -> Timber.d(t.message) },
                        {}
                )


        return binding.root
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

}