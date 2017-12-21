package io.smily.rxjavadigitas

import android.app.Activity
import android.view.View
import kotlin.LazyThreadSafetyMode.NONE

inline fun <reified T : View> Activity.bindView(id: Int) = lazy(NONE) { findViewById(id) as T }
inline fun <reified T : View> View.bindView(id: Int) = lazy(NONE) { findViewById(id) as T }