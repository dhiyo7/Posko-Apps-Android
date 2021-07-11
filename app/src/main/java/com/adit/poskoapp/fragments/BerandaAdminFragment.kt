package com.adit.poskoapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.adit.poskoapp.BencanaActivity
import com.adit.poskoapp.DonaturActivity
import com.adit.poskoapp.PoskoActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.databinding.FragmentBerandaAdminBinding
import com.adit.poskoapp.databinding.FragmentBerandaBinding

class BerandaAdminFragment : Fragment() {

    private var _binding : FragmentBerandaAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaAdminBinding.inflate(inflater, container, false)
        intent()
        return binding.root
    }

    private fun intent(){
        binding.petugas.setOnClickListener {
            Toast.makeText(activity, "Under development", Toast.LENGTH_LONG).show()
        }

        binding.posko.setOnClickListener {
            startActivity(Intent(activity, PoskoActivity::class.java))
        }

        binding.bencana.setOnClickListener {
            startActivity(Intent(activity, BencanaActivity::class.java))
        }

        binding.donatur.setOnClickListener {
            startActivity(Intent(activity, DonaturActivity::class.java))
        }

        binding.distribusi.setOnClickListener {
            Toast.makeText(activity, "Under development", Toast.LENGTH_LONG).show()
        }
    }
}