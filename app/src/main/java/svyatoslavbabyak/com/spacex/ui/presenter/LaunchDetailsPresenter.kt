package svyatoslavbabyak.com.spacex.ui.presenter

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenter
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.LaunchDetailsMVPView


interface LaunchDetailsPresenter <V: LaunchDetailsMVPView>: BasePresenter<V> {
    fun loadFromData(launch: Launch)
}

class LaunchDetailsPresenterImpl <V: LaunchDetailsMVPView> constructor(disposable: CompositeDisposable)
    : BasePresenterImpl<V>(compositeDisposable = disposable), LaunchDetailsPresenter<V> {

    private var launch: Launch? = null

    companion object {
        private const val LAUNCH_DATA_KEY = "LAUNCH_DATA_KEY"
    }

    override fun saveData(): Bundle? {
        val bundle = Bundle()
        bundle.putParcelable(LAUNCH_DATA_KEY, launch)
        return bundle
    }

    override fun loadFromData(bundle: Bundle?) {
        launch = bundle?.getParcelable(LAUNCH_DATA_KEY)
        if(launch == null) {
            return
        } else {
            loadFromData(launch!!)
        }
    }

    override fun loadFromData(launch: Launch) {
        getView()?.showLaunch(launch)
    }

    override fun onResume() {
        if(launch != null) {
            getView()?.showLaunch(launch!!)
        }
    }
}
