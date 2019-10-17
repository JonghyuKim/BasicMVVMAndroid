package com.hyu.basic.mvvm.android.presentation.preview

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.hyu.basic.mvvm.android.domain.model.AmiiboModel
import com.hyu.basic.mvvm.android.domain.usecase.GetAmiiboModelUseCase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent


class PreviewViewModel : ViewModel(), KoinComponent {

    val listItems by lazy {

        fun convertViewModel(modelList : List<AmiiboModel>) : Single<List<AmiiboViewModel>> {

            val list = mutableListOf<AmiiboViewModel>()

            modelList.forEachIndexed { index, amiiboModel ->
                list.add(AmiiboViewModel(amiiboModel, false, index))
            }

            return Single.just(list)
        }

        val process = GetAmiiboModelUseCase().excute()
            .subscribeOn(Schedulers.io())
            .flatMap(::convertViewModel)
            .observeOn(AndroidSchedulers.mainThread())

        LiveDataReactiveStreams.fromPublisher(process.toFlowable())
    }
}

data class AmiiboViewModel(val model : AmiiboModel, var isSelected : Boolean, var listIndex : Int = 0)