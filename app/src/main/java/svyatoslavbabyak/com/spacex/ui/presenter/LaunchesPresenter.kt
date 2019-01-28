package svyatoslavbabyak.com.spacex.ui.presenter

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.entity.LaunchFilter
import svyatoslavbabyak.com.spacex.rest.SpaceXClient
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenter
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.LaunchesMVPView
import svyatoslavbabyak.com.spacex.utility.Utils

interface LaunchesPresenter <V: LaunchesMVPView>: BasePresenter<V> {
    fun loadData()
    fun filterClick()
    fun setFilter(launchFilter: LaunchFilter)
    fun launchesAdapterItemSelected(launch: Launch)
}

class LaunchesPresenterImpl <V: LaunchesMVPView> constructor(private val api: SpaceXClient, disposable: CompositeDisposable)
    :BasePresenterImpl<V>(compositeDisposable = disposable), LaunchesPresenter<V> {

    private var launchesList: ArrayList<Launch>? = null
    private var launchFilter: LaunchFilter? = null

    companion object {
        private const val LAUNCHES_DATA_KEY = "LAUNCHES_DATA_KEY"
    }

    override fun saveData(): Bundle? {
        val bundle = Bundle()
        bundle.putParcelableArrayList(LAUNCHES_DATA_KEY, launchesList)
        return bundle
    }

    override fun loadFromData(bundle: Bundle?) {
        launchesList = bundle?.getParcelableArrayList(LAUNCHES_DATA_KEY)
        if(launchesList == null) {
            loadData()
        } else {
            getView()?.showLaunches(launchesList)
        }
    }

    override fun onResume() {
        if (launchesList != null) {
            getView()?.showLaunches(launchesList)
        }
    }

    override fun filterClick() {
        getView()?.showFilters(launchFilter)
    }

    override fun setFilter(launchFilter: LaunchFilter) {
        this.launchFilter = launchFilter
        loadData()
    }

    override fun launchesAdapterItemSelected(launch: Launch) {
        getView()?.showLaunchDetails(launch)
    }

    override fun loadData() {
        val hasNetwork = Utils.hasNetwork(getView()?.provideContext()!!)

        if(hasNetwork != null && !hasNetwork) {
            getView()?.showSimpleError(getView()?.provideContext()?.resources?.getString(R.string.no_internet_error))
            return
        }

        getView()?.showProgress()
        compositeDisposable.addAll(api.getLaunches(launchFilter?.launchYear)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                launchesListRet -> getView()?.hideProgress()
                launchesList = launchesListRet
                getView()?.showLaunches(launchesList)
            }, {
                    e -> getView()?.hideProgress()
                if (e is RuntimeException) {
                    throw e
                } else {
                    getView()?.showSimpleError(e?.message)
                }
            }))
    }
}
