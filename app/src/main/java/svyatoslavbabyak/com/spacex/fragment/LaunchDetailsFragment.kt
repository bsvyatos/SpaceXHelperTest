package svyatoslavbabyak.com.spacex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_launch_details.*
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.fragment.base.BaseFragment
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchDetailsPresenter
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchDetailsPresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.LaunchDetailsMVPView
import svyatoslavbabyak.com.spacex.utility.loadImage
import java.text.DateFormat

class LaunchDetailsFragment: BaseFragment(), LaunchDetailsMVPView {
    private var presenter: LaunchDetailsPresenter<LaunchDetailsMVPView>? = null
    private lateinit var disposable: CompositeDisposable

    companion object {
        const val TAG = "LaunchDetailsFragment"
        private const val LAUNCH_DATA_KEY = "LAUNCH_DATA_KEY"


        fun newInstance(bundle: Bundle) : LaunchDetailsFragment {
            val launchDetailsFragment = LaunchDetailsFragment()
            launchDetailsFragment.arguments = bundle
            return launchDetailsFragment
        }

        fun newInstance(launch: Launch) : LaunchDetailsFragment {
            val launchDetailsFragment = LaunchDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(LAUNCH_DATA_KEY, launch)
            launchDetailsFragment.arguments = bundle
            return launchDetailsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_launch_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disposable = CompositeDisposable()
        presenter = LaunchDetailsPresenterImpl(disposable)
        presenter?.attachView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onDestroyView() {
        presenter?.detachView()
        super.onDestroyView()
    }

    override fun bindData() {
        //This doesn't make much sense because arguments can only contain Launch data. But in theory presenter could save for himself
        // some kind of different data. So I separated it just in case.
        if (arguments != null) {
            if(arguments!!.getParcelable<Launch>(LAUNCH_DATA_KEY) != null) {
                presenter?.loadFromData(arguments!!.getParcelable<Launch>(LAUNCH_DATA_KEY)!!)
            } else {
                presenter?.loadFromData(arguments)
            }
        }
    }

    override fun saveData(): Bundle? = presenter?.saveData()

    override fun showProgress() { }

    override fun hideProgress() { }

    override fun showLaunch(launch: Launch) {
        missionNameTxt.text = launch.missionName
        missionDateTxt.text = DateFormat.getInstance().format(launch.launchDateUTC)
        flightNumberTxt.text = launch.flightNumber.toString()
        rocketNameTxt.text = launch.rocket.rocketName
        rocketTypeTxt.text = launch.rocket.rocketType
        missionPatch.loadImage(launch.links.missionPatchSmall)
        val recoveryInfoArray = resources.getStringArray(R.array.recovery_info_array)
        rocketRecoveryAttemptTxt.text = if(launch.rocket.fairings?.recoveryAttempt == null) { recoveryInfoArray[0] } else if(launch.rocket.fairings.recoveryAttempt) { recoveryInfoArray[1] } else { recoveryInfoArray[2] }
        rocketRecoverySuccessTxt.text = if(launch.rocket.fairings?.recovered == null) { recoveryInfoArray[0] } else if(launch.rocket.fairings.recovered) { recoveryInfoArray[1] } else { recoveryInfoArray[2] }
        rocketReusedTxt.text = if(launch.rocket.fairings?.reused == null) { recoveryInfoArray[0] } else if(launch.rocket.fairings.reused)  { recoveryInfoArray[1] } else { recoveryInfoArray[2] }
    }
}