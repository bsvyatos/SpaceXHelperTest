package svyatoslavbabyak.com.spacex.fragment.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.ui.view.base.BaseFragmentMVPView

abstract class BaseFragment: Fragment(), BaseFragmentMVPView {

    override fun showSimpleError(errorTitle: String, msg: String?) {
        alert(msg ?: "") {
            title = errorTitle
            okButton {  }
        }.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    override fun showSimpleError(msg: String?) {
        showSimpleError(context!!.getString(R.string.simple_error_title), msg)
    }

    override fun provideContext(): Context? = context

    abstract fun bindData()
    abstract fun saveData(): Bundle?
}
