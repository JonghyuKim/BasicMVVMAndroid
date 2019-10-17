package com.hyu.basic.mvvm.android.domain.usecase

import io.reactivex.Single

interface IUseCase<T> {
    fun excute() : Single<T>
}