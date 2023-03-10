package com.example.listapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listapi.HeroApplication
import com.example.listapi.Repository
import com.example.listapi.database.HeroEntity
import com.example.listapi.model.DataAll
import com.example.listapi.model.DataAllItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class viewModelData: ViewModel() {
    var DATA = MutableLiveData<MutableList<DataAllItem>>()
    var DataDetailed = MutableLiveData<DataAllItem>()
    var selectedUser = MutableLiveData<Int>()
    var Favorites = MutableLiveData<List<HeroEntity>>()
    var NextData = mutableListOf<MutableList<DataAllItem>>()
    var FilteredList = MutableLiveData<MutableList<DataAllItem>>()
    var index = 24
    var llistaCompleta = DataAll()
    init {
        fetchData("all.json")
        getFavorites()
    }
    fun fetchData(url : String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = Repository().getTags(url)
            withContext(Dispatchers.Main) {
                llistaCompleta = response.body()!!
                val listaTempo = mutableListOf<DataAllItem>()
                for (i in 0.. index){
                    listaTempo.add(llistaCompleta[i])
                }
                DATA.postValue(listaTempo)
            }
        }
    }
    private fun  getNextData (){
        val listaTempo = mutableListOf<DataAllItem>()
        for (i in 0.. index){
            listaTempo.add(llistaCompleta[i])
        }
        NextData.add(listaTempo)
    }
    fun moreData () {
        index +=20
        getNextData()
    }
    fun getHero(url : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = Repository().getHero(url)
            withContext(Dispatchers.Main){
                DataDetailed.postValue(response.body())
            }
        }
    }
    fun selectUser (user: Int) {
        selectedUser.postValue(user)
    }
    fun getFavorites () {
        var heroList = mutableListOf<HeroEntity>()
        CoroutineScope(Dispatchers.IO).launch {
            heroList = HeroApplication.database.HeroDao().getAllHeroes()
            Favorites.postValue(heroList)
        }
    }
    fun setHeroesAlignment (typeHero : Boolean) {
        val listaDeHeroes = mutableListOf<DataAllItem>()
            for (i in llistaCompleta){
                if (i.biography.alignment == "good" && typeHero) listaDeHeroes.add(i)
                else if (i.biography.alignment == "bad" && !typeHero) listaDeHeroes.add(i)
            }
        FilteredList.postValue(listaDeHeroes)
    }
    fun setHeroesPublisher (publicado : String) {
        val listaDeHeroes = mutableListOf<DataAllItem>()
        for (i in llistaCompleta){
            if (i.biography.publisher == "Marvel Comics" && publicado == "MARVEL") listaDeHeroes.add(i)
            else if (i.biography.publisher == "DC Comics" && publicado == "DC") listaDeHeroes.add(i)
        }
        FilteredList.postValue(listaDeHeroes)
    }
}