package com.hyu.basic.mvvm.android.domain.parser

import com.google.gson.Gson
import com.hyu.basic.mvvm.android.domain.data.AmiiboData
import com.hyu.basic.mvvm.android.domain.data.AmiiboDatas
import io.reactivex.Observable

class AmiiboDataParser : IDataParser<String, List<AmiiboData>> {
    override fun parse(dataObservable : Observable<String>): Observable<List<AmiiboData>> {
        return dataObservable.map {
            Gson().fromJson(it , AmiiboDatas::class.java).amiiboList
        }
    }

    override fun release() {

    }
}