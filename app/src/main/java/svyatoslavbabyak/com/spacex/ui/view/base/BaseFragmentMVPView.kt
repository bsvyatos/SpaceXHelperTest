package svyatoslavbabyak.com.spacex.ui.view.base

import android.content.Context

interface BaseFragmentMVPView : BaseMVPView {
    fun showProgress()
    fun hideProgress()
    fun provideContext() : Context?
}