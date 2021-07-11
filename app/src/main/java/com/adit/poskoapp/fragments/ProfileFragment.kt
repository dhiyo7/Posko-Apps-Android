package com.adit.poskoapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adit.poskoapp.LoginActivity
import com.adit.poskoapp.R
import com.adit.poskoapp.databinding.FragmentProfileBinding
import com.adit.poskoapp.utils.PoskoUtils
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        displayButton()
        buttonClick()
        return binding.root
    }

    private fun displayButton() {
        val token = PoskoUtils.getToken(requireActivity())
        if (token == null || token.equals("UNDEFINED")){
            binding.btnLogout.apply {
                visibility = View.GONE
            }
        }else{
            binding.btnLogin.apply {
                visibility = View.GONE
            }
        }
    }

    private fun buttonClick() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
        binding.btnLogout.setOnClickListener {
            PoskoUtils.clearToken(requireActivity())
            checkAuthenticated()
        }
    }

    private fun checkAuthenticated(){
        val token = PoskoUtils.getToken(requireActivity())
        if(token == null || token.equals("UNDEFINED")){
            startActivity(Intent(requireActivity(), LoginActivity::class.java).also { activity?.finish() })
        }
    }
}