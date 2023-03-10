package com.example.listapi.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listapi.HeroApplication
import com.example.listapi.R
import com.example.listapi.Repository
import com.example.listapi.database.HeroEntity
import com.example.listapi.databinding.FragmentDetailBinding
import com.example.listapi.databinding.FragmentFavoritosBinding
import com.example.listapi.model.DataAllItem
import com.example.listapi.viewmodel.viewModelData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    private val viewModel: viewModelData by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var heroInfo : DataAllItem
        viewModel.selectedUser.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = Repository().getHero("id/$it.json")
                withContext(Dispatchers.Main){
                    val it = response.body()!!
                    CoroutineScope(Dispatchers.IO).launch {
                        val favouriteHero = HeroApplication.database.HeroDao().getAllHeroes()
                        withContext(Dispatchers.Main){
                            for ( i in favouriteHero){
                                if (i.id.toInt() == heroInfo.id) {
                                    binding.botonFavorito.setBackgroundResource(R.drawable.corazon_relleno)
                                    break
                                }
                            }
                        }
                    }
                    heroInfo = it
                    binding.nombre.text = it.name
                    Glide.with(requireContext())
                        .load(it.images.lg)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.imageHero)
                    binding.tvGender.text = "Genero: ${it.appearance.gender}"
                    binding.tvRace.text = "Raza: ${it.appearance.race}"
                    if (it.appearance.height[1] != "0 cm") binding.tvAltura.text = "Altura: ${it.appearance.height[1]}"
                    else binding.tvAltura.text = "Altura: ${it.appearance.height[0]}"
                    if (it.appearance.weight[1] != "0 kg") binding.tvPeso.text = "Peso: ${it.appearance.weight[1]}"
                    else binding.tvPeso.text = "Peso: ${it.appearance.weight[0]}"
                    object : CountDownTimer(2000,10) {
                        override fun onTick(millisUntilFinished: Long) {
                            if (binding.pbCombat.progress + 1 <= it.powerstats.combat) binding.pbCombat.progress +=1
                            if (binding.pbAguante.progress + 1 <= it.powerstats.durability) binding.pbAguante.progress +=1
                            if (binding.pbPoder.progress + 1 <= it.powerstats.power) binding.pbPoder.progress +=1
                            if (binding.pbVelocidad.progress + 1 <= it.powerstats.speed) binding.pbVelocidad.progress +=1
                            if (binding.pbFuerza.progress + 1 <= it.powerstats.strength) binding.pbFuerza.progress +=1
                            if (binding.pbInteligencia.progress + 1 <= it.powerstats.intelligence) binding.pbInteligencia.progress +=1
                        }
                        override fun onFinish() {
                            binding.pbCombat.progress = it.powerstats.combat
                            binding.pbAguante.progress = it.powerstats.durability
                            binding.pbPoder.progress = it.powerstats.power
                            binding.pbVelocidad.progress = it.powerstats.speed
                            binding.pbFuerza.progress = it.powerstats.strength
                            binding.pbInteligencia.progress = it.powerstats.intelligence
                        }
                    }.start()

                    val stringAppear = "<b>" + "Primera aparición: " + "</b> " + it.biography.firstAppearance
                    binding.firstApperence.text = Html.fromHtml(stringAppear)
                    val stringOccupation = "<b>" + "Trabajos: " + "</b> " + it.work.occupation
                    binding.occupations.text = Html.fromHtml(stringOccupation)
                    val stringAfiliations = "<b>" + "Grupos en los que ha estado: " + "</b> " + it.connections.groupAffiliation
                    binding.groupAffiliations.text = Html.fromHtml(stringAfiliations)
                }
            }
        }


        viewModel.DataDetailed.observe(viewLifecycleOwner) {

        }
        binding.botonFavorito.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val heroList = HeroApplication.database.HeroDao().getAllHeroes()
                withContext(Dispatchers.Main){
                    var favorite = false
                    if (heroList.isEmpty()){
                        CoroutineScope(Dispatchers.IO).launch {
                            HeroApplication.database.HeroDao().addFavorite(HeroEntity(heroInfo.id.toLong(),heroInfo.name,heroInfo.images.lg,true))
                            binding.botonFavorito.setBackgroundResource(R.drawable.corazon_relleno)
                        }
                    }
                    else{
                        for (i in heroList){
                            if (i.id == heroInfo.id.toLong()) {
                                favorite = true
                            }
                        }
                        if (!favorite){
                            CoroutineScope(Dispatchers.IO).launch {
                                HeroApplication.database.HeroDao().addFavorite(HeroEntity(heroInfo.id.toLong(),heroInfo.name,heroInfo.images.lg,true))
                                binding.botonFavorito.setBackgroundResource(R.drawable.corazon_relleno)
                            }
                        }
                        else {
                            CoroutineScope(Dispatchers.IO).launch {
                                HeroApplication.database.HeroDao().deleteFavorite(HeroEntity(heroInfo.id.toLong(),heroInfo.name,heroInfo.images.lg,true))
                                binding.botonFavorito.setBackgroundResource(R.drawable.corazon_vacio)
                            }
                        }
                    }
                }
            }
        }
    }

}