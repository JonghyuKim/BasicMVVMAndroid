package com.hyu.basic.mvvm.android.domain.model

data class AmiiboModel(val amiiboSeries : String,
                       val character : String,
                       val gameSeries : String,
                       override val image: String,
                       override val name: String
): IPreviewModel {
    companion object{
        val EmptyModel = AmiiboModel("", "", "", "", "")
    }
}