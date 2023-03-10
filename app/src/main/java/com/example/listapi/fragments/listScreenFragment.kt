package com.example.listapi.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapi.HeroApplication
import com.example.listapi.OnClickListener
import com.example.listapi.R
import com.example.listapi.adapter.UserAdapter
import com.example.listapi.database.HeroEntity
import com.example.listapi.databinding.FragmentListScreenBinding
import com.example.listapi.model.DataAll
import com.example.listapi.model.DataAllItem
import com.example.listapi.viewmodel.viewModelData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_screen.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Array.get

class listScreenFragment : Fragment(), OnClickListener{
    private lateinit var binding: FragmentListScreenBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var searchView: SearchView
    private var listMoment = mutableListOf<DataAllItem>()
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private val viewModel: viewModelData by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        binding = FragmentListScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        linearLayoutManager = LinearLayoutManager(context)
        viewModel.DATA.observe(viewLifecycleOwner) {
            listMoment = it
            recyclerViewMaker(it)
        }
        binding.botonVerMas.setOnClickListener {
            viewModel.moreData()
            userAdapter.updateList(viewModel.NextData[viewModel.NextData.lastIndex])
            binding.botonVerMas.visibility = View.GONE
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    binding.botonVerMas.visibility = View.VISIBLE
                    binding.botonVerMas.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_ver_mas))
                    binding.botonVerMas.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in))
                }
                else{
                    binding.botonVerMas.animate().alpha(1f).duration = 500
                    binding.botonVerMas.visibility = View.GONE
                }
            }
        })
    }
    private fun recyclerViewMaker (data: MutableList<DataAllItem>) {
        userAdapter = UserAdapter(data, this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }
    private fun filterList (query : String?) {
        if (query != null && query != " "){
            val filteredList = mutableListOf<DataAllItem>()
            for (i in viewModel.llistaCompleta){
                if (i.name.lowercase().contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isNotEmpty()){
                userAdapter.updateList(filteredList)
            }
        }
    }

    override fun onClick(user: DataAllItem) {
        viewModel.selectUser(user.id)
        val action = listScreenFragmentDirections.actionDashboardFragmentToDetailFragment()
        view?.findNavController()?.navigate(action)
    }
    override fun onFavoriteClick(user: HeroEntity) {
    }


}