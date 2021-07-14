package com.adit.poskoapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adit.poskoapp.*
import com.adit.poskoapp.databinding.FragmentBerandaAdminBinding
import com.adit.poskoapp.databinding.FragmentBerandaPetugasBinding

class BerandaPetugasFragment : Fragment() {
    private lateinit var _binding : FragmentBerandaPetugasBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaPetugasBinding.inflate(inflater, container, false)
        intent()
        return binding.root
    }

    private fun intent() {
        binding.posko.setOnClickListener {
            startActivity(Intent(activity, PoskoActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(activity, DonaturActivity::class.java))
        }

        binding.penerimaanLogistik.setOnClickListener {
            startActivity(Intent(activity, PenerimaanLogistikActivity::class.java))
        }

        binding.kebutuhanLogistik.setOnClickListener {
            startActivity(Intent(activity, KebutuhanLogistikActivity::class.java))
        }
    }
}