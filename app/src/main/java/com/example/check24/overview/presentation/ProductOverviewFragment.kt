package com.example.check24.overview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.check24.R
import com.example.check24.common.ui.AppConstant.WEB_URL
import com.example.check24.databinding.FragmentFirstBinding
import com.example.check24.navigate
import com.example.check24.openWebPageWith
import com.example.check24.overview.domain.FilterCategory
import com.example.check24.overview.domain.entity.ProductEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ProductOverviewFragment : Fragment(), OnClickListener, SwipeRefreshLayout.OnRefreshListener {

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
                navigate(ProductOverviewFragmentDirections.actionFirstFragmentToSecondFragment(data!!))
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
        binding.swipeRefresh.setOnRefreshListener(this)
        mAdapter = ProductOverviewAdapter(listener)
        observeUiState()
    }


    private fun observeUiState() {
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
                mAdapter.submitList(state.list)
                /*if (state.list.isEmpty()) {
                    binding.refreshContainer.visibility = View.VISIBLE
                    binding.tvError.text = resources.getString(R.string.text_no_data_found)
                }*/
                binding.rvProductOverview.adapter = mAdapter
            }

            is ProductOverviewUiState.Error -> {
                updateUiLoading(true)
                binding.refreshContainer.visibility = View.VISIBLE
            }
            is ProductOverviewUiState.Init -> Unit
            is ProductOverviewUiState.Loading -> updateUiLoading(true)
        }

    }

    private fun updateUiLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.rvProductOverview.visibility = View.GONE
                binding.layoutFilterContainer.visibility = View.GONE
                binding.refreshContainer.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = true
            }
            false -> {
                binding.rvProductOverview.visibility = View.VISIBLE
                binding.layoutFilterContainer.visibility = View.VISIBLE
                binding.refreshContainer.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
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

            R.id.btn_favourite -> {
                mViewModel.getProduceOverview(FilterCategory.FAVOURITE)
                mViewModel.savedFilter = FilterCategory.FAVOURITE
            }

            R.id.refresh_container -> {
                binding.refreshContainer.visibility = View.GONE
                mViewModel.getProduceOverview(mViewModel.savedFilter)
            }
        }
    }

    override fun onRefresh() {
        mViewModel.getProduceOverview(FilterCategory.ALL, true)
    }
}