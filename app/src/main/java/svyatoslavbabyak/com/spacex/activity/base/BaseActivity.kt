package svyatoslavbabyak.com.spacex.activity.base

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import svyatoslavbabyak.com.spacex.fragment.base.BaseFragment
import svyatoslavbabyak.com.spacex.ui.view.base.BaseMVPView

abstract class BaseFragmentActivity : AppCompatActivity(), BaseMVPView {

    fun replaceFragment(fragment: Fragment, fragmentContainerId : Int, tag : String) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount != 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    fun getCurrentFragment(): Fragment? {
        val fragmentManager = supportFragmentManager

        if(fragmentManager.backStackEntryCount > 0) {
            val fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
            return fragmentManager.findFragmentByTag(fragmentTag)
        }

        return null
    }

    override fun showSimpleError(errorTitle: String, msg: String?) {
    }

    override fun showSimpleError(msg: String?) {
    }
}