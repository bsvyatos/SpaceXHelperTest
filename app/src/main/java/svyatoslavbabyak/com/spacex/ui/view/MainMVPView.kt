package svyatoslavbabyak.com.spacex.ui.view

import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.ui.view.base.BaseMVPView

interface MainMVPView : BaseMVPView {
    fun showLaunchesFragment()
    fun showCompanyInfoFragment()
    fun showLaunchDetailsFragment(launch: Launch?)
    fun lockDrawer(): Unit?
    fun unlockDrawer(): Unit?
    fun showDefaultFragment()
}