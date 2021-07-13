package com.adit.poskoapp.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adit.poskoapp.*
import com.adit.poskoapp.adapters.BencanaHorizontalAdapter
import com.adit.poskoapp.contracts.BencanaActivityContract
import com.adit.poskoapp.databinding.FragmentBerandaBinding
import com.adit.poskoapp.models.Bencana
import com.adit.poskoapp.presenters.BencanaActivityPresenter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

class BerandaFragment : Fragment(), BencanaActivityContract.View {
    private var presenter : BencanaActivityContract.Interaction? = null
    private lateinit var adapterBencana : BencanaHorizontalAdapter

    private var _binding : FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        presenter = BencanaActivityPresenter(this)
        intentActivity()
        return binding.root
    }

    private fun intentActivity (){
        binding.posko.setOnClickListener {
            startActivity(Intent(activity, PoskoActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(activity, DonaturActivity::class.java))
        }

        binding.penerimaanLogistik.setOnClickListener {
            startActivity(Intent(activity, PenerimaanLogistikActivity::class.java))
        }
    }

    private fun infoBencana(){
        presenter?.allBencana()
    }

    override fun attachToRecycle(bencana: List<Bencana>) {
        binding.imageSlider.apply {
            adapterBencana = BencanaHorizontalAdapter(bencana, requireActivity())
            setSliderAdapter(adapterBencana)
            setIndicatorAnimation(IndicatorAnimationType.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            setIndicatorSelectedColor(Color.WHITE);
            setIndicatorUnselectedColor(Color.GRAY);
            setScrollTimeInSec(4); //set scroll delay in seconds :
            startAutoCycle();

        }
//        binding.rvBencanaHomeFragment.apply {
//            adapterBencana = BencanaHorizontalAdapter(bencana, requireActivity())
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = adapterBencana
//        }
    }

    override fun toast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun isLoading(state: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun notConnect(message: String?) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        infoBencana()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}