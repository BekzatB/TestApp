package kz.aviata.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    protected fun <T> LiveData<T>.subscribe(observer: (result: T) -> Unit) {
        this.observe(this@BaseFragment.viewLifecycleOwner, observer)
    }

}