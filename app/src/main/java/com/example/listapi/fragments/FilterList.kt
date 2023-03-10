package com.example.listapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.listapi.R
import com.example.listapi.databinding.FragmentFilterListBinding
import com.example.listapi.databinding.FragmentListScreenBinding
import com.example.listapi.viewmodel.viewModelData

class FilterList : Fragment() {
    lateinit var binding: FragmentFilterListBinding
    private val viewModel : viewModelData by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentFilterListBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.personajesBuenos.setOnClickListener {
            viewModel.setHeroesAlignment(true)
            val action = FilterListDirections.actionFilterListToRecyclerFilteredFragment()
            view.findNavController().navigate(action)
        }
        binding.personajesMalos.setOnClickListener {
            viewModel.setHeroesAlignment(false)
            val action = FilterListDirections.actionFilterListToRecyclerFilteredFragment()
            view.findNavController().navigate(action)
        }
        binding.personajesDc.setOnClickListener {
            viewModel.setHeroesPublisher("DC")
            val action = FilterListDirections.actionFilterListToRecyclerFilteredFragment()
            view.findNavController().navigate(action)
        }
        binding.personajesDeMarvel.setOnClickListener {
            viewModel.setHeroesPublisher("MARVEL")
            val action = FilterListDirections.actionFilterListToRecyclerFilteredFragment()
            view.findNavController().navigate(action)
        }
    }


}