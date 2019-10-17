package com.hyu.basic.mvvm.android.presentation.preview

import android.os.Build

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.hyu.basic.mvvm.android.presentation.SelectViewModel
import com.hyu.basic.mvvm.android.presentation.base.BaseDiffCallback
import com.hyu.basic.mvvm.android.presentation.base.BaseListAdapter
import com.hyu.basic.mvvm.android.presentation.base.BaseViewHolder
import com.hyu.basicmvvmandroid.presentation.BR
import com.hyu.basicmvvmandroid.presentation.R


class PreviewListAdapter(val selectViewModel: SelectViewModel) : BaseListAdapter<AmiiboViewModel>(PreviewDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return if(position % 2 == 0) R.layout.layer_item_preview else R.layout.layer_item_preview_big_size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<AmiiboViewModel>, position: Int) {
        with(holder.dataBinding){
            setVariable(BR.model, itemList!![position])
            setVariable(BR.selectViewModel, selectViewModel)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                root.findViewById<View>(R.id.iv_preview_image).transitionName = "previewItem$position"
            }
            executePendingBindings()
        }
    }
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