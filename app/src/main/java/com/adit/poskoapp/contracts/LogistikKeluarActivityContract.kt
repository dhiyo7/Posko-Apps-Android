package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.LogistikKeluar

interface LogistikKeluarActivityContract {
    interface LogistikKeluarActivityView{
        fun showToast(message : String)
        fun attachLogistikKeluarRecycler(logistik_keluar : List<LogistikKeluar>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface LogistikKeluarPresenter{
        fun getLogistikKeluar(token: String)
        fun destroy()
    }
}