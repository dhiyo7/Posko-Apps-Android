package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.LogistikMasuk
import com.adit.poskoapp.models.PenerimaanLogistik

interface LogistikMasukActivityContract {
    interface LogistikMasukActivityView{
        fun showToast(message : String)
        fun attachLogistikMasukRecycler(logistik_masuk : List<LogistikMasuk>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface LogistikMasukPresenter{
        fun getLogistikMasuk(token: String)
        fun destroy()
    }
}