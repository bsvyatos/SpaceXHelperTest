package svyatoslavbabyak.com.spacex.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_filters.view.*
import kotlinx.android.synthetic.main.fragment_launches.*
import svyatoslavbabyak.com.spacex.R
import svyatoslavbabyak.com.spacex.adapter.CustomPlayListsRecyclerClickListener
import svyatoslavbabyak.com.spacex.adapter.LaunchesListAdapter
import svyatoslavbabyak.com.spacex.entity.Launch
import svyatoslavbabyak.com.spacex.entity.LaunchFilter
import svyatoslavbabyak.com.spacex.fragment.base.BaseFragment
import svyatoslavbabyak.com.spacex.rest.SpaceXClient
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchesPresenter
import svyatoslavbabyak.com.spacex.ui.presenter.LaunchesPresenterImpl
import svyatoslavbabyak.com.spacex.ui.view.LaunchesMVPView
class LaunchesFragment: BaseFragment(), LaunchesMVPView {
    private var presenter: LaunchesPresenter<LaunchesMVPView>? = null
    private lateinit var disposable: CompositeDisposable
    private lateinit var launchesListAdapter: LaunchesListAdapter
    private lateinit var launchesFragmentListener: LaunchesFragmentListener

    companion object {
        const val TAG = "LaunchesFragment"
        fun newInstance() = LaunchesFragment()

        fun newInstance(bundle: Bundle) : LaunchesFragment {
            val launchesFragment = LaunchesFragment()
            launchesFragment.arguments = bundle
            return launchesFragment
        }
    }

    fun setLaunchesFragmentListener(launchesFragmentListener: LaunchesFragmentListener) {
        this.launchesFragmentListener = launchesFragmentListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        launchesListAdapter = LaunchesListAdapter(ArrayList(), context!!, object: CustomPlayListsRecyclerClickListener {
            override fun onItemClick(v: View, launch: Launch) {
                presenter?.launchesAdapterItemSelected(launch)
            }
        })

        disposable = CompositeDisposable()
        presenter = LaunchesPresenterImpl(SpaceXClient.create(), disposable)
        presenter?.attachView(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_launches, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.actionFilter -> presenter?.filterClick()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun bindData() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val decorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.line_divider)!!)
        launchesRecyclerView.addItemDecoration(decorator)
        launchesRecyclerView.layoutManager = layoutManager
        launchesRecyclerView.itemAnimator = DefaultItemAnimator()
        launchesRecyclerView.adapter = launchesListAdapter

        if(arguments != null) {
            presenter?.loadFromData(arguments)
        } else {
            presenter?.loadData()
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showFilters(launchFilter: LaunchFilter?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_filters, null)
        var currFilter = launchFilter
        if (currFilter != null) {
            if(currFilter.launchYear != null) {
                dialogView.filterLaunchYearEdit.setText(currFilter.launchYear.toString())
            }
        } else {
            currFilter = LaunchFilter(null)
        }

        val builder = AlertDialog.Builder(context!!)
            .setView(dialogView)
            .setTitle(resources.getString(R.string.action_filter))

        val alertDialog = builder.show()

        dialogView.dialogApplyBtn.setOnClickListener {
            alertDialog.dismiss()

            if(!dialogView.filterLaunchYearEdit.text.isEmpty()) {
                currFilter.launchYear = dialogView.filterLaunchYearEdit.text.toString().toIntOrNull()
            } else {
                currFilter.launchYear = null
            }

            presenter?.setFilter(currFilter)
        }

        dialogView.dialogCancelBtn.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    override fun saveData(): Bundle? = presenter?.saveData()

    override fun showLaunches(launchesList: ArrayList<Launch>?) {
        if(launchesListAdapter.itemCount > 0) {
            launchesListAdapter.clearItems()
        }
        launchesListAdapter.addLaunchesToTheList(launchesList)
    }

    override fun showLaunchDetails(launch: Launch) {
        launchesFragmentListener.switchToLaunchDetailsFragment(launch)
    }

    interface LaunchesFragmentListener {
        fun switchToLaunchDetailsFragment(launch: Launch)
    }
}


