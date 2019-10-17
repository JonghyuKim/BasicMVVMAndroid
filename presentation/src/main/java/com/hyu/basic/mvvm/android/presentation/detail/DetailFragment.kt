package com.hyu.basic.mvvm.android.presentation.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hyu.basic.mvvm.android.presentation.SelectViewModel
import com.hyu.basic.mvvm.android.presentation.util.imageloader.IImageLoader
import com.hyu.basicmvvmandroid.presentation.R
import com.hyu.basicmvvmandroid.presentation.databinding.FragmentDetailBinding
import org.koin.android.ext.android.inject

class DetailFragment : Fragment(){

    private val selectViewModel : SelectViewModel by activityViewModels()
    private val imageLoader by inject<IImageLoader>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentDetailBinding>(inflater, R.layout.fragment_detail, container, false).apply {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivDetailMainImage.transitionName = "previewItem${selectViewModel.selectIndex}"
            }
            with(imageLoader) {

                onCompliteBinding = {
                    startPostponedEnterTransition()
                }

                bindImg(requireContext(), ivDetailMainImage, selectViewModel.selectModel.value!!.image)
            }
            selectModel = selectViewModel
            lifecycleOwner = this@DetailFragment.viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }
}