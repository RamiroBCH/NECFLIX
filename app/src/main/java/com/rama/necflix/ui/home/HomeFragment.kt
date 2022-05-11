package com.rama.necflix.ui.home

import android.icu.lang.UCharacter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rama.necflix.R
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.NowPlayingDB
import com.rama.necflix.databinding.FragmentHomeBinding
import com.rama.necflix.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), NowPlayingAdapter.OnNowPlayingClickListener {

    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val args: HomeFragmentArgs by navArgs()
    private var genresDB: List<GenresDB> = emptyList()
    var map = HashMap<String, List<String>>()
    var words :List<String> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //obtener datos de la cuenta ingresada
        val primaryKey:String = args.primaryKey
        //obtener el id de sesion de invitado
        val guestSessionId: String = args.sessionId
        //probar si se inicio sesion
        if( primaryKey == "no" || guestSessionId == "no") {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
//RECYCLERVIEW NOWPLAYING
        //obtener los datos nowplaying
        setPlayingRecyclerView()
        getDataNowPlaying()
//***************************************************************
//EXPANDABLELISTVIEW
        //obtener listas de generos
        getGenreApi()
        //clicklistener de los items
        binding.genero.setOnChildClickListener { genero, view, groupPosition, childPosition, l ->
            Toast.makeText(context, map[words[groupPosition]]?.get(childPosition), Toast.LENGTH_SHORT).show()
            true
        }
        //clicklistener del viewGroup que contiene los items
        var lastIndex = -1
        binding.genero.setOnGroupExpandListener {
            if (lastIndex != -1 && lastIndex != it){
                binding.genero.collapseGroup(lastIndex)
            }
            Log.d("tag", "onCreate: $it $lastIndex")
            lastIndex = it
        }
//*******************************************************

    }

    private fun setPlayingRecyclerView() {
        binding.recyclerNowplaying.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerNowplaying.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
    }

    private fun getDataNowPlaying() {
        return homeViewModel.getNowPlaying.observe(viewLifecycleOwner, Observer { list ->
            when(list){
                is Resource.Loading -> {
                    Toast.makeText(
                        context, "Cargando", Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Success -> {
                    //pasar los datos nowplaying a nowPlayingAdapter
                    binding.recyclerNowplaying.adapter = NowPlayingAdapter(requireContext(),list.data,this)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${list.exception}", Toast.LENGTH_LONG )
                        .show()
                }
            }
        })
    }

    //obtener lista de generos
    private fun getGenreApi() {
        return homeViewModel.getGenre.observe(viewLifecycleOwner, Observer {generos ->
            when(generos){
                is Resource.Loading -> {
                    Toast.makeText(
                        context, "Cargando", Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Success -> {
                    genresDB = generos.data
                    Toast.makeText(
                        context, "Datos Cargados", Toast.LENGTH_SHORT
                    ).show()
                    /*for(i in genresDB.indices){
                        Toast.makeText(context, generos.data[i].name, Toast.LENGTH_SHORT).show()
                    }*/
                    //configurar los objetos map y words con los datos obtenidos
                    setData(genresDB)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${generos.exception}", Toast.LENGTH_LONG )
                        .show()
                }
            }
        })
    }
    //pasar los datos al adapter del boton filtrar por generos
    private fun setData(genresDB: List<GenresDB>) {
        var list : List<String> = emptyList()
        for(i in genresDB.indices){
            list = list + genresDB[i].name
            //Toast.makeText(context, list[i], Toast.LENGTH_SHORT).show()
        }
        map["Generos"] = list
        words = listOf("Generos")
        //pasar los datos al adapter
        val adapterExpList = FilterByAdapter(genresDB,map,words)
        binding.genero.setAdapter(adapterExpList)
    }

    override fun onNowPlayingClickListener(item: NowPlayingDB, position: Int) {
        Toast.makeText(context,item.id.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}