package svyatoslavbabyak.com.spacex.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import svyatoslavbabyak.com.spacex.activity.base.BaseFragmentActivity
import svyatoslavbabyak.com.spacex.ui.view.MainMVPView
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.fragment.LaunchesFragment
import svyatoslavbabyak.com.spacex.fragment.base.BaseFragment
import svyatoslavbabyak.com.spacex.ui.presenter.MainPresenterImpl
import svyatoslavbabyak.com.spacexhelper.fragment.CompanyInfoFragment
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.fragment.LaunchDetailsFragment


class MainActivity : BaseFragmentActivity(), MainMVPView, NavigationView.OnNavigationItemSelectedListener,
    LaunchesFragment.LaunchesFragmentListener {

    private lateinit var disposable: CompositeDisposable
    private lateinit var presenter: MainPresenterImpl<MainMVPView>

    private var savedData : Bundle? = null

    companion object {
        private const val SAVE_OUT_STATE = "SAVE_OUT_STATE"
        private const val SAVE_OUT_FRAGMENT = "SAVE_OUT_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedData = savedInstanceState
        setContentView(R.layout.activity_main)
        disposable = CompositeDisposable()
        presenter = MainPresenterImpl(disposable)
        setUpDrawerMenu()
        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (getCurrentFragment() == null) {
            return
        }

        outState?.putString(SAVE_OUT_FRAGMENT, getCurrentFragment()?.tag)
        outState?.putBundle(SAVE_OUT_STATE, (getCurrentFragment() as BaseFragment).saveData())
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onAttachFragment(fragment: android.support.v4.app.Fragment?) {
        if (fragment is LaunchesFragment) {
            fragment.setLaunchesFragmentListener(this)
        }

        super.onAttachFragment(fragment)
    }

    override fun showLaunchesFragment() {
        val launchesFragment = if(savedData?.getBundle(SAVE_OUT_STATE) != null) {
            LaunchesFragment.newInstance(savedData!!.getBundle(SAVE_OUT_STATE)!!)
        } else {
            LaunchesFragment.newInstance()
        }

        savedData = null
        replaceFragment(launchesFragment, rootView.id, LaunchesFragment.TAG)
    }

    override fun showCompanyInfoFragment() {
        val companyInfoFragment = if(savedData?.getBundle(SAVE_OUT_STATE) != null) {
            CompanyInfoFragment.newInstance(savedData!!.getBundle(SAVE_OUT_STATE)!!)
        } else {
            CompanyInfoFragment.newInstance()
        }

        savedData = null
        replaceFragment(companyInfoFragment, rootView.id, LaunchesFragment.TAG)
    }

    override fun showLaunchDetailsFragment(launch: Launch?) {
        val launchDetailsFragment = when {
            savedData?.getBundle(SAVE_OUT_STATE) != null -> LaunchDetailsFragment.newInstance(savedData!!.getBundle(SAVE_OUT_STATE)!!)
            launch != null -> LaunchDetailsFragment.newInstance(launch)
            else -> return
        }

        savedData = null
        replaceFragment(launchDetailsFragment, rootView.id, LaunchDetailsFragment.TAG)
    }

    override fun showDefaultFragment() {
        if(savedData != null) {
            when (savedData!!.getString(SAVE_OUT_FRAGMENT)) {
                LaunchesFragment.TAG -> showLaunchesFragment()
                CompanyInfoFragment.TAG -> showCompanyInfoFragment()
                LaunchDetailsFragment.TAG -> showLaunchDetailsFragment(null)
            }
        } else {
            showLaunchesFragment()
        }
    }

    override fun lockDrawer() = drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    override fun unlockDrawer() = drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navLaunchesList -> {
                presenter.onDrawerLaunchesClick()
                title = resources.getString(R.string.menu_missions)
            }

            R.id.navCompanyDetails -> {
                presenter.onDrawerCompanyInfoClick()
                title = resources.getString(R.string.menu_info)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUpDrawerMenu() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun switchToLaunchDetailsFragment(launch: Launch) {
        showLaunchDetailsFragment(launch)
    }
}
