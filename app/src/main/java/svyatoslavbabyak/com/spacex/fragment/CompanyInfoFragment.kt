package svyatoslavbabyak.com.spacexhelper.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_company_info.*
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.adapter.LaunchesListAdapter
import svyatoslavbabyak.com.spacex.entity.CompanyInfo
import svyatoslavbabyak.com.spacex.fragment.base.BaseFragment
import svyatoslavbabyak.com.spacex.rest.SpaceXClient
import svyatoslavbabyak.com.spacex.ui.presenter.CompanyInfoPresenterImpl
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchesPresenter
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchesPresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.CompanyInfoMVPView
import svyatoslavbabyak.com.spacex.ui.view.LaunchesMVPView
import java.text.DecimalFormat

class CompanyInfoFragment: BaseFragment(), CompanyInfoMVPView {

    private var presenter: CompanyInfoPresenterImpl<CompanyInfoMVPView>? = null
    private lateinit var disposable: CompositeDisposable

    companion object {
        const val TAG = "CompanyInfoFragment"

        fun newInstance() = CompanyInfoFragment()

        fun newInstance(bundle: Bundle) : CompanyInfoFragment {
            val companyInfoFragment = CompanyInfoFragment()
            companyInfoFragment.arguments = bundle
            return companyInfoFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_company_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disposable = CompositeDisposable()
        presenter = CompanyInfoPresenterImpl(SpaceXClient.create(), disposable)
        presenter?.attachView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun bindData() {
        if(arguments != null) {
            presenter?.loadFromData(arguments)
        } else {
            presenter?.loadData()
        }
    }

    override fun saveData(): Bundle? = presenter?.saveData()

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun showInfo(companyInfo: CompanyInfo) {
        val formatter = DecimalFormat("##,###,###,###")
        founderTxt?.text = companyInfo.founder + " " + companyInfo.founded.toString()
        companyHQ?.text = companyInfo.headquarters.address + ", " + companyInfo.headquarters.city + ", " + companyInfo.headquarters.state
        summary?.text = companyInfo.summary
        ceoTxt?.text = companyInfo.ceo
        ctoTxt?.text = companyInfo.cto
        cooTxt?.text = companyInfo.coo
        ctoPropTxt?.text = companyInfo.ctoPropulsion
        valueTxt?.text = companyInfo.valuation.toString().format(formatter)
        testSitesTxt?.text = companyInfo.testSites.toString().format(formatter)
        employeesTxt?.text = companyInfo.employees.toString()
        launchSitesTxt?.text = companyInfo.launchSites.toString()
        vehiclesTxt?.text = companyInfo.vehicles.toString()
    }
}