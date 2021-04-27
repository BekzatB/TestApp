package kz.aviata.utils

import android.view.View

fun View.visible(isVisible: Boolean, visibilityType: Int = View.INVISIBLE){
    visibility = if (isVisible) View.VISIBLE else visibilityType
}
