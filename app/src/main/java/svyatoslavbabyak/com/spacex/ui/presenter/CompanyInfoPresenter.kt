package svyatoslavbabyak.com.spacex.ui.presenter

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_filters.view.*
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.entity.CompanyInfo
import svyatoslavbabyak.com.spacex.rest.SpaceXClient
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenter
import svyatoslavbabyak.com.spacex.ui.presenter.base.BasePresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.CompanyInfoMVPView
import svyatoslavbabyak.com.spacex.utility.Utils

interface CompanyInfoPresenter <V: CompanyInfoMVPView>: BasePresenter<V> {
    fun loadData()
}

class CompanyInfoPresenterImpl <V: CompanyInfoMVPView> constructor(private val api: SpaceXClient, disposable: CompositeDisposable)
    : BasePresenterImpl<V>(compositeDisposable = disposable), CompanyInfoPresenter<V> {

    private var companyInfo: CompanyInfo? = null

    companion object {
        private const val COMPANY_INFO_DATA_KEY = "COMPANY_INFO_DATA_KEY"
    }

    override fun saveData(): Bundle? {
        val bundle = Bundle()
        bundle.putParcelable(COMPANY_INFO_DATA_KEY, companyInfo)
        return bundle
    }

    override fun loadFromData(bundle: Bundle?) {
        companyInfo = bundle?.getParcelable(COMPANY_INFO_DATA_KEY)
        if(companyInfo == null) {
            loadData()
        } else {
            getView()?.showInfo(companyInfo!!)
        }
    }

    override fun onResume() {
        if (companyInfo != null) {
            getView()?.showInfo(companyInfo!!)
        }
    }

    override fun loadData() {
        val hasNetwork = Utils.hasNetwork(getView()?.provideContext()!!)

        if(hasNetwork != null && !hasNetwork) {
            getView()?.showSimpleError(getView()?.provideContext()?.resources?.getString(R.string.no_internet_error))
            return
        }

        getView()?.showProgress()
        compositeDisposable.addAll(api.getCompanyInfo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    companyInfoRet -> getView()?.hideProgress()
                companyInfo = companyInfoRet
                getView()?.showInfo(companyInfo!!)
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