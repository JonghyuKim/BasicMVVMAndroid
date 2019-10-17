package com.hyu.basic.mvvm.android.presentation.util.log

import android.util.Log


fun whosCallMeNow(deps: Int) {
    val a = Throwable().stackTrace
    val userdeps = if (a.size > deps + 1) deps + 1 else a.size
    HLog.v("==========================================================================whosCallMeNow")
    for (i in 1 until userdeps) {
        HLog.v(String.format(
                " - %s.%s() (%s, %s)",
                a[i].className,
                a[i].methodName,
                a[i].fileName,
                a[i].lineNumber
            )
        )
    }
    HLog.v("==========================================================================end whosCallMeNow")
}