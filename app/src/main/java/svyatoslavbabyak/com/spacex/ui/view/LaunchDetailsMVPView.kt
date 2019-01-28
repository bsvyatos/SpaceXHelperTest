package svyatoslavbabyak.com.spacex.ui.view

import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.ui.view.base.BaseFragmentMVPView


interface LaunchDetailsMVPView: BaseFragmentMVPView {
    fun showLaunch(launch: Launch)
}