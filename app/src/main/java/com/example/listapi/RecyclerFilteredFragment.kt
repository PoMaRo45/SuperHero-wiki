package com.example.listapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapi.adapter.UserAdapter
import com.example.listapi.database.HeroEntity
import com.example.listapi.databinding.FragmentRecyclerFilteredBinding
import com.example.listapi.fragments.FilterListDirections
import com.example.listapi.model.DataAllItem
import com.example.listapi.viewmodel.viewModelData

class RecyclerFilteredFragment : Fragment(), OnClickListener {

    lateinit var binding: FragmentRecyclerFilteredBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private val viewModel : viewModelData by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecyclerFilteredBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context)
        viewModel.FilteredList.observe(viewLifecycleOwner){
            recyclerViewMaker(it)
        }
    }
    private fun recyclerViewMaker (data: MutableList<DataAllItem>) {
        userAdapter = UserAdapter(data, this)
        binding.recyclerView.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    override fun onClick(user: DataAllItem) {
        viewModel.selectUser(user.id)
        val action = RecyclerFilteredFragmentDirections.actionRecyclerFilteredFragmentToDetailFragment()
        requireView().findNavController().navigate(action)
    }

    override fun onFavoriteClick(user: HeroEntity) {
    }

}