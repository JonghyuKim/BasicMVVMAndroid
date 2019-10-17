package com.hyu.basic.mvvm.android.di

import com.hyu.basic.mvvm.android.domain.data.AmiiboData
import com.hyu.basic.mvvm.android.domain.datasource.IDataRepository
import com.hyu.basic.mvvm.android.domain.model.AmiiboModel
import com.hyu.basic.mvvm.android.domain.parser.AmiiboDataParser
import com.hyu.basic.mvvm.android.domain.parser.IDataParser
import com.hyu.basic.mvvm.android.domain.repository.AmiiboDataRepository
import com.hyu.basic.mvvm.android.domain.requester.IDataRequester
import com.hyu.basic.mvvm.android.domain.requester.JsonFileRequester
import com.hyu.basic.mvvm.android.domain.requester.OkHttpRequester
import com.hyu.basic.mvvm.android.domain.usecase.GetAmiiboModelUseCase
import com.hyu.basic.mvvm.android.domain.usecase.IUseCase
import com.hyu.basic.mvvm.android.presentation.util.imageloader.IImageLoader
import com.hyu.basic.mvvm.android.presentation.util.imageloader.ImageDataProxy
import com.hyu.basic.mvvm.android.presentation.util.imageloader.ImageGlide
import org.koin.dsl.module


// DI 위치 고민 필요...
val amiiboModule = module{}

val presenterModule = module{}

val dataModule = module{

//        factory<IImageLoader>            { ImageDataProxy() }
    factory<IDataRequester<String>>  { JsonFileRequester(get()) }
    factory<IImageLoader>            { ImageGlide() }
//    factory<IDataRequester<String>>  { OkHttpRequester() }

    factory<IDataParser<String, List<AmiiboData>>> { AmiiboDataParser() }
    single<IDataRepository<AmiiboModel>> { AmiiboDataRepository() }
    factory<IUseCase<List<AmiiboModel>>> { GetAmiiboModelUseCase() }
}
