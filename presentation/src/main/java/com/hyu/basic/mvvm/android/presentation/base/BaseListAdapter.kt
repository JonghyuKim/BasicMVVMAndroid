package com.hyu.basic.mvvm.android.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyu.basicmvvmandroid.presentation.BR

abstract class BaseListAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>(){

    abstract val diffCallBack : BaseDiffCallback<T>

    var itemList: List<T>? = null
    set(value) {
        val diffResult = DiffUtil.calculateDiff(diffCallBack.create(field, value))
        field = value
        diffResult.dispatchUpdatesTo(this)
        diffCallBack.destroy()
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.dataBinding.setVariable(BR.model, itemList!![position])
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>) {
        holder.unbindViewModel()
        super.onViewDetachedFromWindow(holder)
    }
}

open class BaseViewHolder<T>(val dataBinding: ViewDataBinding): RecyclerView.ViewHolder(dataBinding.root){

    open fun unbindViewModel() {}
}

abstract class BaseDiffCallback<T> : DiffUtil.Callback() {
    abstract fun create(oldList : List<T>?, newList : List<T>?) : DiffUtil.Callback
    abstract fun destroy()
}

