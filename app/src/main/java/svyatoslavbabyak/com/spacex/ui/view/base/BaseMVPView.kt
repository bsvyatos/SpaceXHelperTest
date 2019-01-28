package svyatoslavbabyak.com.spacex.ui.view.base

interface BaseMVPView {
    fun showSimpleError(errorTitle: String, msg : String?)
    fun showSimpleError(msg : String?)
}