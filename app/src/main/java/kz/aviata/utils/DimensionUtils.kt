package kz.aviata.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes
import kz.aviata.R

object DimensionUtils {

    fun displayHeightPx() = Resources.getSystem().displayMetrics.heightPixels

    fun displayWidthPx() = Resources.getSystem().displayMetrics.widthPixels

    fun dpToPx(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().displayMetrics)

    fun pxToDp(value: Float) = value / Resources.getSystem().displayMetrics.density

    fun dimenToPx(context: Context, @DimenRes resId: Int) = context.resources.getDimension(resId)
}