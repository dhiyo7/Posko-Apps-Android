package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Donatur

interface DonaturActivityContract {
    interface DonaturActivityView {
        fun showToast(message : String)
        fun attachDonaturRecycler(data_donatur : List<Donatur>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface DonaturActivityPresenter {
        fun infoDonatur()
        fun destroy()
    }
}
