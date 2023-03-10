package com.example.listapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapi.HeroApplication
import com.example.listapi.OnClickListener
import com.example.listapi.adapter.FavoriteAdapter
import com.example.listapi.adapter.UserAdapter
import com.example.listapi.database.HeroEntity
import com.example.listapi.databinding.FragmentFavoritosBinding
import com.example.listapi.databinding.FragmentListScreenBinding
import com.example.listapi.model.DataAll
import com.example.listapi.model.DataAllItem
import com.example.listapi.viewmodel.viewModelData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favoritos.*
import kotlinx.android.synthetic.main.fragment_list_screen.*
import kotlinx.coroutines.*

class FavoritosFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentFavoritosBinding
    private lateinit var userAdapter: FavoriteAdapter
    private lateinit var searchView: SearchView
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private var listaFavoritos = mutableListOf<HeroEntity>()
    private val viewModel: viewModelData by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritosBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
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
        viewModel.Favorites.observe(viewLifecycleOwner) {
            listaFavoritos = it.toMutableList()
            recyclerViewMaker(it)
        }

    }
    private fun recyclerViewMaker (data: List<HeroEntity>) {
        userAdapter = FavoriteAdapter(data, this)
        binding.recyclerViewFavoritos.apply {
            setHasFixedSize(true) //Optimitza el rendiment de l’app
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }
    private fun filterList (query : String?) {
        if (query != null){
            val filteredList = mutableListOf<HeroEntity>()
            for (i in listaFavoritos){
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
    }
    override fun onFavoriteClick(user: HeroEntity) {
        viewModel.selectUser(user.id.toInt())
        val action = FavoritosFragmentDirections.actionFavouritesFragmentToDetailFragment()
        view?.findNavController()?.navigate(action)
    }
}
