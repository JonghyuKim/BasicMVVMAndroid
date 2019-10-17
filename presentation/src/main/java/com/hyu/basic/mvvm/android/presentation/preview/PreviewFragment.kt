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
import androidx.recyclerview.widget.RecyclerView
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
            recyclerViewSetting(recyclerView)
            model = viewModel
            lifecycleOwner = this@PreviewFragment.viewLifecycleOwner
        }.root
    }

    private fun recyclerViewSetting(recyclerView: RecyclerView){
        recyclerView.apply {

            adapter = PreviewListAdapter(selectViewModel)

            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    startPostponedEnterTransition()
                    return true
                }
            })

            addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
                override fun onLayoutChange(v: View?,left: Int,top: Int,right: Int,bottom: Int,oldLeft: Int,oldTop: Int,oldRight: Int,oldBottom: Int) {

                    removeOnLayoutChangeListener(this)

                    val position = selectViewModel.selectIndex.value!!

                    layoutManager?.let{layoutManager ->
                        val viewAtPosition = layoutManager.findViewByPosition(position)

                        if (viewAtPosition == null || layoutManager.isViewPartiallyVisible(viewAtPosition,false,true)) {
                            post { layoutManager.scrollToPosition(position) }
                        }
                    }


                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }

    companion object{
        const val TAG = "PreviewFragment"
    }
}
