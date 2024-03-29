package com.hyu.basic.mvvm.android.domain.repository

import com.hyu.basic.mvvm.android.domain.datasource.IDataRepository
import com.hyu.basic.mvvm.android.domain.model.AmiiboModel
import com.hyu.basic.mvvm.android.domain.data.AmiiboData
import com.hyu.basic.mvvm.android.domain.parser.IDataParser
import com.hyu.basic.mvvm.android.domain.requester.IDataRequester
import io.reactivex.Observable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class AmiiboDataRepository : IDataRepository<AmiiboModel>, KoinComponent{
    private val parser by inject<IDataParser<String, List<AmiiboData>>>()
    private val requester by inject<IDataRequester<String>>()

    private var cacheDataList : List<AmiiboData>? = null

    override fun loadData(): Single<List<AmiiboModel>> {

        val dataObserbable = cacheDataList?.let {
                                                    Observable.just(cacheDataList!!)
                                                }
                                          ?: parser.parse(requester.request(PATH_LOCAL_DUMMY_DATA))
                                                   .doOnNext {
                                                        cacheDataList = it
                                                   }

        return dataObserbable.flatMap {
                Observable.fromIterable(it)
            }
            .map(::convertDataToModel)
            .toList()
    }

    private fun convertDataToModel(data : AmiiboData) : AmiiboModel {
        return AmiiboModel(data.amiiboSeries,data.character,data.gameSeries,data.image,data.name)
    }

    override fun release() {
    }

    companion object {
        private val PATH_LOCAL_DUMMY_DATA = "amiibo_dummy.json"
        private val PATH_OPEN_API_DOMAIN = "https://www.amiiboapi.com/"
        private val PATH_AMIIBO_PATH = PATH_OPEN_API_DOMAIN + "api/amiibo"
    }
}