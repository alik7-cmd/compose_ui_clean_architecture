package com.example.check24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.check24.common.ui.AppConstant.WEB_URL
import com.example.check24.databinding.FragmentFirstBinding
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity
import com.example.check24.overview.presentation.OnProductClickListener
import com.example.check24.overview.presentation.ProductOverviewAdapter
import com.example.check24.overview.presentation.ProductOverviewUiState
import com.example.check24.overview.presentation.ProductOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment(), OnClickListener {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mViewModel: ProductOverviewViewModel by viewModels()

    private lateinit var mAdapter: ProductOverviewAdapter

    private val listener = object : OnProductClickListener {
        override fun onClick(data: ProductEntity?, isFooterClicked: Boolean) {
            if (isFooterClicked) {
                openWebPageWith(WEB_URL)
            } else {
                navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(data!!))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.getProduceOverview(FilterCategory.ALL)
        binding.btnAll.setOnClickListener(this)
        binding.btnAvailable.setOnClickListener(this)
        binding.btnFavourite.setOnClickListener(this)
        binding.refreshContainer.setOnClickListener(this)
        observe()
    }


    private fun observe() {
        mViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state -> handleUi(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleUi(state: ProductOverviewUiState) {

        when (state) {
            is ProductOverviewUiState.Success -> {
                updateUiLoading(false)
                binding.rvProductOverview.layoutManager = LinearLayoutManager(requireActivity())
                mAdapter = ProductOverviewAdapter(listener).apply {
                    submitList(state.list)
                }
                binding.rvProductOverview.adapter = mAdapter
            }

            is ProductOverviewUiState.Error -> {
                binding.refreshContainer.visibility = View.VISIBLE
                updateUiLoading(true)
                binding.progress.visibility = View.GONE
            }
            is ProductOverviewUiState.Init -> Unit
            is ProductOverviewUiState.Loading -> updateUiLoading(state.isLoading)
        }

    }

    private fun updateUiLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.rvProductOverview.visibility = View.GONE
                binding.layoutFilterContainer.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
                binding.refreshContainer.visibility = View.GONE
            }
            false -> {
                binding.rvProductOverview.visibility = View.VISIBLE
                binding.layoutFilterContainer.visibility = View.VISIBLE
                binding.progress.visibility = View.GONE
                binding.refreshContainer.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_all -> {
                mViewModel.getProduceOverview(FilterCategory.ALL)
                mViewModel.savedFilter = FilterCategory.ALL
            }

            R.id.btn_available -> {
                mViewModel.getProduceOverview(FilterCategory.AVAILABLE)
                mViewModel.savedFilter = FilterCategory.AVAILABLE
            }

            R.id.btn_fav ->{
                mViewModel.getProduceOverview(FilterCategory.AVAILABLE)
                mViewModel.savedFilter = FilterCategory.AVAILABLE
            }

            R.id.refresh_container ->{
                mViewModel.getProduceOverview(mViewModel.savedFilter)
            }
        }
    }
}