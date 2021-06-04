package com.adit.poskoapp.contracts

import com.adit.poskoapp.models.Bencana

interface BencanaActivityContract {
    interface View {
        fun attachToRecycle(bencana: List<Bencana>)
        fun toast(message: String?)
        fun isLoading(state: Boolean?)
        fun notConnect(message: String?)
    }

    interface ViewCreate {
        fun success(message: String?)
        fun toast(message: String?)
        fun isLoading(state: Boolean?)
    }

    interface ViewEdit {
        fun success(message: String?)
        fun toast(message: String?)
        fun isLoading(state: Boolean)
    }

    interface ViewDelete {
        fun success(message: String?)
        fun toast(message: String?)
        fun isLoading(state: Boolean)
    }

    interface Interaction {
        fun allBencana()
        fun destroy()
    }

    interface InteractionPost {
        fun validate(name: String, location: String, description: String): Boolean
        fun save(token: String, name: String, location: String, description: String)
        fun destroy()
    }

    interface InteractionUpdate {
        fun validate(name: String, location: String, description: String): Boolean
        fun update(id: Int, token: String, name: String, location: String, description: String)
        fun destroy()
    }

    interface InteractionDelete {
        fun delete(id: Int, token: String)
        fun destroy()
    }

}