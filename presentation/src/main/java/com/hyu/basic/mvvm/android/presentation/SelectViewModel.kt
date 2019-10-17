package com.hyu.basic.mvvm.android.presentation

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyu.basic.mvvm.android.domain.model.AmiiboModel

class SelectViewModel : ViewModel(){
    var selectIndex = MutableLiveData(-1)
    var selectModel = MutableLiveData(AmiiboModel.EmptyModel)
    var selectView = MutableLiveData<View>()


    fun selectView(view : View, index : Int, model : AmiiboModel){
        selectModel.value = model
        selectView.value = view
        selectIndex.value = index
    }
}