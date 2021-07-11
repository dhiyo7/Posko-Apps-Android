package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.PenerimaanLogistik

interface PenerimaanLogisitkContract {
    interface PenerimaanLogistikActivityView{
        fun showToast(message : String)
        fun attachPenerimaanLogistikRecycler(data_penerimaan_logistik : List<PenerimaanLogistik>)
        fun showLoading()
        fun hideLoading()
        fun showDataEmpty()
        fun hideDataEmpty()
    }

    interface PenerimmaanLogistikPresenter{
        fun infoPenerimaanLogistik()
        fun destroy()
    }
}