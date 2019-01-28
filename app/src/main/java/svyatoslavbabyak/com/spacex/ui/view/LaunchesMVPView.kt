package svyatoslavbabyak.com.spacex.ui.view

import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.entity.LaunchFilter
import svyatoslavbabyak.com.spacex.ui.view.base.BaseFragmentMVPView

interface LaunchesMVPView: BaseFragmentMVPView {
    fun showLaunches(launchesList: ArrayList<Launch>?)
    fun showFilters(launchFilter: LaunchFilter?)
    fun showLaunchDetails(launch: Launch)
}