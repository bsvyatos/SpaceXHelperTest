package svyatoslavbabyak.com.spacex.ui.view

import svyatoslavbabyak.com.spacex.entity.CompanyInfo
import svyatoslavbabyak.com.spacex.ui.view.base.BaseFragmentMVPView

interface CompanyInfoMVPView: BaseFragmentMVPView {
    fun showInfo(companyInfo: CompanyInfo)
}