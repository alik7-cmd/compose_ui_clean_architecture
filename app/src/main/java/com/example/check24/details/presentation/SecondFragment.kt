package com.example.check24.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.check24.R
import com.example.check24.common.ui.AppConstant.WEB_URL
import com.example.check24.databinding.FragmentSecondBinding
import com.example.check24.loadImageFromWeb
import com.example.check24.openWebPageWith
import com.example.check24.overview.domain.entity.ProductEntity
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment(), OnClickListener {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mViewModel: ProductDetailsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.data = SecondFragmentArgs.fromBundle(requireArguments()).data
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.footer.tvFooter.setOnClickListener(this)
        binding.btnFav.setOnClickListener(this)

        mViewModel.data?.let { updateUiWith(it) }
    }

    private fun updateUiWith(data: ProductEntity) {
        with(binding) {
            ivImage.loadImageFromWeb(data.imageURL)
            tvName.text = data.name
            tvDescription.text = data.description
            tvDate.text = data.releaseDate
            ratingBar.rating = data.rating
            tvLongDescription.text = data.longDescription
            tvPrice.text = "${data.price} ${data.currency}"
            updateButtonText(data.isLiked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateButtonText(isLiked : Boolean){
        when(isLiked){
            true -> binding.btnFav.text = resources.getString(R.string.text_unselect_fav)
            false -> binding.btnFav.text = resources.getString(R.string.text_select_fav)
        }
    }

    private fun updateFavStatus() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            when (mViewModel.data!!.isLiked) {
                true -> {
                    val data = mViewModel.data!!.apply {
                        isLiked = false
                    }
                    mViewModel.delete(data)
                    binding.btnFav.text = resources.getString(R.string.text_select_fav)
                }
                false -> {
                    val data = mViewModel.data!!.apply {
                        isLiked = true
                    }
                    mViewModel.insert(data)
                    binding.btnFav.text = resources.getString(R.string.text_unselect_fav)
                }
            }
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_footer -> openWebPageWith(WEB_URL)
            R.id.btn_fav -> updateFavStatus()
        }
    }
}