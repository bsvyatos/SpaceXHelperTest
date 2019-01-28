package svyatoslavbabyak.com.spacex.ui.presenter

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenter
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.MainMVPView

interface MainPresenter<V : MainMVPView> : BasePresenter<V> {
    fun onDrawerLaunchesClick() : Unit?
    fun onDrawerCompanyInfoClick(): Unit?
}

class MainPresenterImpl<V : MainMVPView> (compositeDisposable: CompositeDisposable): BasePresenterImpl<V>(compositeDisposable = compositeDisposable), MainPresenter<V> {

    override fun attachView(view: V) {
        super.attachView(view)
        getView()?.showDefaultFragment()
    }

    override fun onDrawerLaunchesClick() = getView()?.showLaunchesFragment()

    override fun onDrawerCompanyInfoClick() = getView()?.showCompanyInfoFragment()

    override fun saveData(): Bundle? = null

    override fun loadFromData(bundle: Bundle?) { }

    override fun onResume() { }
}