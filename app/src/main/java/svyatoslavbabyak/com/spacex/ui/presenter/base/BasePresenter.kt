package svyatoslavbabyak.com.spacex.ui.presenter.base

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import svyatoslavbabyak.com.spacex.ui.view.base.BaseMVPView

interface BasePresenter<V : BaseMVPView> {
    fun attachView(view: V)
    fun detachView()
    fun getView() : V?
    fun saveData() : Bundle?
    fun loadFromData(bundle: Bundle?)
    fun onResume()
}

abstract class BasePresenterImpl<V : BaseMVPView> internal constructor(protected val compositeDisposable: CompositeDisposable) : BasePresenter<V> {
    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        compositeDisposable.dispose()
        view = null
    }

    override fun getView(): V? = view
}