package com.hyu.basic.mvvm.android.presentation.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.hyu.basic.mvvm.android.presentation.SelectViewModel
import com.hyu.basicmvvmandroid.presentation.R
import com.hyu.basicmvvmandroid.presentation.databinding.FragmentPreviewBinding

class PreviewFragment : Fragment(){

    private val viewModel : PreviewViewModel by viewModels()
    private val selectViewModel : SelectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentPreviewBinding>(inflater, R.layout.fragment_preview, container, false).apply {
            recyclerView.apply {

                adapter = PreviewListAdapter(selectViewModel)
                model = viewModel
                lifecycleOwner = this@PreviewFragment.viewLifecycleOwner

                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)
                        startPostponedEnterTransition()
                        return true
                    }
                })
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }

    companion object{
        const val TAG = "PreviewFragment"
    }
}
