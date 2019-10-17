package com.hyu.basic.mvvm.android.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hyu.basicmvvmandroid.presentation.BR

abstract class BaseListAdapter<T>(private val diffCallBack : BaseDiffCallback<T>) : RecyclerView.Adapter<BaseViewHolder<T>>(){

    var itemList: List<T>? = null
    set(value) {
        val diffResult = DiffUtil.calculateDiff(diffCallBack.create(field, value))
        field = value
        diffResult.dispatchUpdatesTo(this)
        diffCallBack.destroy()
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): BaseViewHolder<T> {
        return BaseViewHolder(
            DataBindingUtil.bind(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )!!
        )
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

    open fun bindViewModel() {}
    open fun unbindViewModel() {}
}

abstract class BaseDiffCallback<T> : DiffUtil.Callback() {
    abstract fun create(oldList : List<T>?, newList : List<T>?) : DiffUtil.Callback
    abstract fun destroy()
}

object ListBindingAdapters{

    @BindingAdapter("baseList:itemList")
    @JvmStatic
    fun setItemList(recyclerView : RecyclerView, list : List<Any>?){
        val adapter = recyclerView.adapter as? BaseListAdapter<Any> ?: return
        adapter.itemList = list
    }
}
