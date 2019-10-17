package com.hyu.basic.mvvm.a

import android.os.Build
import com.hyu.basic.mvvm.android.presentation.preview.AmiiboViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyu.basic.mvvm.android.presentation.SelectViewModel
import com.hyu.basic.mvvm.android.presentation.base.BaseDiffCallback
import com.hyu.basic.mvvm.android.presentation.base.BaseListAdapter
import com.hyu.basic.mvvm.android.presentation.base.BaseViewHolder
import com.hyu.basicmvvmandroid.presentation.BR
import com.hyu.basicmvvmandroid.presentation.R
import com.hyu.basicmvvmandroid.presentation.databinding.LayerItemPreviewBigSizeBinding
import com.hyu.basicmvvmandroid.presentation.databinding.LayerItemPreviewBinding


class PreviewListAdapter(val selectViewModel: SelectViewModel) : BaseListAdapter<AmiiboViewModel>() {
    override val diffCallBack = PreviewDiffCallback()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<AmiiboViewModel> {
        return if(viewType == 0) PreviewItemHolder.Base(parent) else PreviewItemHolder.BigSize(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AmiiboViewModel>, position: Int) {
        with(holder.dataBinding){
            setVariable(BR.model, itemList!![position])
            setVariable(BR.selectViewModel, selectViewModel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                root.findViewById<View>(R.id.iv_preview_image).transitionName = "previewItem$position"
            }
        }
    }

    companion object{
        @BindingAdapter("preview:setItemList")
        @JvmStatic
        fun setItemList(recyclerView : RecyclerView, list : List<AmiiboViewModel>?){
            val adapter = recyclerView.adapter as? PreviewListAdapter ?: return
            adapter.itemList = list
        }
    }
}

sealed class PreviewItemHolder{
    class Base(parent: ViewGroup) : BaseViewHolder<AmiiboViewModel>(
        LayerItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class BigSize(parent: ViewGroup) : BaseViewHolder<AmiiboViewModel>(
        LayerItemPreviewBigSizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}


class PreviewDiffCallback : BaseDiffCallback<AmiiboViewModel>() {

    private var old: List<AmiiboViewModel>? = null
    private var new: List<AmiiboViewModel>? = null

    override fun create(
        oldList: List<AmiiboViewModel>?,
        newList: List<AmiiboViewModel>?
    ): DiffUtil.Callback {
        old = oldList
        new = newList
        return this
    }

    override fun destroy() {
        old = null
        new = null
    }

    override fun getOldListSize(): Int = old?.size ?: 0
    override fun getNewListSize(): Int = new?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old?.get(oldItemPosition) == new?.get(newItemPosition)


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old?.get(oldItemPosition)?.model?.name == new?.get(newItemPosition)?.model?.name
}